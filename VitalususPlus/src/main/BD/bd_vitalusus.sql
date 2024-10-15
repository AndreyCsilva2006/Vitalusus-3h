USE master IF EXISTS(select * from sys.databases where name='bd_vitalusus2h') 
DROP DATABASE bd_vitalusus2h
GO 
-- CRIAR UM BANCO DE DADOS
CREATE DATABASE bd_vitalusus2h
GO
-- ACESSAR O BANCO DE DADOS
USE bd_vitalusus2h

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
   chaveSeguranca  UNIQUEIDENTIFIER DEFAULT NEWID() NOT NULL,
   nivelPrivacidade VARCHAR(50)NOT NULL, -- PUBLICO ou PRIVADO
   dataNasc	DATE			NOT NULL,
   idade		INT				NOT NULL,
   genero		VARCHAR(30)		NOT NULL,

   PRIMARY KEY (id),
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, nivelPrivacidade, idade, dataNasc, genero) 
VALUES(
	'Fulano fulanoide',
	'fulano@gmail.com',
	'sdfgh$$%#D',
	'USER',
	null,
	GETDATE(),
	'ATIVO',
	'ALUNO',
	'PUBLICO',
	28,	
	'1996-02-12',
	'Masculino'
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, nivelPrivacidade, idade, dataNasc, genero) 
VALUES(
	'Seranilda de Assis',
	'sera@gmail.com',
	'sdfgh$$%#D',
	'USER',
	null,
	GETDATE(),
	'ATIVO',
	'TREINADOR',
	'PUBLICO',
	26,
	'1998-02-27',
	'Feminino'
)
GO
INSERT Usuario(nome, email, senha, nivelAcesso, foto, dataCadastro, statusUsuario,tipoUsuario, nivelPrivacidade, idade, dataNasc, genero) 
VALUES(
	'Don Corleone',
	'corleoneDon@gmail.com',
	'sdfgh$$%#D',
	'ADMIN',
	null,
	GETDATE(),
	'ATIVO',
	'ADMINISTRADOR',
	'PUBLICO',
	42, 
	'1982-05-23',
	'Masculino'
)
GO
CREATE TABLE Denuncia
(
	id			INT				IDENTITY,
	mensagem	VARCHAR(MAX)	NOT NULL,
	usuario_id	INT				NOT NULL,
	usuarioDenunciado_id	INT	NOT NULL,
	dataDenuncia SMALLDATETIME  NOT NULL,
	categoria	VARCHAR(100)	NOT NULL,
	
	PRIMARY KEY (id),
	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	FOREIGN KEY (usuarioDenunciado_id)	REFERENCES Usuario(id)
)
GO
INSERT Denuncia(mensagem, usuario_id, usuarioDenunciado_id, dataDenuncia, categoria) 
VALUES(
	'Estou denunciando essa treinadora vagabunda que me mandou nudes do marido dela sem minha permissão!!',
	1,
	2,
	GETDATE(),
	'Assédio sexual'
)
GO
-- Tabela admin
CREATE TABLE Administrador
(
	id			 INT		    IDENTITY,
	usuario_id	 INT			NOT NULL,
	numeroUsuarios INT			NOT NULL,

	FOREIGN KEY(usuario_id) REFERENCES Usuario(id),
	PRIMARY KEY (id),
)
GO
INSERT Administrador(usuario_id, numeroUsuarios) VALUES(3, 1)
GO
-- Tabela Aluno
CREATE TABLE Aluno
(
	id			INT					IDENTITY,
	altura		DECIMAL(10,2)		NULL,
	peso		DECIMAL(10,2)		NULL,
	usuario_id	INT					NOT NULL,

	FOREIGN KEY(usuario_id) REFERENCES Usuario (id),
	PRIMARY KEY(id)
)
GO
INSERT Aluno(altura, peso, usuario_id)
VALUES(
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
	usuario_id		INT			  NOT NULL,

	FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
	PRIMARY KEY (id)
)
GO
INSERT Treinador(cref, usuario_id)
VALUES(
	'324321-G/SP',
	2
)
GO

