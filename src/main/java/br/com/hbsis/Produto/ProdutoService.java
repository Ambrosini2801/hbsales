package br.com.hbsis.Produto;

import br.com.hbsis.Categoria.Categoria;
import br.com.hbsis.Categoria.CategoriaDTO;
import br.com.hbsis.Categoria.CategoriaService;
import br.com.hbsis.CategoriaLinha.CategoriaLinha;
import br.com.hbsis.CategoriaLinha.CategoriaLinhaDTO;
import br.com.hbsis.CategoriaLinha.CategoriaLinhaService;
import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorDTO;
import br.com.hbsis.Fornecedor.FornecedorService;
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

        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getPesoUni()))) {
            throw new IllegalArgumentException("Peso por unidade não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getPrecoProduto()))) {
            throw new IllegalArgumentException("Preço não deve ser nulo ou vazio!");
        }

        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getUnidadePeso()))) {
            throw new IllegalArgumentException("Unidade de medida não deve ser nula ou vazia!");
        }

        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getValProduto()))) {
            throw new IllegalArgumentException("Validade não deve ser nula ou vazia!");
        }

        if (StringUtils.isEmpty(String.valueOf(produtoDTO.getUnidadeCx()))) {
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
            LOGGER.debug("Payaload: {}", produtoDTO);
            LOGGER.debug("Produto Existente: {}", produtoExistente);

            produtoExistente.setCodProduto(produtoDTO.getCodProduto().toUpperCase());
            produtoExistente.setNomeProduto(produtoDTO.getNomeProduto().toUpperCase());
            produtoExistente.setCategoriaLinha(categoriaLinhaService.findByIdcategorialinha(produtoDTO.getCategoriaLinha()));
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
            CategoriaLinhaDTO categoriaLinhaDTO = categoriaLinhaService.findById(linha.getCategoriaLinha().getId());
            CategoriaDTO categoriaDTO = categoriaService.findById(linha.getId());
            FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorById(linha.getCategoriaLinha().getCategoria().getFornecedor().getId());

            writer.write("\n");
            writer.append(linha.getCodProduto() + ";");
            writer.append(linha.getNomeProduto() + ";");
            writer.append("R$ " + linha.getPrecoProduto() + ";");
            writer.append(linha.getUnidadeCx() + ";");
            writer.append(linha.getPesoUni() + ";");
            writer.append(linha.getUnidadePeso() + ";");
            writer.append(linha.getValProduto().toString() + ";");
            writer.append(categoriaLinhaDTO.getCodLinha() + ";");
            writer.append(categoriaLinhaDTO.getNomeLinha() + ";");
            writer.append(categoriaDTO.getCodCategoria().toUpperCase() + ";");
            writer.append(categoriaDTO.getNomeCategoria().toUpperCase() + ";");
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
        if ((produtos = produtoReader.readLine()) != null) {
            String[] list = produtos.split(";");
            produtoCSV.add(list);
            Iterator<String[]> iterator = produtoCSV.iterator();
            String[] registro;
            Produto produto1 = new Produto();

            while (iterator.hasNext()) {
                try {
                    registro = iterator.next();

                    for (String[] produto : produtoCSV) {
                        String[] produtoCadastro = produto[0].replaceAll("\"", "").split(";");

                        produto1.setCodProduto(produtoCadastro[0]);
                        produto1.setNomeProduto(produtoCadastro[1]);
                        produto1.setPrecoProduto(Double.parseDouble(produtoCadastro[2]));
                        produto1.setUnidadeCx(Integer.parseInt(produtoCadastro[3]));
                        produto1.setPesoUni(Double.parseDouble(produtoCadastro[4]));
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

    public void uploadFornecedor(MultipartFile importFornecedor, Long id) throws IOException {
        LOGGER.info("Importando produto por fornecedor - CSV");

        FornecedorDTO fornecedorDTO = fornecedorService.findFornecedorById(id);
        BufferedReader produtoReader = new BufferedReader(new InputStreamReader(importFornecedor.getInputStream()));
        produtoReader.readLine();
        String produtosFornecedor;
        List<String[]> produtoCSV = new ArrayList<>();

        while ((produtosFornecedor = produtoReader.readLine()) != null) {
            String[] list = produtosFornecedor.split(";");
            produtoCSV.add(list);
        }
        Iterator<String[]> iterator = produtoCSV.iterator();
        String[] registro;

        if (fornecedorDTO != null) {
            while (iterator.hasNext()) {
                try {
                    registro = iterator.next();
                    ProdutoDTO produtoDTO = new ProdutoDTO();
                    String codProduto = registro[0].toUpperCase();
                    String nomeProduto = registro[1];
                    String precoProduto = registro[2];
                    String unidadeCaixa = registro[3];
                    String peso = registro[4];
                    String unidadeMedida = registro[5];
                    String dataValidade = registro[6];
                    String codLinha = registro[7].toUpperCase();
                    String nomeLinha = registro[8];
                    String codCategoria = registro[9].toUpperCase();
                    String nomeCategoria = registro[10];

                    double precoFinal = Double.parseDouble(this.precoFormat(precoProduto));
                    LocalDate data = this.convertData(dataValidade);

                    Produto produto = this.findCodProduto(codProduto);

                    if (produto != null) {
                        LOGGER.info("PRODUTO ENCONTRADO.");
                        produtoDTO.setCodProduto(codProduto);
                        produtoDTO.setNomeProduto(nomeProduto);
                        produtoDTO.setPrecoProduto(precoFinal);
                        produtoDTO.setUnidadeCx(Integer.parseInt(unidadeCaixa));
                        produtoDTO.setPesoUni(Double.parseDouble(peso));
                        produtoDTO.setUnidadePeso(unidadeMedida);
                        produtoDTO.setValProduto(data);
                        produtoDTO.setCategoriaLinha(produto.getCategoriaLinha().getId());
                        this.update(produtoDTO, produto.getId());

                    } else if (produto == null) {
                        produto = this.findProdutoByFornecedor(fornecedorDTO.getId(), nomeProduto);
                        LOGGER.info("PRODUTO NÃO ENCONTRADO.");

                        if (produto == null) {
                            Categoria categoria = categoriaService.findByCod(codCategoria);

                            if (categoria == null) {
                                categoria = categoriaService.findByFornecedor(nomeCategoria, fornecedorDTO.getId());
                            }
                            if (categoria != null && !categoria.getNomeCategoria().equalsIgnoreCase(nomeCategoria)) {
                                categoria = null;
                            }

                            if (categoria == null) {
                                CategoriaDTO categoriaDTO = new CategoriaDTO();
                                categoriaDTO.setNomeCategoria(nomeCategoria);
                                categoriaDTO.setCodCategoria(codCategoria);
                                categoriaDTO.setFornecedor(fornecedorDTO.getId());
                                categoriaDTO = categoriaService.save(categoriaDTO);
                                categoria.setId(categoriaDTO.getId());
                            }
                            LOGGER.info(categoria.getNomeCategoria() + "nome Categoria" + nomeCategoria);
                            CategoriaLinha categoriaLinha = categoriaLinhaService.findByCodLinhaCategoria(codLinha);

                            if (categoriaLinha == null) {
                                categoriaLinha = categoriaLinhaService.findByFornecedor(fornecedorDTO.getId(), nomeLinha);

                            }
                            if (categoriaLinha != null && !categoriaLinha.getNomeLinha().equalsIgnoreCase(nomeLinha)) {
                                categoriaLinha = null;

                            }

                            if (categoriaLinha == null) {
                                CategoriaLinhaDTO categoriaLinhaDTO = new CategoriaLinhaDTO();
                                categoriaLinhaDTO.setNomeLinha(nomeLinha);
                                categoriaLinhaDTO.setCodLinha(codLinha);
                                categoriaLinhaDTO.setId_categoria(categoria.getId());
                                categoriaLinhaService.save(categoriaLinhaDTO);
                            }
                            LOGGER.info("NOME LINHA CATEGORIA" + categoriaLinha.getNomeLinha());
                            LOGGER.info("AQUI.");
                            LOGGER.info("NOME LINHA CATEGORIA" + categoriaLinha.getNomeLinha());
                            produtoDTO.setCodProduto(codProduto);
                            produtoDTO.setNomeProduto(nomeProduto);
                            produtoDTO.setPrecoProduto(precoFinal);
                            produtoDTO.setUnidadeCx(Integer.parseInt(unidadeCaixa));
                            produtoDTO.setPesoUni(Double.parseDouble(peso));
                            produtoDTO.setUnidadePeso(unidadeMedida);
                            produtoDTO.setValProduto(data);
                            produtoDTO.setCategoriaLinha(categoriaLinha.getId());
                            LOGGER.info("ID categoria linha" + produtoDTO.getCategoriaLinha().toString());
                            this.save(produtoDTO);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.info("Importação de produtos por fornecedor concluída!");
                    e.printStackTrace();
                }
            }
        }
    }

    public String precoFormat(String stringPrecoProduto) {
        String precoProduto;
        precoProduto = stringPrecoProduto.replace("R$", "");
        return precoProduto;
    }

    public LocalDate convertData(String dataValidade) {
        LocalDate dataFinal;
        int ano;
        int mes;
        int dia;
        ano = Integer.parseInt(dataValidade.substring(6, 10));
        mes = Integer.parseInt(dataValidade.substring(3, 5));
        dia = Integer.parseInt(dataValidade.substring(0, 2));
        dataFinal = LocalDate.of(ano, mes, dia);
        return dataFinal;
    }

    public Produto findCodProduto(String codProduto) {
        Optional<Produto> produtoOptional = iProdutoRepository.findCodProduto(codProduto);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            return produto;
        } else return null;
    }

    public Produto findProdutoByFornecedor(Long idFornecedor, String nomeProduto) {
        Optional<Produto> produtoOptional = iProdutoRepository.findProdutoByFornecedor(idFornecedor, nomeProduto);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            return produto;
        } else {
            LOGGER.info("Produto não encotrando. findProdutoByFornecedor");
            return null;
        }
    }

    public Produto findProdutoById(Long idProduto) {
        Optional<Produto> produtoOptional = iProdutoRepository.findProdutoById(idProduto);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            return produto;
        } else {
            LOGGER.info("ID do produto não encontrado. findProdutoById");
            return null;
        }
    }
}