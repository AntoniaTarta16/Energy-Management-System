package com.sd.Monitoring.Communication.repository;

import com.sd.Monitoring.Communication.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {
    Optional<Measurement> findByDeviceIdAndDate(String deviceId, LocalDateTime date);
    void deleteByDeviceId(String deviceId);

    @Query("SELECT m FROM Measurement m WHERE m.deviceId = :deviceId AND DATE(m.date) = :date")
    List<Measurement> findByDeviceIdAndDate(@Param("deviceId") String deviceId, @Param("date") LocalDate date);
}
