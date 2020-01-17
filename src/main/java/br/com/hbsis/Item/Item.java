package br.com.hbsis.Item;

import br.com.hbsis.Pedido.Pedido;
import br.com.hbsis.Produto.Produto;

import javax.persistence.*;

@Entity
@Table(name = "itens")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "quantidade", nullable = false)
    private int quantidade;
    @Column(name = "preco", length = 6, nullable = false)
    private double preco;

    @ManyToOne
    @JoinColumn(name = "fk_produto", referencedColumnName = "id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", referencedColumnName = "id_pedido")
    private Pedido pedido;

    public Item(Long id, int quantidade, double preco, Produto produto, Pedido pedido) {
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        this.pedido = pedido;
    }

    public Item() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", produto=" + produto +
                '}';
    }
}