// Simulando uma chamada à API
async function fetchSponsors() {
        try{
            const response = await
            fetch('https://vitalusus-deploy.onrender.com/vitalusus/patrocinador/findAll',{
                method:'GET',
                 headers: {
                'Content-Type': 'application/json'
        },
            });
            if(!response.ok){
            throw new Error('Erro na requisição: ' + response.statusText);
            }
            const patroData = await response.json()
            return patroData
        }
        catch(error){
            console.error('erro ao buscar os itens', error)
        }
    }
// Função para renderizar os patrocinadores no DOM
const renderSponsors = async () => {
    const sponsors = await fetchSponsors();
       const carouselContainer = document.getElementById('carousel-container'); // Seleciona o contêiner do carrossel

    // Cria dinamicamente o div "container22" dentro de "carousel-container"
    const container = document.getElementById('container');

    sponsors.forEach((sponsor, index) => {
        const sponsorDiv = document.createElement('div'); // Cria um div para cada patrocinador
        sponsorDiv.className = 'img-sla'; // Adiciona a classe para cada patrocinador
        sponsorDiv.id = `container-Patro-${index + 1}`; // Adiciona um ID único para cada div

        // Cria o conteúdo do patrocinador
        if (sponsor.statusPatrocinador == 'ATIVO'){
        carouselContainer.style.display = 'block'
        sponsorDiv.innerHTML = `
            <img class="img-patrão" alt="shoe, sports, training" src="data:image/jpeg;base64,${sponsor.foto}" />
            <h2 class="ajust">${sponsor.nome}</h2>
            <a href="${sponsor.link}" target="_blank"><button class="btn-cor-3" data-target="desc-${index + 1}">Ver Website do Patrocinador</button></a>
        `;

        container.appendChild(sponsorDiv); // Adiciona o div ao container
        }
    });
};

// Função para mostrar/ocultar a descrição
const toggleDescription = (id, button) => {
    const element = document.getElementById(id);
    const isHidden = window.getComputedStyle(element).display === "none";

    if (isHidden) {
        element.style.display = "inline-block";  // Exibe a descrição
        button.innerText = "Mostrar Menos";  // Altera o texto do botão
    } else {
        element.style.display = "none";  // Oculta a descrição
        button.innerText = "Mostrar Mais";  // Altera o texto do botão
    }
};

// Função para inicializar os botões
const initialize = () => {
    const buttons = document.querySelectorAll('button');
    
    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const descId = button.getAttribute('data-target');  // Obtém o ID da descrição correspondente
            toggleDescription(descId, button);  // Chama a função de alternância
        });
    });
};

// Garante que as funções sejam chamadas após o carregamento da página
window.onload = async () => {
    await renderSponsors(); // Renderiza os patrocinadores
    initialize(); // Inicializa os botões
};

//carrousel

let currentIndex = 0;

function moveCarousel(direction) {
    const carousel = document.querySelector('.container22');
    const items = document.querySelectorAll('.img-sla');
    const totalItems = items.length;

    // Atualiza o índice atual
    currentIndex += direction;

    // Restringe o índice para não sair do intervalo
    if (currentIndex < 0) {
        currentIndex = totalItems - 1; // Volta para o último item
    } else if (currentIndex >= totalItems) {
        currentIndex = 0; // Volta para o primeiro item
    }

    // Move o carrossel
    const offset = -currentIndex * 310; // 300px (largura do item) + 10px (margem)
    carousel.style.transform = `translateX(${offset}px)`;
}
