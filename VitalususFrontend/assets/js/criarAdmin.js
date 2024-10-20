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
   function deleteAdmin(id) {
    if (confirm("Você tem certeza que deseja excluir este admin?")) {
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
            loadAdmins()
        }
        catch(error){
            console.error('erro ao buscar o admin', error)
        }
        }
        fetchDeletar()
    }
}

 // Função para ativar um usuário
   function ativarAdmin(id) {
    if (confirm("Você tem certeza que deseja ativar este admin?")) {
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
            const adminData = await response.json()
            loadAdmins()
        }
        catch(error){
            console.error('erro ao buscar o admin', error)
            window.alert('Você não pode reativar este admin, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}

 // Função para inativar um usuário
   function inativarAdmin(id) {
    if (confirm("Você tem certeza que deseja inativar este admin?")) {
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
            const adminData = await response.json()
            loadAdmins()
        }
        catch(error){
            console.error('erro ao buscar o admin', error)
            window.alert('Você não pode reativar este admin, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para banir um usuário
   function banirAdmin(id) {
    if (confirm("Você tem certeza que deseja banir este admin?")) {
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
            const adminData = await response.json()
            loadAdmins()
        }
        catch(error){
            console.error('erro ao buscar o admin', error)
            window.alert('Você não pode reativar este admin, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}
 // Função para desbanir um usuário
   function desbanirAdmin(id) {
    if (confirm("Você tem certeza que deseja desbanir este admin?")) {
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
            const adminData = await response.json()
            loadAdmins()
        }
        catch(error){
            console.error('erro ao buscar o admin', error)
            window.alert('Você não pode reativar este admin, porque ele foi deletado ou banido')
        }
        }
        fetchDeletar()
    }
}

function generateUUID() {
    // Gera um UUID versão 4
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        const r = Math.random() * 16 | 0;
        const v = c === 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

document.getElementById('generateBtn').addEventListener('click', function() {
    event.preventDefault()
    const uuid = generateUUID();
    document.getElementById('uuidInput').value = uuid;
});

function adicionarAdmin(event){
    event.preventDefault()
    const data ={
        usuario:{
            nome: document.getElementById('name').value,
            email: document.getElementById('email').value,
            senha: document.getElementById('uuidInput').value,
            dataNasc: new Date(document.getElementById('dataNasc').value + 'T00:00:00')
        }
    }
     const enviarDados = async()=>{
    try{
        const response = await 
        fetch(`http://localhost:8080/vitalusus/admin/post`,{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
        },
            body: JSON.stringify(data)
        });
        if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
        admin = await response.json()
        console.log('admin criado: ', admin)
        window.alert('Admin '+admin.usuario.nome+' criado')
        loadAdmins()
    }
    catch(error){
        console.error('erro ao criar admin', error)
    }
     }
     enviarDados()
}
    // Função para carregar todos os admins
    function exibirAdmins(admins){
        const tabelaAdmin = document.getElementById('bodyTableAdmin')
        const options = { 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric', 
};
        tabelaAdmin.innerHTML = ''
        admins.forEach(admin => {
            const dataNascBase = new Date(admin.usuario.dataNasc)
            const dataNasc = new Date(dataNascBase)
            if(admin.usuario.statusUsuario != "DELETADO" && admin.usuario.statusUsuario == 'ATIVO'){
            tabelaAdmin.innerHTML +=  `<tr>
                <td>${admin.id}</td>
                <td>${admin.usuario.nome}</td>
                <td>${admin.usuario.email}</td>
                <td>${admin.usuario.chaveSeguranca}</td>
                <td>${atob(admin.usuario.senha)}</td>
                <td>${dataNasc.toLocaleDateString('pt-BR', options)}</td>
                <td>${admin.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAdmin(${admin.usuario.id})">Banir</button>
                  <button onclick="deleteAdmin(${admin.usuario.id})">Deletar</button>
                <button onclick="inativarAdmin(${admin.usuario.id})">Desativar</button>
                </td>
              </tr>`
            }
            
             if(admin.usuario.statusUsuario != "DELETADO" && admin.usuario.statusUsuario == 'INATIVO'){
            tabelaAdmin.innerHTML +=  `<tr>
                <td>${admin.id}</td>
                <td>${admin.usuario.nome}</td>
                <td>${admin.usuario.email}</td>
                <td>${admin.usuario.chaveSeguranca}</td>
                <td>${atob(admin.usuario.senha)}</td>
                <td>${dataNasc.toLocaleDateString('pt-BR', options)}</td>
                <td>${admin.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="banirAdmin(${admin.usuario.id})">Banir</button>
                  <button onclick="deleteAdmin(${admin.usuario.id})">Deletar</button>
                  <button onclick="ativarAdmin(${admin.usuario.id})">Ativar</button>
                </td>
              </tr>`
            }
            
             if(admin.usuario.statusUsuario != "DELETADO" && admin.usuario.statusUsuario == 'BANIDO'){
            tabelaAdmin.innerHTML +=  `<tr>
                <td>${admin.id}</td>
                <td>${admin.usuario.nome}</td>
                <td>${admin.usuario.email}</td>
                <td>${admin.usuario.chaveSeguranca}</td>
                <td>${atob(admin.usuario.senha)}</td>
                <td>${dataNasc.toLocaleDateString('pt-BR', options)}</td>
                <td>${admin.usuario.statusUsuario}</td>
                <td class="actions">
                <button onclick="desbanirAdmin(${admin.usuario.id})">Desbanir</button>
                  <button onclick="deleteAdmin(${admin.usuario.id})">Deletar</button>
                </td>
              </tr>`
            }
        })
    }
 async function loadAdmins() {
        try{
            const response = await
            fetch('http://localhost:8080/vitalusus/admin/findAll',{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const allAdmins = await response.json()
            console.log('Lista de admin: ', allAdmins)
            exibirAdmins(allAdmins)
        }
        catch(error){
            console.error('erro ao buscar os itens', error)
        }
    }
loadAdmins()