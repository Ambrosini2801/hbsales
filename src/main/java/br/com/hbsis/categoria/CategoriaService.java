package br.com.hbsis.categoria;

import br.com.hbsis.Fornecedor.Fornecedor;
import br.com.hbsis.Fornecedor.FornecedorService;
import br.com.hbsis.Fornecedor.IFornecedorRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final FornecedorService fornecedorService;
    private final IFornecedorRepository iFornecedorRepository;


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
        Fornecedor fornecedorCompleto = fornecedorService.findFornecedorById(categoriaDTO.getFornecedor().getId());
        categoriaDTO.setFornecedor(fornecedorCompleto);

        this.validate(categoriaDTO);
        LOGGER.info("Salvando categoria");
        LOGGER.debug("Categoria: {}", categoriaDTO);

        Categoria categoria = new Categoria();

        categoria.setCodCategoria(categoriaDTO.getCodCategoria());
        categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
        categoria.setFornecedor(categoriaDTO.getFornecedor());

        categoria = this.iCategoriaRepository.save(categoria);
        return CategoriaDTO.of(categoria);
    }

    private void validate(CategoriaDTO categoriaDTO) {
        LOGGER.info("Validando Categoria");
        if (categoriaDTO == null) {
            throw new IllegalArgumentException("categoriaDTO não deve ser nulo");
        }

        if (categoriaDTO.getNomeCategoria().isEmpty()) {
            throw new IllegalArgumentException("Nome da categoria não ser vazia.");
        }
        if (categoriaDTO.getCodCategoria().isEmpty()) {
            throw new IllegalArgumentException("Id da Categoria não pode ser vazia.");
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

    public List<Categoria> findAll() {
        return iCategoriaRepository.findAll();
    }

    public void importCSV() throws IOException {

        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
        List<String[]> categoriasCSV = cs.readAll();
        Categoria categoriacadastro = new Categoria();

        for (String[] categoria : categoriasCSV) {
            String[] colunacategoria = categoria[0].replaceAll("\"", "").split(";");

            Fornecedor fornecedor = new Fornecedor();
            fornecedor = fornecedorService.findFornecedorById(Long.parseLong(colunacategoria[2]));

            categoriacadastro.setFornecedor(fornecedor);
            categoriacadastro.setCodCategoria(colunacategoria[1]);
            categoriacadastro.setNomeCategoria(colunacategoria[0]);

            this.iCategoriaRepository.save(categoriacadastro);

        }
    }

}

