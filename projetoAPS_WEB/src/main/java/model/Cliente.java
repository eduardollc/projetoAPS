/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locadoraveiculo.model;

/**
 *
 * @author rentt
 */
public class Cliente extends Usuario {
    
    private String cnh;
    private String telefone;
    private String email;
    private boolean situacaoFinanceira;

    public Cliente(String cnh, String telefone, String email, boolean situacaoFinanceira, String login, String senha, String nome, String cpf) {
        super(login, senha, nome, cpf);
        this.cnh = cnh;
        this.telefone = telefone;
        this.email = email;
        this.situacaoFinanceira = situacaoFinanceira;
    }

    public Cliente(String cnh, String telefone, String email, boolean situacaoFinanceira) {
        this.cnh = cnh;
        this.telefone = telefone;
        this.email = email;
        this.situacaoFinanceira = situacaoFinanceira;
    }

    public Cliente() {
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSituacaoFinanceira() {
        return situacaoFinanceira;
    }

    public void setSituacaoFinanceira(boolean situacaoFinanceira) {
        this.situacaoFinanceira = situacaoFinanceira;
    }
    
    
    
}
