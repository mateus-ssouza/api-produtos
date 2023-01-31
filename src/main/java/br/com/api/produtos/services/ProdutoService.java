package br.com.api.produtos.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.model.Produto;
import br.com.api.produtos.model.Resposta;
import br.com.api.produtos.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private Resposta res;

    // Método para listar todos os produtos
    public Iterable<Produto> listar() {
        return repo.findAll();
    }

    // Método para buscar por ID
    public ResponseEntity<?> buscarId(Long id) {
        try {
            Optional<Produto> prod = repo.findById(id);
            return new ResponseEntity<>(prod.get(), HttpStatus.OK);
            
        } catch (NoSuchElementException e) {
            res.setMensagem("O ID não foi encontrado!");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }   
    }

    // Método para cadastrar e alterar produtos
    public ResponseEntity<?> cadastrarAlterar(Produto produto, String acao) {
        if(produto.getNome().equals("")) {
            res.setMensagem("O nome do produto é obrigatório!");
            return new ResponseEntity<Resposta>(res, HttpStatus.BAD_REQUEST);
        } 
        else if (produto.getMarca().equals("")) {
            res.setMensagem("O nome da marca é obrigatório!");
            return new ResponseEntity<Resposta>(res, HttpStatus.BAD_REQUEST);
        }

        if (acao.equals("cadastrar")) {
            return new ResponseEntity<Produto>(repo.save(produto), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Produto>(repo.save(produto), HttpStatus.OK);
        }
    }

    // Método para remover produtos
    public ResponseEntity<Resposta> remover(Long id) {

        try {
            repo.deleteById(id);
            res.setMensagem("O produto foi removido com sucesso!");
            return new ResponseEntity<Resposta>(res, HttpStatus.OK);
            
        } catch (EmptyResultDataAccessException e) {
            res.setMensagem("O ID não foi encontrado!");
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }  
    }
}
