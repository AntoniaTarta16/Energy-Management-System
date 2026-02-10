package com.sd.Device.Management.repository;

import com.sd.Device.Management.entity.PersonReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonReferenceRepository extends JpaRepository<PersonReference, UUID> {
    Optional<PersonReference> findByIdUser(UUID idUser);
}
