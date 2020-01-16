package br.com.hbsis.Vendas;

import java.time.LocalDate;

public class VendasDTO {

    private Long id;
    private LocalDate inicioVendas;
    private LocalDate fimVendas;
    private LocalDate retiradaPedido;
    private String descricao;
    private Long fornecedor;

    public VendasDTO() {

    }

    public VendasDTO(Long id, LocalDate inicioVendas, LocalDate fimVendas, LocalDate retiradaPedido, String descricao, Long fornecedor) {
        this.id = id;
        this.inicioVendas = inicioVendas;
        this.fimVendas = fimVendas;
        this.retiradaPedido = retiradaPedido;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
    }

    public static VendasDTO of(Vendas vendas) {
        return new VendasDTO(
               vendas.getId(),
                vendas.getInicioVendas(),
                vendas.getFimVendas(),
                vendas.getRetiradaPedido(),
                vendas.getDescricao(),
                vendas.getFornecedor().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioVendas() {
        return inicioVendas;
    }

    public void setInicioVendas(LocalDate inicioVendas) {
        this.inicioVendas = inicioVendas;
    }

    public LocalDate getFimVendas() {
        return fimVendas;
    }

    public void setFimVendas(LocalDate fimVendas) {
        this.fimVendas = fimVendas;
    }

    public LocalDate getRetiradaPedido() {
        return retiradaPedido;
    }

    public void setRetiradaPedido(LocalDate retiradaPedido) {
        this.retiradaPedido = retiradaPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(long fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "VendasDTO{" +
                "id=" + id +
                ", inicioVendas=" + inicioVendas +
                ", fimVendas=" + fimVendas +
                ", retiradaPedido=" + retiradaPedido +
                ", descricao='" + descricao + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}
