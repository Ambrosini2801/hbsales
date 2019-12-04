package br.com.hbsis.produto;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;

    private final IProdutoRepository iProdutoRepository;
    private final ProdutoService produtoService;

    @Autowired
    public ProdutoService(IProdutoRepository iProdutoRepository, ProdutoService produtoService) {
        this.iProdutoRepository = iProdutoRepository;
        this.produtoService = produtoService;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        this.validate(produtoDTO);
        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidadeCx(produtoDTO.getUnidadeCx());
        produto.setPesoUni(produtoDTO.getPesoUni());
        produto.setValProduto(produtoDTO.getValProduto());

        produto = this.iProdutoRepository.save(produto);
        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando Categoria");
        if (produtoService == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo");
        }
    }

    public ProdutoDTO findByid(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoExistenteOptional =
                this.iProdutoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoDTO.getCodProduto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produtoExistente);

            produtoExistente = this.iProdutoRepository.save(produtoExistente);
            return produtoDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);
        this.iProdutoRepository.deleteById(id);
    }

    public List<Produto> findAll() {
        return iProdutoRepository.findAll();
    }

    public void importCSV() throws IOException {

        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
        List<String[]> produtoCSV = cs.readAll();
        Produto produtocadastro = new Produto();

        for (String[] produto : produtoCSV) {
            String[] produtoCadastro = produto[0].replaceAll("\"", "").split(";");

            produtocadastro.setCodProduto(Long.parseLong(produtoCadastro[0]));
            produtocadastro.setNomeProduto(produtoCadastro[1]);
            produtocadastro.setPrecoProduto(Long.parseLong(produtoCadastro[2]));
            produtocadastro.setUnidadeCx(produtoCadastro[3]);
            produtocadastro.setPesoUni(produtoCadastro[4]);
            produtocadastro.setValProduto(produtoCadastro[5]);

            this.iProdutoRepository.save(produtocadastro);
        }


    }
}
