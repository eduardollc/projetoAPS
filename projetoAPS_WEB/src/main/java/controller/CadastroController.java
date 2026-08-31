package controller;

import dao.ClienteDAO;
import model.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//registra essa classe com um Servlet
@WebServlet("/CadastroController")
public class CadastroController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("=== CadastroController doPost chamado ===");
        
        //Pega os valores que estao no cadastrar.jsp
        String nome = request.getParameter("name");
        String cpf = request.getParameter("cpf");
        String cnh = request.getParameter("cnh");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmar-senha");
        
        System.out.println("Nome: " + nome + " | CPF: " + cpf + " | Email: " + email);
        
        // Verifica se as duas senhas são iguais
        if (senha == null || !senha.equals(confirmarSenha)) {
            System.out.println("Cadastro recusado: senhas não coincidem");
            request.setAttribute("erro", "As senhas não coincidem.");
            request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
            return;
        }
        
        String login = email;
        boolean situacaoFinanceira = true;
        
        // monta o objeto Cliente com o construtor completo, que já chama
        // super(login, senha, nome, cpf) internamente
        Cliente cliente = new Cliente(cnh, telefone, email, situacaoFinanceira, login, senha, nome, cpf);
        
        ClienteDAO clienteDAO = new ClienteDAO();
        
        try {
            
            // Grava em usuario
            clienteDAO.inserir(cliente); 
            
            System.out.println("Cadastro realizado com sucesso: " + login);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            
            // Erro de email ou cpf duplicado
            System.out.println("ERRO SQL no cadastro: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("erro", "Não foi possivel concluir o cadastro. Verifique se o email ou CPF já estão cadastrados.");
            request.getRequestDispatcher("cadastrar.jsp").forward(request, response);
        }
        
    }
    
}
