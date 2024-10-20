
//Altura e Largura Antiga da Imagem FIM
var canalDataExibir = JSON.parse(localStorage.getItem('loginData'));
if(canalDataExibir){
    const setInnerHTML = (elementId, content) => {
        const element = document.getElementById(elementId);
        if (element) element.innerHTML = content;
    };
    setInnerHTML('paginaPerfil-nomeUsuario', `<span>${canalDataExibir.nome}</span>`);
    setInnerHTML('pagina-perfil-numeroSeguidores', `<span>${canalDataExibir.seguidores || 0}</span>`);
    setInnerHTML('pagina-perfil-numeroVideos', `<span>${canalDataExibir.numeroVideos || 0}</span>`);
    setInnerHTML('pagina-perfil-txtId', `<span>ID = ${canalDataExibir.id}</span>`);
    if(canalDataExibir.bio !=null){
      document.getElementById('pagina-perfil-bio').value = canalDataExibir.bio
      document.getElementById('editar-bio-canal').value = canalDataExibir.bio
   
document.getElementById('editar-nome-canal').value = canalDataExibir.nome
 }
}

let imgPerfilJava; // Variável para armazenar a imagem escolhida


//modal

function openModal() {
    document.getElementById('modal').style.display = 'block';
    document.getElementById('mainNav').style.setProperty('display', 'none', 'important');

}

function closeModal() {
    document.getElementById('mainNav').style.display = 'block';
    document.getElementById('modal').style.display = 'none';
};

document.getElementById('fileInput').onchange = function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = new Image();
            img.src = e.target.result;
            img.onload = function() {
                const canvas = document.createElement('canvas');
                const ctx = canvas.getContext('2d');

                // Removendo as variáveis maxWidth e maxHeight
                let width = img.width;
                let height = img.height;

                // Redimensiona a imagem mantendo a proporção
                if (width > height) {
                    // Aqui você pode definir um limite se desejar
                } else {
                    // Aqui você pode definir um limite se desejar
                }

                canvas.width = width;
                canvas.height = height;
                ctx.drawImage(img, 0, 0, width, height);

                // Converte o canvas para Base64 com maior compressão
                const resizedImage = canvas.toDataURL('image/jpeg', 0.2); // Aumente a compressão aqui

                const selectedImg = document.getElementById('selectedImage2');
                selectedImg.src = resizedImage;
                selectedImg.style.display = 'block';
                document.getElementById('confirmImage').style.display = 'block';
                document.getElementById('removeImage').style.display = 'block';
                document.getElementById('removeMessage').style.display = 'none';
                
                enviarImagemParaAPI(resizedImage); // Envia a imagem redimensionada
            };
        };

        reader.readAsDataURL(file);
    }
};

// Resto do código permanece o mesmo...


document.getElementById('confirmImage').onclick = async function() {
    const img = document.getElementById('selectedImage2');
    const currentImg = document.getElementById('currentImage');
    currentImg.src = img.src; // Troca a imagem atual pela escolhida
    
    // Aguarda a função de envio de imagem ser concluída
    await enviarImagemParaAPI(img.src); 

    // Atualiza a variável local canalData com a nova imagem
    canalData.treinador.usuario.foto = img.src.split(',')[1]; 

    // Atualiza o localStorage imediatamente
    localStorage.setItem('loginData', JSON.stringify(canalData));

    document.getElementById('modal').style.display = 'none'; // Fecha o modal
    window.location.href = 'perfil.html'
};

document.getElementById('removeImage').onclick = function() {
    const fileName = document.getElementById('fileInput').files[0].name;
    document.getElementById('selectedImage2').style.display = 'none';
    document.getElementById('confirmImage').style.display = 'none';
    document.getElementById('removeImage').style.display = 'none';
    document.getElementById('removeMessage').textContent = `O arquivo ${fileName} foi removido.`;
    document.getElementById('removeMessage').style.display = 'block';
    document.getElementById('fileInput').value = ''; // Limpa o input
};

