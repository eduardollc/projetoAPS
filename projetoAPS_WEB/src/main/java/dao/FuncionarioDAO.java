package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Funcionario;
import service.ConnectionBD;

public class FuncionarioDAO {

    public void inserir(Funcionario funcionario) throws SQLException {

        // Os inserts
        String sqlUsuario = "INSERT INTO usuario (login, senha, nome, cpf) VALUES (?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (login, matricula, cargo) VALUES (?, ?, ?)";

        Connection conn = null;

        try {

            conn = ConnectionBD.getConnection();
            conn.setAutoCommit(false);

            // Gravar (usuario)
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {

                stmtUsuario.setString(1, funcionario.getLogin());
                stmtUsuario.setString(2, funcionario.getSenha());
                stmtUsuario.setString(3, funcionario.getNome());
                stmtUsuario.setString(4, funcionario.getCpf());
                stmtUsuario.executeUpdate();

            }

            // Grava (funcionario), usando o mesmo login como chave
            try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlFuncionario)) {

                stmtFuncionario.setString(1, funcionario.getLogin());
                stmtFuncionario.setString(2, funcionario.getMatricula());
                stmtFuncionario.setString(3, funcionario.getCargo());
                stmtFuncionario.executeUpdate();

            }

            // Confirmar as duas gravações
            conn.commit();

        } catch (SQLException e) {

            if (conn != null) {

                // Desfaz tudo se algo deu errado
                conn.rollback();
            }

            //Repassa pro Controller tratar
            throw e;

        } finally {
            if (conn != null) {

                // Devolve a conexão ao modo padrão antes de fechar 
                conn.setAutoCommit(true);
                // Libera a conexão
                conn.close();
            }
        }

    }

    // busca um Funcionario completo pelo login para descobrir se o usuario que logou realmente é um Cliente
    public Funcionario buscarPorLogin(String login) throws SQLException {

        // JOIN para buscar por login
        String sql = "SELECT u.login, u.senha, u.nome, u.cpf, f.matricula, f.cargo "
                + "FROM usuario u JOIN funcionario f ON u.login = f.login WHERE u.login = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login); // substitui o "?" pelo login recebido

            //Executa o SELECT
            try (ResultSet rs = stmt.executeQuery()) {

                //Se encontrou uma linha correspondente
                if (rs.next()) {

                    //Monta o funcionario com o construtor
                    Funcionario funcionario = new Funcionario(
                            rs.getString("matricula"),
                            rs.getString("cargo")
                    );

                    //Completas os campos herdados de 'Usuario' com os setters
                    funcionario.setLogin(rs.getString("login"));
                    funcionario.setSenha(rs.getString("senha"));
                    funcionario.setNome(rs.getString("nome"));
                    funcionario.setCpf(rs.getString("cpf"));
                    return funcionario;

                }

            }

        }
        return null;
    }

}
