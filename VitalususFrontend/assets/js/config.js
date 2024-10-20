const txtBtnPrivacidade = document.getElementById('txt-button-nivelPrivacidade')
const nivelPrivacidadeCanal = JSON.parse(localStorage.getItem('loginData')).treinador.usuario.nivelPrivacidade
const iconePrivacidade = document.getElementById('icon-tmnh2')
if(nivelPrivacidadeCanal){
    
if (nivelPrivacidadeCanal == 'PUBLICO' && txtBtnPrivacidade){
    txtBtnPrivacidade.textContent = 'Público'
    iconePrivacidade.innerHTML = `<path d="M7 15C5.34315 15 4 13.6569 4 12C4 10.3431 5.34315 9 7 9C8.65685 9 10 10.3431 10 12C10 13.6569 8.65685 15 7 15Z" fill="currentColor"></path>
    <path fill-rule="evenodd" clip-rule="evenodd" d="M24 12C24 8.13401 20.866 5 17 5H7C3.13401 5 0 8.13401 0 12C0 15.866 3.13401 19 7 19H17C20.866 19 24 15.866 24 12ZM17 7H7C4.23858 7 2 9.23858 2 12C2 14.7614 4.23858 17 7 17H17C19.7614 17 22 14.7614 22 12C22 9.23858 19.7614 7 17 7Z" fill="currentColor"></path>`
}
    
if (nivelPrivacidadeCanal == 'PRIVADO' && txtBtnPrivacidade){
    txtBtnPrivacidade.textContent = 'Privado'
    iconePrivacidade.innerHTML = `<path d="M17 15C18.6569 15 20 13.6569 20 12C20 10.3431 18.6569 9 17 9C15.3431 9 14 10.3431 14 12C14 13.6569 15.3431 15 17 15Z" fill="currentColor"></path>
    <path fill-rule="evenodd" clip-rule="evenodd" d="M0 12C0 8.13401 3.13401 5 7 5H17C20.866 5 24 8.13401 24 12C24 15.866 20.866 19 17 19H7C3.13401 19 0 15.866 0 12ZM7 7H17C19.7614 7 22 9.23858 22 12C22 14.7614 19.7614 17 17 17H7C4.23858 17 2 14.7614 2 12C2 9.23858 4.23858 7 7 7Z" fill="currentColor"></path>`
}
    
}
async function tornarPrivado() {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/canal/tornarPrivado/${canalData.id}`,{
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
     window.location.href = "Conf-Principal.html"
}

async function tornarPublico() {
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/canal/tornarPublico/${canalData.id}`,{
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
     window.location.href = "Conf-Principal.html"
}
function inativar() {
    if(confirm('Tem certeza que deseja desativar sua conta?')){
    const desativar = async()=>{
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/usuario/inativar/${canalData.treinador.usuario.id}`,{
            method: 'PUT',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            const errorData = await response.json();
            window.alert(errorData.message)
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const data = await response.json(); // Converte a resposta em JSON        // Aqui você pode fazer algo com os dados, como exibi-los na tela
        localStorage.setItem('loginData', '')
        window.location.href = "index.html"
    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
    }
    desativar()
    }
}
function deletarConta() {
    if(confirm("Tem certeza que deseja deletar sua conta? Essa ação não poderá ser desfeita")){
    const deletar = async()=>{
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/usuario/deletar/${canalData.treinador.usuario.id}`,{
            method: 'PUT',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const data = await response.json(); // Converte a resposta em JSON        // Aqui você pode fazer algo com os dados, como exibi-los na tela
        localStorage.setItem('loginData', '')
        window.location.href = "index.html"
    } catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
    }
    deletar()
    }
}
function mudarPrivacidade(event){
    if (txtBtnPrivacidade.textContent == 'Público'){
        tornarPrivado()
    }
    if (txtBtnPrivacidade.textContent == 'Privado'){
        tornarPublico()
    }
}

function sairConta(event){
    window.location.href = 'login.html'
    localStorage.setItem('loginData', '')
}
