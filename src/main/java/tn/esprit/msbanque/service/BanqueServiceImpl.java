package tn.esprit.msbanque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.entities.Banque;
import tn.esprit.msbanque.mapper.BanqueMapper;
import tn.esprit.msbanque.repositories.BanqueRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BanqueServiceImpl implements IBanqueService {

    private final BanqueRepository banqueRepository;
    private final BanqueMapper banqueMapper;

    @Override
    public BanqueDto create(BanqueDto banqueDto) {
        Banque banque = banqueMapper.toEntity(banqueDto);
        Banque savedBanque = banqueRepository.save(banque);
        return banqueMapper.toDto(savedBanque);
    }

    @Override
    public BanqueDto findById(String id) {
        return banqueRepository.findById(id)
                .map(banqueMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Banque non trouvée avec l'id: " + id));

    }

    @Override
    public Page<BanqueDto> findAll(Pageable pageable) {
        return banqueRepository.findAll(pageable)
                .map(banqueMapper::toDto);
    }

    @Override
    public BanqueDto partialUpdate(String id, Map<String, Object> updates) {
        Banque banque = banqueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Banque non trouvée avec l'id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "nom" -> banque.setNom((String) value);
                case "code" -> banque.setCode((String) value);
                case "adresse" -> banque.setAdresse((String) value);
            }
        });

        Banque updatedBanque = banqueRepository.save(banque);
        return banqueMapper.toDto(updatedBanque);
    }

    @Override
    public void delete(String id) {
        if (!banqueRepository.existsById(id)) {
            throw new IllegalArgumentException("Banque non trouvée avec l'id: " + id);
        }
        banqueRepository.deleteById(id);
    }

    @Override
    public Page<BanqueDto> search(String nom, String code, Pageable pageable) {
        if (nom != null && code != null) {
            return banqueRepository.findByNomContainingIgnoreCaseAndCodeContainingIgnoreCase(nom, code, pageable)
                    .map(banqueMapper::toDto);
        } else if (nom != null) {
            return banqueRepository.findByNomContainingIgnoreCase(nom, pageable)
                    .map(banqueMapper::toDto);
        } else if (code != null) {
            return banqueRepository.findByCodeContainingIgnoreCase(code, pageable)
                    .map(banqueMapper::toDto);
        }
        return findAll(pageable);
    }
}