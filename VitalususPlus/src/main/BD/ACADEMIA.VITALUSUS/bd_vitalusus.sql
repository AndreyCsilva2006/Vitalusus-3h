USE master IF EXISTS(select * from sys.databases where name='bd_vitalusus2h') 
DROP DATABASE bd_vitalusus2h
GO 
-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_vitalusus2h
GO
-- ACESSAR O BANCO DE DADOS
USE bd_vitalusus2h
GO

-- Tabela ChaveSeguranca
CREATE TABLE ChaveSeguranca(
	id					INT			IDENTITY(1231,1),
	chave				VARCHAR(50) 
	PRIMARY KEY(id)
)
GO
INSERT INTO ChaveSeguranca DEFAULT VALUES 
INSERT INTO ChaveSeguranca DEFAULT VALUES 
INSERT INTO ChaveSeguranca DEFAULT VALUES 
GO
-- Tabela Usuario
CREATE TABLE Usuario
( 
   id            INT			IDENTITY,
   nome          VARCHAR(100)	NOT NULL,
   email         VARCHAR(100)	UNIQUE NOT NULL,
   senha         VARCHAR(100)	NOT NULL,
   nivelAcesso   VARCHAR(10)    NULL, -- ADMIN ou USER
   foto			 VARBINARY(MAX) NULL,
   dataCadastro	 SMALLDATETIME	NOT NULL,
   statusUsuario VARCHAR(20)    NOT NULL, -- ATIVO ou INATIVO ou TROCAR_SENHA	
   tipoUsuario	 VARCHAR(15)	NOT NULL, -- ADMINISTRADOR ou ALUNO ou TREINADOR	
   chaveSeguranca_id INT		NOT NULL,
   nivelPrivacidade VARCHAR(50)NOT NULL, -- PUBLICO ou PRIVADO

   PRIMARY KEY (id),
   FOREIGN KEY (chaveSeguranca_id) REFERENCES ChaveSeguranca(id)
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, chaveSeguranca_id, nivelPrivacidade) 
VALUES(
	'Fulano fulanoide',
	'fulano@gmail.com',
	'sdfgh$$%#D',
	'USER',
	null,
	GETDATE(),
	'ATIVO',
	'ALUNO',
	1231,
	'PUBLICO'
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, chaveSeguranca_id, nivelPrivacidade) 
VALUES(
	'Seranilda de Assis',
	'sera@gmail.com',
	'sdfgh$$%#D',
	'USER',
	null,
	GETDATE(),
	'ATIVO',
	'TREINADOR',
	1232,
	'PUBLICO'
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, chaveSeguranca_id, nivelPrivacidade) 
VALUES(
	'Don Corleone',
	'corleoneDon@gmail.com',
	'sdfgh$$%#D',
	'ADMIN',
	null,
	GETDATE(),
	'ATIVO',
	'ADMINISTRADOR',
	1233,
	'PUBLICO'
)
GO
CREATE TABLE Denuncia
(
	id			INT				IDENTITY,
	mensagem	VARCHAR(MAX)	NOT NULL,
	usuario_id	INT				NOT NULL,
	usuarioDenunciado_id	INT	NOT NULL,
	dataDenuncia SMALLDATETIME  NOT NULL,
	
	PRIMARY KEY (id),
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (usuarioDenunciado_id)	REFERENCES Usuario(id)
)
GO
INSERT Denuncia(mensagem, usuario_id, usuarioDenunciado_id, dataDenuncia) 
VALUES(
	'Estou denunciando essa treinadora vagabunda que me mandou nudes do marido dela sem minha permissão!!',
	1,
	2,
	GETDATE()
)
GO
-- Tabela admin
CREATE TABLE Administrador
(
	id			 INT		    IDENTITY,
	usuario_id	 INT			NOT NULL,
	numeroUsuarios INT			NOT NULL,
	dataNasc	DATE			NOT NULL,

	FOREIGN KEY(usuario_id) REFERENCES Usuario(id),
	PRIMARY KEY (id),
)
GO
INSERT Administrador(usuario_id, numeroUsuarios, dataNasc) VALUES(3, 1, '1982-05-23')
GO
-- Tabela Aluno
CREATE TABLE Aluno
(
	id			INT					IDENTITY,
	dataNasc	DATE				NOT NULL,
	altura		DECIMAL(10,2)		NOT NULL,
	peso		DECIMAL(10,2)		NOT NULL,
	usuario_id	INT					NOT NULL,

	FOREIGN KEY(usuario_id) REFERENCES Usuario (id),
	PRIMARY KEY(id)
)
GO
INSERT Aluno(dataNasc, altura, peso, usuario_id)
VALUES(
	'1996-02-12',
	1.78,
	98.5,
	1
)
GO
-- Tabela Treinador
CREATE TABLE Treinador
(
	id	            INT			  IDENTITY,
	cref			VARCHAR(21)	  UNIQUE NOT NULL,
	dataNasc		DATE		  NOT NULL,
	usuario_id		INT			  NOT NULL,

	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	PRIMARY KEY (id)
)
GO
INSERT Treinador(cref, dataNasc, usuario_id)
VALUES(
	'324321-G/SP',
	'1998-02-27',
	2
)
GO

