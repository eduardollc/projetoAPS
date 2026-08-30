package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import service.ConnectionBD;

public class UsuarioDAO {
    
    // valida login e senha; retorna true se bater, false caso contrário
    public Usuario verificarLogin(String login, String senha) throws SQLException {
        String sql = "SELECT login FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setLogin(rs.getString("login"));
                    usuario.setSenha(rs.getString("senha"));
                    return usuario;
                }
                return null;
            }
        }
    }

 
}
