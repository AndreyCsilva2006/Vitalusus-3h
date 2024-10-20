//Esse código faz com que ao clicar no button: login(event), ele busque na API, se tem algum cadastro e senha inseridos, se não emite um erro.
async function reativarConta(id) {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/usuario/reativar/${id}`,{
            method: 'PUT',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const data = await response.json(); // Converte a resposta em JSON        // Aqui você pode fazer algo com os dados, como exibi-los na tela
        localStorage.setItem('contaReativado', JSON.stringify(data))

    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}

function login(event) {
    event.preventDefault();

    const data = {
        treinador: {
            usuario: {
                email: document.getElementById('login-email-input').value,
                senha: document.getElementById('login-senha-input').value
            }
        }
    };
loginEmail = data.treinador.usuario.email
loginSenha = data.treinador.usuario.senha
    const loginRequest = async () => {
        try {
            const response = await fetch(`http://localhost:8080/vitalusus/usuario/login/?email=${encodeURIComponent(loginEmail)}&senha=${encodeURIComponent(loginSenha)}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                const errorData = await response.json();
                const errorCode = errorData.code || 'Código desconhecido';
                const errorMessage = errorData.message;
                window.alert(errorMessage)
                throw new Error(`Erro ${errorCode}: ${errorMessage}`);
            }
    
            const resultData = await response.json();
            console.log('Dados do formulário enviados com sucesso:', resultData);
            let loginData = JSON.stringify(resultData)
            if(resultData.treinador){
            if(resultData.treinador.usuario.statusUsuario == "INATIVO"){
            await reativarConta(resultData.treinador.usuario.id)
         const canalReativado = localStorage.getItem('contaReativado'); // Acessa aqui

                if(canalReativado && canalReativado.statusUsuario){    
            loginData.treinador.usuario.statusUsuario = canalReativado.statusUsuario
                }
        }
            localStorage.setItem('loginData', JSON.stringify(resultData));
            window.location.href = 'canal.html'; // Altere para o URL da próxima página
            }
            
                 if(resultData.usuario && resultData.usuario.nivelAcesso == 'ADMIN'){
            if(resultData.usuario.statusUsuario == "INATIVO"){
            await reativarConta(resultData.usuario.id)
         const adminReativado = localStorage.getItem('contaReativado'); // Acessa aqui

                if(adminReativado && adminReativado.statusUsuario){    
            loginData.usuario.statusUsuario = adminReativado.statusUsuario
                }
        }
            localStorage.setItem('loginData', JSON.stringify(resultData));
            window.location.href = 'gerenciar-alunos.html'; // Altere para o URL da próxima página
            }
        } catch (error) {
            console.error('Erro ao enviar dados do formulário:', error);
            const errorMessage = document.createElement('div');
            errorMessage.textContent = `Ocorreu um erro ao enviar os dados: ${error.message}`;
            errorMessage.style.color = 'red';
            document.body.appendChild(errorMessage);
        }
    };

    loginRequest();
}
// Esse código reativa a conta caso o treinador queira fazer um login depois de ter desativado a conta

var canalData = JSON.parse(localStorage.getItem('loginData'));

