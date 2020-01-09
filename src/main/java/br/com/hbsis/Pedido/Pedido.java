package br.com.hbsis.Pedido;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.vendas.Vendas;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "codigo", unique = true, nullable = false, length = 10)
    private String codPedido;
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @Column(name = "uuid_pedido", nullable = false, length = 36)
    private String uuid;
    @Column(name = "data", nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPedido;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false, length = 10)
    private EnumStatusPedido status;

    @ManyToOne
    @JoinColumn(name = "fk_pedido_fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;
    @ManyToOne
    @JoinColumn(name = "fk_pedido_produto", referencedColumnName = "id")
    private Produto produto;
    @ManyToOne
    @JoinColumn(name = "fk_pedido_vendas", referencedColumnName = "id")
    private Vendas vendas;

    public Pedido() {
    }

    public Pedido(Long id, String codPedido, int quantidade, String uuid, LocalDate dataPedido, EnumStatusPedido status, Fornecedor fornecedor, Produto produto, Vendas vendas) {
        this.id = id;
        this.codPedido = codPedido;
        this.quantidade = quantidade;
        this.uuid = uuid;
        this.dataPedido = dataPedido;
        this.status = status;
        this.fornecedor = fornecedor;
        this.produto = produto;
        this.vendas = vendas;
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

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public EnumStatusPedido getStatus() {
        return status;
    }

    public void setStatus(EnumStatusPedido status) {
        this.status = status;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
                ", quantidade=" + quantidade +
                ", uuid='" + uuid + '\'' +
                ", dataPedido=" + dataPedido +
                ", status=" + status +
                ", fornecedor=" + fornecedor +
                ", produto=" + produto +
                ", vendas=" + vendas +
                '}';
    }
}