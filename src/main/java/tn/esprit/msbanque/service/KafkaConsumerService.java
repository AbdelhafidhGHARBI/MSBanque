package tn.esprit.msbanque.service;

import jdk.jfr.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.dto.CompteDto;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "ms-topic", groupId = "ms-group")
    public void consumeMessage(String message) {
        System.out.println("Message reçu : " + message);
        // Logique métier ici
    }
}

@KafkaListener(topics = "ms-topic", groupId = "ms-group")
public void consumeMessage(Event<CompteDto> event) {
    log.info("Événement reçu : {}", event);

    if ("COMPTE_CREATED".equals(event.getType())) {
        CompteDto compteDto = event.getPayload();

        // Ajouter le compte à la banque correspondante
        BanqueDto updatedBanque = banqueService.addCompteToBanque(compteDto.getBanqueId(), compteDto.getId());
        log.info("Compte ajouté à la banque : {}", updatedBanque);
    } else {
        log.warn("Type d'événement inconnu : {}", event.getType());
    }
}
