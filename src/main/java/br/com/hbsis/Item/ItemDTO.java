package br.com.hbsis.Item;

import br.com.hbsis.Produto.Produto;

public class ItemDTO {

    private Long id;
    private int quantidade;
    private double preco;
    private Long produto;
    private Long pedido;

    public ItemDTO() {
    }


    public ItemDTO(Long id, int quantidade, double preco, Long produto, Long pedido) {
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.produto = produto;
        this.pedido = pedido;
    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getQuantidade(),
                item.getPreco(),
                item.getProduto().getId(),item.getPedido().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
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

    public Long getProduto() {
        return produto;
    }

    public void setProduto(Long produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "id=" + id +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                ", produto=" + produto +
                '}';
    }
}