-- Tabela Canal
CREATE TABLE Canal(
	id				INT				IDENTITY,
	visualizacoes	BIGINT			NULL,
	nome			VARCHAR(100)	NOT NULL,
	seguidores		BIGINT			NOT NULL,
	treinador_id	INT				NOT NULL,
	numeroVideos	INT				NOT NULL,
	bio				VARCHAR(MAX)	NULL,

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
-- Tabela Patrocinador
CREATE TABLE Patrocinador(
	id					INT				IDENTITY,
	nome				VARCHAR(255)	NOT NULL,
	link				VARCHAR(MAX)	NOT NULL,
	foto				VARBINARY(MAX)	NULL,
	statusPatrocinador	VARCHAR(50)		NOT NULL,

	PRIMARY KEY(id)
)
GO
INSERT Patrocinador(nome, link, statusPatrocinador) VALUES('Kikos Fitness', 'https://www.kikos.com.br/', 'ATIVO' )
GO
-- Tabela Equipamento
CREATE TABLE Equipamento(
	id					INT				IDENTITY,
	nome				VARCHAR(255)	NOT NULL,
	link				VARCHAR(MAX)	NOT NULL,
	patrocinador_id		INT				NULL,
	statusEquipamento	VARCHAR(100)	NOT NULL,
	
	PRIMARY KEY(id),
	FOREIGN KEY(patrocinador_id) REFERENCES Patrocinador(id)
)
GO
INSERT Equipamento (nome, link, statusEquipamento) VALUES('Nenhuma das opções', 'null', 'ATIVO')
GO
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Bicicleta Ergométrica Kikos KR9.1 Eletromagnética',
'https://www.kikos.com.br/bicicleta-ergometrica-kikos-kr9-1-eletromagnetica.html',
1,
'ATIVO'
)
GO
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Esteira Ergométrica Kikos E800Ix 2.3 HP 12 Km/H',
'https://www.kikos.com.br/esteira-ergometrica-kikos-e800ix-2-3-hp-12km-h.html',
1,
'ATIVO'
)
GO
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Plataforma Vibratória Kikos P201Ix',
'https://www.kikos.com.br/plataforma-vibratoria-kikos-p201ix.html',
1,
'ATIVO'
)
GO
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Estação De Musculação Kikos Gx2I Torre 65kg',
'https://www.kikos.com.br/estac-o-de-musculac-o-kikos-gx2i-torre-65kg.html',
1,
'ATIVO'
)
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Colchonete Dobrável Kikos',
'https://www.kikos.com.br/colchonete-dobravel-md9013a-kikos.html',
1,
'ATIVO'
)
GO
INSERT Equipamento(nome, link, patrocinador_id, statusEquipamento) VALUES(
'Roda De Exercícios Abdominais Kikos',
'https://www.kikos.com.br/roda-de-exercicios-abdominais-kikos.html',
1,
'ATIVO'
)
GO
GO
-- Tabela Videoaula
CREATE TABLE Videoaula(
	id				INT				IDENTITY,
	descricao		VARCHAR(MAX)	NULL,
	titulo			VARCHAR(100)	NOT NULL,
	likes			INT				NULL,
	deslikes		INT				NULL,
	canal_id		INT				NULL,
	visualizacoes	BIGINT			NOT NULL,
	video			VARBINARY(MAX)	NULL, 
	thumbnail		VARBINARY(MAX)	NULL,
	dataPubli		SMALLDATETIME	NOT NULL,
	categoria		VARCHAR(100)	NOT NULL,
	tags			VARCHAR(MAX)	NOT NULL,
	equipamento_id	INT				NOT NULL,
	statusVideo		VARCHAR(50)		NOT NULL, --ATIVO ou BANIDO
	privacidadeVideo	VARCHAR(50)		NOT NULL, --PÚBLICO ou PRIVADO

	FOREIGN KEY (canal_id) REFERENCES Canal(id),
	FOREIGN KEY(equipamento_id) REFERENCES Equipamento(id),
	PRIMARY KEY(id)
)
GO
INSERT Videoaula(descricao, titulo, likes, deslikes, canal_id, visualizacoes, dataPubli, categoria, tags, equipamento_id, statusVideo, privacidadeVideo)
VALUES(
	'Um vídeo sobre como fazer belas flexões',
	'Como Fazer Flexões',
	1332,
	0,
	1, 
	123,
	GETDATE(),
	'Musculação',
	'Flexões',
	1,
	'ATIVO',
	'PÚBLICO'
)
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
	dataPubli		SMALLDATETIME	NOT NULL,

	PRIMARY KEY (id),
	FOREIGN KEY(aluno_id) REFERENCES Aluno(id),
	FOREIGN KEY(videoaula_id) REFERENCES Videoaula(id)
)
GO
INSERT Comentario(texto, aluno_id, videoaula_id, dataPubli)
VALUES(
	'Uau, que aula daora! Segui as suas instru��es por 6 meses e agora eu t� sheipado!',
	1,
	1,
	GETDATE()
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
SELECT * FROM Equipamento
SELECT * FROM Comentario
SELECT * FROM Aluno_segue_canal
SELECT * FROM Aluno_videoaula
SELECT * FROM Admin_usuario
SELECT * FROM Deslikes
SELECT * FROM Likes
SELECT * FROM Denuncia
SELECT * FROM Patrocinador

/*
UPDATE Usuario SET nome = 'Maria Joana' WHERE id = 1

DELETE FROM Admin_usuario WHERE id = 1
DELETE FROM Equipamento WHERE id = 1
DELETE FROM Aluno_segue_canal WHERE id = 1
DELETE FROM Aluno_videoaula WHERE id = 1
DELETE FROM Aluno WHERE id = 1
DELETE FROM Comentario WHERE usuario_id = 1
DELETE FROM Usuario WHERE id = 1

*/
