package br.com.hbsis.Api.Employee;

public class EmployeeDTO {

    private String nome;
    EmployeeSavingDTO employeeSavingDTO;

    public EmployeeDTO() {
    }

    public EmployeeDTO(String nome, EmployeeSavingDTO employeeSavingDTO) {
        this.nome = nome;
        this.employeeSavingDTO = employeeSavingDTO;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EmployeeSavingDTO getEmployeeSavingDTO() {
        return employeeSavingDTO;
    }

    public void setEmployeeSavingDTO(EmployeeSavingDTO employeeSavingDTO) {
        this.employeeSavingDTO = employeeSavingDTO;
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "nome='" + nome + '\'' +
                ", employeeSavingDTO=" + employeeSavingDTO +
                '}';
    }
}