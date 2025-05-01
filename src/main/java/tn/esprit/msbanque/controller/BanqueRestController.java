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
import tn.esprit.msbanque.client.CompteFeignClient;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.dto.CompteDto;
import tn.esprit.msbanque.repositories.BanqueRepository;
import tn.esprit.msbanque.service.IBanqueService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




@RestController
@RequestMapping("/api/banques")
@RequiredArgsConstructor
@CrossOrigin
public class BanqueRestController {

    private final IBanqueService banqueService;
    private final CompteFeignClient compteFeignClient;
    @PostMapping
    public ResponseEntity<BanqueDto> create(@RequestBody @Valid BanqueDto banqueDto) {
        BanqueDto createdBanque = banqueService.create(banqueDto);
        return new ResponseEntity<>(createdBanque, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BanqueDto> findById(@PathVariable String id) {
        BanqueDto banqueDto = banqueService.findById(id);
        return ResponseEntity.ok(banqueDto);
    }

    @GetMapping
    public ResponseEntity<List<BanqueDto>> findAll() {
        List<BanqueDto> banques = banqueService.findAll();
        return ResponseEntity.ok(banques);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BanqueDto> update(@PathVariable String id, @RequestBody @Valid BanqueDto banqueDto) {
        BanqueDto updatedBanque = banqueService.update(id, banqueDto);
        return ResponseEntity.ok(updatedBanque);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        banqueService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/comptes")
    public ResponseEntity<BanqueDto> addCompteToBanque(
            @PathVariable String id,
            @RequestBody Long compteId) {
        BanqueDto updatedBanque = banqueService.addCompteToBanque(id, compteId);
        return ResponseEntity.ok(updatedBanque);
    }
}