package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CustomerRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    Page<CustomerResponse> getAllCustomer(int page, int size);

    Page<CustomerResponse> getAllCustomerEnable(int page, int size);

    CustomerResponse getCustomerById(Integer customerId);

    CustomerResponse updateCustomer(CustomerRequest request);

    void disableCustomer(Integer customerId);

    void deleteCustomer(Integer customerId);
}
