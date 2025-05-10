<h1>ADMIN</h1>

<h2>ATRIBUTOS</h2>
<p><strong>id</strong> (é um long que representa o número de identificação do administrador)</p>
<p><strong>usuario</strong> (é o objeto Usuario que está ligado ao objeto Admin)</p>
<p><strong>listaUsuarios</strong> (é a lista de usuários que o administrador administra)</p>
<p><strong>numeroUsuarios</strong> (é um int que conta a quantidade de usuários que administrador administra)</p>
<p><strong>dataNasc</strong> (é um Date que representa a data de nascimento do administrador)</p>

###

<h2>ENDPOINTS</h2>
<h3>FIND ALL (GET)</h3>
<a>http://localhost:8080/vitalusus/admin/findAll</a>

<h3>FIND BY ID (GET)</h3>
<a>http://localhost:8080/vitalusus/admin/findById/{id}</a>

<h3>FIND BY ID (POST)</h3>
<a>http://localhost:8080/vitalusus/admin/findById/</a>

<h3>CRIAR UM NOVO ADMINISTRADOR E SUA RESPECTIVA CONTA DE USUÁRIO (POST)</h3>
<a>http://localhost:8080/vitalusus/admin/post</a>

<h3>ARRUMAR AUTOMATICAMENTE O NÚMERO DE USUÁRIOS ADMINISTRADOS (PUT)</h3>
<a>http://localhost:8080/vitalusus/admin/updateFix/{id}</a>

<h3>ADICIONAR USUÁRIO A SER ADMINISTRADO (PUT)</h3>
<a>http://localhost:8080/vitalusus/admin/addUsuariosAdministrados/{id}/{usuarioId}</a>

<h3>REMOVER USUÁRIO ADMINISTRADO (PUT)</h3>
<a>http://localhost:8080/vitalusus/admin/removeUsuariosAdministrados/{id}/{usaurioId}</a>

###

*==ALUNO==*

ATRIBUTOS
id (é um long que representa o número de identificação do aluno)
dataNasc (é um Date que representa a data de nascimento do aluno)
altura (é um float que representa a altura do aluno)
peso (é um float que representa o peso do aluno)
usuario (é o objeto Usuario que está ligado ao objeto Aluno)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/aluno/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/aluno/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/aluno/findById/

CRIAR NOVO ALUNO  E SEU RESPECTIVO USUÁRIO(POST)
http://localhost:8080/vitalusus/aluno/post

ALTERAR ALTURA E PESO DE UMA VEZ
http://localhost:8080/vitalusus/aluno/updateGeral/{id}

ALTERAR ALTURA (PUT)
http://localhost:8080/vitalusus/aluno/updateAltura/{id}

ALTERAR PESO (PUT)
http://localhost:8080/vitalusus/aluno/updatePeso/{id}


*==BANCO==*

ATRIBUTOS
id (é um long que representa o número de identificação do banco)
numeroCartao (é uma String que representa o número do cartão do banco do treinador ligado ao seu respectivo objeto Banco)
treinador (é o objeto Treinador que está ligado ao objeto Banco)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/banco/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/banco/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/banco/findById/

CRIAR UM NOVO BANCO (POST)
http://localhost:8080/vitalusus/banco/post


*==CANAL==*

ATRIBUTOS
id (é um long que representa o número de identificação do canal)
treinador (é o objeto Treinador que está ligado ao objeto Canal)
visualizacoes (é um long que representa o número de visualizações de todos os vídeos do canal)
nome (é uma String que representa o nome do canal)
alunos (é uma lista de objetos Aluno que representa todos os alunos que seguem o canal)
seguidores (é um int que representa a quantidade numérica de seguidores do canal)
videoaulas (é um List contendo todos os objetos Videoaula relacionados ao objeto Canal)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/canal/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/canal/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/canal/findById/

CRIAR UM NOVO CANAL (POST)
http://localhost:8080/vitalusus/canal/post

ARRUMAR AUTOMATICAMENTE O NÚMERO DE SEGUIDORES E VISUALIZAÇÕES (PUT)
http://localhost:8080/vitalusus/canal/updateFixSeguidores/{id}

