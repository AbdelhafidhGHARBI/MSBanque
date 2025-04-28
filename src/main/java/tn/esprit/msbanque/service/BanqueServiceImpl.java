package tn.esprit.msbanque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.entities.Banque;
import tn.esprit.msbanque.exceptions.BanqueNotFoundException;
import tn.esprit.msbanque.mapper.BanqueMapper;
import tn.esprit.msbanque.repositories.BanqueRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BanqueServiceImpl implements IBanqueService {

    private final BanqueRepository banqueRepository;
    private final BanqueMapper banqueMapper;

    @Override
    public BanqueDto create(BanqueDto banqueDto) {
        Banque banque = banqueMapper.toEntity(banqueDto);
        banque.setCreatedAt(LocalDateTime.now());
        Banque savedBanque = banqueRepository.save(banque);
        return banqueMapper.toDto(savedBanque);
    }

    @Override
    public BanqueDto findById(String id) {
        Banque banque = banqueRepository.findById(id)
                .orElseThrow(() -> new BanqueNotFoundException("Banque non trouvée avec l'id: " + id));
        return banqueMapper.toDto(banque);
    }

    @Override
    public List<BanqueDto> findAll() {
        List<Banque> banques = banqueRepository.findAll();
        return banques.stream()
                .map(banqueMapper::toDto)
                .toList();
    }

    @Override
    public BanqueDto update(String id, BanqueDto banqueDto) {
        Banque banque = banqueRepository.findById(id)
                .orElseThrow(() -> new BanqueNotFoundException("Banque non trouvée avec l'id: " + id));

        banqueMapper.updateEntityFromDto(banqueDto, banque);
        banque.setUpdatedAt(LocalDateTime.now());

        Banque updatedBanque = banqueRepository.save(banque);
        return banqueMapper.toDto(updatedBanque);
    }

    @Override
    public void delete(String id) {
        if (!banqueRepository.existsById(id)) {
            throw new BanqueNotFoundException("Banque non trouvée avec l'id: " + id);
        }
        banqueRepository.deleteById(id);
    }


    @Override
    public BanqueDto addCompteToBanque(String banqueId, Long compteId) {
        Banque banque = banqueRepository.findById(banqueId)
                .orElseThrow(() -> new BanqueNotFoundException("Banque not found"));

        if (!banque.getComptes().contains(compteId)) {
            banque.getComptes().add(compteId);
            banque = banqueRepository.save(banque);
        }

        // Convert to DTO using the mapper
        return banqueMapper.toDto(banque);
    }
}