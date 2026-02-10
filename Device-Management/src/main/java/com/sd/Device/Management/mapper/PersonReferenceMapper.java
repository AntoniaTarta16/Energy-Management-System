package com.sd.Device.Management.mapper;

import com.sd.Device.Management.dto.PersonReferenceDTO;
import com.sd.Device.Management.entity.PersonReference;
import org.springframework.stereotype.Component;

@Component
public class PersonReferenceMapper {

    public PersonReferenceDTO toPersonReferenceDTO(PersonReference personReference) {
        return PersonReferenceDTO.builder()
                .id(personReference.getId())
                .idUser(personReference.getIdUser())
                .build();
    }

    public PersonReference toPersonReference(PersonReferenceDTO personReferenceDTO) {
        return PersonReference.builder()
                .id(personReferenceDTO.getId())
                .idUser(personReferenceDTO.getIdUser())
                .build();
    }
}
