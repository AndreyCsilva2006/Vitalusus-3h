 const botao = document.getElementById('button-cd');

    // Função para mudar o texto ao passar o mouse
    function mudarTexto() {
        botao.textContent = 'receber...';
    }

    // Função para restaurar o texto original
    function restaurarTexto() {
        botao.textContent = 'Requerer o código';
    }
    function clikando(){
        botao.textContent = 'verificando...'
    }

    document.getElementById('emailForm').addEventListener('submit', (event)=>{
        event.preventDefault()
        const email = document.getElementById('emailInput').value
        const enviar = async()=>{
            try{
            const response = await
            fetch(`http://localhost:8080/vitalusus/usuario/enviarMail/?email=${encodeURIComponent(email)}`,{
                method:'POST',
                headers:{
                    'Content-Type':'application/json'
                }
            });
                if(!response.ok){
                    const errorData = await response.json()
                    alert(errorData.message)
                }
                else{
                alert('Email enviado com sucesso')
                }
            }
            catch(error){
                console.error(error)
            }
        }
        enviar()
    })
    
    // Adicionando os eventos de mouse
    botao.addEventListener('mouseover', mudarTexto);
    botao.addEventListener('mouseout', restaurarTexto);
    botao.addEventListener('click', clikando)