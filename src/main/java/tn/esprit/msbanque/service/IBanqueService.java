package tn.esprit.msbanque.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tn.esprit.msbanque.dto.BanqueDto;

import java.util.Map;


public interface IBanqueService {
    BanqueDto create(BanqueDto banqueDto);
    BanqueDto findById(String id);
    Page<BanqueDto> findAll(Pageable pageable);
    BanqueDto partialUpdate(String id, Map<String, Object> updates);
    void delete(String id);
    Page<BanqueDto> search(String nom, String code, Pageable pageable);
}