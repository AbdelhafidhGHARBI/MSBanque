package tn.esprit.msbanque.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.msbanque.entities.Banque;

import java.util.Optional;


@Repository
public interface BanqueRepository extends MongoRepository<Banque, String> {

    // Méthode existante pour chercher par nom
    Optional<Banque> findByNom(String nom);

    // Méthode existante pour chercher par code
    Optional<Banque> findByCode(String code);

    // Méthode pour chercher par nom insensible à la casse
    Page<Banque> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    // Méthode pour chercher par code insensible à la casse
    Page<Banque> findByCodeContainingIgnoreCase(String code, Pageable pageable);

    // Ajout de la méthode pour chercher par nom et code insensible à la casse
    Page<Banque> findByNomContainingIgnoreCaseAndCodeContainingIgnoreCase(String nom, String code, Pageable pageable);
}

