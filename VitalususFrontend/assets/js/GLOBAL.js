 // Define o idioma da página de acordo com o idioma do navegador
    document.documentElement.lang = navigator.language;


//PARTE DAS CONFIGURAÇÕES

// icone js
const button = document.getElementById('alterar-tema');
const altIcon = document.getElementById('altIcon'); // Ícone do sol
const altIconMoon = document.getElementById('altIcon-1'); // Ícone da lua (corrigido)

// Carregar tema ao iniciar
window.onload = () => {
    const tema = localStorage.getItem('tema');
    if (tema === 'light') {
        document.body.classList.add('light-theme');
        altIcon.style.display = 'none'; // Esconde o ícone do sol
        altIconMoon.style.display = 'block'; // Mostra o ícone da lua
    } else {
        document.body.classList.remove('light-theme');
        altIcon.style.display = 'block'; // Mostra o ícone do sol
        altIconMoon.style.display = 'none'; // Esconde o ícone da lua
    }
};

if (button){
    button.addEventListener('click', () => {
    document.body.classList.toggle('light-theme');
    
    if (document.body.classList.contains('light-theme')) {
        localStorage.setItem('tema', 'light');
        altIcon.style.display = 'none'; // Esconde o ícone do sol
        altIconMoon.style.display = 'block'; // Mostra o ícone da lua
    } else {
        localStorage.setItem('tema', 'dark');
        altIcon.style.display = 'block'; // Mostra o ícone do sol
        altIconMoon.style.display = 'none'; // Esconde o ícone da lua
    }
});
}








//FIM DA PARTE DAS CONFIGURAÇÕES

 // Inicializa a variável de controle
    
let readingEnabled = true; // Declare a variável fora do DOMContentLoaded

document.addEventListener('DOMContentLoaded', () => {
    const leituraButton = document.getElementById('readTextButton');
    let speechSynthesisUtterance = new SpeechSynthesisUtterance();

    const storedState = localStorage.getItem('readingEnabled');
    console.log('Leitura está ativada: ', storedState);
    
    if (storedState !== null) {
        readingEnabled = storedState === 'true'; // Converte a string para booleano
    }
    if (storedState == null) {
        readingEnabled = false; // Converte a string para booleano
    }
    // Atualiza o texto do botão
    let buttonText = readingEnabled ? 'Desativar leitura' : 'Ativar Leitura';
    if (leituraButton) {
        leituraButton.innerText = buttonText;
    }

    function readText(element, text) {
        if (!readingEnabled) return; // Se a leitura estiver desativada, sai da função
        window.speechSynthesis.cancel(); // Cancela qualquer leitura em andamento
        speechSynthesisUtterance = new SpeechSynthesisUtterance(text);
        speechSynthesisUtterance.lang = 'pt-BR';

        // Inicia a leitura
        window.speechSynthesis.speak(speechSynthesisUtterance);
    }

    function stopReading() {
        window.speechSynthesis.cancel(); // Cancela a leitura atual
    }

    const elementsToRead = document.querySelectorAll('p, span, div, h1, h2, h3, li, textarea');

    elementsToRead.forEach(element => {
        element.addEventListener('mouseover', (event) => {
            let text = event.target.innerText.trim(); // Obtém o texto do elemento e remove espaços extras
            if (element.tagName.toLowerCase() === 'textarea') {
                text = element.value; // Captura o valor do textarea
            }
            if (text) {
                readText(event.target, text); // Inicia a leitura se houver texto
            }
        });

        element.addEventListener('mouseout', stopReading); // Para a leitura quando o mouse sai
    });

    // Adiciona o evento de clique no botão para ativar/desativar a leitura
    if (leituraButton) {
        leituraButton.addEventListener('click', toggleLeitura);
    }
});

function toggleLeitura() {
    readingEnabled = !readingEnabled; // Alterna o estado de habilitação

    // Atualiza o estado no localStorage
    localStorage.setItem('readingEnabled', readingEnabled);

    // Atualiza o texto do botão
    const buttonText = readingEnabled ? 'Desativar leitura' : 'Ativar Leitura';
    document.getElementById('readTextButton').innerText = buttonText;

    console.log('Leitura está ativada: ', localStorage.getItem('readingEnabled'));
}
//FIM DO LEITOR DE TEXTO EM JS

//quando clicar no Aluno
alunoClicavel

//Acessibilidades
document.addEventListener('DOMContentLoaded', () => {
    document.addEventListener('keydown', (event) => {
        if (event.altKey && event.key === 'w') {
            window.location.href = '/canal.html'; // Substitua pelo URL da sua página
        }
    });})

//js para o menu do index
// Adicionando um evento de clique para fechar o menu ao clicar fora dele




