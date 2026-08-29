/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package locadoraveiculo.model;

/**
 *
 * @author rentt
 */
public class Funcionario extends Usuario {
    
    private String matricula;
    private String cargo;

    public Funcionario(String matricula, String cargo, String login, String senha, String nome, String cpf) {
        super(login, senha, nome, cpf);
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public Funcionario(String matricula, String cargo) {
        this.matricula = matricula;
        this.cargo = cargo;
    }

    public Funcionario() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    
    
}
