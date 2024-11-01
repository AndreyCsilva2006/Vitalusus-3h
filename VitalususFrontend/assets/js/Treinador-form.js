function trocarBotoes() {
        const select = document.getElementById('opcoes');
        const Cadastro = document.getElementById('Cadastro');
        const Login = document.getElementById('Login');
        const texto = document.getElementById('texto-alert')
        

        // Verifica qual opção foi selecionada e mostra/esconde os botões
        if (select.value === 'cadastro') {
            Cadastro.classList.remove('hidden');
            Login.classList.add('hidden');
            texto.textContent = "";
        } else if(select.value === 'login') {
            Cadastro.classList.add('hidden');
            Login.classList.remove('hidden');
            texto.textContent = "";
        }
       else{
            texto.Content = "";
            texto.textContent = "escolha sua forma de entrada, por favor"
            texto.style.color = "red"
            Cadastro.classList.add('hidden')
            Login.classList.add('hidden')
       }
    }



const formulario = document.getElementById('p-form');

// Adiciona o evento de pressionar tecla
formulario.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // Impede o envio padrão
        enviarDados(event); // Chama a função de enviar dados
    }
});

// Função para enviar dados do primeiro formulário
function enviarDados(event) {
    event.preventDefault(); // Impede o envio padrão do formulário

    // Coleta os dados do formulário
    const nome = document.getElementById('nm-treinador').value;
    const dataNasc = document.getElementById('dt-nsc').value;
    const cref = document.getElementById('cref').value;
    const messageElement = document.getElementById('message');
    const messageElement2 = document.getElementById('message2');
    const messageElement3 = document.getElementById('message3');

    let validDataNasc = true;
    let validNome = true;
    let validCref = true;

    // Validação da data de nascimento
    const data = new Date(dataNasc);
    const dataAtual = new Date();
    const anoNascimento = data.getFullYear();
    const idade = dataAtual.getFullYear() - anoNascimento;

    if (isNaN(data.getTime()) || anoNascimento <= 1900 || idade < 18) {
        messageElement.textContent = "A data de nascimento deve ser uma data válida e o usuário deve ter pelo menos 18 anos.";
        messageElement.style.color = 'red';
        console.error("Data de nascimento inválida:", dataNasc);
        validDataNasc = false;
    } else {
        messageElement.textContent = "Data de nascimento válida.";
        messageElement.style.color = 'green';
        console.log("Data de nascimento:", dataNasc);
    }

    // Validação do nome
if (!nome || nome.length < 3) {
    messageElement2.textContent = "O nome deve conter pelo menos 3 caracteres";
    messageElement2.style.color = "red";
    console.log("Nome inválido por ter menos de 3 caracteres, nome:", nome);
    validNome = false;
} else if (!/^[A-Z]/.test(nome)) { 
    // Verifica se o primeiro caractere é uma letra maiúscula
    messageElement2.textContent = "O nome deve começar com uma letra maiúscula";
    messageElement2.style.color = "red";
    console.log("Nome inválido por não começar com letra maiúscula, nome:", nome);
    validNome = false;
} else {
    // Regex para verificar apenas letras
    const nomePattern = /^[A-Za-zÀ-ÿ\s]+$/; 
    if (!nomePattern.test(nome)) {
        messageElement2.textContent = "O nome deve conter apenas letras.";
        messageElement2.style.color = 'red';
        console.error("Nome inválido:", nome);
        validNome = false;
    } else {
        messageElement2.textContent = "Nome válido.";
        messageElement2.style.color = 'green';
        console.log("Nome:", nome);
        validNome = true; // Nome é válido
    }
}


    // Validação do CREF
    const regex = /^[0-9]{6}-[GP]\/[A-Z]{2}(-[A-Z]{2})?(-[A-Z]{2}-[A-Z]{2})?$/;
    if (!regex.test(cref)) {
        messageElement3.textContent = "CREF inválido; CREF correto ex: 123456-G ou P/AB; se o cref for extenso ex:123456-G ou P/AB-AM-SP-BA ";
        messageElement3.style.color = 'red';
        console.error("CREF inválido:", cref);
        validCref = false;
    } else {
        messageElement3.textContent = "CREF válido.";
        messageElement3.style.color = 'green';
        console.log("CREF:", cref);
    }

    // Verifica se todas as validações foram bem-sucedidas
    if (validDataNasc && validNome && validCref) {
        const dataToStore = {
            treinador: {
                usuario: {
                    nome: nome,
                    dataNasc: new Date(dataNasc+'T00:00:00')
                },
                genero: document.getElementById('esc-gene').value,
                cref: cref
            }
        };

        localStorage.setItem('formData', JSON.stringify(dataToStore));
        console.log("Dados armazenados no localStorage:", dataToStore);
        
        // Chama a função para enviar os dados do segundo formulário
        window.location.href = 'Treinador-formulário(2).html';
    }
}

