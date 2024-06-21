
function adicionarVideo(){
    // Criar um elemento de vídeo
    var video = document.createElement('video');
    video.controls = true;
    video.width = 400;
    video.src = ''; // coloca o link do vídeo aqui

    var p = document.createElement('p');
    p.textContent = '' //coloca a descrição aqui

    // criar um container para o vídeo e suas informações
    var videoContainer = document.createElement('div');
    videoContainer.classList.add('video-container');
    videoContainer.appendChild(video);
    videoContainer.appendChild(p);

    var videosContainer = document.getElementById('videos-container');
    videosContainer.appendChild(videoContainer);
}