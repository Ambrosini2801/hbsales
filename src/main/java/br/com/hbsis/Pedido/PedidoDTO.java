package br.com.hbsis.Pedido;

import java.time.LocalDate;

public class PedidoDTO {

    private Long id;
    private String codigo;
    private EnumStatusPedido status;
    private String uuid;
    private LocalDate dataPedido;
    private Long fornecedor;
    private Long vendas;

    public PedidoDTO() {

    }

    public PedidoDTO(Long id, String codigo, EnumStatusPedido status, String uuid, LocalDate dataPedido, Long fornecedor, Long vendas) {
        this.id = id;
        this.codigo = codigo;
        this.status = status;
        this.uuid = uuid;
        this.dataPedido = dataPedido;
        this.fornecedor = fornecedor;
        this.vendas = vendas;
    }

    public static PedidoDTO of(Pedido pedido) {
        return new PedidoDTO(
                pedido.getId(),
                pedido.getCodPedido(),
                pedido.getStatus(),
                pedido.getUuid(),
                pedido.getDataPedido(),
                pedido.getFornecedor().getId(),
                pedido.getVendas().getId()
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

    public EnumStatusPedido getStatus(EnumStatusPedido cancelado) {
        return status;
    }

    public void setStatus(EnumStatusPedido status) {
        this.status = status;
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

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getVendas() {
        return vendas;
    }

    public void setVendas(Long vendas) {
        this.vendas = vendas;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", status=" + status +
                ", uuid='" + uuid + '\'' +
                ", dataPedido=" + dataPedido +
                ", fornecedor=" + fornecedor +
                ", vendas=" + vendas +
                '}';
    }
}