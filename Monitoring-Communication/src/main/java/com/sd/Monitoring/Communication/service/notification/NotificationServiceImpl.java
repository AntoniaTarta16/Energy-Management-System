package com.sd.Monitoring.Communication.service.notification;

import com.sd.Monitoring.Communication.dto.DeviceDTO;
import com.sd.Monitoring.Communication.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private DeviceService deviceService;

    private final ConcurrentHashMap<String, LocalDateTime> notifications;

    public NotificationServiceImpl() {
        this.notifications = new ConcurrentHashMap<>();
    }

    public void notifyUser(String deviceId) {
        LocalDateTime currentHour = getCurrentHour();
        String notificationKey = deviceId + ":" + currentHour;

        if (!notifications.containsKey(notificationKey)) {
            DeviceDTO device = deviceService.findById(deviceId);
            messagingTemplate.convertAndSendToUser(device.getIdUser(), "/topic/notifications", "Device " + deviceId + " exceeded the maximum consumption limit.");
            notifications.put(notificationKey, currentHour);
        }
    }

    private LocalDateTime getCurrentHour() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }

    @Scheduled(fixedRate = 86400000)  // 24 hours
    private void cleanOldEntries() {
        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        notifications.entrySet().removeIf(entry -> entry.getValue().isBefore(oneDayAgo));
    }
}
