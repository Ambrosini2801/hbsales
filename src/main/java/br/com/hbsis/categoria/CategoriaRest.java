package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
@RequestMapping("/categorias")
public class CategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);
    private final CategoriaService CategoriaService;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService) {
        this.CategoriaService = categoriaService;

    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Receber a solicitação da categoria");
        LOGGER.debug("Payaload: {}", categoriaDTO);
        return this.CategoriaService.save(categoriaDTO);
    }

    @GetMapping("/{id}")
    public CategoriaDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID.. id: [{}]", id);
        return this.CategoriaService.findById(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO update(@PathVariable("id") Long id, @RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Recebendo Update para categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaDTO);
        return this.CategoriaService.update(categoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Categoria de ID: {}", id);
        this.CategoriaService.delete(id);
    }

    @GetMapping("/export")
    public void exportCSV(HttpServletResponse response) throws Exception {

        String nomeArquivo = "categorias.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; fileName=\"" + nomeArquivo);

        PrintWriter writer = response.getWriter();

        ICSVWriter csvwriter = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {"nome_categoria", "cod_categoria", "id_fornecedor"};
        csvwriter.writeNext(headerCSV);

        for (Categoria linha : CategoriaService.findAll()) {
            csvwriter.writeNext(new String[]{linha.getNomeCategoria(),
                    linha.getCodCategoria(),
                    String.valueOf(linha.getId())});
        }


    }

    @PostMapping("/import")
    public CategoriaDTO importCsv() throws Exception {
        String arquivo = "import.csv";
        Reader reader = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\categorias.import"));
        CSVReader CSVReader = new CSVReader(reader, ';');

        String[] line;
        while ((line = CSVReader.readNext()) != null) {
            Fornecedor fornecedorCompleto = new Fornecedor();
            CategoriaDTO categoriaDTO = new CategoriaDTO();
            categoriaDTO.setId(Long.parseLong(line[0]));
            categoriaDTO.setNomeCategoria(line[1]);

            return this.CategoriaService.save(categoriaDTO);

        }
        return null;
    }
}




