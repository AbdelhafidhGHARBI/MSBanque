package tn.esprit.msbanque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Schema(description = "Objet représentant une banque")
public record BanqueDto(
        @Schema(description = "ID unique de la banque", example = "507f1f77bcf86cd799439011", accessMode = Schema.AccessMode.READ_ONLY)
        String id,

        @NotBlank(message = "Le nom de la banque est obligatoire")
        @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
        @Schema(description = "Nom complet de la banque", example = "Banque Internationale Arabe de Tunisie")
        String nom,

        @NotBlank(message = "Le code BIC/SWIFT est obligatoire")
        @Pattern(regexp = "^[A-Z]{6}[A-Z0-9]{2}([A-Z0-9]{3})?$",
                message = "Format de code BIC/SWIFT invalide")
        @Schema(description = "Code BIC/SWIFT de la banque", example = "BIATTNTT")
        String code,

        @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
        @Schema(description = "Adresse physique de la banque", nullable = true,
                example = "70 Avenue Habib Bourguiba, Tunis")
        String adresse,

        @Schema(description = "Date de création", example = "2023-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(description = "Date de dernière modification", example = "2023-01-01T00:00:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime updatedAt
) {
    // Méthode factory pour la création
    public static BanqueDto create(String nom, String code, String adresse) {
        return new BanqueDto(null, nom, code, adresse, null, null);
    }
}
