package br.com.hbsis.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/produto")
public class ProdutoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);
    private ProdutoService ProdutoService = null;

    @Autowired
    public ProdutoRest(ProdutoService produtoService) {
        this.ProdutoService = produtoService;
    }

    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Receber solicitação do produto");
        LOGGER.debug("Payload: {}", produtoDTO);
        return this.ProdutoService.save(produtoDTO);
    }

    @GetMapping("/{id}")
    public ProdutoDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by ID... id: [{}]", id);
        return this.ProdutoService.findByid(id);
    }

    @PutMapping("{/id}")
    public ProdutoDTO update(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo Update para produto de ID: {}", id);
        LOGGER.info("Payload: {}", produtoDTO);
        return this.ProdutoService.update(produtoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo Delete para Produto de ID: {}", id);
        this.ProdutoService.delete(id);
    }

    @GetMapping("/exportProduto")
    public void exportCSV(HttpServletResponse exportProduto) throws Exception {
        LOGGER.info("Exportando Produto CSV");
        this.ProdutoService.exportCSV(exportProduto);

    }

    @PostMapping("/importProduto")
    public void uploadCSV (@RequestParam ("file")MultipartFile importProduto) throws  Exception{
        LOGGER.info("Importando Produto CSV!");
        this.ProdutoService.uploadCSV(importProduto);
    }

    @PostMapping("/importFornecedor/{id}")
    public void uploadFornecedor(@RequestParam ("file")MultipartFile importFornecedor,@PathVariable Long id) throws Exception{
        LOGGER.info("Importando produto por fornecedor - CSV");
        this.ProdutoService.uploadFornecedor(importFornecedor,id);
    }

}