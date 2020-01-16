package br.com.hbsis.CategoriaLinha;

import br.com.hbsis.Categoria.Categoria;
import br.com.hbsis.Categoria.CategoriaDTO;
import br.com.hbsis.Categoria.CategoriaService;
import br.com.hbsis.Categoria.ICategoriaRepository;
import com.google.common.net.HttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaLinhaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaLinhaService.class);

    private final ICategoriaLinhaRepository iCategoriaLinhaRepository;
    private final CategoriaService categoriaService;
    private final ICategoriaRepository iCategoriaRepository;

    @Autowired
    public CategoriaLinhaService(ICategoriaLinhaRepository iCategoriaLinhaRepository, CategoriaService categoriaService, ICategoriaRepository iCategoriaRepository) {
        this.iCategoriaLinhaRepository = iCategoriaLinhaRepository;
        this.categoriaService = categoriaService;
        this.iCategoriaRepository = iCategoriaRepository;
    }

    public CategoriaLinhaDTO save(CategoriaLinhaDTO categoriaLinhaDTO) {
        CategoriaLinha categorialinha = new CategoriaLinha();
        this.validate(categoriaLinhaDTO);
        LOGGER.info("Salvando categoria linha");
        LOGGER.debug("Categoria linha: {}", categoriaLinhaDTO);

        String zeroEsquerda = categoriaLinhaDTO.getCodLinha();
        String zeroEsqUpperCase = zeroEsquerda.toUpperCase();
        String zeroEsquerdaFinal = StringUtils.leftPad(zeroEsqUpperCase, 10, "0");

        categorialinha.setCodLinha(zeroEsquerdaFinal);
        categorialinha.setNomeLinha(categoriaLinhaDTO.getNomeLinha());
        categorialinha.setCategoria(categoriaService.findCategoriaById(categoriaLinhaDTO.getId_categoria()));
        categorialinha = this.iCategoriaLinhaRepository.save(categorialinha);


        return CategoriaLinhaDTO.of(categorialinha);
    }

    public Categoria converter(CategoriaDTO categoriaDTO) {
        Categoria categoria = new Categoria();
        categoria.setId(categoriaDTO.getId());
        return categoria;
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

    public CategoriaLinha findByLinhaId(Long id) {
        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            return linhaOptional.get();
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public CategoriaLinha findByIdcategorialinha(Long id) {
        Optional<CategoriaLinha> linhaOptional = this.iCategoriaLinhaRepository.findById(id);

        if (linhaOptional.isPresent()) {
            CategoriaLinha categoriaLinha = linhaOptional.get();
            return categoriaLinha;
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

    public void exportCSV(HttpServletResponse exportLinha) throws IOException {

        String nomelinha = "linhas.csv";
        exportLinha.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + nomelinha);
        exportLinha.setContentType("text/csv");

        PrintWriter writer = exportLinha.getWriter();
        String lista = ("cod_linha; nome_linha; cod_categoria; nome_categoria");
        writer.write(lista);

        for (CategoriaLinha linha : iCategoriaLinhaRepository.findAll()) {
            writer.write("\n");

            writer.append(linha.getCodLinha() + ";");
            writer.append(linha.getNomeLinha() + ";");

            CategoriaDTO categoriaDTO = categoriaService.findById(linha.getCategoria().getId());
            Categoria categoria = converter(categoriaDTO);

            writer.append(categoriaDTO.getNomeCategoria() + ";");
            writer.append(categoriaDTO.getCodCategoria() + ";");

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

            //Optional<CategoriaLinha> categoriaLinhaExistenteOptional = this.iCategoriaLinhaRepository.findById(categoriaLinhaExistenteOptional.isPresent());
            while (iterator.hasNext()) {
                try {
                    uplando = iterator.next();
                    CategoriaLinha categoriaLinhaCadastro = new CategoriaLinha();

                    for (String[] categoriaLinha : linhaCSV) {
                        String[] colunalinhacategoria = categoriaLinha[0].replaceAll("\"", "").split(";");

//                    if (categoriaLinhaExistenteOptional.isPresent()) {
//                           continue;
//                      }
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

    public CategoriaLinha findByCodLinhaCategoria(String codLinhaCategoria) {
        CategoriaLinha categoriaLinha = new CategoriaLinha();
        Optional<CategoriaLinha> categoriaLinhaOptional = iCategoriaLinhaRepository.findCategoriaLinhaByCod(codLinhaCategoria);

        if (categoriaLinhaOptional.isPresent()) {
            categoriaLinha = categoriaLinhaOptional.get();
            return categoriaLinha;
        } else return null;
    }

    public CategoriaLinha findByFornecedor(Long idFornecedor, String nomeCategoria) {
        Optional<CategoriaLinha> categoriaLinhaOptional = iCategoriaLinhaRepository.findByFornecedor(idFornecedor, nomeCategoria);
        if (categoriaLinhaOptional.isPresent()) {
            CategoriaLinha categoriaLinha = categoriaLinhaOptional.get();
            return categoriaLinha;
        } else {
            LOGGER.info("Categoria Linha não encontrado.");
            return null;
        }
    }
}