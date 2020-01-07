package br.com.hbsis.Pedido;

import br.com.hbsis.Funcionario.Funcionario;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.vendas.Vendas;

import java.time.LocalDate;

public class PedidoDTO {

    private Long id;
    private String codigo;
    private String status;
    private LocalDate dataPedido;
    private Funcionario funcionario;
    private Produto produto;
    private Vendas vendas;

    public PedidoDTO() {
    }

    public PedidoDTO(Long id, String codigo, String status, LocalDate dataPedido, Funcionario funcionario, Produto produto, Vendas vendas) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.dataPedido = dataPedido;
        this.funcionario = funcionario;
        this.produto = produto;
        this.vendas = vendas;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getCodPedido(),
                pedido.getStatus(),
                pedido.getDataPedido(),
                pedido.getFuncionario(),
                pedido.getProduto(),
                pedido.getVendas()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status='" + status + '\'' +
                ", dataPedido=" + dataPedido +
                ", funcionario=" + funcionario +
                ", produto=" + produto +
                ", vendas=" + vendas +
                '}';
    }
}