if (canalData) {
    console.log('Dados do login:', canalData);
    const setInnerHTML = (elementId, content) => {
        const element = document.getElementById(elementId);
        if (element) element.innerHTML = content;
    };
    setInnerHTML('nome-canalzinho', `<span>Bem vindo, ${canalData.nome}</span>`);
    

    const setImage = (elementId, base64String) => {
        const imgElement = document.getElementById(elementId);
        if (imgElement && base64String) {
            imgElement.src = `data:image/jpeg;base64,${base64String}`;
            imgElement.style.display = 'block';
        }
    };

    const base64String = canalData.treinador.usuario.foto;
    setImage('currentImage', base64String);
    setImage('imagem-perfil', base64String);
}
async function fetchAllItems() {
    try {
        const response = await fetch('http://localhost:8080/vitalusus/videoaula/findAllByCanal',{
            method: 'POST',
             headers: {
            'Content-Type': 'application/json'
        },
            body:JSON.stringify(canalData)
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }

        const allVideoaulasData = await response.json(); // Converte a resposta em JSON
        console.log('Itens encontrados:', allVideoaulasData); // Manipula os dados recebidos
        exibirVideoaulas(allVideoaulasData);
        exibirVideoaulasPopulares(allVideoaulasData)
        tirarMediaVisualizacoes(allVideoaulasData);
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}
//Código que tira a média de visualizações de todas as videoaulas do canal
var mediaVisualizacoes = 0

function tirarMediaVisualizacoes(videoaulas){
    videoaulas.forEach(videoaula => {
        mediaVisualizacoes = mediaVisualizacoes + videoaula.visualizacoes
    })
    console.log(mediaVisualizacoes/videoaulas.length)
}

function exibirVideoaulas(videoaulas) {
    const videoaulasDiv = document.getElementById('videoaulasDiv');// Altere para o ID da sua div
   
videoaulas.sort((a, b) => new Date(b.dataPubli) - new Date(a.dataPubli));

    if (!videoaulas.length == 0){ 
        videoaulasDiv.innerHTML = ''; // Limpa o conteúdo anterior
        document.getElementById('todosVideosTxt').textContent = "Todos os Videos"
        document.getElementById('criarVideoTxt').style.display = 'none'
        document.getElementById('todosVideosTxt').style.display='block'
    videoaulas.forEach(videoaula => {
        const videoaulaElement = document.createElement('div'); // Cria um novo elemento div
        videoaulaElement.className = 'col-12 col-md-6'; // Adiciona uma classe, se necessário
 //instantaneos do objeto Date, veja explicação no final da resposta
       const date = new Date();
        var dataAtual = date.getTime();
        var dataVideo = new Date(videoaula.dataPubli).getTime();
        const dataEmAnos = (dataAtual - dataVideo)/(1000*60*60*24*7*30*12)
        const dataEmMeses = (dataAtual - dataVideo)/(1000*60*60*24*7*30)
        const dataEmSemanas = (dataAtual - dataVideo)/(1000*60*60*24*7)
        const dataEmDias = (dataAtual - dataVideo)/(1000*60*60*24)
        const dataEmHoras = (dataAtual - dataVideo)/(1000*60*60)
        const dataEmMinutos = (dataAtual - dataVideo)/(1000*60)
        const dataEmSegundos = (dataAtual - dataVideo)/(1000)
        var dataContada = dataEmDias;
        var dataMostrada
        var diaString = 'dia'
        if (dataEmDias < 1){
            
            if(dataEmHoras>=1){
            dataContada = Math.round(dataEmHoras)
            if (dataContada !=1) {diaString = 'horas'} else diaString = 'hora'
            }
            
            if(dataEmHoras<1 && dataEmMinutos>=1){
            dataContada = Math.round(dataEmMinutos)
            if (dataContada !=1) {diaString = 'minutos'} else diaString = 'minuto'
            }
            
            if(dataEmHoras<1 && dataEmMinutos<1){
            dataContada = Math.round(dataEmSegundos)
            if(dataContada>=0){
            if (dataContada !=1) {diaString = 'segundos'} else diaString = 'segundo'
            }
            else{
                dataContada=0
            }
            }
        }
        if(dataEmDias>=1){
        if(dataEmDias<6){
        dataContada = Math.round(dataEmDias)
        if (dataContada !=1) {diaString = 'dias'} else diaString = 'dia'
        }
        if(dataEmDias>=7){
            dataContada = Math.round(dataEmSemanas)
            if (dataContada !=1) {diaString = 'semanas'} else diaString = 'semana'
            if (dataEmSemanas >=4){
            dataContada = Math.round(dataEmMeses)
            if (dataContada !=1) {diaString = 'meses'} else diaString = 'mês'
            }
             if (dataEmMeses >=12){
            dataContada = Math.round(dataEmAnos)
            if (dataContada !=1) {diaString = 'ano'} else diaString = 'anos'
            }
        }
        }
        // Exemplo de como você pode formatar o conteúdo
        videoaulaElement.innerHTML = `
          <div class="card background-video tamanho-video">
    <div class="texto-mud"><span id="mudd" class="texto-nov tmnh-h3" onclick="mudarTexto(this, event, &#39;exibirVideo.html?id=${videoaula.id}&#39;)">Ver vídeo</span></div><img id="capa-de-video-2" class="card-img w-100 d-block capa-de-video" src="data:image/jpeg;base64, ${videoaula.thumbnail}" />
</div>
<section id="organizacao-vd-4" class="organizacao-vd">
    <div id="separNomeConfig-4" class="separNomeConfig">
        <h1 id="Titulo-vd-4" class="text-break text-two-lines Titulo-vd tmnh-h3">${videoaula.titulo}</h1>
        <div class="action-button"><svg class="icon icon-repar-k icon-tabler icon-tabler-settings-automation icon-tmnh" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round" onclick="toggleOptions(event)">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                <path d="M10.325 4.317c.426 -1.756 2.924 -1.756 3.35 0a1.724 1.724 0 0 0 2.573 1.066c1.543 -.94 3.31 .826 2.37 2.37a1.724 1.724 0 0 0 1.065 2.572c1.756 .426 1.756 2.924 0 3.35a1.724 1.724 0 0 0 -1.066 2.573c.94 1.543 -.826 3.31 -2.37 2.37a1.724 1.724 0 0 0 -2.572 1.065c-.426 1.756 -2.924 1.756 -3.35 0a1.724 1.724 0 0 0 -2.573 -1.066c-1.543 .94 -3.31 -.826 -2.37 -2.37a1.724 1.724 0 0 0 -1.065 -2.572c-1.756 -.426 -1.756 -2.924 0 -3.35a1.724 1.724 0 0 0 1.066 -2.573c-.94 -1.543 .826 -3.31 2.37 -2.37c1 .608 2.296 .07 2.572 -1.065z"></path>
                <path d="M10 9v6l5 -3z"></path>
            </svg>
            <div class="options text-black">
                <div id="editar-4" onclick="edit(${videoaula.id})"><svg class="icon icon-tabler icon-tabler-edit-circle div-icon-vd" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                        <path d="M12 15l8.385 -8.415a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3z"></path>
                        <path d="M16 5l3 3"></path>
                        <path d="M9 7.07a7 7 0 0 0 1 13.93a7 7 0 0 0 6.929 -6"></path>
                    </svg>
                    <p>Editar</p>
                </div>
                <div id="deletar-4" onclick="deleteItem(${videoaula.id})"><svg class="div-icon-vd" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor">
                        <path d="M0 0h24v24H0V0z" fill="none"></path>
                        <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5l-1-1h-5l-1 1H5v2h14V4z"></path>
                    </svg>
                    <p>Deletar</p>
                </div>
                <div id="compartilhar-4" onclick="share()"><svg class="icon icon-tabler icon-tabler-share-3 div-icon-vd" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                        <path d="M13 4v4c-6.575 1.028 -9.02 6.788 -10 12c-.037 .206 5.384 -5.962 10 -6v4l8 -7l-8 -7z"></path>
                    </svg>
                    <p>Compartilhar</p>
                </div>
            </div>
        </div>
    </div><span id="nome-canal-4" class="text-one-line nome-canal tmnh-p">${videoaula.canal.nome}</span>
    <div class="info-vd"><span id="contador-visu-4" class="contador-visu tmnh-p">${videoaula.visualizacoes}</span><span class="visualizacao tmnh-p">visualizações</span><span id="tempo" class="tempo tmnh-p">há ${dataContada} ${diaString}</span><span class="videoaulaId">${videoaula.id}</span>
 <div class="LikeDeslike">
                <div class="divLike tmnh-p"><i class="icon-like"></i><span id="like-count-1">${videoaula.likes}</span></div>
                <div class="divDeslike tmnh-p"><i class="icon-dislike"></i><span id="deslike-count-1">${videoaula.deslikes}</span></div>
            </div>
</div>
</section>
        `;

        videoaulasDiv.appendChild(videoaulaElement); // Adiciona o novo elemento à div
        
    });
    }
}

//Função que exibe as videoaulas que estão acima da média de visualizações
function exibirVideoaulasPopulares(videoaulas) {
    const videoaulasDiv = document.getElementById('videoaulasPopularesDiv');// Altere para o ID da sua div
    const videoaulasPopularesDiv = document.getElementById('videos-populares-container')
   
   if (!videoaulas.length == 0){
    videoaulasDiv.innerHTML = ''; // Limpa o conteúdo anterior
videoaulas.sort((a, b) => b.visualizacoes - a.visualizacoes);

    videoaulas.forEach(videoaula => {
        const videoaulaElement = document.createElement('div'); // Cria um novo elemento div
        videoaulaElement.className = 'col-12 col-md-6'; // Adiciona uma classe, se necessário
 //instantaneos do objeto Date, veja explicação no final da resposta
               const date = new Date();
        var dataAtual = date.getTime();
        var dataVideo = new Date(videoaula.dataPubli).getTime();
        const dataEmAnos = (dataAtual - dataVideo)/(1000*60*60*24*7*30*12)
        const dataEmMeses = (dataAtual - dataVideo)/(1000*60*60*24*7*30)
        const dataEmSemanas = (dataAtual - dataVideo)/(1000*60*60*24*7)
        const dataEmDias = (dataAtual - dataVideo)/(1000*60*60*24)
        const dataEmHoras = (dataAtual - dataVideo)/(1000*60*60)
        const dataEmMinutos = (dataAtual - dataVideo)/(1000*60)
        const dataEmSegundos = (dataAtual - dataVideo)/(1000)
        var dataContada = dataEmDias;
        var dataMostrada
        var diaString = 'dia'
        if (dataEmDias < 1){
            
            if(dataEmHoras>=1){
            dataContada = Math.round(dataEmHoras)
            if (dataContada !=1) {diaString = 'horas'} else diaString = 'hora'
            }
            
            if(dataEmHoras<1 && dataEmMinutos>=1){
            dataContada = Math.round(dataEmMinutos)
            if (dataContada !=1) {diaString = 'minutos'} else diaString = 'minuto'
            }
            
            if(dataEmHoras<1 && dataEmMinutos<1){
            dataContada = Math.round(dataEmSegundos)
            if(dataContada>=0){
            if (dataContada !=1) {diaString = 'segundos'} else diaString = 'segundo'
            }
            else{
                dataContada=0
            }
            }
        }
        if(dataEmDias>=1){
        if(dataEmDias<6){
        dataContada = Math.round(dataEmDias)
        if (dataContada !=1) {diaString = 'dias'} else diaString = 'dia'
        }
        if(dataEmDias>=7){
            dataContada = Math.round(dataEmSemanas)
            if (dataContada !=1) {diaString = 'semanas'} else diaString = 'semana'
            if (dataEmSemanas >=4){
            dataContada = Math.round(dataEmMeses)
            if (dataContada !=1) {diaString = 'meses'} else diaString = 'mês'
            }
             if (dataEmMeses >=12){
            dataContada = Math.round(dataEmAnos)
            if (dataContada !=1) {diaString = 'ano'} else diaString = 'anos'
            }
        }
        }
        // Exemplo de como você pode formatar o conteúdo
        if(videoaula.visualizacoes > mediaVisualizacoes){
        videoaulasPopularesDiv.style.display = 'block'
        videoaulaElement.innerHTML = `
            <div class="card background-video tamanho-video">
    <div class="texto-mud"><span id="mudd" class="texto-nov tmnh-h3" onclick="mudarTexto(this, event, &#39;exibirVideo.html?id=${videoaula.id}&#39;)">Ver vídeo</span></div><img id="capa-de-video-2" class="card-img w-100 d-block capa-de-video" src="data:image/jpeg;base64, ${videoaula.thumbnail}" />
</div>
<section id="organizacao-vd-4" class="organizacao-vd">
    <div id="separNomeConfig-4" class="separNomeConfig">
        <h1 id="Titulo-vd-4" class="text-break text-two-lines Titulo-vd tmnh-h3">${videoaula.titulo}</h1>
        <div class="action-button"><svg class="icon icon-tabler icon-tabler-settings-automation icon-tmnh" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round" onclick="toggleOptions(event)">
                <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                <path d="M10.325 4.317c.426 -1.756 2.924 -1.756 3.35 0a1.724 1.724 0 0 0 2.573 1.066c1.543 -.94 3.31 .826 2.37 2.37a1.724 1.724 0 0 0 1.065 2.572c1.756 .426 1.756 2.924 0 3.35a1.724 1.724 0 0 0 -1.066 2.573c.94 1.543 -.826 3.31 -2.37 2.37a1.724 1.724 0 0 0 -2.572 1.065c-.426 1.756 -2.924 1.756 -3.35 0a1.724 1.724 0 0 0 -2.573 -1.066c-1.543 .94 -3.31 -.826 -2.37 -2.37a1.724 1.724 0 0 0 -1.065 -2.572c-1.756 -.426 -1.756 -2.924 0 -3.35a1.724 1.724 0 0 0 1.066 -2.573c-.94 -1.543 .826 -3.31 2.37 -2.37c1 .608 2.296 .07 2.572 -1.065z"></path>
                <path d="M10 9v6l5 -3z"></path>
            </svg>
            <div class="options text-black">
                <div id="editar-4" onclick="edit(${videoaula.id})"><svg class="icon icon-tabler icon-tabler-edit-circle div-icon-vd" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                        <path d="M12 15l8.385 -8.415a2.1 2.1 0 0 0 -2.97 -2.97l-8.415 8.385v3h3z"></path>
                        <path d="M16 5l3 3"></path>
                        <path d="M9 7.07a7 7 0 0 0 1 13.93a7 7 0 0 0 6.929 -6"></path>
                    </svg>
                    <p>Editar</p>
                </div>
                <div id="deletar-4" onclick="deleteItem(${videoaula.id})"><svg class="div-icon-vd" xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 24 24" width="1em" fill="currentColor">
                        <path d="M0 0h24v24H0V0z" fill="none"></path>
                        <path d="M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM8 9h8v10H8V9zm7.5-5l-1-1h-5l-1 1H5v2h14V4z"></path>
                    </svg>
                    <p>Deletar</p>
                </div>
                <div id="compartilhar-4" onclick="share()"><svg class="icon icon-tabler icon-tabler-share-3 div-icon-vd" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                        <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                        <path d="M13 4v4c-6.575 1.028 -9.02 6.788 -10 12c-.037 .206 5.384 -5.962 10 -6v4l8 -7l-8 -7z"></path>
                    </svg>
                    <p>Compartilhar</p>
                </div>
            </div>
        </div>
    </div><span id="nome-canal-4" class="text-one-line nome-canal tmnh-p">${videoaula.canal.nome}</span>
    <div class="info-vd"><span id="contador-visu-4" class="contador-visu tmnh-p">${videoaula.visualizacoes}</span><span class="visualizacao tmnh-p">visualizações</span><span id="tempo" class="tempo tmnh-p">há ${dataContada} ${diaString}</span><span class="videoaulaId">${videoaula.id}</span>
 <div class="LikeDeslike">
                <div class="divLike tmnh-p"><i class="icon-like"></i><span id="like-count-1">${videoaula.likes}</span></div>
                <div class="divDeslike tmnh-p"><i class="icon-dislike"></i><span id="deslike-count-1">${videoaula.deslikes}</span></div>
            </div>
</div>
</section>
        `;

        videoaulasDiv.appendChild(videoaulaElement); // Adiciona o novo elemento à div
        }
    });
   }
}
// Chamada da função para buscar os itens
fetchAllItems();

