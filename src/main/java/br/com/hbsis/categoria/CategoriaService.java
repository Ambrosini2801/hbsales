package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
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
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;
    private static ICategoriaRepository iCategoriaRepository;
    private FornecedorService fornecedorService;

    @Autowired
    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);
        Categoria categoria = new Categoria();
        categoria.setFornecedor(fornecedorService.findFornecedorById1(categoriaDTO.getFornecedor()));
        System.out.println(categoria);

        String cnpj = (categoria.getFornecedor().getCNPJ());
        cnpj = cnpj.substring(10, 14);
        String codigo = "CAT";
        String zeroEsquerda = new String();
        zeroEsquerda = categoriaDTO.getCodCategoria();
        String zeroEsquerdaFinal = (StringUtils.leftPad(zeroEsquerda, 3, "0")).toUpperCase();

        categoria.setCodCategoria(codigo.concat(cnpj).concat(zeroEsquerdaFinal));
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());

        this.validate(categoriaDTO);
        categoria = this.iCategoriaRepository.save(categoria);
        return categoriaDTO.of(categoria);
    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando Categoria");
        if (categoriaDTO == null) {
            throw new IllegalArgumentException("Categoria DTO não deve ser nulo ou vazia!");
        }

        if (categoriaDTO.getNomeCategoria().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não deve ser nula ou vazia!");
        }

        if (categoriaDTO.getCodCategoria().isEmpty()) {
            throw new IllegalArgumentException("Código da categoria não deve ser nula ou vazia!");
        }
    }

    public CategoriaDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return CategoriaDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public Categoria findCategoriaById(Long id) {
        Categoria categoria = new Categoria();
        Optional<Categoria> categoriaOptional = this.iCategoriaRepository.findById(id);

        if (categoriaOptional.isPresent()) {
            return categoria = categoriaOptional.get();
        }

        throw new IllegalArgumentException("A categoria não existe");
    }

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        //TODO: 12/12/2019 Realizar construção do código também no update
        Optional<Categoria> categoriaExistenteOptional = this.iCategoriaRepository.findById(id);
        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando usuário... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaDTO);
            LOGGER.debug("Usuario Existente: {}", categoriaExistente);

            categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);
            return CategoriaDTO.of(categoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);
        this.iCategoriaRepository.deleteById(id);
    }

    public String mascaraCNPJ(String cnpj) throws IOException {
        try {
            MaskFormatter format = new MaskFormatter("##.###.###/####-##");
            format.setValueContainsLiteralCharacters(false);
            return format.valueToString(cnpj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void exportCSV(HttpServletResponse exportCSV) throws IOException {
        LOGGER.info("Exportando Categoria - CSV");

        exportCSV.setContentType("text/csv");
        exportCSV.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName= Categoria.csv");
        PrintWriter writer = exportCSV.getWriter();
        FileWriter write = new FileWriter("import.csv");
        String lista = ("nome_categoria; cod_categoria; razão_social; cnpj;");
        writer.write(lista);

        for (Categoria categoria : iCategoriaRepository.findAll()) {

            writer.write("\n");

            writer.append(categoria.getNomeCategoria() + ";");
            writer.append(categoria.getCodCategoria() + ";");
            writer.append(categoria.getFornecedor().getRazaoSocial() + ";");
            writer.append(mascaraCNPJ(categoria.getFornecedor().getCNPJ()) + ";");

            writer.flush();
        }
    }

    public void uploadCSV(MultipartFile importCategoria) throws IOException {
        LOGGER.info("Importando Categoria - CSV");
        BufferedReader importReader = new BufferedReader(new InputStreamReader(importCategoria.getInputStream()));
        importReader.readLine();
        String linha;
        List<String[]> categoriaCSV = new ArrayList<>();
        while ((linha = importReader.readLine()) != null) {
            String[] list = linha.split(";");
            categoriaCSV.add(list);
            Iterator<String[]> iterator = categoriaCSV.iterator();
            String[] registrando;
            while (iterator.hasNext()) {

                try {
                    registrando = iterator.next();
                    Categoria categoriaCadastro = new Categoria();
                    for (String[] categoria : categoriaCSV) {
                        String[] colunaCategoriaCSV = categoria[0].replaceAll("\"", "").split(";");

                        Fornecedor fornecedor = fornecedorService.findFornecedorById1(Long.parseLong(colunaCategoriaCSV[2]));
                        categoriaCadastro.setFornecedor(fornecedor);
                        categoriaCadastro.setCodCategoria(colunaCategoriaCSV[0]);
                        categoriaCadastro.setNomeCategoria(colunaCategoriaCSV[1]);

                        this.iCategoriaRepository.save(categoriaCadastro);
                    }

                } catch (Exception e) {
                    LOGGER.info("Importação concluída...");
                }
            }
        }
    }

    public Categoria findByCod(String codCategoria) {
        Optional<Categoria> categoriaOptional = iCategoriaRepository.findByCod(codCategoria);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            return categoria;
        } else {
            LOGGER.info("CATEGORIA NÃO ENCONTRADA.");
            return null;
        }
    }

    public Categoria findByFornecedor(String nomeCategoria, Long idFornecedor) {
        Optional<Categoria> categoriaOptional = iCategoriaRepository.findByFornecedor(nomeCategoria, idFornecedor);
        if (categoriaOptional.isPresent()) {
            Categoria categoria = categoriaOptional.get();
            return categoria;
        } else {
            LOGGER.info("CATEGORIA NÃO ENCONTRADA.");
            return null;
        }
    }
}