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
        const result = await sql.query`SELECT * FROM Usuario`;
        res.json(result.recordset);
    } catch (err) {
        res.status(500).send(err.message);
    }
});

app.post('/usuario', async (req, res) => {
    const { nome, email, senha, tipoUsuario } = req.body;
    try {
        const result = await sql.query`INSERT INTO Usuario (nome, email, senha, tipoUsuario, dataCadastro, statusUsuario, chaveSeguranca_id, nivelPrivacidade) VALUES (${nome}, ${email}, ${senha}, ${tipoUsuario}, GETDATE(), 'ATIVO', 1231, 'PUBLICO')`;
        res.status(201).json({ message: 'Usuário criado com sucesso!' });
    } catch (err) {
        res.status(500).send(err.message);
    }
});

app.listen(8080, () => {
    console.log('Servidor rodando na porta 8080');
});
