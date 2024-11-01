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
// Função para inativar um usuário
   function aceitarDenuncia(id) {
    if (confirm("Você tem certeza que deseja aceitar esta denúncia?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/banir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const adminData = await response.json()
            window.alert('Denúncia aceita')
            window.location.href = 'denuncia.html'
        }
        catch(error){
            console.error('erro ao buscar o usuário', error)
        }
        }
        fetchDeletar()
    }
}
// Função para inativar um usuário
   function cancelarDenuncia(id) {
    if (confirm("Você tem certeza que deseja cancelar esta denúncia?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/desbanir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const adminData = await response.json()
            window.alert('Denúncia cancelada')
            window.location.href = 'denuncia.html'
        }
        catch(error){
            console.error('erro ao buscar o usuário', error)
        }
        }
        fetchDeletar()
    }
}
// Função para renderizar as denúncias
function exibirDenuncias(denuncias) {
    const container = document.getElementById('denuncias-list');
    container.innerHTML = ""; // Limpa a lista antes de carregar
    denuncias.forEach(denuncia => {
        const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        }
        const denunciaEl = document.createElement('div');
        const dataDen = new Date(denuncia.dataDenuncia + 'Z')
        denunciaEl.className = 'denuncia';
        const statusDenunciado = denuncia.usuarioDenunciado.statusUsuario
        if(statusDenunciado == 'ATIVO'){
        denunciaEl.innerHTML = `
            <img src="data:image/jpeg;base64, ${denuncia.usuario.foto}" alt="Foto de perfil de ${denuncia.usuario.nome}" class="profile-pic">
            <div class="denuncia-info">
                <p><strong>Denuncia feita por:</strong> ${denuncia.usuario.nome}</p>
                <p><strong>Usuário denunciado:</strong> ${denuncia.usuarioDenunciado.nome}</p>
                <p><strong>Motivo:</strong> ${denuncia.mensagem}</p>
                <p><strong>Categoria:</strong> ${denuncia.categoria}</p>
                <p><strong>Data da denúncia</strong> ${dataDen.toLocaleDateString('pt-Br', options)}</p>
                <button class="accept-btn" aria-label="Aceitar denúncia de ${denuncia.usuario.nome}" onclick="aceitarDenuncia(${denuncia.usuarioDenunciado.id})">Aceitar denuncia</button>
            </div>
        `;}
        else{
        denunciaEl.innerHTML = `
            <img src="data:image/jpeg;base64, ${denuncia.usuario.foto}" alt="Foto de perfil de ${denuncia.usuario.nome}" class="profile-pic">
            <div class="denuncia-info">
                <p><strong>Denuncia feita por:</strong> ${denuncia.usuario.nome}</p>
                <p><strong>Usuário denunciado:</strong> ${denuncia.usuarioDenunciado.nome}</p>
                <p><strong>Motivo:</strong> ${denuncia.mensagem}</p>
                <p><strong>Categoria:</strong> ${denuncia.categoria}</p>
                <p><strong>Data da denúncia</strong> ${dataDen.toLocaleDateString('pt-Br', options)}</p>
                <p><strong>Usuário banido até o cancelamento da denúncia</strong></p>
                <button class="accept-btn btn-cor5" aria-label="Cancelar denúncia de ${denuncia.usuario.nome}" onclick="cancelarDenuncia(${denuncia.usuarioDenunciado.id})">Cancelar denúncia</button>
            </div>
        `;}
        container.appendChild(denunciaEl);
    });
}
async function loadDenuncias(){
   try{
       const response = await
       fetch('https://vitalusus-deploy.onrender.com/vitalusus/denuncia/findAll', {
           method: 'GET',
           headers:{
            'Content-Type': 'application/json'
           }
       });
       if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
       }
       const denuncias = await response.json()
       exibirDenuncias(denuncias)
   }
    catch(error){
        console.error('não foi possível encontrar as denúncias')
    }
}
// Inicializa as denúncias ao carregar a página
loadDenuncias();
