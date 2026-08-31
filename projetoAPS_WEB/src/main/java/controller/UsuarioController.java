package controller;
import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.UsuarioDAO;
import model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import model.Cliente;
import model.Funcionario;

//registra essa classe com um Servlet
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet{
    
    //processo de converter um objeto Java em uma sequência de bytes
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        System.out.println("=== doPost chamado ===");
        
        //Pega os valores enviados pelo formulário.
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        System.out.println("Login recebido: " + login);
        System.out.println("Senha recebida: " + senha);
        
        //retorna um usuario se baterem as informacoes, se nao baterem volta null
        UsuarioDAO dao = new UsuarioDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        
        try{
            Usuario usuario = dao.verificarLogin(login, senha);
            System.out.println("Resultado autenticação: " + (usuario != null ? "SUCESSO" : "FALHOU"));
            
            if (usuario == null) {
                request.setAttribute("erro", "Login ou senha inválidos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            //primeiro tenta como Funcionario
            Funcionario funcionario = funcionarioDAO.buscarPorLogin(login);
            if (funcionario != null) {
                System.out.println("Perfil: FUNCIONARIO - " + funcionario.getCargo());
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", funcionario);
                response.sendRedirect("funcionario.jsp");
                return;
            }

            //Se não é funcionário, tenta como Cliente
            Cliente cliente = clienteDAO.buscarPorLogin(login);
            if (cliente != null) {
                System.out.println("Perfil: CLIENTE");
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", cliente);
                response.sendRedirect("pagamento.jsp");
                return;
            }

            //Login válido na tabela usuario, mas sem perfil associado
            System.out.println("Login válido mas sem perfil em Cliente nem Funcionario");
            request.setAttribute("erro", "Usuário sem perfil definido. Contate o suporte.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        }catch(SQLException e){
            System.out.println("ERRO SQL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao acessar o banco de dados.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }
    
}
