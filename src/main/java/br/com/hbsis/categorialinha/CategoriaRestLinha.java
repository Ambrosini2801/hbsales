package br.com.hbsis.categorialinha;


import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping("/linhas")
public class CategoriaRestLinha {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRestLinha.class);
    private final CategoriaLinhaService CategoriaLinhaService;

    @Autowired
    public CategoriaRestLinha(CategoriaLinhaService categoriaLinhaService) {
        CategoriaLinhaService = categoriaLinhaService;
    }

    @PostMapping
    public CategoriaLinhaDTO save(@RequestBody CategoriaLinhaDTO categoriaLinhaDTO) {
        LOGGER.info("Receber solitação da categoria linha");
        LOGGER.debug("Payaload: {}", categoriaLinhaDTO);
        return this.CategoriaLinhaService.save(categoriaLinhaDTO);
    }

    @GetMapping("/{id}")
    public CategoriaLinhaDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID.. id: [{}]", id);
        return this.CategoriaLinhaService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriaLinhaDTO update(@PathVariable("id") Long id, @RequestBody CategoriaLinhaDTO categoriaLinhaDTO) {
        LOGGER.info("Recebendo Update para categoria de ID: {}", id);
        LOGGER.info("Payload: {}", categoriaLinhaDTO);
        return this.CategoriaLinhaService.update(categoriaLinhaDTO, id);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria de ID: {}", id);
        this.CategoriaLinhaService.delete(id);
    }

    @GetMapping("/exportlinha")
    public void exportCSV(HttpServletResponse response) throws Exception {

        String nomelinha = "linhas.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomelinha);

        PrintWriter writer = response.getWriter();

        ICSVWriter csvwriter = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {"cod_linha", "nome_linha", "catLinha"};
        csvwriter.writeNext(headerCSV);

        for (CategoriaLinha linha : CategoriaLinhaService.findAll()) {
            csvwriter.writeNext(new String[]{String.valueOf(linha.getId()),
                    linha.getCatLinha(),
                    linha.getNomeLinha(),
                    linha.getCodLinha()});
        }

    }

    @PostMapping("/import")
    public void importCSV() throws Exception {
        CategoriaLinhaService.importCSV();

    }
}