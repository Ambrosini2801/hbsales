package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorDTO;

public class CategoriaDTO {

    private Long id;
    private String nomeCategoria;
    private String codCategoria;
    private Long fornecedor;

    public CategoriaDTO() {
    }

    public CategoriaDTO(Long id, String nomeCategoria, String codCategoria, Long fornecedor) {
        this.id = id;
        this.nomeCategoria = nomeCategoria;
        this.codCategoria = codCategoria;
        this.fornecedor = fornecedor;

    }

    public static CategoriaDTO of(Categoria categoriaExistente) {
        return new CategoriaDTO(
                categoriaExistente.getId(),
                categoriaExistente.getNomeCategoria(),
                categoriaExistente.getCodCategoria(),
                categoriaExistente.getFornecedor().getId()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public Long getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Long fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", nomeCategoria='" + nomeCategoria + '\'' +
                ", codCategoria='" + codCategoria + '\'' +
                ", fornecedor=" + fornecedor +
                '}';
    }
}