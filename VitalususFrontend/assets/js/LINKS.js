  // Função para gerenciar a navegação
        function handleLinkClick(event) {
            const href = this.getAttribute('href');

            // Impede a ação padrão
            event.preventDefault();

            // Obtém a porta atual
            const port = window.location.port ? `:${window.location.port}` : '';

            // Atualiza a URL mantendo a porta e o caminho sem duplicações
            const cleanUrl = `${window.location.protocol}//${window.location.hostname}${port}/${href.split('#')[0]}`;
            history.pushState("", document.title, cleanUrl); // Atualiza a URL

            // Verifica se o link contém hash
            if (href.includes('#')) {
                // Navega para a nova página mantendo o hash
                window.location.href = href; // Redireciona para o link original
            } else {
                // Para links normais
                window.location.href = href; // Redireciona para o link original
            }
        }

        // Seleciona todos os links na página
        const links = document.querySelectorAll('a');

        links.forEach(link => {
            link.addEventListener('click', handleLinkClick);
        });

        // Restaura o comportamento do hash quando a URL muda
        window.addEventListener('hashchange', function() {
            const hash = window.location.hash;
            if (hash) {
                const target = document.querySelector(hash);
                if (target) {
                    target.scrollIntoView({ behavior: 'smooth' });
                }
            }
        });