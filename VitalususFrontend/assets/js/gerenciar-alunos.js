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
   function deleteAluno(id) {
    if (confirm("Você tem certeza que deseja excluir este aluno?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/deletar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            window.location.href = 'gerenciar-alunos.html'
        }
        catch(error){
            console.error('erro ao buscar o aluno', error)
        }
        }
        fetchDeletar()
    }
}

 // Função para ativar um usuário
   function ativarAluno(id) {
    if (confirm("Você tem certeza que deseja ativar este aluno?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/reativar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const alunoData = await response.json()
            window.location.href = 'gerenciar-alunos.html'
        }
        catch(error){
            console.error('erro ao buscar o aluno', error)
            window.alert('Você não pode reativar este aluno, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}

 // Função para inativar um usuário
   function inativarAluno(id) {
    if (confirm("Você tem certeza que deseja inativar este aluno?")) {
        const fetchDeletar = async() =>{
             try{
            const response = await
            fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/inativar/${id}`,{
                method:'PUT',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const alunoData = await response.json()
            window.location.href = 'gerenciar-alunos.html'
        }
        catch(error){
            console.error('erro ao buscar o aluno', error)
            window.alert('Você não pode reativar este aluno, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para banir um usuário
   function banirAluno(id) {
    if (confirm("Você tem certeza que deseja banir este aluno?")) {
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
            const alunoData = await response.json()
            window.location.href = 'gerenciar-alunos.html'
        }
        catch(error){
            console.error('erro ao buscar o aluno', error)
            window.alert('Você não pode reativar este aluno, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para desbanir um usuário
   function desbanirAluno(id) {
    if (confirm("Você tem certeza que deseja desbanir este aluno?")) {
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
            const alunoData = await response.json()
            window.location.href = 'gerenciar-alunos.html'
        }
        catch(error){
            console.error('erro ao buscar o aluno', error)
            window.alert('Você não pode reativar este aluno, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById('treinador-form');
    const searchBtn = document.getElementById('searchBtn');
    const alunoTable = document.querySelector('#aluno-table tbody');
    
    // Função para carregar todos os usuários
    function exibirAlunos(alunos){
        const tabelaAluno = document.getElementById('bodyTableTreinador')
        tabelaAluno.innerHTML = ''
        alunos.forEach(aluno => {
            if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'ATIVO'){
            tabelaAluno.innerHTML +=  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAluno(${aluno.usuario.id})">Banir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                <button onclick="inativarAluno(${aluno.usuario.id})">Desativar</button>
                </td>
              </tr>`
            }
            
             if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'INATIVO'){
            tabelaAluno.innerHTML +=  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAluno(${aluno.usuario.id})">Banir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                  <button onclick="ativarAluno(${aluno.usuario.id})">Ativar</button>
                </td>
              </tr>`
            }
            
             if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'BANIDO'){
            tabelaAluno.innerHTML +=  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="desbanirAluno(${aluno.usuario.id})">Desbanir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                </td>
              </tr>`
            }
        })
    }
     // Função para carregar só um aluno
    function exibirAluno(aluno){
            const tabelaAluno = document.getElementById('bodyTableTreinador')
            if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'ATIVO'){
            tabelaAluno.innerHTML =  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAluno(${aluno.usuario.id})">Banir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                <button onclick="inativarAluno(${aluno.usuario.id})">Desativar</button>
                </td>
              </tr>`
            }
            
             if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'INATIVO'){
            tabelaAluno.innerHTML +=  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAluno(${aluno.usuario.id})">Banir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                  <button onclick="ativarAluno(${aluno.usuario.id})">Ativar</button>
                </td>
              </tr>`
            }
            
             if(aluno.usuario.statusUsuario != "DELETADO" && aluno.usuario.statusUsuario == 'BANIDO'){
            tabelaAluno.innerHTML +=  `<tr>
                <td>${aluno.id}</td>
                <td>${aluno.usuario.nome}</td>
                <td>${aluno.usuario.email}</td>
                <td>${aluno.usuario.nivelAcesso}</td>
                <td>${aluno.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="desbanirAluno(${aluno.usuario.id})">Desbanir</button>
                  <button onclick="deleteAluno(${aluno.usuario.id})">Deletar</button>
                </td>
              </tr>`
            }
    }
    async function loadAlunos() {
        try{
            const response = await
            fetch('https://vitalusus-deploy.onrender.com/vitalusus/aluno/findAll',{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const allAlunos = await response.json()
            console.log('Lista de alunos: ', allAlunos)
            exibirAlunos(allAlunos)
        }
        catch(error){
            console.error('erro ao buscar os itens', error)
        }
    }
loadAlunos()

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
      const alunoId = parseInt(document.getElementById('searchId').value);
        fetch(`https://vitalusus-deploy.onrender.com/vitalusus/aluno/findById/${alunoId}`)
          .then(res => res.json())
          .then(aluno => {
            exibirAluno(aluno)
            if(aluno.usuario.statusUsuario == 'DELETADO'){
            loadAlunos()
            }
          })
          .catch(() => {
            loadAlunos()
          });
    });
  
    // Função para salvar ou atualizar um usuário
    form.addEventListener('submit', (e) => {
      e.preventDefault();
      
      const alunoId = document.getElementById('userId').value;
      const name = document.getElementById('name').value;
      const email = document.getElementById('email').value;
      const password = document.getElementById('password').value;
      const role = document.getElementById('role').value;
  
      const alunoData = { name, email, password, role };
      
      if (alunoId) {
        // Atualizar usuário
        fetch(`/api/alunos/${alunoId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(alunoData),
        }).then(() => loadAlunos());
      } else {
        // Criar novo usuário
        fetch('/api/alunos', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(alunoData),
        }).then(() => loadAlunos());
      }
  
      form.reset();
    });
  
    // Função para editar um usuário
    window.editaluno = (id) => {
      fetch(`/api/alunos/${id}`)
        .then(res => res.json())
        .then(aluno => {
          document.getElementById('userId').value = aluno.id;
          document.getElementById('name').value = aluno.name;
          document.getElementById('email').value = aluno.email;
          document.getElementById('password').value = '';
          document.getElementById('role').value = aluno.role;
        });
    };
  
  
  
    loadAlunos();
  });
  //Mudar Texto
     document.getElementById('ALUNO').addEventListener('click', function() {
     document.getElementById('h2mudar').textContent = 'Gerenciar Aluno';
     document.getElementById('h2muda').textContent = 'Buscar Aluno';
     document.getElementById('h2mud').textContent = 'Lista de Aluno';
});
    document.getElementById('TREINADOR').addEventListener('click', function() {
    document.getElementById('h2mudar').textContent = 'Gerenciar Aluno';
    document.getElementById('h2muda').textContent = 'Buscar Aluno';
    document.getElementById('h2mud').textContent = 'Lista de Aluno';
});

    document.getElementById('USUARIO').addEventListener('click', function() {
    document.getElementById('h2mudar').textContent = 'Gerenciar Usuario';
    document.getElementById('h2muda').textContent = 'Buscar Usuario';
    document.getElementById('h2mud').textContent = 'Lista de Usuario';
});

    