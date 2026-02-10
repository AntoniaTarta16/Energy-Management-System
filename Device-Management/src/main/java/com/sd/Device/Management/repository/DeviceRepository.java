package com.sd.Device.Management.repository;

import com.sd.Device.Management.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByPersonReferenceIdUser(UUID idUser);
}
