package tn.esprit.msbanque.service;


import tn.esprit.msbanque.dto.BanqueDto;

import java.util.List;


public interface IBanqueService {
    BanqueDto create(BanqueDto banqueDto);

    BanqueDto findById(String id);

    List<BanqueDto> findAll();

    BanqueDto update(String id, BanqueDto banqueDto);

    void delete(String id);

    // méthode pour feignClient
    BanqueDto addCompteToBanque(String banqueId, Long compteId);
}