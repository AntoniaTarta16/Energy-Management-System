package com.sd.Device.Management.service.device;

import com.sd.Device.Management.dto.DeviceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceService {
    List<DeviceDTO> findAll();

    DeviceDTO save(DeviceDTO deviceDTO);

    void deleteById(UUID idDevice);

    DeviceDTO update(UUID idDevice, DeviceDTO deviceDTO);

    List<DeviceDTO> findByPersonReferenceIdUser(UUID idUser);

    Optional<DeviceDTO> findById(UUID idDevice);
}
