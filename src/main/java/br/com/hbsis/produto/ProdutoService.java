package br.com.hbsis.produto;

import br.com.hbsis.categorialinha.CategoriaLinhaService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;
    private final IProdutoRepository iProdutoRepository;
    private final CategoriaLinhaService categoriaLinhaService;
    private ProdutoService iCategoriaLinhaRepository;

    @Autowired
    public ProdutoService(IProdutoRepository iProdutoRepository, CategoriaLinhaService categoriaLinhaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaLinhaService = categoriaLinhaService;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO.getCategoriaLinhaID());

        Produto produto = new Produto();

        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidadeCx(produtoDTO.getUnidadeCx());
        produto.setPesoUni(produtoDTO.getPesoUni());
        produto.setValProduto(produtoDTO.getValProduto());
        produto.setCategoriaLinha(categoriaLinhaService.findByIdcategorialinha(produtoDTO.getCategoriaLinhaID()));

        produto = this.iProdutoRepository.save(produto);
        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");
        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty( produtoDTO.getCodProduto())){
            throw new IllegalArgumentException("Código da linha da categoria não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())){
            throw  new  IllegalArgumentException("Nome do produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getPesoUni())){
            throw  new IllegalArgumentException("Peso por unidade não deve ser nulo ou vazio!");
        }

//////        if (StringUtils.isEmpty(produtoDTO.getPrecoProduto())){
//////            throw  new IllegalArgumentException("Preço do produto não deve ser nulo ou vazio!");
////
//        if (StringUtils.isEmpty(produtoDTO.getUnidadeCx()){
//            throw  new IllegalArgumentException("Unidade da caixa não deve ser nula ou vazia!");
//        }
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

    public Produto findAll() {
        return (Produto) iProdutoRepository.findAll();
    }

 /*   public void importCSV() throws IOException {

        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
        List<String[]> produtoCSV = cs.readAll();
        Produto produtocadastro = new Produto();

        for (String[] produto : produtoCSV) {
            String[] produtoCadastro = produto[0].replaceAll("\"", "").split(";");

            produtocadastro.setCodProduto(Long.parseLong(produtoCadastro[0]));
            produtocadastro.setNomeProduto(produtoCadastro[1]);
            produtocadastro.setPrecoProduto((float) Long.parseLong(produtoCadastro[2]));
            produtocadastro.setUnidadeCx((float) Long.parseLong(produtoCadastro[3]));
            produtocadastro.setPesoUni((float) Long.parseLong(produtoCadastro[4]));
            produtocadastro.setValProduto(produtoCadastro[5].toString());
            produtocadastro.setCategoriaLinha(produtoCadastro[6]);

            this.iProdutoRepository.save(produtocadastro);
        }
    }*/
}