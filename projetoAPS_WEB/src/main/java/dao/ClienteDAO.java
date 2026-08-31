package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cliente;
import service.ConnectionBD;

public class ClienteDAO {

    // Insere um cliente completo: Grava em 'Usuario' e em 'Cliente'
    public void inserir(Cliente cliente) throws SQLException {

        // Os inserts
        String sqlUsuario = "INSERT INTO usuario (login, senha, nome, cpf) VALUES (?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO cliente (login, cnh, telefone, email, situacaoFinanceira) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {

            conn = ConnectionBD.getConnection();
            conn.setAutoCommit(false);

            // Gravar (usuario)
            try (PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario)) {

                stmtUsuario.setString(1, cliente.getLogin());
                stmtUsuario.setString(2, cliente.getSenha());
                stmtUsuario.setString(3, cliente.getNome());
                stmtUsuario.setString(4, cliente.getCpf());
                stmtUsuario.executeUpdate();

            }

            // Grava (cliente), usando o mesmo login como chave
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente)) {

                stmtCliente.setString(1, cliente.getLogin());
                stmtCliente.setString(2, cliente.getCnh());
                stmtCliente.setString(3, cliente.getTelefone());
                stmtCliente.setString(4, cliente.getEmail());
                stmtCliente.setBoolean(5, cliente.isSituacaoFinanceira());
                stmtCliente.executeUpdate();

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

    // busca um Cliente completo pelo login para descobrir se o usuario que logou realmente é um Cliente
    public Cliente buscarPorLogin(String login) throws SQLException {

        // JOIN para buscar por login
        String sql = "SELECT u.login, u.senha, u.nome, u.cpf, "
                + "c.cnh, c.telefone, c.email, c.situacaoFinanceira "
                + "FROM usuario u JOIN cliente c ON u.login = c.login WHERE u.login = ?";

        try (Connection conn = ConnectionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login); // substitui o "?" pelo login recebido

            //Executa o SELECT
            try (ResultSet rs = stmt.executeQuery()) {

                //Se encontrou uma linha correspondente
                if (rs.next()) {

                    //Monta o cliente com o construtor
                    Cliente cliente = new Cliente(
                            rs.getString("cnh"),
                            rs.getString("telefone"),
                            rs.getString("email"),
                            rs.getBoolean("situacaoFinanceira")
                    );

                    //Completas os campos herdados de 'Usuario' com os setters
                    cliente.setLogin(rs.getString("login"));
                    cliente.setSenha(rs.getString("senha"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    return cliente;

                }

            }

        }
        return null;
    }

}
