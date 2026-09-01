package dao;

import model.Pagamento;
import service.ConnectionBD;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PagamentoDAO {

    public void salvar(Pagamento p) {

        String sql = "INSERT INTO Pagamento (formaPagamento, valor, dataPagamento, status, "
                + "comprovante, notaFiscal, codCliente, codFuncionario, numeroCartao, dataValidade, cvv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnectionBD.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, formatarEnumBanco(p.getFormaPagamento()));
            stmt.setDouble(2, p.getValor());
            stmt.setTimestamp(3, Timestamp.valueOf(p.getDataPagamento()));
            stmt.setString(4, p.getStatus() == Pagamento.Status.APROVADO ? "Aprovado" : p.getStatus() == Pagamento.Status.RECUSADO ? "Recusado" : "Pendente");
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
            case PIX:
                return "Pix";
            case DINHEIRO:
                return "Dinheiro";
            case CARTAO:
                return "Cartão";
            default:
                throw new IllegalArgumentException();
        }
    }

    public List<Pagamento> listarPendentes() {

        String sql = "SELECT * FROM Pagamento "
                + "WHERE status = 'Aprovado' "
                + "AND codFuncionario IS NULL "
                + "AND formaPagamento = 'Dinheiro'";

        List<Pagamento> lista = new ArrayList<>();

        try (Connection con = ConnectionBD.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pagamento p = new Pagamento();
                p.setId(rs.getInt("id"));
                p.setValor(rs.getDouble("valor"));
                p.setFormaPagamento(Pagamento.FormaPagamento.valueOf(rs.getString("formaPagamento").toUpperCase().replace("Ã", "A")));
                p.setDataPagamento(rs.getTimestamp("dataPagamento").toLocalDateTime());
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pagamentos pendentes: " + e.getMessage(), e);
        }
        
        return lista;

    }

}
