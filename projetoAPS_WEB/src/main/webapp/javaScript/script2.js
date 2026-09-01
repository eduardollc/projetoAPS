// Pega as referências dos elementos principais que o script vai manipular.
// document.getElementById() busca UM elemento pelo id.
const paymentSelect = document.getElementById('payment-select');
const modal = document.getElementById('modal');

// document.querySelectorAll() busca VÁRIOS elementos que combinam com
// o seletor CSS informado — aqui, todo elemento com a classe "modal-box".
// Retorna uma NodeList (parecido com array), por isso dá pra usar forEach.
const modalBoxes = document.querySelectorAll('.modal-box');

// Objeto que "traduz" o value do <select> (pix/cartao/dinheiro) pro id
// do modal correspondente no HTML. Isso evita um if/else grande depois.
const modaisPorValor = {
    pix: 'modal-pix',
    cartao: 'modal-cartao',
    dinheiro: 'modal-dinheiro'
};

// addEventListener('change', ...) registra uma função que roda toda
// vez que o usuário troca a opção selecionada no <select>.
paymentSelect.addEventListener('change', () => {

    // paymentSelect.value pega o "value" da <option> escolhida
    // (ex: "pix", "cartao" ou "dinheiro").
    const valor = paymentSelect.value;

    // Usa o objeto modaisPorValor pra descobrir qual id de modal abrir.
    const idModal = modaisPorValor[valor];

    if (idModal) {
        abrirModal(idModal);

        // Dinheiro não tem nenhum campo pra preencher nem botão de
        // confirmação dentro do modal — então assim que o modal abre,
        // já dispara o envio do pagamento direto.
        if (valor === 'dinheiro') {
            enviarPagamento('dinheiro');
        }
    }
});

/**
 * Abre o modal certo (aquele cujo id foi passado) e esconde os outros.
 */
function abrirModal(id) {
    // Remove a classe "ativo" de TODOS os modal-box primeiro,
    // garantindo que só um fique visível por vez.
    modalBoxes.forEach(box => box.classList.remove('ativo'));

    // Adiciona "ativo" só no modal-box que corresponde ao id recebido.
    // É essa classe que o CSS usa (.modal-box.ativo { display: flex })
    // pra mostrar a caixa.
    document.getElementById(id).classList.add('ativo');

    // Adiciona "show" no overlay geral (#modal.show { display: flex }),
    // que é o fundo escurecido por trás da caixa.
    modal.classList.add('show');
}

/**
 * Fecha o modal inteiro (esconde o overlay e, por consequência,
 * qualquer modal-box que estivesse visível dentro dele).
 */
function fecharModal() {
    modal.classList.remove('show');
}

// Busca TODOS os "X" de fechar (um em cada modal-box) e registra
// o clique de cada um pra chamar fecharModal.
document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', fecharModal);
});

// Mesma ideia pros botões "Cancelar" (só existe no modal de cartão,
// mas o código funciona igual mesmo se houver só um).
document.querySelectorAll('.modal-cancel').forEach(btn => {
    btn.addEventListener('click', fecharModal);
});

// Fecha o modal ao clicar no fundo escuro (fora da caixa branca).
modal.addEventListener('click', (e) => {
    // e.target é o elemento exato que recebeu o clique.
    // Se o clique foi diretamente no overlay (#modal) — e não em algo
    // dentro dele, como a caixa — então fecha. Isso evita que clicar
    // DENTRO da caixa (que também está dentro do #modal) feche sem querer.
    if (e.target === modal) fecharModal();
});

// Clique no botão "Confirmar" do modal de Cartão.
document.getElementById('modal-confirm').addEventListener('click', () => {
    // Lê o que o usuário digitou em cada input pelo id.
    const numero = document.getElementById('numeroCartao').value;
    const validade = document.getElementById('validadeCartao').value;
    const cvv = document.getElementById('cvvCartao').value;

    // Envia os dados pro servlet, junto com a forma de pagamento
    // e um objeto extra contendo os dados específicos do cartão.
    enviarPagamento('cartao', {
        numeroCartao: numero,
        validadeCartao: validade,
        cvvCartao: cvv
    });
});

// Clique na "chave" Pix: copia o código pra área de transferência
// E também confirma o pagamento (já que não tem outro botão pro Pix).
document.getElementById('chave').addEventListener('click', () => {
    const chave = document.getElementById('pix').textContent;

    // navigator.clipboard.writeText() copia o texto pro clipboard do
    // usuário. É assíncrono (retorna uma Promise), por isso o .catch()
    // pra pegar erro caso o navegador bloqueie por algum motivo.
    navigator.clipboard.writeText(chave).catch(err => console.error('Erro ao copiar:', err));

    enviarPagamento('pix');
});

/**
 * Função central: monta os dados do pagamento e manda pro
 * PagamentoController via fetch, sem recarregar a página.
 *
 * formaPag: "pix" | "cartao" | "dinheiro"
 * dadosExtra: objeto opcional com campos extras (só usado no cartão)
 */
function enviarPagamento(formaPag, dadosExtra = {}) {

    // Pega o texto exibido no valor da compra, ex: "R$ 150,00".
    const valorTexto = document.getElementById('valorCompra').textContent;

    // Precisamos transformar "R$ 150,00" em "150.00", que é o formato
    // que Double.parseDouble() do Java entende.
    const valor = valorTexto
        .replace('R$', '')   // tira o "R$"
        .trim()               // tira espaços sobrando nas pontas
        .replace('.', '')    // remove ponto de milhar (ex: "1.500,00")
        .replace(',', '.');  // troca a vírgula decimal por ponto

    // URLSearchParams monta um corpo no formato "chave=valor&chave2=valor2",
    // que é o formato padrão de formulário (application/x-www-form-urlencoded).
    // É o mesmo formato que req.getParameter() no Java já sabe ler,
    // sem precisar de nenhuma configuração extra no servlet.
    const params = new URLSearchParams();
    params.append('formaPag', formaPag);
    params.append('valor', valor);

    // Adiciona os campos extras (numeroCartao, validadeCartao, cvvCartao),
    // se houver. Pra Pix e Dinheiro, dadosExtra vem vazio, então esse
    // loop simplesmente não faz nada.
    for (const chave in dadosExtra) {
        params.append(chave, dadosExtra[chave]);
    }

    // fetch() faz a requisição HTTP pro servlet, em segundo plano,
    // sem recarregar/navegar a página.
    fetch('PagamentoController', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
    })
    .then(response => {
        // response.ok é true quando o status HTTP está entre 200 e 299
        // (no nosso Controller, isso é o resp.setStatus(SC_OK)).
        if (response.ok) {
            fecharModal();
            alert('Pagamento registrado! Aguarde a confirmação do funcionário.');
        } else {
            // Cai aqui se o Controller respondeu SC_INTERNAL_SERVER_ERROR
            // (por exemplo, se o DAO lançou uma exceção ao salvar).
            alert('Não foi possível registrar o pagamento. Tente novamente.');
        }
    })
    .catch(err => {
        // Cai aqui se nem chegou a ter resposta — ex: servidor fora do
        // ar, sem internet, erro de rede etc.
        console.error('Erro de conexão:', err);
        alert('Erro de conexão com o servidor.');
    });
}