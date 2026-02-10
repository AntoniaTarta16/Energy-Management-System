package com.sd.Device.Management.service.device;

import com.sd.Device.Management.dto.DeviceDTO;
import com.sd.Device.Management.dto.PersonReferenceDTO;
import com.sd.Device.Management.entity.Device;
import com.sd.Device.Management.entity.PersonReference;
import com.sd.Device.Management.mapper.DeviceMapper;
import com.sd.Device.Management.mapper.PersonReferenceMapper;
import com.sd.Device.Management.repository.DeviceRepository;
import com.sd.Device.Management.service.personReference.PersonReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;
    private final PersonReferenceService personReferenceService;
    private final PersonReferenceMapper personReferenceMapper;

    @Override
    public List<DeviceDTO> findAll() {
        return deviceMapper.toDeviceDTOs(deviceRepository.findAll());
    }

    @Override
    @Transactional
    public DeviceDTO save(DeviceDTO deviceDTO) {
        Device device = deviceMapper.toDevice(deviceDTO);
        setPersonReference(deviceDTO, device);
        Device deviceAdded = deviceRepository.save(device);
        return deviceMapper.toDeviceDTO(deviceAdded);
    }

    @Override
    @Transactional
    public void deleteById(UUID idDevice) {
        Optional<Device> device = deviceRepository.findById(idDevice);
        device.ifPresent(deviceRepository::delete);
    }

    @Override
    @Transactional
    public DeviceDTO update(UUID idDevice, DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(idDevice);

        if (deviceOptional.isEmpty()) {
            throw new IllegalArgumentException("Device not found!");
        }

        Device device = deviceOptional.get();
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxHourlyEnergyConsumption(deviceDTO.getMaxHourlyEnergyConsumption());
        setPersonReference(deviceDTO, device);

        Device deviceUpdated = deviceRepository.save(device);
        return deviceMapper.toDeviceDTO(deviceUpdated);
    }

    @Override
    public List<DeviceDTO> findByPersonReferenceIdUser(UUID idUser) {
        return deviceMapper.toDeviceDTOs(deviceRepository.findByPersonReferenceIdUser(idUser));
    }

    @Override
    public Optional<DeviceDTO> findById(UUID idDevice) {
        return deviceRepository.findById(idDevice)
                .map(deviceMapper::toDeviceDTO);
    }

    private void setPersonReference(DeviceDTO deviceDTO, Device device) {
        if (deviceDTO.getIdUser() != null) {
            UUID idUser = deviceDTO.getIdUser();

            PersonReferenceDTO personReferenceDTO = personReferenceService.findByIdUser(idUser)
                    .orElseThrow(() -> new IllegalArgumentException("User not found!"));

            PersonReference personReference = personReferenceMapper.toPersonReference(personReferenceDTO);
            device.setPersonReference(personReference);
        } else {
            device.setPersonReference(null);
        }
    }

}
