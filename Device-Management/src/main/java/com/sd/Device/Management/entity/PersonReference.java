package com.sd.Device.Management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "person_reference")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonReference {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    @OneToMany(mappedBy = "personReference",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.REFRESH, CascadeType.DETACH})
    private List<Device> devices;
}
