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
        String sqlCliente = "INSERT INTO cliente (login, cnh, telefone, email, situacao_financeira) VALUES (?, ?, ?, ?, ?)";
        
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
            
            if(conn != null) {
                
                // Desfaz tudo se algo deu errado
                conn.rollback();
            }
            
            //Repassa pro Controller tratar
            throw e;
            
        } finally {
            if(conn != null) {
                
                // Devolve a conexão ao modo padrão antes de fechar 
                conn.setAutoCommit(true);
                // Libera a conexão
                conn.close();
            }
        }
        
    }
    
}