ADICIONAR ALUNO INSCRITO NO CANAL (PUT)
http://localhost:8080/vitalusus/canal/addAlunos/{id}/{alunoId}

REMOVER ALUNO INSCRITO NO CANAL (PUT)
http://localhost:8080/vitalusus/canal/removeAlunos/{id}/{alunoId}

ALTERAR NOME DO CANAL (PUT)
http://localhost:8080/vitalusus/canal/updateNome/{id}


*==COMENTÁRIO==*

ATRIBUTOS
id (é um long que representa o número de identificação do comentário)
texto (é uma String que representa o texto que foi escrito no comentário)
videoaula (é o objeto Videoaula que está ligado ao objeto Comentario)
aluno (é o objeto Aluno que está ligado ao objeto Comentario)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/comentario/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/comentario/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/comentario/findById/

CRIAR UM NOVO COMENTÁRIO (POST)
http://localhost:8080/vitalusus/comentario/post

ALTERAR TEXTO DO COMENTÁRIO (PUT)
http://localhost:8080/vitalusus/comentario/updateTexto/{id}


*==DENÚNCIA==*

ATRIBUTOS
id (é um long que representa o número de identificação da denuncia)
mensagem (é uma String que representa o texto que foi escrito na denúncia)
usuario (é o objeto Usuario que está ligado ao objeto Denuncia, representando o usuário que fez a denúncia)
usuarioDenunciado (é o objeto Usuario que está ligado ao objeto Denuncia, representando o usuário que foi denunciado)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/denuncia/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/denuncia/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/denuncia/findById/

CRIAR UMA NOVA DENÚNCIA (POST)
http://localhost:8080/vitalusus/denuncia/post

ATUALIZAR A MENSAGEM DA DENÚNCIA(PUT)
http://localhost:8080/vitalusus/denuncia/updateMensagem/{id}


*==EVOLUÇÃO==*

ATRIBUTOS
id (é um long que representa o número de identificação da evolução)
aluno (é o objeto Aluno que está ligado ao objeto Evolucao)
imc (é um float que representa o imc do aluno ligado à evolução)
metBasal (é um float que representa o metabolismo basal do aluno ligado à evolução)
pesoAtual (é um float que representa o pese atual do aluno ligado à evolução)
alturaAtual (é um float que representa a altura atual do aluno ligado à evolução)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/evolucao/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/evolucao/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/evolucao/findById/

CRIAR UMA NOVA EVOLUÇÃO (POST)
http://localhost:8080/vitalusus/evolucao/post

ALTERAR ALTURA ATUAL (PUT)
http://localhost:8080/vitalusus/evolucao/updateAlturaAtual/{id}

ALTERAR IMC ATUAL (PUT)
http://localhost:8080/vitalusus/evolucao/updateImc/{id}

ALTERAR METABOLISMO BASAL ATUAL (PUT)
http://localhost:8080/vitalusus/evolucao/updateMetBasal/{id}

ALTERAR PESO ATUAL (PUT)
http://localhost:8080/vitalusus/evolucao/updatePesoAtual/{id}


*==TREINADOR==*

ATRIBUTOS
id (é um long que representa o número de identificação do treinador)
usuario (é o objeto Usuario que está ligado ao objeto Treinador)
cref (é uma String que representa o CREF do treinador)
dataNasc (é um Date que representa a data de nascimento do treinador)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/treinador/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/treinador/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/treinador/findById/

CRIAR UM NOVO TREINADOR E SEU RESPECTIVO USUÁRIO(POST)
http://localhost:8080/vitalusus/treinador/post


*==USUÁRIO==*

ATRIBUTOS
id (é um long que representa o número de identificação do usuário)
nome (é uma String que representa o nome do usuário)
email (é uma String que representa o email do usuário)
senha (é uma String que representa a senha da conta do usuário)
nivelAcesso (é uma String que representa o nível de acesso do usuario, sendo ele USER ou ADMIN)
foto (é um byte[] que representa a foto de perfil do usuário)
dataCadstro (é um LocalDateTime que representa a data na qual o usuário criou sua conta)
statusUsuario (é uma String que representa o status do usuário, sendo ele ATIVO ou INATIVO)
tipoUsuario (é uma String que representa o tipo do usuário, sendo ele ADMINISTRADOR, ALUNO ou TREINADOR)
chaveSeguranca (é um objeto ChaveSeguranca que representa o id da chave de segurança do Usuário)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/usuario/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/usuario/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/usuario/findById/

