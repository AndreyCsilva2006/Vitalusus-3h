function adicionarVideo(videoUrl) {
    // Criar um elemento de vídeo
    var video = document.createElement('iframe');
    video.width = 400;
    video.height = 300; // Definir altura conforme desejado
    video.src = videoUrl;
    video.setAttribute('allowfullscreen', ''); // Permitir tela cheia para vídeos incorporados

    // Criar um parágrafo para a descrição do vídeo (opcional)
    var p = document.createElement('p');
    p.textContent = 'Descrição do vídeo aqui';

    // Criar um identificador único para o vídeo
    var videoId = 'video-' + Date.now(); // Usando timestamp como identificador único

    // Criar um container para o vídeo e suas informações
    var videoContainer = document.createElement('div');
    videoContainer.id = videoId; // Definir o ID do contêiner com o identificador único
    videoContainer.classList.add('video-container');
    videoContainer.appendChild(video);
    if (p.textContent !== '') {
        videoContainer.appendChild(p);
    }

    // Adicionar um botão de remover para este vídeo
    var removerBtn = document.createElement('button');
    removerBtn.textContent = 'Remover Vídeo';
    removerBtn.addEventListener('click', function() {
        var videoContainerToRemove = document.getElementById(videoId);
        if (videoContainerToRemove) {
            videoContainerToRemove.parentNode.removeChild(videoContainerToRemove);
        }
    });
    videoContainer.appendChild(removerBtn);

    // Adicionar o container do vídeo ao elemento 'videos-container' na página
    var videosContainer = document.getElementById('videos-container');
    videosContainer.appendChild(videoContainer);
}

// Adicionar vídeos com diferentes URLs

adicionarVideo('https://www.youtube.com/embed/SIGG2fEnwEQ?si=Ya215kHNK-GCxkWv')
adicionarVideo('https://www.youtube.com/embed/UY3QiYxSpDs?si=8PGzE6WWOq8IJNXK')
adicionarVideo('https://www.youtube.com/embed/cJ3ZxD2pIc8?si=FiqUor8SLMeLRyHt')
adicionarVideo('https://www.youtube.com/embed/t8p7sb31M0I?si=lUUwOn6u7sBP7zwr')

// Exemplo de como adicionar vídeos em resposta a um evento (como clique em um botão)
var adicionarVideoBtn = document.getElementById('adicionarVideoBtn');
adicionarVideoBtn.addEventListener('click', function() {
    adicionarVideo('https://www.pexels.com/pt-br/video/natureza-campo-area-flores-9547126/');
});
