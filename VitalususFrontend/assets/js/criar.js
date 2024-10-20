
//criar-video.html / editar-video.html
function exibirEquipamentos(equipamentos){
    const equipSelect = document.getElementById('equipamentosSelect')
    equipSelect.innerHTML = ''
    equipamentos.forEach(equipamento => {
        if(equipamento.statusEquipamento == 'ATIVO'){
        equipSelect.innerHTML += `
        <option value="${equipamento.id}">${equipamento.nome}</option>
        `
        }
    })
}



async function findEquipamentos(){
    try{
        const response = await
        fetch('http://localhost:8080/vitalusus/equipamento/findAll', {
            method:'GET',
            headers:{
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            throw new Error('Erro na requisição: ', response.statusText)
        }
        const equipamentos = await response.json()
        exibirEquipamentos(equipamentos)
    }
    catch(error){
        console.error(error)
    }
}


findEquipamentos()






// BOTÃO PARA SELECIONAR ARQUVIOS DO TIPO IMAGEM. OU SEJA A CAPA DO VIDEO

        const fileInputVideo= document.getElementById('fileInputVideo');
        const selectedVideo = document.getElementById('selectedVideo');
        const removeVideoButton = document.getElementById('removeVideoButton');
        const addVideoButton = document.getElementById('addVideoButton');
        
        


let videoSubmited
 fileInputVideo.addEventListener('change', (event) => {
            fileVideo = event.target.files[0];
                const readerVideo = new FileReader();
                readerVideo.onload = function(e) {
                    selectedVideo.src = e.target.result;
                    videoSubmited = e.target.result;
                    addVideoButton.style.display = 'none'
                    removeVideoButton.style.display = 'block'
                };
                readerVideo.readAsDataURL(fileVideo);
        });

removeButton.addEventListener('click', () => {
            capaVideo.src = 'photo-1581009137042-c552e485697a.jpg';
            imageContainer.style.display = 'none';
            removeButton.style.display = 'none';
            message.style.display = 'block';
            message.textContent = `Arquivo removido: ${selectedFileName}`; // Exibe o nome do arquivo removido
            fileInput.value = '';
            selectedFileName = ''; // Limpa o nome do arquivo
        });

 removeVideoButton.addEventListener('click', () => {
            selectedVideo.src = '';
            addVideoButton.style.display = 'block'
            removeVideoButton.style.display = 'none'
// Exibe o nome do arquivo removido
            fileInputVideo.value = '';
        });

        addVideoButton.addEventListener('click', () => {
            fileInputVideo.click();
        });



function criarVideo(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    const equipamentoId = document.getElementById('equipamentosSelect').value
    const videoInfo = {
        titulo: document.getElementById('titulo').value,
        descricao: document.getElementById('descricao').value,
        canal: canalData,
        thumbnail: imgSubmited.split(',')[1],
        video: videoSubmited.split(',')[1],
        categoria: document.getElementById('categoria-video').value,
        tags: document.getElementById('tags-video').value,
    };

    console.log(videoInfo);

    // Envio dos dados para a API
    fetch(`http://localhost:8080/vitalusus/canal/addVideoaula/${canalData.id}/${equipamentoId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(videoInfo)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Vídeo adicionado com sucesso:', data);
        
        // Armazenando os dados do vídeo no localStorage
        localStorage.setItem('canalNovaVideoaula', JSON.stringify(data));

        // Redireciona para canal.html
        window.location.href = 'canal.html';
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
}

// Adiciona o evento ao botão de adicionar vídeo
addVideoButton.addEventListener('click', criarVideo);