// Função para enviar dados do segundo formulário
function enviarDados2(event) {
    event.preventDefault(); // Impede o envio padrão do formulário
    const formData = JSON.parse(localStorage.getItem('formData'));
 
    // Coleta os dados do formulário
    const email = document.getElementById('email-1').value;
    const senha = document.getElementById('senha').value;
    const nomeCanal = document.getElementById('nomeCanalInput').value;
 
    // Validação do nome do canal
    const elementCanal = document.getElementById('messageCanal');
    if (nomeCanal.length < 3) {
        elementCanal.innerHTML = "O nome do Canal tem que ter pelo menos 3 letras.";
        elementCanal.style.color = "red";
        return;
    } else {
        elementCanal.innerHTML = "Nome do canal válido.";
        elementCanal.style.color = "green";
    }
 
        // Validação do email
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const elementEmail = document.getElementById('messageEmail');
 
    if (!emailPattern.test(email)) {
        elementEmail.innerHTML = "Email inválido.";
        elementEmail.style.color = "red";
        return; // Impede o envio se o email for inválido
    } else {
        elementEmail.innerHTML = "Email válido.";
        elementEmail.style.color = "green";
    }
    // Validação da senha
    const confirmarSenha = document.getElementById('confirmarSenha').value;
    const elementConfirm = document.getElementById('Confirm');
    const vefSenha = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

if (senha === "") {
    elementConfirm.innerHTML = "Digite algo!";
    elementConfirm.style.color = "red";
    return;
}

if (senha.length < 8 || !vefSenha.test(senha)) {
    elementConfirm.innerHTML = "A senha deve ter 8 caracteres, contendo pelo menos 1 caractere especial e no mínimo 1 letra maiúscula, 1 minúscula e 1 número.";
    elementConfirm.style.color = "red";
    return; // Adicionando return aqui para evitar continuar
}

if (senha !== confirmarSenha) {
    elementConfirm.innerHTML = "As senhas estão diferentes!";
    elementConfirm.style.color = "red";
    return; // Retorna aqui se as senhas não coincidirem
} else {
    elementConfirm.innerHTML = "Senhas compatíveis.";
    elementConfirm.style.color = "green";
}


  // Função para limpar mensagens de erro ao digitar
function limparErro(elementId) {
    const element = document.getElementById(elementId);
    element.innerHTML = ""; // Limpa a mensagem de erro
 
    // Limpa espaços em branco no campo correspondente
    const inputElement = document.getElementById(elementId.replace('message', '')); // Substitui 'message' para pegar o input correspondente
    if (inputElement) {
        inputElement.value = inputElement.value.trim(); // Aplica trim apenas aqui
    }
}
 
// Adicionando eventos de input para limpar erros
document.getElementById('nomeCanalInput').addEventListener('input', () => limparErro('messageCanal'));
document.getElementById('email-1').addEventListener('input', () => limparErro('messageEmail'));
document.getElementById('senha').addEventListener('input', () => limparErro('Confirm'));
document.getElementById('confirmarSenha').addEventListener('input', () => limparErro('Confirm'));
 
  
 
    const data2 = {
        nome: nomeCanal,
        treinador: {
            usuario: {
                nome: formData.treinador.usuario.nome,
                email: email,
                senha: senha,
                dataNasc: formData.treinador.usuario.dataNasc
                
            },
            genero: formData.treinador.genero,
            cref: formData.treinador.cref
        }
    };

    // Envia os dados para a API
    fetch('https://vitalusus-deploy.onrender.com/vitalusus/canal/post', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data2)
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                const errorCode = errorData.error.code || 'Código desconhecido';
                const errorMessage = errorData.message;
                window.alert(`${errorMessage}`)
                throw new Error(`Erro ${errorCode}: ${errorMessage}`);
            });
        }
        return response.json();
    })
    .then(data2 => {
        console.log('Dados do segundo formulário enviados com sucesso:', data2);
        window.location.href = 'login.html'; // Altere para o URL da próxima página
    })
    .catch((error) => {
        console.error('Erro ao enviar dados do segundo formulário:', error);
        const errorMessage = document.createElement('div');
        errorMessage.textContent = `Ocorreu um erro ao enviar os dados: ${error.message}`;
        errorMessage.style.color = 'red';
        document.body.appendChild(errorMessage);
    });
}
