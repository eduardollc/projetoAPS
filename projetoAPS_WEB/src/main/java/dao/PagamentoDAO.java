package dao;

import model.Pagamento;
import service.ConnectionBD;
import java.sql.*;

public class PagamentoDAO {
    
    public void salvar(Pagamento p) {
        
        String sql = "INSERT INTO Pagamento (formaPagamento, valor, dataPagamento, status, "
                + "comprovante, notaFiscal, codCliente, codFuncionario, numeroCartao, dataValidade, cvv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConnectionBD.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, formatarEnumBanco(p.getFormaPagamento()));
            stmt.setDouble(2, p.getValor());
            stmt.setTimestamp(3, Timestamp.valueOf(p.getDataPagamento()));
            stmt.setString(4, p.getStatus() == Pagamento.Status.APROVADO ? "Aprovado" : "Recusado");
            stmt.setString(5, p.getComprovante());
            stmt.setString(6, p.getNotaFiscal());
            stmt.setString(7, p.getCliente().getLogin());
            stmt.setString(8, p.getFuncionario() != null ? p.getFuncionario().getLogin() : null);
            stmt.setString(9, p.getNumeroCartao());
            stmt.setString(10, p.getDataValidade());
            stmt.setString(11, p.getCvv());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pagamento: " + e.getMessage(), e);
        }      
    }
    
    private String formatarEnumBanco(Pagamento.FormaPagamento f) {
            switch (f) {
                case PIX: return "Pix";
                case DINHEIRO: return "Dinheiro";
                case CARTAO: return "Cartão";
                default: throw new IllegalArgumentException();
            }
        }
    
}
