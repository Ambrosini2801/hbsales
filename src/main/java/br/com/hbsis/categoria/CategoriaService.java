package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorService;
import br.com.hbsis.Fornecedor.IFornecedorRepository;
import com.google.common.net.HttpHeaders;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;

    private final ICategoriaRepository iCategoriaRepository;
    private FornecedorService fornecedorService;
    private IFornecedorRepository iFornecedorRepository;

    @Autowired
    public CategoriaService(ICategoriaRepository iCategoriaRepository, FornecedorService fornecedorService, IFornecedorRepository iFornecedorRepository) {
        this.iCategoriaRepository = iCategoriaRepository;
        this.fornecedorService = fornecedorService;
        this.iFornecedorRepository = iFornecedorRepository;
    }

    public Categoria findCategoriaById(Long id) {
        Optional<Categoria> CategoriaOptional = this.iCategoriaRepository.findById(id);
        if (CategoriaOptional.isPresent()) {
            return CategoriaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        this.validate(categoriaDTO);
        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Fornecedor fornecedorCompleto = fornecedorService.findFornecedorById(categoriaDTO.getFornecedor().getId());
        categoriaDTO.setFornecedor(fornecedorCompleto);
        Categoria categoria = new Categoria();
        String cnpj = (categoriaDTO.getFornecedor().getCNPJ());
        cnpj = cnpj.substring(10, 14);
        String codigo = "CAT";
        String zeroEsquerda = new String();
        zeroEsquerda = categoriaDTO.getCodCategoria();
        String zeroEsquerdaFinal = (StringUtils.leftPad(zeroEsquerda, 3, "0")).toUpperCase();

        categoria.setCodCategoria(codigo.concat(cnpj).concat(zeroEsquerdaFinal));
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(fornecedorService.findFornecedorById(categoriaDTO.getFornecedor().getId()));

        categoria = this.iCategoriaRepository.save(categoria);
        TODO: 12/12/2019 Usar a chamada do método estático pela classe
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

    public CategoriaDTO update(CategoriaDTO categoriaDTO, Long id) {
        TODO: 12/12/2019 Realizar construção do código também no update
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

    public void exportCSV(HttpServletResponse response) throws IOException {
        String exportCategoria = "export.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + exportCategoria + "\"");

        PrintWriter writer = response.getWriter();
        ICSVWriter csvwriter = new CSVWriterBuilder(response.getWriter())
                .withSeparator(';')
                .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .build();

        String headerCSV[] = {"nome_categoria", "cod_categoria", "razão", "cnpj"};
        csvwriter.writeNext(headerCSV);

        for (Categoria categoria : iCategoriaRepository.findAll()) {
            csvwriter.writeNext(new String[]{
                    categoria.getNomeCategoria(),
                    categoria.getCodCategoria(),
                    categoria.getFornecedor().getRazaoSocial(),
                    mascaraCNPJ(categoria.getFornecedor().getCNPJ())
            });
        }
    }

    private Categoria findAll() {
        return (Categoria) this.iCategoriaRepository.findAll();

    }

    public void importCSV(HttpServletResponse response) throws IOException {
        String importCategoria = "import.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + importCategoria + "\"");
        TODO: 12/12/2019 Realizar upload do arquivo de CSV
        Reader caminhoImport = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader csv = new CSVReaderBuilder(caminhoImport).withSkipLines(1).build();
        List<String[]> categoriaCSV = csv.readAll();
        Categoria categoriaCadastro = new Categoria();

        for (String[] categoria : categoriaCSV) {
            String[] colunaCategoriaCSV = categoria[0].replaceAll("\"", "").split(";");

            Fornecedor fornecedor = fornecedorService.findFornecedorById(Long.parseLong(colunaCategoriaCSV[2]));
            categoriaCadastro.setFornecedor(fornecedor);
            categoriaCadastro.setCodCategoria(colunaCategoriaCSV[0]);
            categoriaCadastro.setNomeCategoria(colunaCategoriaCSV[1]);

            this.iCategoriaRepository.save(categoriaCadastro);
        }
    }
}