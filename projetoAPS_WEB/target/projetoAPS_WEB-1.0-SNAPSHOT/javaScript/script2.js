const paymentSelect = document.getElementById('payment-select');
const modal = document.getElementById('modal');
const modalBoxes = document.querySelectorAll('.modal-box');
const modaisPorValor = {
    pix: 'modal-pix',
    cartao: 'modal-cartao',
    dinheiro: 'modal-dinheiro'
};
 
//quando o usuário troca a opção do select
paymentSelect.addEventListener('change', () => {
 
    // Pega o valor que o usuário selecionou
    const valor = paymentSelect.value;
    const idModal = modaisPorValor[valor];
    if (idModal) {
        abrirModal(idModal);
    }
});
 

function abrirModal(id) {
    modalBoxes.forEach(box => box.classList.remove('ativo'));
    document.getElementById(id).classList.add('ativo');
    modal.classList.add('show');
}
 
 

//fecha o modal (esconde o overlay inteiro)
function fecharModal() {
    modal.classList.remove('show');
}
 
// Busca TODOS os elementos com a classe "modal-close" (o "X" de cada modal)
document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', fecharModal);
});
 
 
// busca todos os botões "Cancelar" e registra o clique
document.querySelectorAll('.modal-cancel').forEach(btn => {
    btn.addEventListener('click', fecharModal);
});
 
 

//fechar clicando fora do card (no fundo escuro)
modal.addEventListener('click', (e) => {
    // "e.target" é o elemento exato que foi clicado.
    if (e.target === modal) fecharModal();
});
 

//botão "Confirmar" do modal de Cartão
document.getElementById('modal-confirm').addEventListener('click', () => {

    // Pega o valor digitado em cada campo do formulário de cartão
    const numero = document.getElementById('numeroCartao').value;
    const validade = document.getElementById('validadeCartao').value;
    const cvv = document.getElementById('cvvCartao').value;

    console.log({ numero, validade, cvv });
 
    fecharModal();
});