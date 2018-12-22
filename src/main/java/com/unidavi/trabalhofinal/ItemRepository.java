package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ItemRepository
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByPedidoNumero(Long pedidoNumero);

   
}