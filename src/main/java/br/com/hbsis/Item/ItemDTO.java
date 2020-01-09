package br.com.hbsis.Item;

import br.com.hbsis.produto.Produto;

public class ItemDTO {

    private Long id;
    private int quantidade;
    private Produto produto;

    public ItemDTO() {
    }

    public ItemDTO(Long id, int quantidade, Produto produto) {
        this.id = id;
        this.quantidade = quantidade;
        this.produto = produto;
    }

    public ItemDTO(Long id, int quantidade, Long id1) {
    }

    public static ItemDTO of(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getQuantidade(),
                item.getProduto().getId()
        );
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

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "produto=" + produto +
                ", id=" + id +
                ", quantidade=" + quantidade +
                '}';
    }
}