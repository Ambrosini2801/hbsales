package br.com.hbsis.Funcionario;

import javax.persistence.*;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome_funcionario", nullable = false, length = 50)
    private String nomeFuncionario;
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Column(name = "uuid", unique = true, nullable = false, length = 36)
    private String uuid;


    public Funcionario() {
    }

    public Funcionario(Long id, String nomeFuncionario, String email, String uuid) {
        this.id = id;
        this.nomeFuncionario = nomeFuncionario;
        this.email = email;
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nomeFuncionario='" + nomeFuncionario + '\'' +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}