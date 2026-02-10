package com.sd.Monitoring.Communication.repository;

import com.sd.Monitoring.Communication.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
