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

@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        
        UsuarioDAO dao = new UsuarioDAO();
        
        try{
            Usuario usuario = dao.verificarLogin(login, senha);
            
            if(usuario != null){
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", usuario);
                response.sendRedirect("pagamento.jsp");
            }else{
                request.setAttribute("erro", "Login ou senha inválidos.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            
        }catch(SQLException e){
            e.printStackTrace();
            request.setAttribute("erro", "Erro ao acessar o banco de dados.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        
    }
    
}