-- Tabela Canal
CREATE TABLE Canal(
	id				INT				IDENTITY,
	visualizacoes	INT				NULL,
	nome			VARCHAR(100)	NOT NULL,
	seguidores		BIGINT			NOT NULL,
	treinador_id	INT				NOT NULL,
	numeroVideos	INT				NOT NULL,
	bio				VARCHAR(255)	NULL,

	FOREIGN KEY (treinador_id) REFERENCES Treinador(id),
	PRIMARY KEY (id)
)
GO
INSERT Canal(visualizacoes, nome, seguidores, treinador_id, numeroVideos, bio) 
VALUES(
	3243254,
	'Paradas Musculat�rias', 1, 1, 1, 'é um canal muito bom' 
)

GO
-- Tabela Banco 
CREATE TABLE Banco(
	id				INT				IDENTITY,
	numeroCartao	VARCHAR(16)		NOT NULL,
	treinador_id	INT				NOT NULL,

	FOREIGN KEY (treinador_id) REFERENCES Treinador(id),
	PRIMARY KEY(id)
)
GO
INSERT Banco(numeroCartao, treinador_id)
VALUES('1232334523123', 1)
GO

-- Tabela Videoaula
CREATE TABLE Videoaula(
	id				INT				IDENTITY,
	descricao		VARCHAR(255)	NULL,
	titulo			VARCHAR(100)	NOT NULL,
	likes			INT				NULL,
	deslikes		INT				NULL,
	canal_id		INT				NULL,
	visualizacoes	BIGINT			NOT NULL,
	video			VARBINARY(MAX)	NULL, 
	thumbnail		VARBINARY(MAX)	NULL,
	dataPubli		SMALLDATETIME	NOT NULL,
	categoria		VARCHAR(100)	NOT NULL,
	tipoVideoaula	VARCHAR(100)	NOT NULL,

	FOREIGN KEY (canal_id) REFERENCES Canal(id),
	PRIMARY KEY(id)
)
GO
INSERT Videoaula(descricao, titulo, likes, deslikes, canal_id, visualizacoes, dataPubli, categoria, tipoVideoaula)
VALUES(
	'Um v�deo sobre como fazer belas flex�es',
	'Como Fazer Flex�es',
	1332,
	0,
	1, 
	123,
	GETDATE(),
	'Musculação',
	'Flexões'
)
GO

