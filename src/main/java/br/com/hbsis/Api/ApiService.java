package br.com.hbsis.Api;

import br.com.hbsis.Api.Employee.EmployeeDTO;
import br.com.hbsis.Api.Employee.EmployeeSavingDTO;
import br.com.hbsis.Api.Invoice.InvoiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class ApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    public String getUuid(String nome) {
        String url = "http://10.2.54.25:9999/api/employees";
        RestTemplate restTemplate = new RestTemplate();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setNome(nome);
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f5a004ce-1b67-11ea-978f-2e728ce88125");

        HttpEntity<EmployeeDTO> entity = new HttpEntity<>(employeeDTO, headers);
        ResponseEntity<EmployeeSavingDTO> result = restTemplate.exchange(url, HttpMethod.POST, entity, EmployeeSavingDTO.class);

        if (result.getStatusCodeValue() == 200) {
            return result.getBody().getEmployeeUuid();
        } else throw new IllegalArgumentException("Falha na busca de Uuid!" + result.getStatusCode().toString());
    }

    public String sendValidacao(InvoiceDTO invoiceDTO) throws IOException {

        String url = "http://10.2.54.25:9999/api/invoice";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f5a004ce-1b67-11ea-978f-2e728ce88125");

        HttpEntity<InvoiceDTO> entity = new HttpEntity<>(invoiceDTO, headerApiHbEmployee());
        ResponseEntity<InvoiceDTO> result = restTemplate.exchange(url, HttpMethod.POST, entity, InvoiceDTO.class);
        LOGGER.info(result.getStatusCode().toString());

        return result.getStatusCode().toString();
    }

    public static HttpHeaders headerApiHbEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Content-type", "application/json");
        headers.set("Authorization", "f59ffdbc-1b67-11ea-978f-2e728ce88125");
        return headers;
    }
}