package br.com.hbsis.categoria;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
private final ICategoriaRepository iCategoriaRepository;

        public CategoriaService(ICategoriaRepository iCategoriaRepository) {
            this.iCategoriaRepository = iCategoriaRepository;
        }

        private void validate(CategoriaDTO categoriaDTO) {
            LOGGER.info("Validando Categoria");
            if (categoriaDTO == null) {
                throw new IllegalArgumentException("categoriaDTO não deve ser nulo");
            }
            if (categoriaDTO.getFornecedorCategoria().equals(null)) {
                throw new IllegalArgumentException("SEM ID DO FORNECEDOR");
            }
            if (categoriaDTO.getNomeCategoria().isEmpty()) {
                throw new IllegalArgumentException("Nome da categoria não ser vazia.");
            }
        }

        public CategoriaDTO save(CategoriaDTO categoriaDTO) {
            this.validate(categoriaDTO);
            LOGGER.info("Salvando categoria");
            LOGGER.debug("Categoria: {}", categoriaDTO);
            Categoria categoria = new Categoria();
            categoria.setFornecedorCategoria(categoriaDTO.getFornecedorCategoria());
            categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
            categoria = this.iCategoriaRepository.save(categoria);
            return CategoriaDTO.of(categoria);
        }
        public CategoriaDTO findById(Long id) {
            Optional<Categoria> categoriaProdutoOptional = this.iCategoriaRepository.findById(id);
            if (categoriaProdutoOptional.isPresent()) {
                return CategoriaDTO.of(categoriaProdutoOptional.get());
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
                categoriaExistente.setFornecedorCategoria(categoriaDTO.getFornecedorCategoria());
                categoriaExistente.setNomeCategoria(categoriaDTO.getNomeCategoria());
                categoriaExistente = this.iCategoriaRepository.save(categoriaExistente);
                return CategoriaDTO.of(categoriaExistente);
            }
            throw new IllegalArgumentException(String.format("ID %s não existe", id));
        }

        public void delete(Long id) {
            LOGGER.info("Executando delete para fornecedor de ID: [{}]", id);
            this.iCategoriaRepository.deleteById(id);
        }

}