GO
-- Tabela Evolucao
CREATE TABLE Evolucao(
	id				INT				IDENTITY,
	imc				DECIMAL			NOT NULL,
	met_basal		DECIMAL(10,2)	NULL,
	peso_atual		DECIMAL(10,2)	NULL,
	altura_atual	DECIMAL(10,2)	NULL,
	aluno_id		INT				NOT NULL,

	PRIMARY KEY(id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id)
)

GO
INSERT Evolucao(imc, met_basal, peso_atual, altura_atual, aluno_id)
VALUES(
	31.09,
	1202,
	98.50,
	1.78,
	1
)
GO
-- Tabela Comentario
CREATE TABLE Comentario(
	id				INT				IDENTITY,
	texto			VARCHAR(255)	NOT NULL,
	aluno_id		INT				NOT NULL,
	videoaula_id	INT				NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id),
	FOREIGN KEY(videoaula_id) REFERENCES Videoaula(id)
)
GO
INSERT Comentario(texto, aluno_id, videoaula_id)
VALUES(
	'Uau, que aula daora! Segui as suas instru��es por 6 meses e agora eu t� sheipado!',
	1,
	1
)
GO
-- Tabela Aluno_segue_canal
CREATE TABLE Aluno_segue_canal(
	id				INT				IDENTITY,
	seguidor_id		INT				NOT NULL,
	canal_id		INT				NOT NULL,

	PRIMARY KEY(id),
	FOREIGN KEY(seguidor_id) REFERENCES Aluno(id),
	FOREIGN KEY(canal_id) REFERENCES Canal(id)
)
GO
INSERT Aluno_segue_canal(seguidor_id, canal_id)
VALUES(
	1,
	1
)
GO
-- Tabela Aluno_videoaula
CREATE TABLE Aluno_videoaula(
	id					INT				IDENTITY,
	aluno_id			INT				NOT NULL,
	videoaula_id		INT				NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id),
	FOREIGN KEY(videoaula_id) REFERENCES Videoaula(id)
)
GO

INSERT Aluno_videoaula(aluno_id, videoaula_id)
VALUES(
	1,
	1
)
GO
-- Tabela Admin_usuario
CREATE TABLE Admin_usuario(
	id					INT				IDENTITY,
	admin_id			INT				NOT NULL,
	usuario_id			INT				NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(admin_id) REFERENCES Administrador(id),
	FOREIGN KEY(usuario_id) REFERENCES Usuario(id)
)
GO

INSERT Admin_usuario(admin_id, usuario_id)
VALUES(
	1,
	1
)
GO

-- Tabela Likes
CREATE TABLE Likes(
	id					INT					IDENTITY,
	videoaula_id		INT					NOT NULL,
	aluno_id			INT					NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(videoaula_id) REFERENCES Videoaula(id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id)
)
GO

-- Tabela Deslikes
CREATE TABLE Deslikes(
	id					INT				IDENTITY,
	videoaula_id		INT				NOT NULL,
	aluno_id			INT				NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(videoaula_id) REFERENCES Videoaula(id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id)
)
GO



SELECT * FROM Usuario
SELECT * FROM Canal
SELECT * FROM Videoaula
SELECT * FROM Aluno
SELECT * FROM Banco
SELECT * FROM Administrador
SELECT * FROM Treinador
SELECT * FROM Evolucao
SELECT * FROM Comentario
SELECT * FROM Aluno_segue_canal
SELECT * FROM Aluno_videoaula
SELECT * FROM Admin_usuario
SELECT * FROM Deslikes
SELECT * FROM Likes
SELECT * FROM ChaveSeguranca

/*
UPDATE Usuario SET nome = 'Maria Joana' WHERE id = 1

DELETE FROM Admin_usuario WHERE id = 1
DELETE FROM Evolucao WHERE id = 1
DELETE FROM Aluno_segue_canal WHERE id = 1
DELETE FROM Aluno_videoaula WHERE id = 1
DELETE FROM Aluno WHERE id = 1
DELETE FROM Comentario WHERE usuario_id = 1
DELETE FROM Usuario WHERE id = 1

*/
