const dadosCanal = JSON.parse(localStorage.getItem('loginData'))
if(document.getElementById('tiuloPaginaCanal')){
document.getElementById('tiuloPaginaCanal').textContent = "Canal – "+JSON.parse(localStorage.getItem('loginData')).nome
}
async function updateFix() {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/canal/updateFix/${dadosCanal.id}`,{
            method: 'PUT',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const data = await response.json(); // Converte a resposta em JSON        // Aqui você pode fazer algo com os dados, como exibi-los na tela
        localStorage.setItem('loginData', JSON.stringify(data))

    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}

//canal.html
if(dadosCanal){
updateFix()
}
//Aqui é a função do botão que abre a opção para clicar no editar, excluir etc...

function toggleOptions(event) {
    // Fecha todas as opções abertas
    closeOptions();

    const options = event.currentTarget.nextElementSibling;
    options.style.display = options.style.display === 'block' ? 'none' : 'block';
}
function criarVideoPagina(){
    window.location.href = 'criar-video.html'
}
function closeOptions() {
    const optionsList = document.querySelectorAll('.options');
    optionsList.forEach(options => {
        options.style.display = 'none'; // Oculta cada um deles
    });
}

async function deleteItem(id) {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/videoaula/delete/${id}`,{
            method: 'DELETE',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        window.location.href = 'canal.html'
    }
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
     catch (error) {
        console.error('Erro ao deletar o vídeo:', error);
    }
}

function share() {
    alert('Compartilhar clicado');
    closeOptions();
}

// Fecha as opções ao clicar fora
window.onclick = function(event) {
    if (!event.target.matches('.action-button svg')) {
        closeOptions();
    }
};

//função que encontra o vídeo pelo id
function edit(videoaulaId) {
window.location.href = `editar-video.html?id=${videoaulaId}`;
}

        //Código para aquele botão que adiciona o vídeo
        const iconContainer = document.getElementById('iconContainer');
        const fileInput = document.getElementById('fileInput');
        const selectedImage = document.getElementById('selectedImage');
        const imageContainer = document.getElementById('imageContainer');
        const removeButton = document.getElementById('removeButton');
        const message = document.getElementById('message');
        const capaVideo = document.getElementById('capa-video');
        let selectedFileName = ''; // Variável para armazenar o nome do arquivo
        
    
        iconContainer.addEventListener('click', () => {
            fileInput.click();
        });
        
        let imgSubmited
        fileInput.addEventListener('change', (event) => {
            fileImg = event.target.files[0];
            if (fileImg && fileImg.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    capaVideo.src= e.target.result;
                    imgSubmited = e.target.result;
                    capaVideo.alt = fileImg.name;
                    selectedFileName = fileImg.name; // Armazena o nome do arquivo
                    imageContainer.style.display = 'block';
                    message.style.display = 'none';
                    removeButton.style.display = 'block';
                };
                reader.readAsDataURL(fileImg);
            } else {
                message.style.display = 'block';
                message.textContent = 'Por favor, selecione uma imagem válida.';
            }
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

//função que coloca os inputs com o valor a ser editado
// Função para atualizar o vídeo




// Fim...

    var textoAtual = "Ver vídeo"; // Usando let para permitir reatribuição

    function mudarTexto(span, event, url) {
        event.preventDefault(); // Impede o comportamento padrão do link
        textoAtual = "Abrindo...";
        span.textContent = textoAtual; // Atualiza o texto do elemento com id 'mudd'

        // Redireciona após um pequeno atraso
        setTimeout(() => {
            window.location.href = url; // Redireciona para a nova página
        }, 500); // Atraso de 0.5 segundo (500 milissegundos)
    }



