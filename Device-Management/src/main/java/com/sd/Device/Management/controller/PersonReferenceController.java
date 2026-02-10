package com.sd.Device.Management.controller;

import com.sd.Device.Management.dto.PersonReferenceDTO;
import com.sd.Device.Management.service.personReference.PersonReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonReferenceController {
    @Autowired
    private final PersonReferenceService personReferenceService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> addPerson(@RequestBody PersonReferenceDTO personReferenceDTO) {
        personReferenceService.save(personReferenceDTO.getIdUser());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deletePerson(@PathVariable("idUser") UUID idUser) {
        personReferenceService.deleteByIdUser(idUser);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/ids")
    public ResponseEntity<List<UUID>> getAllIdUsers() {
        List<UUID> idUsers = personReferenceService.findAllIds();
        return new ResponseEntity<>(idUsers, HttpStatus.OK);
    }

}
