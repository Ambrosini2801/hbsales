package br.com.hbsis.Categoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);
    private final CategoriaService CategoriaService;
    private Object categoriaService;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService) {
        this.CategoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaDTO save(@RequestBody CategoriaDTO categoriaDTO) {
        LOGGER.info("Receber a solicitação da categoria");
        LOGGER.debug("Payload: {}", categoriaDTO);
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
    public void exportCSV(HttpServletResponse export) throws Exception {
        LOGGER.info("Exportando Arquivo CSV-Categorias disponíveis...");
        this.CategoriaService.exportCSV(export);
    }

    @PostMapping("/import")
    public void uploadCSV(@RequestParam("file") MultipartFile importCat) throws Exception {
        LOGGER.info("Importando arquivo categorias.csv'");
        this.CategoriaService.uploadCSV(importCat);
    }
}