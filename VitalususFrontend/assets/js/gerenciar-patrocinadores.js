async function deletarEquip(id, patrocinadorId){
    try{
        const response = await     fetch(`http://localhost:8080/vitalusus/equipamento/deletar/${id}`, {
            method:'PUT',
            headers:{
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const equipamentosData = await response.json()
        console.log(equipamentosData)
        openModal(patrocinadorId)
        }   
        catch(error){
        console.error(error)
        }
}
function editarEquip(id, patrocinadorId, event){
    event.preventDefault()
    const data = {
        nome: document.getElementById('nomeEquip'+id).value,
        link: document.getElementById('linkEquip'+id).value,
    }
    const editar = async()=>{
         try{
        const response = await     fetch(`http://localhost:8080/vitalusus/equipamento/update/${id}/${patrocinadorId}`, {
            method:'PUT',
            headers:{
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if(!response.ok){
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const equipamentosData = await response.json()
        console.log(equipamentosData)
        openModal(patrocinadorId)
        }   
        catch(error){
        console.error(error)
        }
    }
    editar()
}

let patrocinadorFoto64
//Modal dos equipamentos 
const tabela = document.getElementById("tabela")
const modal = document.getElementById("modal")
 // Quando o usuário clicar no botão, abrir o modal
function exibirModal(equipamentos) {
    const equipamentosContainer = document.getElementById('equipamentos-container');
    const modal = document.getElementById("modal");
    const mainNav = document.getElementById('mainNav');

    // Verifique se o modal existe
    if (!modal) {
        console.error("Modal não encontrado!");
        return;
    }

    if (!modal.style.display || modal.style.display === 'none') {
        modal.style.display = "flex";
        mainNav.style.setProperty('display', 'none', 'important'); // Oculta o mainNav
        
        // Verificação se o display foi alterado
        if (getComputedStyle(mainNav).display === 'none') {
            console.log("mainNav está oculto.");
        } else {
            console.log("mainNav NÃO está oculto.");
        }
    }

    // Certifique-se de que 'equipamentos' é um array
    if (Array.isArray(equipamentos)) {
        equipamentosContainer.innerHTML = '';
        equipamentos.forEach(equipamento => {
            if (equipamento.statusEquipamento === 'ATIVO') {
                equipamentosContainer.innerHTML += `
                    <div class="caixinha-PT">
                        <div id="equipamentos-nome-container" class="flex-column elementos2" style="flex-grow: 2; max-width: 40%;">
                            <span id="nomePatrocinador-1" style="max-width: 100%; overflow-x: auto; white-space: nowrap; font-family: 'Inter Tight';" maxlength="800">${equipamento.nome}</span>
                        </div>
                        <div id="equipamentos-link-container" class="flex-column elementos2" style="flex-grow: 1; max-width: 30%;">
                            <a id="linkPatrocinador-1" class="link-max" href="${equipamento.link}" target="_blank" style="color: var(--padrao);" maxlength="800">${equipamento.link}</a>
                        </div>
                        <div class="elementos2" style="flex-grow: 1; max-width: 30%;">
                            <button class="button-submit btn-cor5" type="button" onclick="formEditar(${equipamento.id})">Editar</button>
                            <button class="button-submit btn-cor5" type="button" onclick="deletarEquip(${equipamento.id}, ${equipamento.patrocinador.id})">Excluir</button>
                        </div>
                    </div>
                    <form onsubmit="editarEquip(${equipamento.id}, ${equipamento.patrocinador.id}, event)" id="editar${equipamento.id}" class="criar" style="display: none;">
                        <label class="form-label" style="max-width: 100%; overflow: hidden;">Nome: <input id="nomeEquip${equipamento.id}" class="input-generico" type="text" value="${equipamento.nome}"/></label>
                        <label class="form-label" style="max-width: 100%; overflow: hidden;">Link: <input id="linkEquip${equipamento.id}" class="input-generico" type="text" style="color: var(--padrao);" value="${equipamento.link}" /></label>
                        <div class="d-lg-flex">
                        <button class="button-submit btn-cor5" type="submit" style="border: none;outline: none;display: flex;justify-content: center;align-items: center;">Salvar</button>
                        <button class="button-submit" type="button" style="background: none;border: none;outline: none;display: flex;justify-content: center;align-items: center;" onclick="formEditarFechar(${equipamento.id})">Cancelar<i class="material-icons">clear</i></button>
                        </div>
                    </form>
                `;
            }
        });
    } else {
        console.error("Equipamentos não é um array válido!");
    }
}


async function openModal(id){
        try{
        const response = await
        fetch(`http://localhost:8080/vitalusus/equipamento/findAllByPatrocinador/${id}`, {
            method:'GET',
            headers:{
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const equipamentosData = await response.json()
        console.log(equipamentosData)
        exibirModal(equipamentosData)
            document.getElementById('criarEquipbtn').addEventListener('click', () =>{
            formCriar(id)
        })
        }   
        catch(error){
        console.error(error)
        }
            }

        // Quando o usuário clicar no "x", fechar o modal
        function closeModal() {
                const mainNav = document.getElementById('mainNav');

            document.getElementById("modal").style.display = "none";
             mainNav.style.setProperty('display', 'block', 'important')
        }

//Form para o criar Equipamentos
let currentPatrocinadorId = null;
function formCriar(id){
    currentPatrocinadorId = id
    const formC = document.getElementById("criar");
    formC.style.display = "flex";
}
const criarEquipForm = document.getElementById('criar-equipamento-form')
criarEquipForm.addEventListener('submit',(event)=>{
    event.preventDefault()
    const data = {
        nome: document.getElementById('formCriar-nome-equip').value,
        link: document.getElementById('formCriar-link-equip').value
    }
    const enviar = async()=>{
        try{
        const response = await
        fetch(`http://localhost:8080/vitalusus/equipamento/post/${currentPatrocinadorId}`, {
            method:'POST',
            headers:{
                'Content-Type': 'application/json'
            },
            body:JSON.stringify(data)
        });
        if(!response.ok){
            const errorData = await response.json()
            window.alert(errorData.message)
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const equipamentosData = await response.json()
        console.log(equipamentosData)
        document.getElementById('formCriar-nome-equip').value = ''
        document.getElementById('formCriar-link-equip').value =  ''
        openModal(currentPatrocinadorId)
        formFechar()
        }   
        catch(error){
        console.error(error)
        }
            }
    enviar()
})
function formFechar(){
    const formC = document.getElementById("criar");
    formC.style.display = "none";
}
window.onclick = function(event) {
            if (event.target == document.getElementById("criar")) {
                document.getElementById("criar").style.display = "none";
            }
    if (event.target == document.getElementById("modalEdit")) {
                document.getElementById("modalEdit").style.display = "none";
            }
    if (event.target == document.getElementById("modal")) {
                closeModal()
            }
        }
function formEditar(id){
    const formE = document.getElementById("editar"+id);
    formE.style.display = "flex";
    formFechar(id)
}
function formEditarFechar(id){
    const formE = document.getElementById("editar"+id);
    formE.style.display = "none";
}
let patrocinadorFoto64Editar
let idPatroEditar
function editSponsor(id){
    document.getElementById("modalEdit").style.display = 'flex';
    const nome =  document.getElementById('formEditarPatro-nome')
    const link = document.getElementById('formEditarPatro-link')
    const foto = document.getElementById('formEditarPatro-foto')
    const fotoSubmit = document.getElementById('formEditarPatro-fotoShow')
    idPatroEditar = id
    
    const findPatro = async()=>{
        try{
        const response = await
        fetch(`http://localhost:8080/vitalusus/patrocinador/findById/${id}`,{
            method:'GET',
            headers:{
                'Content-Type':'application/json'
            }
        });
            if(!response.ok){
                const errorData = await response.json()
                console.log('Ocorreu um erro: ', errorData.message)
            }
            const patroData = await response.json()
            nome.value = patroData.nome
            link.value = patroData.link
            fotoSubmit.src = `data:image/jpeg;base64,${patroData.foto}`
        }
        catch(error){
            console.error(error)
        }
    }
    findPatro()
}
// Função para excluir um patrocinador
 function deleteSponsor(id){
    if (confirm("Você tem certeza que deseja excluir este patrocinador?")) {
        const deletar = async()=>{
        try{
        const response = await
        fetch(`http://localhost:8080/vitalusus/patrocinador/deletar/${id}`,{
            method: 'PUT',
            headers:{
                'Content-Type': 'application/json'
            }
        })
        if (!response){
            throw new Error("Erro na requisição: ", response.statusText)
        }
            document.getElementById('patrocinadorTableBody').innerHTML = ''
            findPatrocinadores()
        }
        catch(error){
            console.error(error)
        }
    }
        deletar()    
 }}
// Função para renderizar a lista de patrocinadores
    function renderSponsors(patrocinadores) {
        const patrocinadorTableBody = document.getElementById('patrocinadorTableBody')
        patrocinadorTableBody.innerHTML = '';
        patrocinadores.forEach(patrocinador => {
            if(patrocinador.statusPatrocinador == "ATIVO"){
            patrocinadorTableBody.innerHTML += `
                <td>${patrocinador.nome}</td>
                <td><a href="${patrocinador.link}" target="_blank">${patrocinador.link}</a></td>
                <td><img class="patroFoto" src="data:image/jpeg;base64,${patrocinador.foto}" alt="Foto da Logo do Patrocinador"></td>
                <td>
                    <button onclick="editSponsor(${patrocinador.id})">Editar</button>
                    <button onclick="deleteSponsor(${patrocinador.id})">Excluir</button>

                    <button id="buttonMM" type="button" onclick="openModal(${patrocinador.id})">
                     Equipamentos</button>
                </td>
                <td>
  
                </td>
            `;
            }
        });
    }

async function findPatrocinadores(){
    try{
        const response = await
        fetch('http://localhost:8080/vitalusus/patrocinador/findAll', {
            method:'GET',
            headers:{
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const patrocinadoresData = await response.json()
        console.log(patrocinadoresData)
        renderSponsors(patrocinadoresData)
    }
    catch(error){
        console.error(error)
    }
}

findPatrocinadores()


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
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('sponsor-form');
    const sponsorTable = document.getElementById('sponsor-table').querySelector('tbody');

    let sponsors = [];


    // Adicionar patrocinador
    form.addEventListener('submit', (event) => {
        event.preventDefault();
        const nome = document.getElementById('name').value;
        const website = document.getElementById('website').value;
        const data = {
            nome: nome,
            link: website,
            foto: patrocinadorFoto64.split(',')[1]
        }
        const enviarDados = async() =>{
            try{
            const response = await
            fetch('http://localhost:8080/vitalusus/patrocinador/post',{
                method:'POST',
                body: JSON.stringify(data),
                headers:{
                    'Content-Type': 'application/json'
                }
            });
                if(!response.ok){
                    throw new Error('Erro na requisição: ', response.statusText)
                }                
                const patrocinadorData = response.json()
                console.log(patrocinadorData)
                findPatrocinadores()
                document.getElementById('name').value = ''
                document.getElementById('website').value = ''
                const addImg = document.getElementById('addImg')
                const removeImg = document.getElementById('removeImg')
                const fileInput = document.getElementById('input-foto')
                const imageContainer = document.getElementById('imageContainer')
                const imgTxt = document.getElementById('imgTxt')
                document.getElementById('input-foto').value = ''
                imageContainer.src= ''
                imageContainer.style.display = 'none'
                addImg.style.display = 'block'
                removeImg.style.display = 'none'
                patrocinadorFoto64 = ''
                imgTxt.textContent = 'Adicionar foto da logo do patrocinador'

            }
            catch(error){
                console.error(error)
            }
        }
        enviarDados()
    });

});
  // Adicionar patrocinador
    document.getElementById('editarPatroForm').addEventListener('submit', (event) => {
        event.preventDefault();
        const nome = document.getElementById('formEditarPatro-nome').value;
        const website = document.getElementById('formEditarPatro-link').value;
        const data = {
            nome: nome,
            link: website,
            foto: patrocinadorFoto64Editar.split(',')[1]
        }
        const enviarDados = async() =>{
            try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/patrocinador/update/${idPatroEditar}`,{
                method:'PUT',
                body: JSON.stringify(data),
                headers:{
                    'Content-Type': 'application/json'
                }
            });
                if(!response.ok){
                    throw new Error('Erro na requisição: ', response.statusText)
                }                
                const patrocinadorData = response.json()
                console.log(patrocinadorData)
                findPatrocinadores()

            }
            catch(error){
                console.error(error)
            }
        }
        enviarDados()
    });
const addImg = document.getElementById('addImg')
const removeImg = document.getElementById('removeImg')
const fileInput = document.getElementById('input-foto')
const imageContainer = document.getElementById('imageContainer')
const imgTxt = document.getElementById('imgTxt')
addImg.addEventListener('click', () =>{
    document.getElementById('input-foto').click()
})
removeImg.addEventListener('click', () =>{
    document.getElementById('input-foto').value = ''
    imageContainer.src= ''
    imageContainer.style.display = 'none'
    addImg.style.display = 'block'
    removeImg.style.display = 'none'
    patrocinadorFoto64 = ''
    imgTxt.textContent = 'Adicionar foto da logo do patrocinador'
})
fileInput.addEventListener('change', (event) => {
            fileImg = event.target.files[0];
                const reader = new FileReader();
                reader.onload = function(e) {
                    imageContainer.src = e.target.result;
                    imageContainer.style.display = 'block';
                    addImg.style.display = 'none'
                    removeImg.style.display = 'block'
                    imgTxt.textContent = 'Remover foto da logo do patrocinador'
                    patrocinadorFoto64 = e.target.result
                    console.log(patrocinadorFoto64)
                };
                reader.readAsDataURL(fileImg);
        });


const addImgEditar = document.getElementById('fotoPatroEditar')
const fileInputEditar = document.getElementById('formEditarPatro-foto')
const imageContainerEditar = document.getElementById('formEditarPatro-fotoShow')
addImgEditar.addEventListener('click', () =>{
    fileInputEditar.click()
})
fileInputEditar.addEventListener('change', (event) => {
            fileImgEditar = event.target.files[0];
                const reader = new FileReader();
                reader.onload = function(e) {
                    imageContainerEditar.src = e.target.result;
                    patrocinadorFoto64Editar = e.target.result
                    console.log(patrocinadorFoto64Editar)
                };
                reader.readAsDataURL(fileImgEditar);
        });