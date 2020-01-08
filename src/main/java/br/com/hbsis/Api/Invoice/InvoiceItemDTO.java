package br.com.hbsis.Api.Invoice;

public class InvoiceItemDTO {
    private String cnpjFornecedor;
    private String employeeUuid;

    public InvoiceItemDTO() {
    }

    public InvoiceItemDTO(String cnpjFornecedor, String employeeUuid) {
        this.cnpjFornecedor = cnpjFornecedor;
        this.employeeUuid = employeeUuid;
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

    @Override
    public String toString() {
        return "InvoiceItemDTO{" +
                "cnpjFornecedor='" + cnpjFornecedor + '\'' +
                ", employeeUuid='" + employeeUuid + '\'' +
                '}';
    }
}