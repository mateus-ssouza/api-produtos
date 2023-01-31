package br.com.api.produtos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.api.produtos.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    
}
