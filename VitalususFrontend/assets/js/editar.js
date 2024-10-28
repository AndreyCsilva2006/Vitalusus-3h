//criar-video.html / editar-video.html
function exibirEquipamentos(equipamentos){
    const equipSelect = document.getElementById('equipamentosSelect')
    equipSelect.innerHTML = ''
    equipamentos.forEach(equipamento => {
        if(equipamento.statusEquipamento == 'ATIVO'){
        equipSelect.innerHTML += `
        <option value="${equipamento.id}">${equipamento.nome}</option>
        `
        }
    })
}



async function findEquipamentos(){
    try{
        const response = await
        fetch('http://localhost:8080/vitalusus/equipamento/findAll', {
            method:'GET',
            headers:{
                'Content-Type': 'application/json'
            }
        });
        if(!response.ok){
            throw new Error('Erro na requisição: '+response.statusText)
        }
        const equipamentos = await response.json()
        exibirEquipamentos(equipamentos)
    }
    catch(error){
        console.error(error)
    }
}


findEquipamentos()

//função assíncrona que pega o id da videoaula que foi passado como parâmetro no link da página
 const titulo = document.getElementById('titulo-video');
const descricao = document.getElementById('descricao-video');
const categoria = document.getElementById('categoria-video-1');
const thumbnail = document.getElementById('capa-video');
const tagsInput = document.getElementById('tags-video')
const equipamento = document.getElementById('equipamentosSelect')
const idVideoEditar = document.getElementById('id-video')
async function videoaulaFindById(id){
    try {
        const response = await fetch(`http://localhost:8080/vitalusus/videoaula/findById/${id}`,{
            method: 'GET',
             headers: {
            'Content-Type': 'application/json'
        },
        }); // URL do endpoint

        if (!response.ok) {
            throw new Error('Erro na requisição: ' + response.statusText);
        }
        const videoaulaData = await response.json(); // Converte a resposta em JSON
        if (canalData.id == videoaulaData.canal.id){
        console.log('Videoaula:', videoaulaData); // Manipula os dados recebidos
        // Armazena os dados no localStorage
        loadingFormEditar(videoaulaData)
        }
    }
        // Aqui você pode fazer algo com os dados, como exibi-los na tela
     catch (error) {
        console.error('Erro ao buscar itens:', error);
    }
}
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
videoaulaFindById(id)

function loadingFormEditar(videoaula){
        idVideoEditar.value = videoaula.id;
        titulo.value = videoaula.titulo;
        descricao.value = videoaula.descricao;
        categoria.value = videoaula.categoria;
        setImage('capa-video', videoaula.thumbnail);
        equipamento.value = videoaula.equipamento.id;
        tagsInput.value = videoaula.tags;
    }
  const setImage = (elementId, base64String) => {
        const imgElement = document.getElementById(elementId);
        if (imgElement && base64String) {
            imgElement.src = `data:image/jpeg;base64,${base64String}`;
            imgElement.style.display = 'block';
        }
    };

function updateVideo() {
    

    const data = {
        titulo: titulo.value,
        descricao: descricao.value,
        categoria: categoria.value,
        thumbnail: thumbnail.src.split(',')[1],
        tags: tagsInput.value,
        equipamento:{
            id: equipamento.value
        }
    };

    // Enviar dados atualizados para o backend
    //mexa aqui OTTO
    fetch(`http://localhost:8080/vitalusus/videoaula/updateGeral/${idVideoEditar.value}`, {
        method: 'PUT', // Usando PUT para atualizar
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Erro na atualização');
    })
    .then(data => {
        console.log('Atualização bem-sucedida:', data);
        // Redirecionar para a página do Canal
        window.location.href = 'canal.html';
    })
    .catch((error) => {
        console.error('Erro:', error);
    });
}