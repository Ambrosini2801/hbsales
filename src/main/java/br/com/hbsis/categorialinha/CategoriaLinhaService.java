package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.Categoria;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.slf4j.Logger;
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
public class CategoriaLinhaService {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(CategoriaLinhaService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;

    private final ICategoriaLinhaRepository iCategoriaLinhaRepository;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;

    @Autowired
    public CategoriaLinhaService(ICategoriaLinhaRepository iCategoriaLinhaRepository, CategoriaService categoriaService, ICategoriaRepository icategoriarepository) {
        this.iCategoriaLinhaRepository = iCategoriaLinhaRepository;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = icategoriarepository;

    }

    public CategoriaLinha findCategoriaLinhaById(Long id) {
        Optional<CategoriaLinha> CategoriaLinhaOptional = this.iCategoriaLinhaRepository.findById(id);
        if (CategoriaLinhaOptional.isPresent()) {
            return CategoriaLinhaOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaLinhaDTO save(CategoriaLinhaDTO categoriaLinhaDTO) {
        Categoria categoria = null;
        Categoria categoriaCompleta = categoriaService.findCategoriaById(categoriaLinhaDTO.getCategoria(categoria).getId());
        categoriaLinhaDTO.setCategoria(categoriaCompleta);

        this.validate(categoriaLinhaDTO);
        LOGGER.info("Salvando categoria linha");
        LOGGER.debug("Categoria linha: {}", categoriaLinhaDTO);

        CategoriaLinha categorialinha = new CategoriaLinha();

        categorialinha.setCatLinha(categoriaLinhaDTO.getCodLinha());
        categorialinha.setNomeLinha(categoriaLinhaDTO.getNomeLinha());
        categorialinha.setCodLinha(categoriaLinhaDTO.getCodLinha());

        categorialinha = this.iCategoriaLinhaRepository.save(categorialinha);
        return CategoriaLinhaDTO.of(categorialinha);
    }

    private void validate(CategoriaLinhaDTO categoriaLinhaDTO) {
        LOGGER.info("Validando Categoria");
        if (categoriaLinhaDTO == null) {
            throw new IllegalArgumentException("categoriaLinhaDTO não deve ser nulo");
        }

        if (categoriaLinhaDTO.getNomeLinha().isEmpty()) {
            throw new IllegalArgumentException("Nome da linha não ser vazia.");
        }
        if (categoriaLinhaDTO.getCodLinha().isEmpty()) {
            throw new IllegalArgumentException("Id do código linha não pode ser vazia.");
        }
        if (categoriaLinhaDTO.getCatLinha().isEmpty()) {
            throw new IllegalArgumentException("Categoria não pode ser vazia.");
        }
    }

    public CategoriaLinhaDTO findById(Long id) {
        Optional<CategoriaLinha> categoriaLinhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (categoriaLinhaOptional.isPresent()) {
            return CategoriaLinhaDTO.of(categoriaLinhaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaLinhaDTO update(CategoriaLinhaDTO categoriaLinhaDTO, Long id) {
        Optional<CategoriaLinha> categoriaLinhaExistenteOptional = this.iCategoriaLinhaRepository.findById(id);
        if (categoriaLinhaExistenteOptional.isPresent()) {
            CategoriaLinha categoriaLinhaExistente = categoriaLinhaExistenteOptional.get();

            LOGGER.info("Atualizando usuário... id: [{}]", categoriaLinhaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaLinhaDTO);
            LOGGER.debug("Usuario Existente: {}", categoriaLinhaExistente);

            categoriaLinhaExistente = this.iCategoriaLinhaRepository.save(categoriaLinhaExistente);
            return CategoriaLinhaDTO.of(categoriaLinhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);
        this.iCategoriaLinhaRepository.deleteById(id);
    }

    public List<Categoria> findAll() {
        return iCategoriaLinhaRepository.findAll();
    }

    public void importCSV() throws IOException {
        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
        List<String[]> categoriasCSV = cs.readAll();
        CategoriaLinha linhacadastro = new CategoriaLinha();

        for (String[] categorialinha : categoriasCSV) {
            String[] colunacategoria = categorialinha[0].replaceAll("\"", "").split(";");

            Categoria categoria = new Categoria();
            categoria = categoriaService.findCategoriaById(Long.parseLong(colunacategoria[1]));

            linhacadastro.setCategoria(categoria);
            linhacadastro.setCodLinha(colunacategoria[0]);
            linhacadastro.setNomeLinha(colunacategoria[2]);
            linhacadastro.setCatLinha(colunacategoria[3]);

            this.iCategoriaLinhaRepository.save(linhacadastro);
        }

    }

}