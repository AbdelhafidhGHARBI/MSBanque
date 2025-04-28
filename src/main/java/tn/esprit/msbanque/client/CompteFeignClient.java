package tn.esprit.msbanque.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.msbanque.dto.CompteDto;

@FeignClient(name = "ms-compte", url = "http://localhost:8081/api/comptes")
public interface CompteFeignClient {

    @GetMapping("/{id}")
    CompteDto getCompteById(@PathVariable("id") Long id);

}
