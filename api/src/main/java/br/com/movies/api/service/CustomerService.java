package br.com.movies.api.service;

import br.com.movies.api.entities.Customer;
import br.com.movies.api.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;


    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listCustomers(){
        return customerRepository.findAll();
    }

    public Customer searchCustomerById(UUID id){
        return customerRepository.findById(id).orElse(null);
    }

    public Customer createCustomer(Customer movie){
        return customerRepository.save(movie);
    }

    public Customer updateCustomer(Customer updatedMovie){
        return customerRepository.save(updatedMovie);
    }

    public void deleteCustomer(UUID id){
        customerRepository.deleteById(id);
    }
}
