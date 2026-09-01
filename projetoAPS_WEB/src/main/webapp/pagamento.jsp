<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.1/css/all.min.css" integrity="sha512-QeR2VH+lsBE5LSAe1Q5EnTBbe7XTBubt8dG93Y7gidSgdMCr8nVqKcfKAMyN96SV8KDbZVTDXChatu5G2KQGzg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="./styles/styles3.css">
    <title>Pagamento</title>
</head>
<body>
    <main id="form_container">
        <div id="form_header">
            <h1 id="form_title">
                Pagamento
            </h1>
        </div>
        <form action="PagamentoController" method="post" id="form">
            <div id="input_container">
                <div class="input-box">
                    <div id="infocompra">
                        <div class="info-item">
                            <span class="info-label">ID da Compra</span>
                            <span class="info-valor" id="idCompra">#000123</span>
                        </div>
                        <div class="info-item">
                            <span class="info-label">Valor</span>
                            <span class="info-valor" id="valorCompra">R$ 150,00</span>
                        </div>
                    </div>
                </div>
                <div class="input-box">
                    <label for="formaPag" class="form-label">Selecione a Forma de Pagamento</label>
                    <div id="payment">
                        <select name="formaPag" id="payment-select">
                            <option value="" disabled selected></option>
                            <option value="pix">Pix</option>
                            <option value="cartao">Cartão</option>
                            <option value="dinheiro">Dinheiro</option>
                        </select>
                </div>
                </div>
            </div>
            <button type="submit" class="btn-default">
      
                    Confirmar Pagamento
                
            </button>
        </form>
    </main>
    <div id="modal">
        <div class="modal-box" id="modal-pix">
            <div class="modal-header">
                <h3 class="modal-title"><i class="fa-solid fa-credit-card"></i> Pix</h3>
                <i class="fa-solid fa-xmark modal-close"></i>
            </div>
            <div class="modal-body">
                <div class="input-box">
                    <div id="img">
                        <img src="./img/qrcode.png" alt="">
                    </div>
                </div>
                <div class="input-box">
                    <div id="chave">
                        <span id="pix">00020101021126360014br.gov.bcb.pix0114+55779986207025204000053039865802BR5919ANTONIO P P L SILVA6007BRUMADO62070503***6304CFE3</span>
                        <i class="fa-regular fa-copy" id="pixIcon"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal-box" id="modal-cartao">
            <div class="modal-header">
                <h3 class="modal-title"><i class="fa-solid fa-credit-card"></i> Dados do Cartão</h3>
                <i class="fa-solid fa-xmark modal-close"></i>
            </div>
            <div class="modal-body">
                <div class="input-box">
                    <label for="numeroCartao" class="form-label">Número do cartão</label>
                    <input type="text" id="numeroCartao" placeholder="0000 0000 0000 0000" maxlength="19">
                </div>
                <div id="modal-row">
                    <div class="input-box">
                        <label for="validadeCartao" class="form-label">Validade</label>
                        <input type="text" id="validadeCartao" placeholder="MM/AA" maxlength="5">
                    </div>
                    <div class="input-box">
                        <label for="cvvCartao" class="form-label">CVV</label>
                        <input type="text" id="cvvCartao" placeholder="000" maxlength="4">
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-secondary modal-cancel">Cancelar</button>
                <button type="button" class="btn-default" id="modal-confirm">Confirmar</button>
            </div>
        </div>
        <div class="modal-box" id="modal-dinheiro">
            <div class="modal-header">
                <h3 class="modal-title"><i class="fa-solid fa-money-bill-wave"></i> Dinheiro</h3>
                <i class="fa-solid fa-xmark modal-close"></i>
            </div>
            <div class="modal-body">
                <div id="aguardando">
                    <i class="fa-solid fa-clock"></i>
                    <p>Aguardando confirmação do funcionário...</p>
                </div>
            </div>
        </div>
    </div>
    <script src="./javaScript/script2.js"></script>
</body>
</html>