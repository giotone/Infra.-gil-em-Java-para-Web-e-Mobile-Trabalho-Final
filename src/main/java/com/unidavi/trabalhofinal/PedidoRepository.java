package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PedidoRepository
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByNumero(Long numero);

   
}