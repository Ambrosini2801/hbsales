package br.com.hbsis.produto;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorDTO;
import br.com.hbsis.Fornecedor.FornecedorService;
import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categorialinha.CategoriaLinha;
import br.com.hbsis.categorialinha.CategoriaLinhaDTO;
import br.com.hbsis.categorialinha.CategoriaLinhaService;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);
    private final IProdutoRepository iProdutoRepository;
    private final CategoriaLinhaService categoriaLinhaService;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;

    @Autowired
    public ProdutoService(IProdutoRepository iProdutoRepository, CategoriaLinhaService categoriaLinhaService, FornecedorService fornecedorService, CategoriaService categoriaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.categoriaLinhaService = categoriaLinhaService;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        this.validate(produtoDTO);
        LOGGER.info("Salvando produto");
        LOGGER.debug("Produto: {}", produtoDTO.getCategoriaLinha());

        CategoriaLinha categoriaLinha = categoriaLinhaService.findByLinhaId(produtoDTO.getCategoriaLinha());

        Produto produto = new Produto();

        produto.setCodProduto(produtoDTO.getCodProduto());
        produto.setNomeProduto(produtoDTO.getNomeProduto().toUpperCase());
        produto.setPrecoProduto(produtoDTO.getPrecoProduto());
        produto.setUnidadeCx(produtoDTO.getUnidadeCx());
        produto.setPesoUni(produtoDTO.getPesoUni());
        produto.setValProduto(produtoDTO.getValProduto());
        produto.setCategoriaLinha(categoriaLinha);

        String unidadePeso = produtoDTO.getUnidadePeso();
        if (unidadePeso.equals("mg") || unidadePeso.equals("g") || unidadePeso.equals("kg")) {
            produto.setUnidadePeso(unidadePeso);
        } else {
            throw new IllegalArgumentException("Informe peso em 'mg' 'g' ou 'Kg'");
        }

        produto = this.iProdutoRepository.save(produto);

        // TODO: 12/12/2019 utilziar método estático através da classe
        return ProdutoDTO.of(produto);
    }

    private void validate(ProdutoDTO produtoDTO) {
        LOGGER.info("Validando produto");
        if (produtoDTO == null) {
            throw new IllegalArgumentException("Produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getCodProduto())) {
            throw new IllegalArgumentException("Código do produto não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(produtoDTO.getNomeProduto())) {
            throw new IllegalArgumentException("Nome do produto não deve ser nulo ou vazio!");
        }

        if (String.valueOf(produtoDTO.getPesoUni()).equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Peso por unidade não deve ser nulo ou vazio!");
        }

        if (String.valueOf(produtoDTO.getPrecoProduto()).equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Preço não deve ser nulo ou vazio!");
        }

        if (String.valueOf(produtoDTO.getUnidadePeso()).equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Unidade de peso não deve ser nula ou vazia!");
        }

        if (produtoDTO.getValProduto() == null) {
            throw new IllegalArgumentException("Validade não deve ser nula ou vazia!");
        }

        if (String.valueOf(produtoDTO.getUnidadeCx()).equalsIgnoreCase("")) {
            throw new IllegalArgumentException("Unidade de caixa não deve ser nula ou vazia!");
        }
    }

    public ProdutoDTO findByid(Long id) {
        Optional<Produto> produtoOptional = this.iProdutoRepository.findById(id);

        if (produtoOptional.isPresent()) {
            return ProdutoDTO.of(produtoOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para produto de ID: [{}]", id);
        this.iProdutoRepository.deleteById(id);
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

    public Fornecedor converter(FornecedorDTO fornecedorDTO) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(fornecedorDTO.getId());
        return fornecedor;
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id) {
        Optional<Produto> produtoExistenteOptional =
                this.iProdutoRepository.findById(id);

        if (produtoExistenteOptional.isPresent()) {
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando produto... id: [{}]", produtoDTO.getCodProduto());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produtoExistente);

            produtoExistente.setCodProduto(produtoDTO.getCodProduto().toUpperCase());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto().toUpperCase());
            this.categoriaLinhaService.findByIdcategorialinha(produtoDTO.getCategoriaLinha());
            produtoExistente.setPrecoProduto(produtoDTO.getPrecoProduto());
            produtoExistente.setUnidadeCx(produtoDTO.getUnidadeCx());
            produtoExistente.setPesoUni(produtoDTO.getPesoUni());
            produtoExistente.setUnidadePeso(produtoDTO.getUnidadePeso());
            produtoExistente.setValProduto(produtoDTO.getValProduto());

            produtoExistente = this.iProdutoRepository.save(produtoExistente);
            return produtoDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public String mascaraCNPJ(String cnpj) {
        try {
            MaskFormatter format = new MaskFormatter("##.###.###/####-##");
            format.setValueContainsLiteralCharacters(false);
            return format.valueToString(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void exportCSV(HttpServletResponse exportProduto) throws IOException {
        String nomeProduto = "produtos.csv";

        exportProduto.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomeProduto + "\"");
        exportProduto.setContentType("text/csv");

        PrintWriter writer = exportProduto.getWriter();
        String lista = ("cod_produto; nome_produto; preco_produto; unidade_cx; peso_uni; val_produto; cod_linha; nome_linha; cod_categoria; nome_categoria; cnpj; razao_social");

        writer.write(lista);

        for (Produto linha : iProdutoRepository.findAll()) {
            writer.write("\n");

            writer.append(linha.getCodProduto() + ";");
            writer.append(linha.getNomeProduto() + ";");
            writer.append("R$ " + linha.getPrecoProduto() + ";");
            writer.append(linha.getUnidadeCx() + ";");
            writer.append(linha.getPesoUni() + ";");
            writer.append("un " + linha.getUnidadePeso() + ";");
            writer.append(linha.getValProduto().toString() + ";");

            CategoriaLinhaDTO categoriaLinhaDTO  = categoriaLinhaService.findById(linha.getCategoriaLinha().getId());
            CategoriaLinha categoriaLinha = converter(categoriaLinhaDTO);

            writer.append(categoriaLinhaDTO.getCodLinha() + ";");
            writer.append(categoriaLinhaDTO.getNomeLinha() + ";");

            CategoriaDTO categoriaDTO = categoriaService.findById(linha.getId());
            Categoria categoria = converter(categoriaDTO);

            writer.append(categoriaDTO.getCodCategoria().toUpperCase() + ";");
            writer.append(categoriaDTO.getNomeCategoria().toUpperCase() + ";");

            FornecedorDTO fornecedorDTO = fornecedorService.findById(linha.getCategoriaLinha().getCategoria().getFornecedor().getId());
            Fornecedor fornecedor = converter(fornecedorDTO);

            writer.append(fornecedorDTO.getRazaoSocial() + ";");
            writer.append(mascaraCNPJ(fornecedorDTO.getCnpj() + ";"));

            writer.flush();
        }
    }

    public void uploadCSV(MultipartFile importProduto) throws IOException {
        LOGGER.info("Importando Produto - CSV");

        BufferedReader produtoReader = new BufferedReader(new InputStreamReader(importProduto.getInputStream()));
        produtoReader.readLine();
        String produtos;
        List<String[]> produtoCSV = new ArrayList<>();
        while ((produtos = produtoReader.readLine()) != null) {
            String[] list = produtos.split(";");
            produtoCSV.add(list);
            Iterator<String[]> iterator = produtoCSV.iterator();
            String[] uplando;
            Produto produto1 = new Produto();

            while (iterator.hasNext()) {
                try {
                    uplando = iterator.next();

                    for (String[] produto : produtoCSV) {
                        String[] produtoCadastro = produto[0].replaceAll("\"", "").split(";");

                        produto1.setCodProduto(produtoCadastro[0]);
                        produto1.setNomeProduto(produtoCadastro[1]);
                        produto1.setPrecoProduto((double) Double.parseDouble(produtoCadastro[2]));
                        produto1.setUnidadeCx((int) Long.parseLong(produtoCadastro[3]));
                        produto1.setPesoUni((double) Double.parseDouble(produtoCadastro[4]));
                        produto1.setUnidadePeso(produtoCadastro[5]);
                        produto1.setValProduto((LocalDate.parse(produtoCadastro[6])));

                        categoriaLinhaService.findByIdcategorialinha(Long.parseLong(produtoCadastro[7]));

                        this.iProdutoRepository.save(produto1);
                    }

                } catch (Exception e) {
                    LOGGER.info("Importação concluída!");
                }
            }
        }
    }
}