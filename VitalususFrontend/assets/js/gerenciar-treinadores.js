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
// Função para deletar um usuário
   function deleteTreinador(id) {
    if (confirm("Você tem certeza que deseja excluir este treinador?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/deletar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            window.location.href = 'gerenciar-treinadores.html'
        }
        catch(error){
            console.error('erro ao buscar o treinador', error)
        }
        }
        fetchDeletar()
    }
}


 // Função para ativar um usuário
   function ativarTreinador(id) {
    if (confirm("Você tem certeza que deseja ativar este treinador?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/reativar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const treinadorData = await response.json()
            window.location.href = 'gerenciar-treinadores.html'
        }
        catch(error){
            console.error('erro ao buscar o treinador', error)
            window.alert('Você não pode reativar este treinador, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}

 // Função para inativar um usuário
   function inativarTreinador(id) {
    if (confirm("Você tem certeza que deseja inativar este treinador?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/inativar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const treinadorData = await response.json()
            window.location.href = 'gerenciar-treinadores.html'
        }
        catch(error){
            console.error('erro ao buscar o treinador', error)
            window.alert('Você não pode reativar este treinador, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para banir um usuário
   function banirTreinador(id) {
    if (confirm("Você tem certeza que deseja banir este treinador?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/banir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const treinadorData = await response.json()
            window.location.href = 'gerenciar-treinadores.html'
        }
        catch(error){
            console.error('erro ao buscar o treinador', error)
            window.alert('Você não pode reativar este treinador, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para desbanir um usuário
   function desbanirTreinador(id) {
    if (confirm("Você tem certeza que deseja desbanir este treinador?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/desbanir/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const treinadorData = await response.json()
            window.location.href = 'gerenciar-treinadores.html'
        }
        catch(error){
            console.error('erro ao buscar o treinador', error)
            window.alert('Você não pode reativar este treinador, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('treinador-form');
    const searchBtn = document.getElementById('searchBtn');
    const treinadorTable = document.querySelector('#treinador-table tbody');
    
    // Função para carregar todos os usuários
    function exibirTreinadores(treinadores){
        const tabelaTreinador = document.getElementById('bodyTableTreinador')
        tabelaTreinador.innerHTML = ''
        treinadores.forEach(treinador => {
            if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'ATIVO'){
            tabelaTreinador.innerHTML +=  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirTreinador(${treinador.usuario.id})">Banir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                <button onclick="inativarTreinador(${treinador.usuario.id})">Desativar</button>
                </td>
              </tr>`
            }
            
             if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'INATIVO'){
            tabelaTreinador.innerHTML +=  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirTreinador(${treinador.usuario.id})">Banir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                  <button onclick="ativarTreinador(${treinador.usuario.id})">Ativar</button>
                </td>
              </tr>`
            }
            
             if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'BANIDO'){
            tabelaTreinador.innerHTML +=  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="desbanirTreinador(${treinador.usuario.id})">Desbanir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                </td>
              </tr>`
            }
        })
    }
     // Função para carregar só um treinador
    function exibirTreinador(treinador){
            const tabelaTreinador = document.getElementById('bodyTableTreinador')
            if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'ATIVO'){
            tabelaTreinador.innerHTML =  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirTreinador(${treinador.usuario.id})">Banir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                <button onclick="inativarTreinador(${treinador.usuario.id})">Desativar</button>
                </td>
              </tr>`
            }
            
             if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'INATIVO'){
            tabelaTreinador.innerHTML +=  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirTreinador(${treinador.usuario.id})">Banir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                  <button onclick="ativarTreinador(${treinador.usuario.id})">Ativar</button>
                </td>
              </tr>`
            }
            
             if(treinador.usuario.statusUsuario != "DELETADO" && treinador.usuario.statusUsuario == 'BANIDO'){
            tabelaTreinador.innerHTML +=  `<tr>
                <td>${treinador.id}</td>
                <td>${treinador.usuario.nome}</td>
                <td>${treinador.usuario.email}</td>
                <td>${treinador.usuario.nivelAcesso}</td>
                <td>${treinador.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="desbanirTreinador(${treinador.usuario.id})">Desbanir</button>
                  <button onclick="deleteTreinador(${treinador.usuario.id})">Deletar</button>
                </td>
              </tr>`
            }
    }
    async function loadTreinadores() {
        try{
            const response = await
            fetch('http://localhost:8080/vitalusus/treinador/findAll',{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const allTreinadores = await response.json()
            console.log('Lista de treinadores: ', allTreinadores)
            exibirTreinadores(allTreinadores)
        }
        catch(error){
            console.error('erro ao buscar os itens', error)
        }
    }
loadTreinadores()

const searchInput = document.getElementById('searchId')
const cleanBtn = document.getElementById('cleanBtn')
searchInput.addEventListener('change', function() {
    if (searchInput.value !== '' && searchInput.value !== null) {
        cleanBtn.style.display = 'inline-block';
    } else {
        cleanBtn.style.display = 'none';
    }
});
//limpa o número do searchInput quando clica no botão de limpar
cleanBtn.addEventListener('click',() =>{
    searchInput.value = ''
    searchBtn.click()
    cleanBtn.style.display = 'none'
})
    // Função para buscar usuário por ID
   document.getElementById('search-form').addEventListener('submit', () => {
       event.preventDefault()
      const treinadorId = parseInt(document.getElementById('searchId').value);
        fetch(`http://localhost:8080/vitalusus/treinador/findById/${treinadorId}`)
          .then(res => res.json())
          .then(treinador => {
            exibirTreinador(treinador)
            if(treinador.usuario.statusUsuario == 'DELETADO'){
            loadTreinadores()
            }
          })
          .catch(() => {
            loadTreinadores()
          });
    });
  
    // Função para salvar ou atualizar um usuário
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      
      const treinadorId = document.getElementById('userId').value;
      const name = document.getElementById('name').value;
      const email = document.getElementById('email').value;
      const password = document.getElementById('password').value;
      const role = document.getElementById('role').value;
  
      const treinadorData = { name, email, password, role };
      
      if (treinadorId) {
        // Atualizar usuário
        fetch(`/api/treinadors/${treinadorId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(treinadorData),
        }).then(() => loadTreinadores());
      } else {
        // Criar novo usuário
        fetch('/api/treinadors', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(treinadorData),
        }).then(() => loadTreinadores());
      }
  
      form.reset();
    });
  
    // Função para editar um usuário
    window.edittreinador = (id) => {
      fetch(`/api/treinadors/${id}`)
        .then(res => res.json())
        .then(treinador => {
          document.getElementById('userId').value = treinador.id;
          document.getElementById('name').value = treinador.name;
          document.getElementById('email').value = treinador.email;
          document.getElementById('password').value = '';
          document.getElementById('role').value = treinador.role;
        });
    };
  
  
  
    loadTreinadores();
  });
  //Mudar Texto
     document.getElementById('ALUNO').addEventListener('click', function() {
     document.getElementById('h2mudar').textContent = 'Gerenciar Aluno';
     document.getElementById('h2muda').textContent = 'Buscar Aluno';
     document.getElementById('h2mud').textContent = 'Lista de Aluno';
});
    document.getElementById('TREINADOR').addEventListener('click', function() {
    document.getElementById('h2mudar').textContent = 'Gerenciar Treinador';
    document.getElementById('h2muda').textContent = 'Buscar Treinador';
    document.getElementById('h2mud').textContent = 'Lista de Treinador';
});

    document.getElementById('USUARIO').addEventListener('click', function() {
    document.getElementById('h2mudar').textContent = 'Gerenciar Usuario';
    document.getElementById('h2muda').textContent = 'Buscar Usuario';
    document.getElementById('h2mud').textContent = 'Lista de Usuario';
});

    