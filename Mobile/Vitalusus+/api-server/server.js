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
    // Rota para obter todos os usuários
    app.get('/usuarios', async (req, res) => {
        try {
            const result = await pool.request().query('SELECT * FROM Usuario');

            // Mapeia os usuários para incluir a foto como base64
            const usuarios = result.recordset.map(usuario => {
                if (usuario.foto) {
                    usuario.foto = Buffer.from(usuario.foto).toString('base64');
                }
                return usuario;
            });

            res.json(usuarios);
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

    app.get('/videos', async (req, res) => {
            try {
                const result = await pool.request().query('SELECT * FROM Videoaula');
                res.json(result.recordset);
            } catch (err) {
                console.error('Erro ao buscar canais:', err.message);
                res.status(500).send(err.message);
            }
        });

  app.get('/videos/com-detalhes', async (req, res) => {
      try {
          const result = await pool.request().query(`
              SELECT
                  V.titulo,
                  V.descricao,
                  V.visualizacoes,
                  V.dataPubli,
                  C.nome AS nomeCanal,
                  U.foto AS fotoUsuario,
                  U.nome AS nomeTreinador
              FROM Videoaula V
              JOIN Canal C ON V.canal_id = C.id
              JOIN Treinador T ON C.treinador_id = T.id
              JOIN Usuario U ON T.usuario_id = U.id
          `);

          const videosComDetalhes = result.recordset.map(video => ({
              video: {
                  titulo: video.titulo,
                  descricao: video.descricao,
                  visualizacoes: video.visualizacoes,
                  dataPubli: new Date(video.dataPubli).toISOString() // Corrigindo o formato da data para ISO 8601
              },
              canal: {
                  nome: video.nomeCanal
              },
              usuario: {
                  foto: video.fotoUsuario ? Buffer.from(video.fotoUsuario).toString('base64') : null,
                  nome: video.nomeTreinador
              }
          }));

          // Certifique-se de que a resposta seja enviada apenas uma vez
          res.json(videosComDetalhes);
      } catch (err) {
          console.error('Erro ao buscar vídeos com detalhes:', err.message);

          // O envio de erro acontece apenas se `res.json` não tiver sido chamado antes
          if (!res.headersSent) {
              res.status(500).send('Erro ao buscar vídeos com detalhes');
          }
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

        app.post('/usuarios', async (req, res) => {
                const { nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc } = req.body;

                try {
                    // Verificar se a data de nascimento foi enviada
                    if (!dataNasc) {
                        return res.status(400).json({ error: 'A data de nascimento é obrigatória.' });
                    }

                    // Verificar se a idade foi calculada no cliente
                    let idade = req.body.idade;
                    if (!idade) {
                        // Se a idade não veio do cliente, calculá-la no servidor
                        const birthDate = new Date(dataNasc);
                        const currentDate = new Date();
                        idade = currentDate.getFullYear() - birthDate.getFullYear();
                        const monthDifference = currentDate.getMonth() - birthDate.getMonth();
                        if (monthDifference < 0 || (monthDifference === 0 && currentDate.getDate() < birthDate.getDate())) {
                            idade--; // Ajuste se ainda não fez aniversário neste ano
                        }
                    }

                    // Verifique se a idade não é nula
                    if (!idade) {
                        return res.status(400).json({ error: 'Não foi possível calcular a idade.' });
                    }

                    // Inserir o usuário com a data de nascimento e idade calculada
                    const result = await pool.request()
                        .input('nome', sql.VarChar, nome)
                        .input('email', sql.VarChar, email)
                        .input('senha', sql.VarChar, senha)
                        .input('nivelAcesso', sql.VarChar, nivelAcesso || 'ALUNO')
                        .input('foto', sql.VarBinary, foto || null)
                        .input('dataCadastro', sql.DateTime, dataCadastro || new Date())
                        .input('statusUsuario', sql.VarChar, statusUsuario || 'ATIVO')
                        .input('tipoUsuario', sql.VarChar, tipoUsuario)
                        .input('nivelPrivacidade', sql.VarChar, nivelPrivacidade)
                        .input('dataNasc', sql.Date, dataNasc)
                        .input('idade', sql.Int, idade)
                        .query(`
                            INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, idade)
                            OUTPUT INSERTED.id
                            VALUES (@nome, @email, @senha, @nivelAcesso, @foto, @dataCadastro, @statusUsuario, @tipoUsuario, @nivelPrivacidade, @dataNasc, @idade);
                        `);

                        const usuarioId = result.recordset[0].id; // Para pegar o ID gerado

                    // Inserir aluno, agora sem a data de nascimento
                    await pool.request()
                        .input('usuario_id', sql.Int, usuarioId)
                        .query(`
                            INSERT INTO Aluno ( usuario_id)
                            VALUES ( @usuario_id);
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
