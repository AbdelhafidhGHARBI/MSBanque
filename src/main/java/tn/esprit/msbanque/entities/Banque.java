package tn.esprit.msbanque.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "banques")
@Getter
@Setter
public class Banque {

    @Id
    String id;

    String nom;
    String code;     // ex: BIC/SWIFT
    String adresse;

    List<Long> comptes = new ArrayList<>(); // liste des id des comptes dans le microservice ms-compte

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
