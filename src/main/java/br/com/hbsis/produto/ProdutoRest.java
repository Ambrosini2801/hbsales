package br.com.hbsis.produto;

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
@RequestMapping("/produto")
public class ProdutoRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);
    private final ProdutoService ProdutoService;

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

    @GetMapping("/exportproduto")
    public void exportCSV(HttpServletResponse response) throws Exception {

        String produto = "produto.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + produto);

        PrintWriter writer = response.getWriter();

        ICSVWriter csvwriter = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {"cod_produto", "nome_produto", "preco_produto", "unidade_cx",
                "peso_uni", "val_produto", "id_categorialinha"};
        csvwriter.writeNext(headerCSV);

        //SimpleDateFormat valProduto = new SimpleDateFormat("dd/mm/yyyy");

        for (Produto produtos : findAll()) {
            csvwriter.writeNext(new String[]{String.valueOf(produtos.getId()),
                    String.valueOf(produtos.getCodProduto()),
                    produtos.getNomeProduto(),
                    String.valueOf(produtos.getPrecoProduto()),
                    String.valueOf(produtos.getUnidadeCx()),
                    String.valueOf(produtos.getPesoUni()),
                    String.valueOf(produtos.getValProduto()),
                    String.valueOf(produtos.getCategoriaLinha())
            });
        }
    }

    private Iterable<? extends Produto> findAll() {
        return null;
    }

    /*@PostMapping("/import")
    public void importCSV() throws Exception {
        ProdutoService.importCSV();

    }*/
}