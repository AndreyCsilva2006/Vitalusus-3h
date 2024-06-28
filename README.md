**PROTOCOLOS HTTP PARA USAR**


*ADMINISTRADOR*

FIND ALL
http://localhost:8080/vitalusus/admin/findAll

FIND BY ID 
http://localhost:8080/vitalusus/admin/findById/{id}

POST
http://localhost:8080/vitalusus/admin/post

ARRUMAR AUTOMATICAMENTE O NÚMERO DE USUÁRIOS ADMINISTRADOS
http://localhost:8080/vitalusus/admin/updateFix/{id}

ADICIONAR USUÁRIO A SER ADMINISTRADO
http://localhost:8080/vitalusus/admin/addUsuariosAdministrados/{id}

REMOVER USUÁRIO ADMINISTRADO
http://localhost:8080/vitalusus/admin/removeUsuariosAdministrados/{id}


*ALUNO*

FIND ALL
http://localhost:8080/vitalusus/aluno/findAll

FIND BY ID 
http://localhost:8080/vitalusus/aluno/findById/{id}

POST
http://localhost:8080/vitalusus/aluno/post

ALTERAR ALTURA
http://localhost:8080/vitalusus/aluno/updateAltura/{id}

ALTERAR PESO
http://localhost:8080/vitalusus/aluno/updatePeso/{id}


*BANCO*

FIND ALL
http://localhost:8080/vitalusus/banco/findAll

FIND BY ID 
http://localhost:8080/vitalusus/banco/findById/{id}

POST
http://localhost:8080/vitalusus/banco/post


*CANAL*

FIND ALL
http://localhost:8080/vitalusus/canal/findAll

FIND BY ID 
http://localhost:8080/vitalusus/canal/findById/{id}

POST
http://localhost:8080/vitalusus/canal/post

ARRUMAR AUTOMATICAMENTE O NÚMERO DE SEGUIDORES E VISUALIZAÇÕES
http://localhost:8080/vitalusus/canal/updateFixSeguidores/{id}

ADICIONAR ALUNO
http://localhost:8080/vitalusus/canal/addAlunos/{id}

REMOVER ALUNO
http://localhost:8080/vitalusus/canal/removeAlunos/{id}

ALTERAR NOME DO CANAL
http://localhost:8080/vitalusus/canal/updateNome/{id}


*COMENTÁRIO*

FIND ALL
http://localhost:8080/vitalusus/comentario/findAll

FIND BY ID 
http://localhost:8080/vitalusus/comentario/findById/{id}

POST
http://localhost:8080/vitalusus/comentario/post

ALTERAR TEXTO DO COMENTÁRIO
http://localhost:8080/vitalusus/comentario/updateTexto/{id}


*EVOLUÇÃO*

FIND ALL
http://localhost:8080/vitalusus/evolucao/findAll

FIND BY ID 
http://localhost:8080/vitalusus/evolucao/findById/{id}

POST
http://localhost:8080/vitalusus/evolucao/post

ALTERAR ALTURA ATUAL
http://localhost:8080/vitalusus/evolucao/updateAlturaAtual/{id}

ALTERAR IMC ATUAL
http://localhost:8080/vitalusus/evolucao/updateImc/{id}

ALTERAR METABOLISMO BASAL ATUAL
http://localhost:8080/vitalusus/evolucao/updateMetBasal/{id}

ALTERAR PESO ATUAL
http://localhost:8080/vitalusus/evolucao/updatePesoAtual/{id}


*TREINADOR*

FIND ALL
http://localhost:8080/vitalusus/treinador/findAll

FIND BY ID 
http://localhost:8080/vitalusus/treinador/findById/{id}

POST
http://localhost:8080/vitalusus/treinador/post


*USUÁRIO*

FIND ALL
http://localhost:8080/vitalusus/usuario/findAll

FIND BY ID 
http://localhost:8080/vitalusus/usuario/findById/{id}

POST
http://localhost:8080/vitalusus/usuario/post

ALTERAR SENHA
http://localhost:8080/vitalusus/usuario/updateSenha/{id}

INATIVAR
http://localhost:8080/vitalusus/usuario/inativar/{id}

REATIVAR
http://localhost:8080/vitalusus/usuario/reativar/{id}

LOGIN
http://localhost:8080/vitalusus/usuario/login
