<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.3.1/css/all.min.css" integrity="sha512-QeR2VH+lsBE5LSAe1Q5EnTBbe7XTBubt8dG93Y7gidSgdMCr8nVqKcfKAMyN96SV8KDbZVTDXChatu5G2KQGzg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
    <link rel="stylesheet" href="./styles/styles.css"> 
    <title>Criar Conta</title>
</head>
<body>
    <main id="form_container"> 
        <div id="form_header">
            <h1 id="form_title">
                Cadastrar
            </h1>
            <button class="btn-default" >
                <a id="a" href="./index.jsp">
                    <i class="fa-solid fa-right-to-bracket"></i>
                </a>
            </button>
        </div>

        <form method="post" action="CadastroController" id="form">
            <div id="input_container">
                <div class="input-box">
                    <label for="name" class="form-label">Nome</label>
                    <div class="input-field">
                        <input type="text" id="name" name="name" class="form-control" placeholder="Digite seu nome" maxlength="250">
                        <i class="fa-solid fa-user"></i>
                    </div>
                </div>
                <div class="input-box">
                    <label for="cpf" class="form-label">CPF</label>
                    <div class="input-field">
                        <input type="text" id="cpf" name="cpf" class="form-control" placeholder="Digite seu CPF" maxlength="11">
                        <i class="fa-solid fa-id-card-clip"></i>
                    </div>
                </div>
                <div class="input-box">
                    <label for="cnh" class="form-label">CNH</label>
                    <div class="input-field">
                        <input type="text" id="cnh" name="cnh" class="form-control" placeholder="Digite sua CNH" maxlength="11">
                        <i class="fa-solid fa-id-card"></i>
                    </div>
                </div>
                <div class="input-box">
                    <label for="telefone" class="form-label">Telefone</label>
                    <div class="input-field">
                        <input type="text" id="telefone" name="telefone" class="form-control" placeholder="77-99999-9999" maxlength="11">
                        <i class="fa-solid fa-phone"></i>
                    </div>
                </div>
                <div class="input-box full-width">
                    <label for="email" class="form-label">Email</label>
                    <div class="input-field">
                        <input type="text" id="email" name="email" class="form-control" placeholder="exemplo@gmail.com" maxlength="100">
                        <i class="fa-solid fa-envelope"></i>
                    </div>
                </div>
                <div class="input-box">
                    <label for="senha" class="form-label">Senha</label>
                    <div class="input-field">
                        <input type="password" id="senha" name="senha" class="form-control" placeholder="********" maxlength="12">
                        <i class="fa-solid fa-eye-slash pass"></i>
                    </div>
                </div>
                <div class="input-box">
                    <label for="confirmar-senha" class="form-label">Confirmar Senha</label>
                    <div class="input-field">
                        <input type="password" id="confirmar-senha" name="confirmar-senha" class="form-control" placeholder="********" maxlength="12">
                        <i class="fa-solid fa-eye-slash pass"></i>
                    </div>
                </div>
                <div class="radio-container"></div>
            </div>
            <button type="submit" class="btn-default">
              <i class="fa-solid fa-check"></i>  
              Criar Conta
            </button>
        </form>
    </main>
    <script src="./javaScript/script.js"></script>
    
  <% if (request.getAttribute("erro") != null) { %>
        <script>
            alert("<%= request.getAttribute("erro") %>");
        </script>
    <% } %>
    
</body>
</html>
