package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.CategoriaService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaLinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaLinhaService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;

    private final ICategoriaLinhaRepository iCategoriaLinhaRepository;
    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaLinhaService(ICategoriaLinhaRepository iCategoriaLinhaRepository, CategoriaService categoriaService) {
        this.iCategoriaLinhaRepository = iCategoriaLinhaRepository;
        this.categoriaService = categoriaService;
    }

    public static void importCSV(MultipartFile arquivo) {
    }

    public static void findAll(HttpServletResponse file) {
    }

    public CategoriaLinhaDTO save(CategoriaLinhaDTO categoriaLinhaDTO) {
        this.validate(categoriaLinhaDTO);
        LOGGER.info("Salvando categoria linha");
        LOGGER.debug("Categoria linha: {}", categoriaLinhaDTO);

        CategoriaLinha categorialinha = new CategoriaLinha();

        categorialinha.setCatLinha(categoriaLinhaDTO.getCatLinha());
        categorialinha.setCodLinha(categoriaLinhaDTO.getCodLinha());
        categorialinha.setNomeLinha(categoriaLinhaDTO.getNomeLinha());

        categorialinha = this.iCategoriaLinhaRepository.save(categorialinha);
        return CategoriaLinhaDTO.of(categorialinha);
    }

    private void validate(CategoriaLinhaDTO categoriaLinhaDTO) {
        LOGGER.info("Validando Categoria");
        if (categoriaLinhaDTO == null) {
            throw new IllegalArgumentException("Categoria linha não deve ser nulo");
        }

    }

    public CategoriaLinhaDTO findById(Long id) {
        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return CategoriaLinhaDTO.of(linhaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

//    public CategoriaLinha findCategoriaLinhaById(Long id) {
//        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);
//        if (linhaOptional.isPresent()) {
//            return linhaOptional.get();
//        }
//        throw new IllegalArgumentException(String.format("ID %s não existe", id));
//    }

    public CategoriaLinhaDTO update(CategoriaLinhaDTO categoriaLinhaDTO, Long id) {
        Optional<CategoriaLinha> categoriaLinhaExistenteOptional =
                this.iCategoriaLinhaRepository.findById(id);

        if (categoriaLinhaExistenteOptional.isPresent()) {
            CategoriaLinha categoriaLinhaExistente = categoriaLinhaExistenteOptional.get();

            LOGGER.info("Atualizando usuário... id: [{}]", categoriaLinhaDTO.getCodLinha());
            LOGGER.debug("Payload: {}", categoriaLinhaDTO);
            LOGGER.debug("Usuario Existente: {}", categoriaLinhaExistente);

            categoriaLinhaExistente.setNomeLinha(categoriaLinhaDTO.getNomeLinha());
            categoriaLinhaExistente.setCatLinha(categoriaLinhaDTO.getCatLinha());

            categoriaLinhaExistente = this.iCategoriaLinhaRepository.save(categoriaLinhaExistente);
            return CategoriaLinhaDTO.of(categoriaLinhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long CodLinha) {
        LOGGER.info("Executando delete para fornecedor de ID: [{}]", CodLinha);
        this.iCategoriaLinhaRepository.deleteById(CodLinha);

    }

    public void importCSV() throws IOException {

        Reader caminho = Files.newBufferedReader(Paths.get("C:\\Users\\vanessa.silva\\Desktop\\arquivoimport.csv"));
        CSVReader cs = new CSVReaderBuilder(caminho).withSkipLines(1).build();
        List<String[]> linhaCSV = cs.readAll();
        CategoriaLinha categorialinhacadastro = new CategoriaLinha();

        for (String[] linha : linhaCSV) {
            String[] colunalinhacategoria = linha[0].replaceAll("\"", "").split(";");

            categorialinhacadastro.setCodLinha(colunalinhacategoria[0]);
            categorialinhacadastro.setCatLinha(colunalinhacategoria[1]);
            categorialinhacadastro.setNomeLinha(colunalinhacategoria[2]);

            this.iCategoriaLinhaRepository.save(categorialinhacadastro);

        }
    }

    public List<CategoriaLinha>findAll(){
        return iCategoriaLinhaRepository.findAll();
    }
}