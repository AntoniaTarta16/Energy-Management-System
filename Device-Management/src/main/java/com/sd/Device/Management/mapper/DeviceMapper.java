package com.sd.Device.Management.mapper;

import com.sd.Device.Management.dto.DeviceDTO;
import com.sd.Device.Management.entity.Device;
import com.sd.Device.Management.entity.PersonReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceMapper {

    public DeviceDTO toDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .idDevice(device.getIdDevice())
                .description(device.getDescription())
                .address(device.getAddress())
                .maxHourlyEnergyConsumption(device.getMaxHourlyEnergyConsumption())
                .idUser(device.getPersonReference() != null ? device.getPersonReference().getIdUser() : null)
                .build();
    }

    public Device toDevice(DeviceDTO deviceDTO) {
        PersonReference personReference = deviceDTO.getIdUser() != null ?
                PersonReference.builder().idUser(deviceDTO.getIdUser()).build() : null;
        return Device.builder()
                .idDevice(deviceDTO.getIdDevice())
                .description(deviceDTO.getDescription())
                .address(deviceDTO.getAddress())
                .maxHourlyEnergyConsumption(deviceDTO.getMaxHourlyEnergyConsumption())
                .personReference(personReference)
                .build();
    }

    public List<DeviceDTO> toDeviceDTOs(List<Device> devices) {
        return devices.stream()
                .map(this::toDeviceDTO)
                .toList();
    }
}
