package br.com.hbsis.produto;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.ICategoriaRepository;
import br.com.hbsis.categorialinha.CategoriaLinha;
import br.com.hbsis.categorialinha.CategoriaLinhaDTO;
import br.com.hbsis.categorialinha.CategoriaLinhaService;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;
    private final IProdutoRepository iProdutoRepository;
    private final CategoriaLinhaService categoriaLinhaService;

    @Autowired
    public ProdutoService(IProdutoRepository iProdutoRepository, CategoriaLinhaService categoriaLinhaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaLinhaService = categoriaLinhaService;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO.getCategoriaLinha());
        // TODO: 12/12/2019 chamar validate de produto

        Produto produto = new Produto();

        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidadeCx(produtoDTO.getUnidadeCx());
        produto.setPesoUni(produtoDTO.getPesoUni());
        produto.setValProduto(produtoDTO.getValProduto());
        produto.setCategoriaLinha(categoriaLinhaService.findByIdcategorialinha(produtoDTO.getCategoriaLinha()));

        produto = this.iProdutoRepository.save(produto);
        // TODO: 12/12/2019 utilziar método estático através da classe
        return produtoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");
        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getCodProduto())) {
            throw new IllegalArgumentException("Código da linha da categoria não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())) {
            throw new IllegalArgumentException("Nome do produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getPesoUni())) {
            throw new IllegalArgumentException("Peso por unidade não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getPrecoProduto())) {
            throw new IllegalArgumentException("Preço do produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getUnidadeCx())) {
            throw new IllegalArgumentException("Unidade da caixa não deve ser nula ou vazia!");
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

    public Produto findAll() {
        return (Produto) iProdutoRepository.findAll();

    }

    public CategoriaLinha converter(CategoriaLinhaDTO categoriaLinhaDTO) {
        CategoriaLinha CategoriaLinha = new CategoriaLinha();
        CategoriaLinha.setId(categoriaLinhaDTO.getId());
        return CategoriaLinha;
    }

    public Categoria converter(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        return categoria;
    }

    public void exportCSV(HttpServletResponse exportProduto) throws IOException {
        String nomeProduto = "produtos.csv";
        exportProduto.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomeProduto);
        exportProduto.setContentType("text/csv");

        PrintWriter writer = exportProduto.getWriter();
        String lista = ("cod_produto; nome_produto; preco_produto; unidade_cx; peso_uni; val_produto");
        writer.write(lista);

        for (Produto linha : iProdutoRepository.findAll()) {
            writer.write("\n");

            writer.append(linha.getCodProduto() + ";");
            writer.append(linha.getNomeProduto() + ";");
            writer.append(linha.getPrecoProduto() + ";");
            writer.append(linha.getUnidadeCx() + ";");
            writer.append(linha.getPesoUni() + ";");
            writer.append(linha.getValProduto() + ";");

            CategoriaLinhaDTO categoriaLinhaDTO = categoriaLinhaService.findById(linha.getCategoriaLinha().getId());
            CategoriaLinha categoriaLinha = converter(categoriaLinhaDTO);

            writer.append(categoriaLinhaDTO.getCodLinha() + ";");
            writer.append(categoriaLinhaDTO.getNomeLinha() + ";");

            ICategoriaRepository categoriaService;
            CategoriaDTO categoriaDTO = categoriaService.findById(linha.getCategoria().getId());
            Categoria categoria = converter(categoriaDTO);


            writer.flush();

        }


//    public void importCSV() throws IOException {
//
//        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
//        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
//        List<String[]> produtoCSV = cs.readAll();
//        Produto produtocadastro = new Produto();
//
//        for (String[] produto : produtoCSV) {
//            String[] produtoCadastro = produto[0].replaceAll("\"", "").split(";");
//
//            produtocadastro.setCodProduto(Long.parseLong(produtoCadastro[0]));
//            produtocadastro.setNomeProduto(produtoCadastro[1]);
//            produtocadastro.setPrecoProduto((float) Long.parseLong(produtoCadastro[2]));
//            produtocadastro.setUnidadeCx((float) Long.parseLong(produtoCadastro[3]));
//            produtocadastro.setPesoUni((float) Long.parseLong(produtoCadastro[4]));
//            produtocadastro.setValProduto(produtoCadastro[5].toString());
//            produtocadastro.setCategoriaLinha(produtoCadastro[6]);
//
//            this.iProdutoRepository.save(produtocadastro);
//        }
//    }*/
    }

