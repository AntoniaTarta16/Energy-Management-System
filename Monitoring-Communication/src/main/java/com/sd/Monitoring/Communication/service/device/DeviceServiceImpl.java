package com.sd.Monitoring.Communication.service.device;

import com.sd.Monitoring.Communication.dto.DeviceDTO;
import com.sd.Monitoring.Communication.entity.Device;
import com.sd.Monitoring.Communication.mapper.DeviceMapper;
import com.sd.Monitoring.Communication.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public void createDevice(DeviceDTO deviceDTO) {
        Device device = deviceMapper.toDevice(deviceDTO);
        deviceRepository.save(device);
    }

    @Override
    @Transactional
    public void updateDevice(DeviceDTO deviceDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(deviceDTO.getIdDevice());

        if (deviceOptional.isEmpty()) {
            throw new RuntimeException("Device not found!");
        }

        Device device = deviceOptional.get();
        device.setIdDevice(deviceDTO.getIdDevice());
        device.setIdUser(deviceDTO.getIdUser());
        device.setConsumption(deviceDTO.getConsumption());

        deviceRepository.save(device);
    }

    @Override
    @Transactional
    public void deleteDevice(String idDevice) {
        Optional<Device> device = deviceRepository.findById(idDevice);
        device.ifPresent(deviceRepository::delete);
    }

    @Override
    public DeviceDTO findById(String idDevice) {
        return deviceMapper.toDeviceDTO(deviceRepository.findById(idDevice).orElseThrow(() -> new RuntimeException("Device not found!")));
    }
}
