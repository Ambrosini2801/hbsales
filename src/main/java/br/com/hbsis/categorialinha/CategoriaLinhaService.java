package br.com.hbsis.categorialinha;

import br.com.hbsis.categoria.CategoriaDTO;
import br.com.hbsis.categoria.CategoriaService;
import br.com.hbsis.categoria.ICategoriaRepository;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaLinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaLinhaService.class);
    private static final URI SAMPLE_CSV_FILE_PATH = null;

    private final ICategoriaLinhaRepository iCategoriaLinhaRepository;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;

    @Autowired
    public CategoriaLinhaService(ICategoriaLinhaRepository iCategoriaLinhaRepository, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaLinhaRepository = iCategoriaLinhaRepository;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

//    public CategoriaLinha findCategoriaLinhaById(Long id) {
//        Optional<CategoriaLinha> CategoriaLinhaOptional = this.iCategoriaLinhaRepository.findById(id);
//        if (CategoriaLinhaOptional.isPresent()) {
//            return CategoriaLinhaOptional.get();
//        }
//        throw new IllegalArgumentException(String.format("ID %s não existe", id));
//    }

    public CategoriaLinhaDTO save(CategoriaLinhaDTO categoriaLinhaDTO) {
        this.validate(categoriaLinhaDTO);
        LOGGER.info("Salvando categoria linha");
        LOGGER.debug("Categoria linha: {}", categoriaLinhaDTO);

        String codigo = "CAT";
        String zeroEsquerda = new String();
        zeroEsquerda = categoriaLinhaDTO.getCodLinha();
        String zeroEsquerdaFinal = (StringUtils.leftPad(zeroEsquerda, 3, "0")).toUpperCase();

        CategoriaDTO categoriaCompleta = categoriaService.findById(categoriaLinhaDTO.getCategoria().getId());

        CategoriaLinha categorialinha = new CategoriaLinha();
        categorialinha.setCategoria(categorialinha.getCategoria(categoriaLinhaDTO));
        categorialinha.setCodLinha(categoriaLinhaDTO.getCodLinha() + zeroEsquerdaFinal);
        categorialinha.setNomeLinha(categoriaLinhaDTO.getNomeLinha());
        categorialinha.getCategoria(categoriaLinhaDTO);

        categorialinha = this.iCategoriaLinhaRepository.save(categorialinha);
        return CategoriaLinhaDTO.of(categorialinha);
    }

    private void validate(CategoriaLinhaDTO categoriaLinhaDTO) {
        LOGGER.info("Validando Categoria");
        if (categoriaLinhaDTO == null) {
            throw new IllegalArgumentException("Categoria linha não deve ser nulo");
        }

        if (categoriaLinhaDTO.getCodLinha().isEmpty()) {
            throw new IllegalArgumentException("Código da categoria linha não deve ser nulo ou vazio!");
        }

        if (categoriaLinhaDTO.getNomeLinha().isEmpty()) {
            throw new IllegalArgumentException("Nome da linha não deve ser nulo ou vazio!");
        }
    }

    public CategoriaLinhaDTO findById(Long id) {
        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return CategoriaLinhaDTO.of(linhaOptional.get());
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaLinha findByIdcategorialinha(Long id) {
        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public CategoriaLinhaDTO update(CategoriaLinhaDTO categoriaLinhaDTO, Long id) {
        Optional<CategoriaLinha> categoriaLinhaExistenteOptional =
                this.iCategoriaLinhaRepository.findById(id);

        if (categoriaLinhaExistenteOptional.isPresent()) {
            CategoriaLinha categoriaLinhaExistente = categoriaLinhaExistenteOptional.get();

            LOGGER.info("Atualizando categoria linha... id: [{}]", categoriaLinhaDTO.getCodLinha());
            LOGGER.debug("Payload: {}", categoriaLinhaDTO);
            LOGGER.debug("Categoria Linha Existente: {}", categoriaLinhaExistente);

            categoriaLinhaExistente.setNomeLinha(categoriaLinhaDTO.getNomeLinha());
            categoriaLinhaExistente = this.iCategoriaLinhaRepository.save(categoriaLinhaExistente);
            return CategoriaLinhaDTO.of(categoriaLinhaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long CodLinha) {
        LOGGER.info("Executando delete para o código linha de ID: [{}]", CodLinha);
        this.iCategoriaLinhaRepository.deleteById(CodLinha);

    }

    public CategoriaLinha findByCodLinha(String codLinha) {
        return this.iCategoriaLinhaRepository.findByCodLinha(codLinha);
    }

    public void exportCSV(HttpServletResponse exportLinha) throws IOException {

        String nomelinha = "linhas.csv";
        exportLinha.setContentType("text/csv");
        exportLinha.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomelinha);

        PrintWriter writer = exportLinha.getWriter();
        FileWriter write = new FileWriter("exportLinhas.csv");
        String lista = ("cod_linha; nome_linha; cod_categoria;");
        writer.write(lista);

        for (CategoriaLinha linha : iCategoriaLinhaRepository.findAll()) {
            writer.write("\n");

            writer.append(linha.getCodLinha() + ";");
            writer.append(linha.getNomeLinha() + ";");
            CategoriaLinhaDTO categoriaLinhaDTO = null;
            writer.append(linha.getCategoria(categoriaLinhaDTO).getNomeCategoria() + ";");
            writer.append(linha.getCategoria(categoriaLinhaDTO).getCodCategoria() + ";");

            writer.flush();
        }
    }

    public void upload(MultipartFile importLinha) throws IOException {
        LOGGER.info("Importando Linha - CSV");

        BufferedReader linhaReader = new BufferedReader(new InputStreamReader(importLinha.getInputStream()));
        linhaReader.readLine();
        String linha;
        List<String[]> linhaCSV = new ArrayList<>();
        while ((linha = linhaReader.readLine()) != null) {
            String[] list = linha.split(";");
            linhaCSV.add(list);
            Iterator<String[]> iterator = linhaCSV.iterator();
            String[] uplando;
            while (iterator.hasNext()) {

                try {
                    uplando = iterator.next();
                    CategoriaLinha categoriaLinhaCadastro = new CategoriaLinha();
                    for (String[] categoriaLinha : linhaCSV) {
                        String[] colunalinhacategoria = categoriaLinha[0].replaceAll("\"", "").split(";");

                        categoriaLinhaCadastro.setCodLinha(colunalinhacategoria[0]);
                        categoriaLinhaCadastro.setNomeLinha(colunalinhacategoria[1]);

                        this.iCategoriaLinhaRepository.save(categoriaLinhaCadastro);
                    }

                } catch (Exception e) {
                    LOGGER.info("Importação concluída!");
                }
            }
        }
    }
}