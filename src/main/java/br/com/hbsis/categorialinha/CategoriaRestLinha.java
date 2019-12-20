package br.com.hbsis.categorialinha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linhas")
public class CategoriaRestLinha {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRestLinha.class);
    private final CategoriaLinhaService CategoriaLinhaService;

    @Autowired
    public CategoriaRestLinha(CategoriaLinhaService categoriaLinhaService) {
       this.CategoriaLinhaService = categoriaLinhaService;
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

    @GetMapping("/exportLinha")
    public void exportCSV(HttpServletResponse export) throws Exception {
        LOGGER.info("Exportando Arquivo CSV.");
        this.CategoriaLinhaService.exportCSV(export);
    }

    @PostMapping("/importLinha")
    public void upload (@RequestParam("file") MultipartFile importLinha) throws Exception {
        LOGGER.info("Importando arquivo categorias.csv'");
        this.CategoriaLinhaService.upload(importLinha);

    }
}