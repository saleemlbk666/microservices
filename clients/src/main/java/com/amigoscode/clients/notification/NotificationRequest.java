package com.amigoscode.clients.notification;

public record NotificationRequest(Integer customerId, String customerEmail, String message) {
}
