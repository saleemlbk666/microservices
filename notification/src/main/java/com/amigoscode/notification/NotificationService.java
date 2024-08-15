package com.amigoscode.notification;

import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    void send(NotificationRequest notificationRequest){

        Notification notification = Notification.builder().customerEmail(notificationRequest.customerEmail())
                .customerId(notificationRequest.customerId()).message(notificationRequest.message())
                .sender("Saleem").sentAt(LocalDateTime.now()).build();

        notificationRepository.save(notification);

    }

}
