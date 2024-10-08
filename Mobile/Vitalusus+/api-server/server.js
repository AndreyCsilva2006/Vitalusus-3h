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

    // Rota para buscar usuário pelo ID
    app.get('/usuarios/:id', async (req, res) => {
        const usuarioId = req.params.id;
        try {
            const result = await pool.request()
                .input('id', sql.Int, usuarioId)
                .query('SELECT * FROM Usuario WHERE id = @id');

            if (result.recordset.length > 0) {
                res.json(result.recordset[0]);
            } else {
                res.status(404).send('Usuário não encontrado');
            }
        } catch (err) {
            console.error('Erro ao buscar usuário pelo ID:', err.message);
            res.status(500).send(err.message);
        }
    });

        // Rota para buscar Treinador pelo ID
        app.get('/treinadores/:id', async (req, res) => {
            const treinadorId = req.params.id;
            try {
                const result = await pool.request()
                    .input('id', sql.Int, treinadorId)
                    .query(`
                        SELECT T.*, U.id AS usuarioId
                        FROM Treinador T
                        JOIN Usuario U ON T.usuario_id = U.id
                        WHERE T.id = @id
                    `);

                if (result.recordset.length > 0) {
                    res.json(result.recordset[0]);
                } else {
                    res.status(404).send('Treinador não encontrado');
                }
            } catch (err) {
                console.error('Erro ao buscar Treinador pelo ID:', err.message);
                res.status(500).send(err.message);
            }
        });


        // Rota para buscar canal pelo ID
            app.get('/canais/:id', async (req, res) => {
                const canalId = req.params.id;
                try {
                    const result = await pool.request()
                        .input('id', sql.Int, canalId)
                        .query('SELECT * FROM Canal WHERE id = @id');

                    if (result.recordset.length > 0) {
                        res.json(result.recordset[0]);
                    } else {
                        res.status(404).send('Canal não encontrado');
                    }
                } catch (err) {
                    console.error('Erro ao buscar canal pelo ID:', err.message);
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

    // Exemplo de endpoint para criar uma chave de segurança
    app.post('/chaveSeguranca', (req, res) => {
        const query = 'INSERT INTO ChaveSeguranca DEFAULT VALUES; SELECT SCOPE_IDENTITY() AS id;';

        // Executar a query
        db.query(query, (err, result) => {
            if (err) {
                return res.status(500).json({ message: 'Erro ao inserir chave de segurança', error: err });
            }

            // Retorna o id gerado da chave de segurança
            const idChaveSeguranca = result.recordset[0].id;
            return res.status(201).json({ id: idChaveSeguranca, chave: 'nova-chave' }); // Caso queira retornar uma chave gerada
        });
    });


    // Rota para criar um usuário
    app.post('/usuarios', async (req, res) => {
        const { nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, chaveSeguranca_id, nivelPrivacidade, dataNasc, altura, peso } = req.body;
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

                const usuarioId = result.recordset[0].id; // pra pegar o ID gerado

                // inserir aluno
                        await pool.request()
                            .input('dataNasc', sql.Date, dataNasc)
                            .input('altura', sql.Decimal, altura)
                            .input('peso', sql.Decimal, peso)
                            .input('usuario_id', sql.Int, usuarioId)
                            .query(`
                                INSERT INTO Aluno (dataNasc, altura, peso, usuario_id)
                                VALUES (@dataNasc, @altura, @peso, @usuario_id);
                            `);

            res.status(201).json({ message: 'Usuário e Aluno criado com sucesso!' });
        } catch (err) {
            console.error('Erro ao criar usuário e Aluno:', err.message);
            res.status(500).send(err.message);
        }
    });

    app.listen(3030, () => {
        console.log('Servidor rodando na porta 3030');
    });
}).catch(err => {
    console.error('Erro ao conectar ao banco de dados:', err.message);
});
