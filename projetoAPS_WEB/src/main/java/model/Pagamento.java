package locadoraveiculo.model;

import java.time.LocalDateTime;

public class Pagamento {
    
    private int id;
    private FormaPagamento formaPagamento;
    private double valor;
    private LocalDateTime dataPagamento;
    private Status status;
    private String comprovante;
    private String notaFiscal;
    
    private String numeroCartao;
    private String cvv;
    private String dataValidade;
    
    private Cliente cliente;
    private Funcionario funcionario;
    
    public enum FormaPagamento {
    PIX,
    CARTAO,
    DINHEIRO
}
    
    public enum Status {
    APROVADO,
    RECUSADO
   
}

    public Pagamento(int id, FormaPagamento formaPagamento, double valor, LocalDateTime dataPagamento, Status status, String comprovante, String notaFiscal, Cliente cliente, Funcionario funcionario) {
        this.id = id;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.comprovante = comprovante;
        this.notaFiscal = notaFiscal;
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public Pagamento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    
    
}
