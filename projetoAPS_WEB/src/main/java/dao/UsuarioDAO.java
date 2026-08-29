package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuario;
import service.ConnectionBD;

public class UsuarioDAO {
    
    // valida login e senha; retorna true se bater, false caso contrário
    public boolean verificarLogin(String login, String senha) throws SQLException {
        String sql = "SELECT login FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = ConnectionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void inserir(Usuario usuario, Connection conn) throws SQLException {
        String sql = "INSERT INTO usuario (login, senha, nome, cpf) VALUES (?, ?, ?, ?)";

        // aqui NÃO usamos try-with-resources na Connection, porque ela não é nossa —
        // quem abriu foi o DAO que chamou este método, e é ele quem deve fechá-la
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getNome());
            stmt.setString(4, usuario.getCpf());
            stmt.executeUpdate();
        }
    }
     
}
