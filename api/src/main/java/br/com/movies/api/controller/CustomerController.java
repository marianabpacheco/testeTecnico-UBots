package br.com.movies.api.controller;

import br.com.movies.api.dto.CustomerDTO;
import br.com.movies.api.dto.RatingDTO;
import br.com.movies.api.entities.Customer;
import br.com.movies.api.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> listCustomers(){
        return customerService.listCustomers().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDTO searchCustomerById(@PathVariable UUID id){
        Customer customer = customerService.searchCustomerById(id);
        if(customer != null){
            return mapToDTO(customer);
        }
        return null;
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = mapToEntity(customerDTO);
        Customer createdCustomer = customerService.createCustomer(customer);
        return mapToDTO(createdCustomer);
    }


    @PutMapping("/{id}")
    public CustomerDTO updateMovie(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO){
        Customer customer = mapToEntity(customerDTO);
        customer.setId(id);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        if(updatedCustomer != null){
            return mapToDTO(updatedCustomer);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
    }



    private Customer mapToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }

    private CustomerDTO mapToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }


}
