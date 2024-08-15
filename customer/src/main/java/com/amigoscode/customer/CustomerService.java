package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService{

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;

    private final NotificationClient notificationClient;

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName())
                .email(request.email()).build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudSter(customer.getId());

        if(fraudCheckResponse.isFraudSter()){
            throw new IllegalStateException("fraudster");
        }

        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail(),
                String.format("Hi %s, Welcome to Amigoscode", customer.getFirstName())));
    }
}