// Fecha o modal se o usuário clicar fora dele
window.onclick = function(event) {
    if (event.target === document.getElementById('modal')) {
        document.getElementById('modal').style.display = 'none';
    }
};

//transforma a imagem num byte

// Função para enviar a imagem para a API
async function enviarImagemParaAPI(imagemBase64) {
        const imagem = imagemBase64.split(',')[1]; // Obtém somente a parte Base64
const data = {
    treinador:{
        usuario:{
        foto: imagem
        }
    }// Adiciona a imagem à requisição
}
const id = canalData.id

    fetch(`http://localhost:8080/vitalusus/canal/updateFoto/${id}`, { // Substitua pela URL da sua API
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data) // Converte o objeto em JSON
    })
    .then(response => response.json())
    .then(data => {
        console.log('Sucesso:', data);
        localStorage.setItem('editarFotoData', JSON.stringify(data));
        canalData = JSON.parse(localStorage.getItem('editarFotoData'));
        return data;
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
}

// Modal do editar perfil

// Obtém o modal e os elementos necessários
var modalEditar = document.getElementById("Editar-perfil");
var btnEditar = document.getElementById("openEditar");
var spanVoltar = document.getElementsByClassName("close2")[0];
const editContent = document.getElementsByClassName("edit-content")[0];
const formEdit = document.getElementsByClassName("formEdit")[0];

// Verifica se os elementos existem antes de adicionar eventos
if (btnEditar && modalEditar && spanVoltar && editContent && formEdit) {

    // Quando o usuário clicar no botão, abre o modal 
    btnEditar.addEventListener('click', function() {
        modalEditar.style.display = "block"; // Alterado para "flex" se necessário
        editContent.style.display = "flex";
        formEdit.style.display = "flex";
        
        const labels = document.getElementsByClassName("labelEd");
        for (let i = 0; i < labels.length; i++) {
            labels[i].style.display = "flex";
        }
    });

    // Quando o usuário clicar no <span> (Voltar), fecha o modal
    spanVoltar.addEventListener('click', function() {
        modalEditar.style.display = "none"; // Fecha o modal
    });
}


function editarCanal(event){
        event.preventDefault();
        const dataEditar = {
        id:canalData.id,
        visualizacoes:canalData.visualizacoes,
        seguidores:canalData.seguidores,
        treinador:canalData.treinador,
        numeroVideos:canalData.numeroVideos,
        nome: document.getElementById('editar-nome-canal').value,
        bio:  document.getElementById('editar-bio-canal').value
    };

    // Envia os dados para a API
    fetch(`http://localhost:8080/vitalusus/canal/updateInformacoes/${canalData.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dataEditar)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                const errorCode = errorData.error.code || 'Código desconhecido';
                const errorMessage = errorData.error.message || 'Erro desconhecido';
                throw new Error(`Erro ${errorCode}: ${errorMessage}`);
            });
        }
        return response.json();
    })
    .then(data2 => {
        console.log('Perfil editado com sucesso:', dataEditar);
        canalData = localStorage.setItem('loginData',JSON.stringify(dataEditar))
        window.location.href = 'perfil.html'; // Altere para o URL da próxima página
    })
    .catch((error) => {
        console.error('Erro ao editar perfil:', error);
        const errorMessage = document.createElement('div');
        errorMessage.textContent = `Ocorreu um erro ao enviar os dados: ${error.message}`;
        errorMessage.style.color = 'red';
        document.body.appendChild(errorMessage);
    });
}
// Quando o usuário clicar em qualquer lugar fora do modal, fecha o modal
window.onclick = function(event) {
    if (event.target == modalEditar) {
        modalEditar.style.display = "none";
    }
}


//código para desativar conta
async function desativarConta(event) {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/usuario/inativar/${canalData.treinador.usuario.id}`,{
            method: 'PUT',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const data = await response.json(); // Converte a resposta em JSON        // Aqui você pode fazer algo com os dados, como exibi-los na tela
    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
     window.location.href = "index.html"
}







