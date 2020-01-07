package br.com.hbsis.Pedido;

import br.com.hbsis.Funcionario.Funcionario;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.vendas.Vendas;

import javax.persistence.*;
import java.time.LocalDate;

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long id;
    @Column(name = "codigo")
    private String codPedido;
    @Column(name = "status")
    private String status;
    @Column(name = "data")
    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "id_pedido_funcionario", referencedColumnName = "id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "id_pedido_produto", referencedColumnName = "id")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "id_pedido_vendas", referencedColumnName = "id")
    private Vendas vendas;

    public Pedido(Long id, String codPedido, String status, LocalDate dataPedido, Funcionario funcionario, Produto produto, Vendas vendas) {
        this.id = id;
        this.codPedido = codPedido;
        this.status = status;
        this.dataPedido = dataPedido;
        this.funcionario = funcionario;
        this.produto = produto;
        this.vendas = vendas;
    }

    public Pedido() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodPedido() {
        return codPedido;
    }

    public void setCodPedido(String codPedido) {
        this.codPedido = codPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", codPedido='" + codPedido + '\'' +
                ", status='" + status + '\'' +
                ", dataPedido=" + dataPedido +
                ", funcionario=" + funcionario +
                ", produto=" + produto +
                ", vendas=" + vendas +
                '}';
    }
}