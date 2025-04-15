package tn.esprit.msbanque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.service.IBanqueService;

import java.util.Map;


@RestController
@RequestMapping("/api/banques")
@RequiredArgsConstructor
@Tag(name = "Banque", description = "API de gestion des banques")
public class BanqueRestController {

    private final IBanqueService banqueService;
    private static final Logger logger = LoggerFactory.getLogger(BanqueRestController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une nouvelle banque")
    @ApiResponse(responseCode = "201", description = "Banque créée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    @ApiResponse(responseCode = "409", description = "La banque existe déjà")
    public BanqueDto create(@Valid @RequestBody BanqueDto banqueDto) {
        logger.info("Création d'une nouvelle banque: {}", banqueDto.nom());
        return banqueService.create(banqueDto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Mettre à jour partiellement une banque")
    public BanqueDto partialUpdate(
            @PathVariable String id,
            @RequestBody Map<@NotBlank String, Object> updates) {
        logger.info("Mise à jour partielle de la banque avec ID: {} et données: {}", id, updates);
        return banqueService.partialUpdate(id, updates);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une banque")
    @ApiResponse(responseCode = "204", description = "Banque supprimée")
    @ApiResponse(responseCode = "404", description = "Banque non trouvée")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.info("Suppression de la banque avec ID: {}", id);
        banqueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Lister toutes les banques (paginated)")
    public Page<BanqueDto> getAll(
            @Parameter(description = "Paramètres de pagination")
            @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        logger.info("Récupération des banques avec pagination: {}", pageable);
        return banqueService.findAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver une banque par son ID")
    @ApiResponse(responseCode = "404", description = "Banque non trouvée")
    public BanqueDto getById(@PathVariable String id) {
        logger.info("Recherche de la banque avec ID: {}", id);
        return banqueService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des banques")
    public Page<BanqueDto> search(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String code,
            @PageableDefault(size = 10) Pageable pageable) {
        logger.info("Recherche de banques par nom: {}, code: {}", nom, code);
        return banqueService.search(nom, code, pageable);
    }


}