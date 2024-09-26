const express = require('express');
const bodyParser = require('body-parser');
const sql = require('mssql');

const app = express();
app.use(bodyParser.json());

const dbConfig = {
    user: 'sa',
    password: 'admin123',
    server: 'localhost',
    database: 'bd_vitalusus2h',
    options: {
        encrypt: true,
        trustServerCertificate: true,
    },
};

// Conecta ao banco de dados uma vez, reutilizando a conexão
sql.connect(dbConfig).then(pool => {
    if (pool.connected) {
        console.log('Conectado ao banco de dados');
    }

    // Rota para obter todos os usuários
    app.get('/usuarios', async (req, res) => {
        try {
            const result = await pool.request().query('SELECT * FROM Usuario');
            res.json(result.recordset);
        } catch (err) {
            console.error('Erro ao buscar usuários:', err.message);
            res.status(500).send(err.message);
        }
    });

    // Rota para obter treinadores
    app.get('/treinadores', async (req, res) => {
        try {
            const result = await pool.request().query('SELECT * FROM Treinador');
            res.json(result.recordset);
        } catch (err) {
            console.error('Erro ao buscar treinadores:', err.message);
            res.status(500).send(err.message);
        }
    });

    // Rota para obter todos os usuários filtrados por tipoUsuario
    app.get('/usuarios/tipo', async (req, res) => {
        const tipoUsuario = req.query.tipoUsuario;
        try {
            const result = await pool.request()
                .input('tipoUsuario', sql.VarChar, tipoUsuario)
                .query('SELECT * FROM Usuario WHERE tipoUsuario = @tipoUsuario');
            res.json(result.recordset);
        } catch (err) {
            console.error('Erro ao buscar usuários por tipo:', err.message);
            res.status(500).send(err.message);
        }
    });

    // Rota para obter todos os canais
    app.get('/canais', async (req, res) => {
        try {
            const result = await pool.request().query('SELECT * FROM Canal');
            res.json(result.recordset);
        } catch (err) {
            console.error('Erro ao buscar canais:', err.message);
            res.status(500).send(err.message);
        }
    });

    // Rota para buscar vídeos
    app.get('/search', async (req, res) => {
        const busca = req.query.q;
        try {
            const result = await pool.request()
                .input('busca', sql.VarChar, `%${busca}%`)
                .query(`
                    SELECT V.titulo, V.descricao, V.visualizacoes, V.dataPubli, C.nome AS nomeCanal
                    FROM Videoaula V
                    JOIN Canal C ON V.canal_id = C.id
                    WHERE V.titulo LIKE @busca
                    OR V.descricao LIKE @busca
                    OR V.categoria LIKE @busca
                    ORDER BY V.dataPubli DESC
                `);
            res.json(result.recordset);
        } catch (err) {
            console.error('Erro ao buscar vídeos:', err.message);
            res.status(500).send('Erro ao buscar vídeos');
        }
    });

    // Rota para criar um usuário
    app.post('/usuarios', async (req, res) => {
        const { nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, chaveSeguranca_id, nivelPrivacidade } = req.body;
        try {
            const result = await pool.request()
                .input('nome', sql.VarChar, nome)
                .input('email', sql.VarChar, email)
                .input('senha', sql.VarChar, senha)
                .input('nivelAcesso', sql.VarChar, nivelAcesso || 'ALUNO')
                .input('foto', sql.VarBinary, foto || null)
                .input('dataCadastro', sql.DateTime, dataCadastro || new Date())
                .input('statusUsuario', sql.VarChar, statusUsuario || 'ATIVO')
                .input('tipoUsuario', sql.VarChar, tipoUsuario)
                .input('chaveSeguranca_id', sql.Int, chaveSeguranca_id)
                .input('nivelPrivacidade', sql.VarChar, nivelPrivacidade)
                .query(`
                    INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, chaveSeguranca_id, nivelPrivacidade)
                    VALUES (@nome, @email, @senha, @nivelAcesso, @foto, @dataCadastro, @statusUsuario, @tipoUsuario, @chaveSeguranca_id, @nivelPrivacidade)
                `);
            res.status(201).json({ message: 'Usuário criado com sucesso!' });
        } catch (err) {
            console.error('Erro ao criar usuário:', err.message);
            res.status(500).send(err.message);
        }
    });

    app.listen(3030, () => {
        console.log('Servidor rodando na porta 3030');
    });
}).catch(err => {
    console.error('Erro ao conectar ao banco de dados:', err.message);
});
