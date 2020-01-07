package br.com.hbsis.Api.Employee;

public class EmployeeSavingDTO {
    private String employeeName;
    private String employeeUuid;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public EmployeeSavingDTO(String employeeName, String employeeUuid) {
        this.employeeName = employeeName;
        this.employeeUuid = employeeUuid;
    }

    public EmployeeSavingDTO(){

    }
}