async function comentarioFindAll(videoaula){
    try {
        const response = await fetch('http://localhost:8080/vitalusus/comentario/findAllByVideoaula',{
            method: 'POST',
            body: JSON.stringify(videoaula),
             headers: {
            'Content-Type': 'application/json'
        }
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const comentariosData = await response.json(); // Converte a resposta em JSON
        console.log('Comentários:', comentariosData); // Manipula os dados recebidos
        // Armazena os dados no localStorage
        loadComentarios(comentariosData)
    }
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
     catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}

async function videoaulaFindById(id){
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/videoaula/findById/${id}`,{
            method: 'GET',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const videoaulaData = await response.json(); // Converte a resposta em JSON
        if (JSON.parse(localStorage.getItem('loginData')).id == videoaulaData.canal.id){
        console.log('Videoaula:', videoaulaData); // Manipula os dados recebidos
        // Armazena os dados no localStorage
        loadingVideo(videoaulaData)
        comentarioFindAll(videoaulaData)
        }
    }
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
     catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}

const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
videoaulaFindById(id)

function loadingVideo(videoData){
    // Verifica se os dados do vídeo existem
    if (videoData) {
               const date = new Date();
        var dataAtual = date.getTime();
        var dataVideo = new Date(videoData.dataPubli).getTime();
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
        // Exibe os dados do vídeo na página
        document.getElementById('video-titulo').textContent = videoData.titulo;
        document.getElementById('video-descricao').value = videoData.descricao;
        document.getElementById('video-canal').textContent = videoData.canal.nome;
        document.getElementById('video-visualizacoes').textContent = videoData.visualizacoes;
        document.getElementById('video-likes').textContent = videoData.likes;
        document.getElementById('video-deslikes').textContent = videoData.deslikes;
        document.getElementById('tempo').textContent = "Há " + dataContada + ' '+diaString  
        document.getElementById('video-canal-seguidores').textContent = videoData.canal.seguidores+" seguidores"
        // Exibir o vídeo em si (se houver)
        if (videoData.video) {
            document.getElementById('video-player').src = `data:video/mp4;base64,${videoData.video}`;
        }
        if (videoData.canal.treinador.usuario.foto != null) {
            document.getElementById('video-canal-fotoPerfil').src = `data:image/jpeg;base64,${videoData.canal.treinador.usuario.foto}`;
        }
    } else {
         document.getElementById('video-canal-fotoPerfil').src = `user_padrao-removebg-preview.png`;
    };
}

function loadComentarios(comentarios){
    comentarios.forEach(comentario =>{
        const comentariosSection = document.getElementById('comentariosSection')
        comentariosSection.style.display = 'flex'
        const dataPubli = new Date(comentario.dataPubli)
        const options ={
            year:'numeric',
            month:'long',
            day:'numeric'
        }
        const fotoPerfilAtiva = "data:image/jpeg;base64, ${comentario.aluno.usuario.foto}"
        const fotoPerfilNula = "user_padrao-removebg-preview.png"
        let fotoPerfil = ''
        const fotoPerfilUsuario = comentario.aluno.usuario.foto
        if(fotoPerfilUsuario == null){
            fotoPerfil = fotoPerfilNula
        }
        else{
            fotoPerfil = fotoPerfilAtiva
        }
        document.getElementById('comentariosSection').innerHTML =
        `
        <h1>Comentários</h1>
<div id="comentariosContainer" class="d-xl-flex flex-column justify-content-xl-center mb-3">
    <div class="text-white border rounded border-0 border-primary d-flex flex-row align-items-xl-center p-4 p-md-5 comentarioContainer"><a class="link-header-canal-2 tmnh-h4" style="max-width: fit-content;max-height: fit-content;width: auto;min-width: auto;"><img id="imagem-perfil-1" class="img-perfil" style="width: 7vw;height: 7vw;" src=${fotoPerfil} /></a>
        <div class="pb-2 pb-lg-1">
            <p class="mb-0 comentarioDataPubli">${dataPubli.toLocaleDateString('pt-Br', options)}</p>
            <p class="nomeAluno">${comentario.aluno.usuario.nome}</p>
            <p class="mb-0">${comentario.texto}</p>
        </div>
        <div class="my-2"></div>
    </div>
</div>
        `
    })
}