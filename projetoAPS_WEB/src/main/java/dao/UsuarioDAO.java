package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import service.ConnectionBD;

public class UsuarioDAO {
    
    public Usuario buscarPorEmailSenha(String email, String senha)throws SQLException {
        
         
        String sql = "SELECT id, nome, email, senha FROM usuario WHERE email = ? AND senha = ?";

        // try-with-resources: garante que a conexão e o statement são fechados automaticamente
        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email); // substitui o primeiro "?" pelo email recebido
            stmt.setString(2, senha); // substitui o segundo "?" pela senha recebida

            try (ResultSet rs = stmt.executeQuery()) { // executa o SELECT e recebe o resultado
                if (rs.next()) { // se encontrou uma linha, o login é válido
                    // monta o objeto Usuario com os dados vindos do banco
                    return new Usuario(
                        rs.getString("login"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                    );
                }
            }
        }
        return null; // se não encontrou nada, retorna null (login inválido)
        
    }
    
}