FIND BY CHAVESEGURANCA (POST)
http://localhost:8080/vitalusus/videoaula/findByChaveSeguranca/

CRIAR UM NOVO USUÁRIO (POST)
http://localhost:8080/vitalusus/usuario/post

ALTERAR SENHA (PUT)
http://localhost:8080/vitalusus/usuario/updateSenha/{id}

TORNAR PÚBLICO (PUT)
http://localhost:8080/vitalusus/usuario/tornarPublico/{id}

TORNAR PRIVADO (PUT)
http://localhost:8080/vitalusus/usuario/tornarPrivado/{id}

INATIVAR (PUT)
http://localhost:8080/vitalusus/usuario/inativar/{id}

REATIVAR (PUT)
http://localhost:8080/vitalusus/usuario/reativar/{id}

LOGIN (POST)
http://localhost:8080/vitalusus/usuario/login

CORRIGIR BUG DA ENCRIPTAÇÃO DA SENHA DOS TRÊS USUÁRIOS QUE O SCRIPT SQL CRIA (PUT)
http://localhost:8080/vitalusus/usuario/corrigirBugSenha/{id}

*==VIDEOAULA==*

ATRIBUTOS
id (é um long que representa o número de identificação da videoaula)
canal (é o objeto Canal que está ligado ao objeto Treinador)
titulo (é uma String que representa o título da videoaula)
descricao (é uma String que representa a descrição da videoaula)
likes (é um long que representa o número de likes)
alunosLikes (é uma lista de objetos Aluno que representa os alunos que deram like na videoaula)
deslikes (é um long que representa o número de deslikes)
alunosDeslikes (é uma lista de objetos Aluno que representa os alunos que deram deslike na videoaula)
alunos (é uma lista de objetos Aluno que representa os alunos que visualizaram a videoaula)
visualizacoes (é um int que representa o número de vizualizações da videoaula)
video (é um byte[] que representa o arquivo de vídeo da videoaula)
thumbnail (é um byte[] que representa o arquivo de imagem da thumbnail da videoaula)
dataPubli (é um LocalDateTime que representa a data de publicação da videoaula)
categoria (é uma String que representa a categoria da videoaula)
tipoVideoaula (é uma String que representa o tipo de videoaula)

ENDPOINTS
FIND ALL (GET)
http://localhost:8080/vitalusus/videoaula/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/videoaula/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/videoaula/findById/

CRIAR UMA NOVA VIDEOAULA (POST)
http://localhost:8080/vitalusus/videoaula/post

CORRIGIR POSSÍVEIS INCONSISTÊNCIAS NOS ATRIBUTOS DA VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/updateFix/{id}

FAZER ATUALIZAÇÃO DO TÍTULO, DESCRIÇÃO E THUMBNAIL DE UMA VEZ (PUT)
http://localhost:8080/vitalusus/videoaula/updateGeral/{id}

FAZER A ATUALIZAÇÃO SÓ DO TÍTULO (PUT)
http://localhost:8080/vitalusus/videoaula/updateTitulo/{id}

FAZER A ATUALIZAÇÃO SÓ DA DESCRIÇÃO (PUT)
http://localhost:8080/vitalusus/videoaula/updateDescricao/{id}

FAZER A ATUALIZAÇÃO SÓ DA THUMBNAIL (PUT)
http://localhost:8080/vitalusus/videoaula/updateThumbnail/{id}

ADICIONAR UM LIKE À VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/addLikes/{id}/{alunoId}

REMOVER UM LIKE DA VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/removeLikes/{id}/{alunoId}

ADICIONAR UM DESLIKE À VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/addDeslikes/{id}/{alunoId}

REMOVER UM DESLIKE DA VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/removeDeslikes/{id}/{alunoId}

ADICIONAR UMA VISUALIZAÇÃO À VIDEOAULA (PUT)
http://localhost:8080/vitalusus/videoaula/addAlunos/{id}/{alunoId}
