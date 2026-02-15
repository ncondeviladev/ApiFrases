package apifrases.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apifrases.model.Frase;

@Repository
public interface FraseRepository extends JpaRepository<Frase, Long> {

    Optional<Frase> findByFechaProgramada(LocalDate fecha);

    Page<Frase> findByAutorId(Long autorId, Pageable pageable);

    Page<Frase> findByCategoriaId(Long categoriaId, Pageable pageable);

   
    Page<Frase> findByTextoContainingIgnoreCase(String texto, Pageable pageable);

}