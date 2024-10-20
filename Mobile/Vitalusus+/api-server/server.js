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

    app.get('/likes', async (req, res) => {
            try {
                const result = await pool.request().query('SELECT * FROM Likes');
                res.json(result.recordset);
            } catch (err) {
                console.error('Erro ao buscar tabela Likes:', err.message);
                res.status(500).send(err.message);
            }
    });

    app.get('/likes/:id', async (req, res) => {
            const likeId = req.params.id;
            try {
                const result = await pool.request()
                    .input('id', sql.Int, likeId)
                    .query('SELECT * FROM Likes WHERE id = @id');

                if (result.recordset.length > 0) {
                    res.json(result.recordset[0]);
                } else {
                    res.status(404).send('Like não encontrado');
                }
            } catch (err) {
                console.error('Erro ao buscar Like pelo ID:', err.message);
                res.status(500).send(err.message);
            }
        });

  app.get('/videos/com-detalhes', async (req, res) => {
      try {
          const result = await pool.request().query(`
              SELECT
                  V.id,
                  V.titulo,
                  V.descricao,
                  V.visualizacoes,
                  V.dataPubli,
                  V.thumbnail,
                  C.id AS canalId,
                  C.nome AS nomeCanal,
                  C.seguidores AS seguidoresCanal,
                  T.id AS treinadorId,
                  U.id AS usuarioId,
                  U.foto AS fotoUsuario,
                  U.nome AS nomeTreinador,
                  Coment.id AS comentarioId,
                  Coment.texto AS comentarioTexto,
                  Coment.aluno_id AS aluno_id,
                  Coment.videoaula_id AS videoaula_id,
                  Coment.dataPubli AS dataPubliComentario
              FROM Videoaula V
              JOIN Canal C ON V.canal_id = C.id
              JOIN Treinador T ON C.treinador_id = T.id
              JOIN Usuario U ON T.usuario_id = U.id
              JOIN Comentario Coment ON Coment.videoaula_id = V.id
          `);

          const videosComDetalhes = result.recordset.map(video => ({
              video: {
                  id: video.id,
                  titulo: video.titulo,
                  descricao: video.descricao,
                  visualizacoes: video.visualizacoes,
                  dataPubli: new Date(video.dataPubli).toISOString(), // Corrigindo o formato da data para ISO 8601
                  thumbnail: video.thumbnail ? Buffer.from(video.thumbnail).toString('base64') : null
              },
              canal: {
                  id: video.canalId,
                  nome: video.nomeCanal,
                  seguidores: video.seguidoresCanal
              },
              usuario: {
                  id: video.usuarioId,
                  foto: video.fotoUsuario ? Buffer.from(video.fotoUsuario).toString('base64') : null,
                  nome: video.nomeTreinador
              },
              treinador:{
                  id: video.treinadorId
              },
              comentario:{
                  id: video.comentarioId,
                  texto: video.comentarioTexto,
                  aluno_id: video.aluno_id,
                  videoaula_id: video.comentario_id,
                  dataPubli: video.dataPubliComentario
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

                // Mapeia os usuários para incluir a foto como base64
                const usuarios = result.recordset.map(usuario => {
                    if (usuario.foto) {
                          usuario.foto = Buffer.from(usuario.foto).toString('base64');
                    }
                    return usuario;
                });

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

        app.get('/videos/comentarios/:videoId', async (req, res) => {
            const id = req.params.id;
               try {
                   const result = await pool.request()
                       .input('id', sql.Int, id)
                       .query(`SELECT
                          V.id,
                          V.titulo,
                          V.descricao,
                          V.visualizacoes,
                          V.dataPubli,
                          V.video,
                          Coment.id AS comentarioId,
                          Coment.texto AS comentarioTexto,
                          Coment.aluno_id AS aluno_id,
                          Coment.videoaula_id AS videoaula_id,
                          Coment.dataPubli AS dataPubliComentario
                       FROM Videoaula V
                       JOIN Comentario Coment ON Coment.videoaula_id = V.id
                       WHERE V.id = @id
                   `);

                   // Verifica se o resultado contém algum vídeo
                   if (result.recordset.length > 0) {
                       const video = result.recordset[0]; // Pega o primeiro vídeo
                       const videoComDetalhes = {

                               id: video.id,
                               titulo: video.titulo,
                               descricao: video.descricao,
                               visualizacoes: video.visualizacoes,
                               dataPubli: new Date(video.dataPubli).toISOString(), // Corrigindo o formato da data para ISO 8601
                               video: video.video ? Buffer.from(video.video).toString('base64') : null,
                               ComentarioId: video.comentarioId,
                               comentarioTexto: video.comentarioTexto,
                               aluno_id: video.aluno_id,
                               videoaula_id: video.comentario_id,
                               dataPubliComentario: video.dataPubliComentario
                       };

                       res.json(videoComDetalhes); // Retorna o objeto em vez de um array
                   } else {
                       res.status(404).json({ message: "Vídeo não encontrado." });
                   }

               } catch (err) {
                   console.error('Erro ao buscar Video pelo ID:', err.message);
                   res.status(500).send(err.message);
               }
        });

        app.get('/videos/:id', async (req, res) => {
            const id = req.params.id;
            try {
                const result = await pool.request()
                    .input('id', sql.Int, id)
                    .query(`SELECT
                       V.id,
                       V.titulo,
                       V.descricao,
                       V.visualizacoes,
                       V.dataPubli,
                       V.video,
                       Coment.id AS comentarioId,
                       Coment.texto AS comentarioTexto,
                       Coment.aluno_id AS aluno_id,
                       Coment.videoaula_id AS videoaula_id,
                       Coment.dataPubli AS dataPubliComentario
                    FROM Videoaula V
                    JOIN Comentario Coment ON Coment.videoaula_id = V.id
                    WHERE V.id = @id
                `);

                // Verifica se o resultado contém algum vídeo
                if (result.recordset.length > 0) {
                    const video = result.recordset[0]; // Pega o primeiro vídeo
                    const videoComDetalhes = {

                            id: video.id,
                            titulo: video.titulo,
                            descricao: video.descricao,
                            visualizacoes: video.visualizacoes,
                            dataPubli: new Date(video.dataPubli).toISOString(), // Corrigindo o formato da data para ISO 8601
                            video: video.video ? Buffer.from(video.video).toString('base64') : null,
                            ComentarioId: video.comentarioId,
                            comentarioTexto: video.comentarioTexto,
                            aluno_id: video.aluno_id,
                            videoaula_id: video.comentario_id,
                            dataPubliComentario: video.dataPubliComentario
                    };

                    res.json(videoComDetalhes); // Retorna o objeto em vez de um array
                } else {
                    res.status(404).json({ message: "Vídeo não encontrado." });
                }

            } catch (err) {
                console.error('Erro ao buscar Video pelo ID:', err.message);
                res.status(500).send(err.message);
            }
        });


           // Rota para obter todos os alunos
    app.get('/alunos', async (req, res) => {
        try {
            const result = await pool.request()
                .input('tipoUsuario', sql.VarChar, 'ALUNO')
                .query(`
                    SELECT A.altura, A.peso, A.usuario_id, A.sexo, U.nome, U.email, U.senha
                    FROM Aluno A
                    JOIN Usuario U ON A.usuario_id = U.id
                    WHERE U.tipoUsuario = @tipoUsuario
                `);

            // Mapeia os resultados para retornar uma lista de alunos
            const alunos = result.recordset.map(aluno => ({
                altura: aluno.altura,
                peso: aluno.peso,
                usuario_id: aluno.usuario_id,
                sexo: aluno.sexo,
                nome: aluno.nome,
                email: aluno.email,
                senha: aluno.senha
            }));

            res.json(alunos);
        } catch (err) {
            console.error('Erro ao buscar alunos:', err.message);
            res.status(500).send('Erro ao buscar alunos');
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
            const { nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, sexo } = req.body;

            try {
                // Validação dos campos obrigatórios
                if (!nome || !email || !senha || !dataNasc || !sexo) {
                    return res.status(400).json({ error: 'Nome, email, senha, data de nascimento e sexo são obrigatórios.' });
                }

                // Calculando a idade no servidor
                const birthDate = new Date(dataNasc);
                const currentDate = new Date();
                let idade = currentDate.getFullYear() - birthDate.getFullYear();
                const monthDifference = currentDate.getMonth() - birthDate.getMonth();
                if (monthDifference < 0 || (monthDifference === 0 && currentDate.getDate() < birthDate.getDate())) {
                    idade--; // Ajuste se ainda não fez aniversário neste ano
                }

                if (idade <= 0) {
                    return res.status(400).json({ error: 'A idade calculada é inválida.' });
                }

                // Inserção do usuário
                const result = await pool.request()
                    .input('nome', sql.VarChar, nome)
                    .input('email', sql.VarChar, email)
                    .input('senha', sql.VarChar, senha)
                    .input('nivelAcesso', sql.VarChar, nivelAcesso || 'USER')
                    .input('foto', sql.VarBinary, foto || null)
                    .input('dataCadastro', sql.DateTime, dataCadastro || new Date())
                    .input('statusUsuario', sql.VarChar, statusUsuario || 'ATIVO')
                    .input('tipoUsuario', sql.VarChar, 'ALUNO')
                    .input('nivelPrivacidade', sql.VarChar, nivelPrivacidade || 'PUBLICO')
                    .input('dataNasc', sql.Date, dataNasc)
                    .input('idade', sql.Int, idade)
                    .input('sexo', sql.VarChar, sexo)
                    .query(`
                        INSERT INTO Usuario (nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario, tipoUsuario, nivelPrivacidade, dataNasc, idade, sexo)
                        OUTPUT INSERTED.id
                        VALUES (@nome, @email, @senha, @nivelAcesso, @foto, @dataCadastro, @statusUsuario, @tipoUsuario, @nivelPrivacidade, @dataNasc, @idade, @sexo);
                    `);

                const usuarioId = result.recordset[0].id; // Obtendo o ID do usuário inserido

                // Inserindo o aluno associado ao usuário
                await pool.request()
                    .input('usuario_id', sql.Int, usuarioId)
                    .query(`INSERT INTO Aluno (usuario_id) VALUES (@usuario_id);`);

                // Resposta de sucesso
                res.status(201).json({ message: 'Usuário e Aluno criado com sucesso!' });

            } catch (err) {
                console.error('Erro ao criar usuário e Aluno:', err.message);
                res.status(500).json({ error: 'Erro ao criar usuário e Aluno.' });
            }
        });

        app.put('/usuarios/:id', async (req, res) => {
            const usuarioId = req.params.id;
            const { nome, email, senha, foto, statusUsuario, nivelPrivacidade, tipoUsuario } = req.body;

            try {
                // Atualiza os dados do usuário
                await pool.request()
                    .input('id', sql.Int, usuarioId)
                    .input('nome', sql.VarChar, nome)
                    .input('email', sql.VarChar, email)
                    .input('senha', sql.VarChar, senha)
                    .input('foto', sql.VarBinary, foto || null)
                    .input('statusUsuario', sql.VarChar, statusUsuario || 'ATIVO')
                    .input('nivelPrivacidade', sql.VarChar, nivelPrivacidade || 'PUBLICO')
                    .input('tipoUsuario', sql.VarChar, tipoUsuario || 'ALUNO')
                    .query(`
                        UPDATE Usuario
                        SET nome = @nome,
                            email = @email,
                            senha = @senha,
                            foto = @foto,
                            statusUsuario = @statusUsuario,
                            nivelPrivacidade = @nivelPrivacidade,
                            tipoUsuario = @tipoUsuario
                        WHERE id = @id
                    `);

                res.json({ message: 'Usuário atualizado com sucesso!' });
            } catch (err) {
                console.error('Erro ao atualizar usuário:', err.message);
                res.status(500).send(err.message);
            }
        });

        app.delete('/usuarios/:id', async (req, res) => {
            const usuarioId = req.params.id;

            try {
                await pool.request()
                    .input('id', sql.Int, usuarioId)
                    .query('DELETE FROM Usuario WHERE id = @id');

                res.json({ message: 'Usuário excluído com sucesso!' });
            } catch (err) {
                console.error('Erro ao excluir usuário:', err.message);
                res.status(500).send(err.message);
            }
        });



    app.listen(3030, () => {
        console.log('Servidor rodando na porta 3030');
    });
}).catch(err => {
    console.error('Erro ao conectar ao banco de dados:', err.message);
});
