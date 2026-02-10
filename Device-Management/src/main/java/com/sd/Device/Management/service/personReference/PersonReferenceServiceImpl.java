package com.sd.Device.Management.service.personReference;

import com.sd.Device.Management.dto.PersonReferenceDTO;
import com.sd.Device.Management.entity.PersonReference;
import com.sd.Device.Management.mapper.PersonReferenceMapper;
import com.sd.Device.Management.repository.DeviceRepository;
import com.sd.Device.Management.repository.PersonReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersonReferenceServiceImpl implements PersonReferenceService {
    private final PersonReferenceRepository personReferenceRepository;
    private final PersonReferenceMapper personReferenceMapper;
    private final DeviceRepository deviceRepository;

    @Override
    @Transactional
    public void save(UUID idUser) {

        Optional<PersonReference> existingPersonReference = personReferenceRepository.findByIdUser(idUser);
        if (existingPersonReference.isPresent()) {
            throw new IllegalArgumentException("User with this idUser already has a person reference.");
        }

        PersonReference personReference = PersonReference.builder()
                .idUser(idUser)
                .build();
        personReferenceRepository.save(personReference);
    }

    @Override
    public Optional<PersonReferenceDTO> findByIdUser(UUID idUser) {
        return personReferenceRepository.findByIdUser(idUser)
                .map(personReferenceMapper::toPersonReferenceDTO);
    }

    @Override
    public List<UUID> findAllIds() {
        return personReferenceRepository.findAll().stream()
                .map(PersonReference::getIdUser)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByIdUser(UUID idUser) {
        Optional<PersonReference> personReferenceOptional = personReferenceRepository.findByIdUser(idUser);
        if (personReferenceOptional.isPresent()) {
            PersonReference personReference = personReferenceOptional.get();
            // Set the personReference field to null in all associated devices
            personReference.getDevices().forEach(device -> {
                device.setPersonReference(null);
                deviceRepository.save(device);
            });
            personReferenceRepository.delete(personReference);
        }
    }

}
