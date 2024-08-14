**PROTOCOLOS HTTP PARA USAR**


*==ADMINISTRADOR==*

FIND ALL (GET)
http://localhost:8080/vitalusus/admin/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/admin/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/admin/findById

CRIAR UM NOVO ADMINISTRADOR (POST)
http://localhost:8080/vitalusus/admin/post

ARRUMAR AUTOMATICAMENTE O NÚMERO DE USUÁRIOS ADMINISTRADOS (PUT)
http://localhost:8080/vitalusus/admin/updateFix/{id}

ADICIONAR USUÁRIO A SER ADMINISTRADO (PUT)
http://localhost:8080/vitalusus/admin/addUsuariosAdministrados/{id}

REMOVER USUÁRIO ADMINISTRADO (PUT)
http://localhost:8080/vitalusus/admin/removeUsuariosAdministrados/{id}


*==ALUNO==*

FIND ALL (GET)
http://localhost:8080/vitalusus/aluno/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/aluno/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/aluno/findById

CRIAR NOVO ALUNO (POST)
http://localhost:8080/vitalusus/aluno/post

ALTERAR ALTURA (PUT)
http://localhost:8080/vitalusus/aluno/updateAltura/{id}

ALTERAR PESO (PUT)
http://localhost:8080/vitalusus/aluno/updatePeso/{id}


*==BANCO==*

FIND ALL (GET)
http://localhost:8080/vitalusus/banco/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/banco/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/banco/findById

CRIAR UM NOVO BANCO (POST)
http://localhost:8080/vitalusus/banco/post


*==CANAL==*

FIND ALL (GET)
http://localhost:8080/vitalusus/canal/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/canal/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/canal/findById

CRIAR UM NOVO CANAL (POST)
http://localhost:8080/vitalusus/canal/post

ARRUMAR AUTOMATICAMENTE O NÚMERO DE SEGUIDORES E VISUALIZAÇÕES (PUT)
http://localhost:8080/vitalusus/canal/updateFixSeguidores/{id}

ADICIONAR ALUNO (PUT)
http://localhost:8080/vitalusus/canal/addAlunos/{id}

REMOVER ALUNO (PUT)
http://localhost:8080/vitalusus/canal/removeAlunos/{id}

ALTERAR NOME DO CANAL (PUT)
http://localhost:8080/vitalusus/canal/updateNome/{id}


*==COMENTÁRIO==*

FIND ALL (GET)
http://localhost:8080/vitalusus/comentario/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/comentario/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/comentario/findById

CRIAR UM NOVO COMENTÁRIO (POST)
http://localhost:8080/vitalusus/comentario/post

ALTERAR TEXTO DO COMENTÁRIO (PUT)
http://localhost:8080/vitalusus/comentario/updateTexto/{id}


*==EVOLUÇÃO==*

FIND ALL (GET)
http://localhost:8080/vitalusus/evolucao/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/evolucao/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/evolucao/findById

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

FIND ALL (GET)
http://localhost:8080/vitalusus/treinador/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/treinador/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/treinador/findById

CRIAR UM NOVO TREINADOR (POST)
http://localhost:8080/vitalusus/treinador/post


*==USUÁRIO==*

FIND ALL (GET)
http://localhost:8080/vitalusus/usuario/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/usuario/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/usuario/findById

CRIAR UM NOVO USUÁRIO (POST)
http://localhost:8080/vitalusus/usuario/post

ALTERAR SENHA (PUT)
http://localhost:8080/vitalusus/usuario/updateSenha/{id}

INATIVAR (PUT)
http://localhost:8080/vitalusus/usuario/inativar/{id}

REATIVAR (PUT)
http://localhost:8080/vitalusus/usuario/reativar/{id}

LOGIN (POST)
http://localhost:8080/vitalusus/usuario/login

CORRIGIR BUG DA ENCRIPTAÇÃO DA SENHA DOS TRÊS USUÁRIOS QUE O SCRIPT SQL CRIA (PUT)
http://localhost:8080/vitalusus/usuario/corrigirBugSenha/{id}

*==VIDEOAULA==*

FIND ALL (GET)
http://localhost:8080/vitalusus/videoaula/findAll

FIND BY ID (GET)
http://localhost:8080/vitalusus/videoaula/findById/{id}

FIND BY ID (POST)
http://localhost:8080/vitalusus/videoaula/findById

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

ADICIONAR UM LIKE À VIDEOAULA (PUT) [NOTA -- O ATRIBUTO DA VIDEOAULA QUE O FRONTEND TEM QUE MANDAR PARA O BACKEND SE CHAMA alunosLikes E SE TRATA DE UMA CLASSE ALUNO DENTRO DA CLASSE VIDEOAULA]
http://localhost:8080/vitalusus/videoaula/addLikes/{id}

REMOVER UM LIKE DA VIDEOAULA (PUT) [NOTA -- O ATRIBUTO DA VIDEOAULA QUE O FRONTEND TEM QUE MANDAR PARA O BACKEND SE CHAMA alunosLikes E SE TRATA DE UMA CLASSE ALUNO DENTRO DA CLASSE VIDEOAULA]
http://localhost:8080/vitalusus/videoaula/removeLikes/{id}

ADICIONAR UM DESLIKE À VIDEOAULA (PUT) [NOTA -- O ATRIBUTO DA VIDEOAULA QUE O FRONTEND TEM QUE MANDAR PARA O BACKEND SE CHAMA alunosDeslikes E SE TRATA DE UMA CLASSE ALUNO DENTRO DA CLASSE VIDEOAULA]
http://localhost:8080/vitalusus/videoaula/addDeslikes/{id}

REMOVER UM DESLIKE DA VIDEOAULA (PUT) [NOTA -- O ATRIBUTO DA VIDEOAULA QUE O FRONTEND TEM QUE MANDAR PARA O BACKEND SE CHAMA alunosDeslikes E SE TRATA DE UMA CLASSE ALUNO DENTRO DA CLASSE VIDEOAULA]
http://localhost:8080/vitalusus/videoaula/removeDeslikes/{id}

ADICIONAR UMA VISUALIZAÇÃO À VIDEOAULA (PUT) [NOTA -- AQUI O FRONTEND TEM QUE MANDAR UM ATRIBUTO DA VIDEOAULA CHAMADO alunos, QUE SE TRATA DE UMA CLASSE ALUNO DENTRO DA CLASSE VIDEOAULA PARA QUE O BACKEND POSSA CONTAR OS ALUNOS QUE VIRAM A VIDEOAULA]
http://localhost:8080/vitalusus/videoaula/addAlunos/{id}


