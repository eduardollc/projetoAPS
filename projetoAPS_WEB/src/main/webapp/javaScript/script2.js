document.addEventListener('DOMContentLoaded', () => {

    const paymentSelect = document.getElementById('payment-select');
    const modal = document.getElementById('modal');
    const modalBoxes = document.querySelectorAll('.modal-box');
    const form = document.getElementById('form'); // o <form id="form"> do pagamento.jsp

    // Verificação defensiva: se algum elemento essencial não existir,
    // avisa claramente no Console em vez de quebrar silenciosamente
    // e deixar o submit cair no comportamento nativo do navegador.
    const elementosEssenciais = { paymentSelect, modal, form };
    let faltando = [];
    for (const nome in elementosEssenciais) {
        if (!elementosEssenciais[nome]) faltando.push(nome);
    }
    if (faltando.length > 0) {
        console.error('[script2.js] Elemento(s) não encontrado(s) no DOM:', faltando.join(', '));
        console.error('[script2.js] O formulário vai cair no submit padrão (recarregando a página) até isso ser corrigido.');
        return; // não tenta seguir sem os elementos essenciais
    }

    console.log('[script2.js] Elementos essenciais encontrados. Listener de submit sendo registrado...');

    const modaisPorValor = {
        pix: 'modal-pix',
        cartao: 'modal-cartao',
        dinheiro: 'modal-dinheiro'
    };

    // Trocar a forma de pagamento só abre o modal correspondente,
    // pra mostrar as instruções/campos. Não envia nada ainda.
    paymentSelect.addEventListener('change', () => {
        const valor = paymentSelect.value;
        const idModal = modaisPorValor[valor];
        if (idModal) {
            abrirModal(idModal);
        }
    });

    function abrirModal(id) {
        modalBoxes.forEach(box => box.classList.remove('ativo'));
        const alvo = document.getElementById(id);
        if (alvo) alvo.classList.add('ativo');
        modal.classList.add('show');
    }

    function fecharModal() {
        modal.classList.remove('show');
    }

    document.querySelectorAll('.modal-close').forEach(btn => {
        btn.addEventListener('click', fecharModal);
    });

    document.querySelectorAll('.modal-cancel').forEach(btn => {
        btn.addEventListener('click', fecharModal);
    });

    modal.addEventListener('click', (e) => {
        if (e.target === modal) fecharModal();
    });

    // Botão "Confirmar" do modal de Cartão agora só FECHA o modal
    // (serve como "ok, revisei meus dados"). O envio de verdade só
    // acontece quando o cliente clicar no botão principal do formulário.
    const modalConfirm = document.getElementById('modal-confirm');
    if (modalConfirm) {
        modalConfirm.addEventListener('click', () => {
            fecharModal();
        });
    }

    // Clicar na chave Pix só copia — não confirma mais nada sozinho.
    const chaveEl = document.getElementById('chave');
    if (chaveEl) {
        chaveEl.addEventListener('click', () => {
            const pixEl = document.getElementById('pix');
            if (!pixEl) return;
            const chave = pixEl.textContent;
            navigator.clipboard.writeText(chave).catch(err => console.error('Erro ao copiar:', err));
        });
    }

    // Ponto único de confirmação: o submit do formulário principal
    // ("Confirmar Pagamento"). Interceptamos o submit padrão (que
    // recarregaria a página) e enviamos via fetch em vez disso.
    form.addEventListener('submit', (e) => {
        e.preventDefault(); // impede o comportamento padrão de submit/reload
        console.log('[script2.js] Submit interceptado. preventDefault() executado.');

        const formaPag = paymentSelect.value;

        if (!formaPag) {
            alert('Selecione uma forma de pagamento.');
            return;
        }

        let dadosExtra = {};

        // Só valida/coleta dados extras se for cartão — Pix e Dinheiro
        // não têm campo nenhum pra preencher.
        if (formaPag === 'cartao') {
            const numero = document.getElementById('numeroCartao').value;
            const validade = document.getElementById('validadeCartao').value;
            const cvv = document.getElementById('cvvCartao').value;

            if (!numero || !validade || !cvv) {
                alert('Preencha todos os dados do cartão antes de confirmar.');
                return;
            }

            dadosExtra = {
                numeroCartao: numero,
                validadeCartao: validade,
                cvvCartao: cvv
            };
        }

        enviarPagamento(formaPag, dadosExtra);
    });

    function enviarPagamento(formaPag, dadosExtra = {}) {
        const valorEl = document.getElementById('valorCompra');
        const valorTexto = valorEl ? valorEl.textContent : '';
        const valor = valorTexto
            .replace('R$', '')
            .trim()
            .replace('.', '')
            .replace(',', '.');

        console.log('[script2.js] Enviando pagamento via fetch. formaPag =', formaPag, '| valor =', valor);

        const params = new URLSearchParams();
        params.append('formaPag', formaPag);
        params.append('valor', valor);

        for (const chave in dadosExtra) {
            params.append(chave, dadosExtra[chave]);
        }

        fetch('PagamentoController', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: params
        })
        .then(response => {
            if (response.ok) {
                fecharModal();
                alert('Pagamento registrado com sucesso!');
            } else {
                alert('Não foi possível registrar o pagamento. Tente novamente.');
            }
        })
        .catch(err => {
            console.error('Erro de conexão:', err);
            alert('Erro de conexão com o servidor.');
        });
    }

});