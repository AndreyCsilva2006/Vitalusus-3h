var adminData = JSON.parse(localStorage.getItem('loginData'))
localStorage.setItem('loginData', JSON.stringify(adminData));

if (adminData){
if(adminData.usuario && adminData.usuario.tipoUsuario && adminData.usuario.tipoUsuario=="ADMINISTRADOR"){
    document.getElementById('main-admin').style.display = 'block'
    }
}
else{
    document.getElementById('main-admin').style.display = 'none'
}
/*
document.addEventListener('DOMContentLoaded', function() {
    // Simulação de vídeos buscados do banco de dados
    const videos = [
        {
            title: "Vídeo 1",
            description: "Descrição do vídeo 1",
            category: "Categoria A",
            thumbnailUrl: "https://via.placeholder.com/150",
            banned: false
        },
        {
            title: "Vídeo 2",
            description: "Descrição do vídeo 2",
            category: "Categoria B",
            thumbnailUrl: "https://via.placeholder.com/150",
            banned: false
        },
        {
            title: "Vídeo 3",
            description: "Descrição do vídeo 3",
            category: "Categoria C",
            thumbnailUrl: "https://via.placeholder.com/150",
            banned: true
        }
    ];

    videos.forEach(video => addVideoToTable(video));
});

function addVideoToTable(video) {
    const tableBody = document.getElementById('videoTableBody');

    const row = document.createElement('tr');
    const banButtonLabel = video.banned ? "Desbanir" : "Banir";

    video
    tableBody.innerHTML += `
<tr>
        <td><img src="${video.thumbnailUrl}" alt="Capa do Vídeo"></td>
        <td>${video.title}</td>
        <td>${video.description}</td>
        <td>${video.category}</td>
        <td class = "buttonsContainer">
            <button class="edit" onclick="openEditModal(this)"><i class="fas fa-edit"></i> Editar</button>
            <button class="delete" onclick="deleteVideo(this)"><i class="fas fa-trash"></i> Excluir</button>
            <button class="ban" onclick="toggleBan(this)"><i class="fas fa-ban"></i> ${banButtonLabel}</button>
        </td>
</tr>
    `;

    tableBody.appendChild(row);
}
*/

function exibirVideoaulas(videoaulas) {
    const tableBody = document.getElementById('videoTableBody');

    const row = document.createElement('tr');
    const banButtonLabel = videoaulas.banned ? "Desbanir" : "Banir";
    videoaulas.forEach(video =>{
        if (video.statusVideo == 'ATIVO'){
    tableBody.innerHTML += `
<tr>
        <td>${video.id}</td>
        <td><img src="data:image/jpeg;base64, ${video.thumbnail}" alt="Capa do Vídeo"></td>
        <td>${video.titulo}</td>
        <td>${video.descricao}</td>
        <td>${video.categoria}</td>
        <td>${video.canal.nome}</td>
        <td>${video.equipamento.nome}</td>
        <td>${video.tags}</td>
        <td>${video.visualizacoes}</td>
        <td>${video.statusVideo}</td>
        <td>${video.privacidadeVideo}</td>
        <td class = "buttonsContainer">
            <button class="delete" onclick="banVideo(${video.id})"><i class="fas fa-trash"></i> Banir</button>
            <button class="delete" onclick="deleteVideo(${video.id})"><i class="fas fa-trash"></i> Excluir</button>
        </td>
</tr>
    `;}
         if (video.statusVideo == 'BANIDO'){
    tableBody.innerHTML += `
<tr>
        <td>${video.id}</td>
        <td><img src="data:image/jpeg;base64, ${video.thumbnail}" alt="Capa do Vídeo"></td>
        <td>${video.titulo}</td>
        <td>${video.descricao}</td>
        <td>${video.categoria}</td>
        <td>${video.canal.nome}</td>
        <td>${video.equipamento.nome}</td>
        <td>${video.tags}</td>
        <td>${video.visualizacoes}</td>
        <td>${video.statusVideo}</td>
        <td>${video.privacidadeVideo}</td>
        <td class = "buttonsContainer">
            <button class="delete" onclick="unbanVideo(${video.id})"><i class="fas fa-trash"></i> Desbanir</button>
            <button class="delete" onclick="deleteVideo(${video.id})"><i class="fas fa-trash"></i> Excluir</button>
        </td>
</tr>
    `;}
    })
}
   async function loadVideos() {
        try{
            const response = await
            fetch('https://vitalusus-deploy.onrender.com/vitalusus/videoaula/findAll',{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const allVideos= await response.json()
            console.log('Lista de videoaulas: ', allVideos)
            exibirVideoaulas(allVideos)
        }
        catch(error){
            console.error('erro ao buscar os itens', error)
        }
    }
loadVideos()
   async function findVideoToOpenModal(id) {
        try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/videoaula/findById/${id}`,{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const video = await response.json()
            openEditModal(video)
        }
        catch(error){
            console.error('erro ao buscar a videoaula', error)
        }
    }
function openEditModal(video) {
    document.getElementById('idHiddenInput').value = video.id;
    document.getElementById('editTitle').value = video.titulo;
    document.getElementById('editDescription').value = video.descricao;
    document.getElementById('editCategory').value = video.categoria;

    const modal = document.getElementById('editModal');
    modal.style.display = 'block';
    
    document.getElementById('editVideoForm').onsubmit = function(event){
        event.preventDefault()
        editarVideo(video)
    }
}
function deleteVideo(id) {
    if (confirm("Você tem certeza que deseja excluir este vídeo?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/videoaula/delete/${id}`,{
                method:'DELETE',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            window.location.href = 'gerenciar-videos.html'
        }
        catch(error){
            console.error('erro ao buscar a videoaula', error)
        }
        }
        fetchDeletar()
    }
}
function banVideo(id) {
    if (confirm("Você tem certeza que deseja banir este vídeo?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/videoaula/banir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            window.location.href = 'gerenciar-videos.html'
        }
        catch(error){
            console.error('erro ao buscar a videoaula', error)
        }
        }
        fetchDeletar()
    }
}
function unbanVideo(id) {
    if (confirm("Você tem certeza que deseja desbanir este vídeo?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/videoaula/desbanir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            window.location.href = 'gerenciar-videos.html'
        }
        catch(error){
            console.error('erro ao buscar a videoaula', error)
        }
        }
        fetchDeletar()
    }
}
function toggleBan(button) {
    const row = button.parentElement.parentElement;
    const isBanned = button.textContent.includes("Desbanir");

    button.textContent = isBanned ? "Banir" : "Desbanir";
    row.style.opacity = isBanned ? "1" : "0.5";
}

// Fechar o modal ao clicar no 'X'
document.querySelector('.close').onclick = function() {
    document.getElementById('editModal').style.display = 'none';
}

// Fechar o modal ao clicar fora dele
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}

function editarVideo(video){
     const data = {
            titulo: document.getElementById('editTitle').value,
            descricao: document.getElementById('editDescription').value,
            categoria: document.getElementById('editCategory').value,
            thumbnail: video.thumbnail,
            tags: video.tags,
            equipamento: video.equipamento
        }
    const enviarDados = async()=>{
    try{
        const response = await 
        fetch(`https://vitalusus-deploy.onrender.com/vitalusus/videoaula/updateGeral/${video.id}`,{
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
        },
            body: JSON.stringify(data)
        });
        if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
        window.location.href = 'gerenciar-videos.html'
    }
    catch(error){
        console.error('erro ao editar vídeo', error)
    }
     }
    enviarDados()
}