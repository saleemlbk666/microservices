package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService{

    private final CustomerRepository customerRepository;

    private final RestTemplate restTemplate;

    private final FraudClient fraudClient;

    public void registerCustomer(CustomerRegistrationRequest request){
        Customer customer = Customer.builder().firstName(request.firstName()).lastName(request.lastName())
                .email(request.email()).build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudSter(customer.getId());

        if(fraudCheckResponse.isFraudSter()){
            throw new IllegalStateException("fraudster");
        }
    }
}
