package com.unidavi.trabalhofinal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * ClienteRepository
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeContaining(String nome);

    @Query("SELECT c FROM Cliente c, Endereco e WHERE c.id = e.clienteId and e.rua LIKE :rua")
    List<Cliente> findByRuaContaining(@Param("rua") String rua);

    @Query("SELECT c FROM Cliente c, Endereco e WHERE c.id = e.clienteId and e.cidade LIKE :cidade")
    List<Cliente> findByCidadeContaining(@Param("cidade") String cidade);

    @Query("SELECT c FROM Cliente c, Endereco e WHERE c.id = e.clienteId and e.estado = :estado")
    List<Cliente> findByEstadoContaining(@Param("estado") String estado);
    
    
}