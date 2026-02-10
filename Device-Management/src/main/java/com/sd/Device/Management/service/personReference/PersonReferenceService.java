package com.sd.Device.Management.service.personReference;

import com.sd.Device.Management.dto.PersonReferenceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonReferenceService {
    void deleteByIdUser(UUID idUser);

    void save(UUID idUser);

    Optional<PersonReferenceDTO> findByIdUser(UUID idUser);

    List<UUID> findAllIds();
}
