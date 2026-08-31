package controller;
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
        
        try{
            Usuario usuario = dao.verificarLogin(login, senha);
            System.out.println("Resultado autenticação: " + (usuario != null ? "SUCESSO" : "FALHOU"));
            
            if(usuario != null){
                //pega a sessao http
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                //redireciona para pagamento 
                System.out.println("Redirecionando para pagamento.jsp");
                response.sendRedirect("pagamento.jsp");
            }else{
                request.setAttribute("erro", "Login ou senha inválidos.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            
        }catch(SQLException e){
            System.out.println("ERRO SQL: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao acessar o banco de dados.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }
    
}
