package tn.esprit.msbanque.dto;

public record CompteDto(
        Long id,
        String numero,
        String titulaire,
        String banqueId,
        String typeCompte,
        Long solde
) { }
