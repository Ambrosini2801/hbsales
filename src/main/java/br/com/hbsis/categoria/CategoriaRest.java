package br.com.hbsis.categoria;

import br.com.hbsis.categorialinha.CategoriaLinhaService;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
        HttpServletResponse file = null;
        CategoriaLinhaService.findAll(file);
    }

    @PostMapping("/import")
    public void importCSV(@RequestParam ("file")MultipartFile arquivo) throws Exception {
        CategoriaLinhaService.importCSV(arquivo);
    }


}

