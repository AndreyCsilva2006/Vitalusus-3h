const express = require('express');
const bodyParser = require('body-parser');
const sql = require('mssql');

const app = express();
app.use(bodyParser.json());

const dbConfig = {
    user: 'sa',
    password: '@ITB123456',
    server: 'localhost',
    database: 'bd_vitalusus2h',
    options: {
        encrypt: true,
        trustServerCertificate: true,
    },
};

sql.connect(dbConfig, err => {
    if (err) {
        console.error('Erro ao conectar ao banco de dados: ', err);
    } else {
        console.log('Conectado ao banco de dados');
    }
});

app.get('/usuarios', async (req, res) => {
    try {
        await sql.connect(dbConfig);
        const result = await sql.query`SELECT * FROM Usuario`;
        res.json(result.recordset);
    } catch (err) {
        console.error('Erro ao buscar usuarios:', err.message);
        res.status(500).send(err.message);
    } finally {
        // Fecha a conexão
        await sql.close();
    }
});

app.get('/canais', async (req, res) => {
    try {
        await sql.connect(dbConfig);
        const result = await sql.query`SELECT * FROM Canal`;
        res.json(result.recordset);
    } catch (err) {
        console.error('Erro ao buscar treinadores:', err.message);
        res.status(500).send(err.message);
    } finally {
        // Fecha a conexão
        await sql.close();
    }
});

// Rota para obter todos os usuários filtrados pelo tipoUsuario
app.get('/usuarios', async (req, res) => {
    // Captura o parâmetro tipoUsuario da query string
    const tipoUsuario = req.query.tipoUsuario;

    try {
        await sql.connect(dbConfig);
        const result = await sql.query`SELECT * FROM Usuario WHERE tipoUsuario = ${tipoUsuario}`;
        res.json(result.recordset);
    } catch (err) {
        // Tratar erro
        console.error('Erro ao buscar usuários com tipoUsuario Treinador:', err.message);
        res.status(500).send(err.message);
    } finally {
        // Fecha a conexão
        await sql.close();
    }
});

app.post('/usuarios', async (req, res) => {
    const { nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, chaveSeguranca_id, nivelPrivacidade } = req.body;
    try {
        await sql.connect(dbConfig);
        const result = await sql.query`INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, chaveSeguranca_id, nivelPrivacidade) VALUES (${nome}, ${email}, ${senha}, 'ALUNO', null, GETDATE(), 'ATIVO', ${tipoUsuario}, 1231, 'PUBLICO')`;
        res.status(201).json({ message: 'Usuário criado com sucesso!' });
    } catch (err) {
        res.status(500).send(err.message);
    } finally {
        // Fecha a conexão
        await sql.close();
    }
});

app.listen(3030, () => {
    console.log('Servidor rodando na porta 3030');
});
