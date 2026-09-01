package controller;

import dao.PagamentoDAO;
import model.Pagamento;
import model.Cliente;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/PagamentoController")
public class PagamentoController extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String formaPagStr = req.getParameter("formaPag");
        double valor = Double.parseDouble(req.getParameter("valor"));
        
        Cliente cliente = (Cliente) req.getSession().getAttribute("usuarioLogado");
        
        Pagamento p = new Pagamento();
        p.setFormaPagamento(Pagamento.FormaPagamento.valueOf(formaPagStr.toUpperCase()));
        p.setValor(valor);
        p.setDataPagamento(LocalDateTime.now());
        
        // status depende da forma de pagamento
        if (formaPagStr.equalsIgnoreCase("dinheiro")) {
            p.setStatus(Pagamento.Status.PENDENTE);
        } else {
            p.setStatus(Pagamento.Status.APROVADO); // simplificando, pode mudar depois
        }
        
        p.setComprovante("pendente");
        p.setNotaFiscal("pendente");
        p.setCliente(cliente);
        
        if (formaPagStr.equalsIgnoreCase("cartao")) {
            p.setNumeroCartao(req.getParameter("numeroCartao"));
            p.setDataValidade(req.getParameter("validadeCartao"));
            p.setCvv(req.getParameter("cvvCartao"));
        }
        
        try {
            new PagamentoDAO().salvar(p);
            // fetch() só olha se a resposta teve status ok
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch(RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        
    }
    
}
