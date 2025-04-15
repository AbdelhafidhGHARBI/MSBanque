package tn.esprit.msbanque.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "banques")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Banque implements Serializable {

    @Id
    @Indexed
    @Setter(AccessLevel.PROTECTED)
    String id;

    String nom;
    String code;     // ex: BIC/SWIFT
    String adresse;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
