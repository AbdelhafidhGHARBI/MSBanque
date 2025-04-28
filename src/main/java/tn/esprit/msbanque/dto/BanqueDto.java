package tn.esprit.msbanque.dto;


import java.util.List;

public record BanqueDto(
        String id,
        String nom,
        String code,
        String adresse,
        List<Long> comptes
) { }
