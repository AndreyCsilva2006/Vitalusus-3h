document.getElementById('main-admin').style.display = 'none'
if (JSON.parse(localStorage.getItem('loginData')).usuario){
    document.getElementById('main-admin').style.display = 'block'
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('sponsor-form');
    const sponsorTable = document.getElementById('sponsor-table').querySelector('tbody');

    let sponsors = [];

    // Função para renderizar a lista de patrocinadores
    function renderSponsors() {
        sponsorTable.innerHTML = '';
        sponsors.forEach((sponsor, index) => {
            const row = document.createElement('tr');

            row.innerHTML = `
                <td>${sponsor.name}</td>
                <td><a href="${sponsor.website}" target="_blank">${sponsor.website}</a></td>
                <td>
                    <button onclick="editSponsor(${index})">Editar</button>
                    <button onclick="deleteSponsor(${index})">Excluir</button>
                </td>
            `;
            sponsorTable.appendChild(row);
        });
    }

    // Adicionar patrocinador
    form.addEventListener('submit', (event) => {
        event.preventDefault();

        const name = document.getElementById('name').value;
        const website = document.getElementById('website').value;

        sponsors.push({ name, website });
        form.reset();
        renderSponsors();
    });

    // Função para editar um patrocinador
    window.editSponsor = (index) => {
        const sponsor = sponsors[index];
        document.getElementById('name').value = sponsor.name;
        document.getElementById('website').value = sponsor.website;

        // Remover o patrocinador editado
        sponsors.splice(index, 1);
        renderSponsors();
    };

    // Função para excluir um patrocinador
    window.deleteSponsor = (index) => {
        sponsors.splice(index, 1);
        renderSponsors();
    };

    renderSponsors();
});
