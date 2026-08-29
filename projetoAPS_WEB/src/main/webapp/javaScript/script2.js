const paymentSelect = document.getElementById('payment-select');
const modal = document.getElementById('modal');
const modalClose = document.getElementById('modal-close');
const modalCancel = document.getElementById('modal-cancel');

// selecio cartao no select do html, aciona o show(pop-up)
paymentSelect.addEventListener('change', () => {
    if (paymentSelect.value === 'cartao') {
        modal.classList.add('show');
    }
});

// fecha o show(pop-up) e faz o css esconder o model
function fecharModal(){
    modal.classList.remove('show');
}

modalClose.addEventListener('click', fecharModal);
modalCancel.addEventListener('click', fecharModal);

// fecha na hora que clica fora do model 
modal.addEventListener('click', (e) => {
    if (e.target === modal) fecharModal();
});

