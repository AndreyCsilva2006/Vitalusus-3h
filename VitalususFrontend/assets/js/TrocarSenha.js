// Validação da senha
document.getElementById('formSenha').addEventListener('submit', (event)=> {
    event.preventDefault(); // Impede o envio do formulário
    const urlParams = new URLSearchParams(window.location.search);
    const idUsuario = urlParams.get('id');
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;
    const elementConfirm = document.getElementById('message1');
    const elementConfirm2 = document.getElementById('message2');
    
    const trocarSenha = async()=>{
    try{
    const usuarioReturn = await usuarioFindById(idUsuario)
    const response = await
    fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/updateSenha/${usuarioReturn.id}`,{
        method:'PUT',
        headers:{
        'Content-Type':'application/json'
    },
        body: JSON.stringify({senha: senha})
    });
        if(!response.ok){
            const errorData = await response.json()
            alert('Ocorreu um erro: '+errorData.message)
        }
        const usuarioDataSuccess = await response.json()
        console.log(usuarioDataSuccess)
        window.location.href = 'login.html'
    }
    catch(error){
        console.error(error)
    }
}

    const vefSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    // Limpa mensagens anteriores
    limparErro('message1');
    limparErro('message2');

    // Verifica se os campos estão vazios
    if (senha === "" || confirmarSenha === "") {
        elementConfirm.innerHTML = "Digite algo!";
        elementConfirm.style.color = "red";
        elementConfirm2.innerHTML = "Digite algo aqui também!";
        elementConfirm2.style.color = "red";
        return;
    }

    if (senha.length < 8 || !vefSenha.test(senha)) {
        elementConfirm.innerHTML = "A senha deve ter 8 caracteres, contendo pelo menos 1 caractere especial e no mínimo 1 letra maiúscula, 1 minúscula e 1 número.";
        elementConfirm.style.color = "red";
        return;
    }

    if (senha !== confirmarSenha) {
        elementConfirm2.innerHTML = "As senhas estão diferentes!";
        elementConfirm2.style.color = "red";
        return;
    } else {
        elementConfirm2.innerHTML = "Senhas compatíveis.";
        elementConfirm2.style.color = "green";
        trocarSenha()
    }

});

// Função para limpar mensagens de erro
function limparErro(elementId) {
    const element = document.getElementById(elementId);
    element.innerHTML = ""; // Limpa a mensagem de erro
}

// Adiciona listeners de input para limpeza de erros
document.getElementById('senha').addEventListener('input', () => {
    limparErro('message1');
    limparErro('message2');
});

document.getElementById('confirmarSenha').addEventListener('input', () => {
    limparErro('message2');
});


//Consumos de API
async function usuarioFindById(id){
    try {
        const response = await fetch(`https://vitalusus-deploy.onrender.com/vitalusus/usuario/findById/${id}`,{
            method: 'GET',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const usuarioData = await response.json(); // Converte a resposta em json
        console.log('usuário: ', usuarioData)
        return usuarioData
        }
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
     catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}




