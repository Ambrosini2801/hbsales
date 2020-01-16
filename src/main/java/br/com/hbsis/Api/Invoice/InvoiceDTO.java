package br.com.hbsis.Api.Invoice;

import java.util.Set;

public class InvoiceDTO {

    private String cnpjFornecedor;
    private String employeeUuid;
    private Set<InvoiceItemDTO> invoiceItemDTOSet;
    private Number totalValue;


    public InvoiceDTO() {
    }

    public InvoiceDTO(String cnpjFornecedor, String employeeUuid, Number totalValue) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
        this.totalValue = totalValue;

    }

    public String getCnpjFornecedor() {
        return cnpjFornecedor;
    }

    public void setCnpjFornecedor(String cnpjFornecedor) {
        this.cnpjFornecedor = cnpjFornecedor;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public Number getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Number totalValue) {
        this.totalValue = totalValue;
    }

    public Set<InvoiceItemDTO> getInvoiceItemDTOSet() {
        return invoiceItemDTOSet;
    }

    public void setInvoiceItemDTOSet(Set<InvoiceItemDTO> invoiceItemDTOSet) {
        this.invoiceItemDTOSet = invoiceItemDTOSet;
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
                "cnpjFornecedor='" + cnpjFornecedor + '\'' +
                ", employeeUuid='" + employeeUuid + '\'' +
                ", totalValue=" + totalValue +
                '}';
    }
}