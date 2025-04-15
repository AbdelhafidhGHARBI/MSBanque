package tn.esprit.msbanque.mapper;

import org.mapstruct.Mapper;
import tn.esprit.msbanque.dto.BanqueDto;
import tn.esprit.msbanque.entities.Banque;

@Mapper(componentModel = "spring")
public interface BanqueMapper {
    Banque toEntity(BanqueDto dto);
    BanqueDto toDto(Banque entity);
}
