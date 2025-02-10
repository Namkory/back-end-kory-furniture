package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.request.CustomerRequest;
import kory.spring.com.bekoryfurniture.dto.response.CustomerResponse;
import kory.spring.com.bekoryfurniture.entity.Customer;
import kory.spring.com.bekoryfurniture.enums.Role;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.CustomerRepo;
import kory.spring.com.bekoryfurniture.repository.UserRepo;
import kory.spring.com.bekoryfurniture.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo;
    private ModelMapper modelMapper;
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CloudinaryImageServiceImpl cloudinaryImageService;

    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        String urlImage = cloudinaryImageService.uploadImage(request.getImage());
        if (request.getUserName() == null || request.getUserName().length() < 3){
            throw new AppException(ErrorCode.USERNAME_INVALID);
        }
        boolean exists = customerRepo.existsByUserName(request.getUserName());
        if (exists) {
            throw new AppException(ErrorCode.NOT_FOUND_CUSTOMER);
        }
        if (request.getPassword() == null || request.getPassword().length() < 6){
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        Customer customerEntity = new Customer();
        modelMapper.map(request, customerEntity);
        customerEntity.setImage(urlImage);
        customerEntity.setCreatedAt(getCurrentDate());
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        customerEntity.setRoles(roles);
        customerEntity.setDelete(false);
        customerEntity.setLastPurchaseDate("00/00/0000");
        customerRepo.save(customerEntity);

        CustomerResponse response = modelMapper.map(customerEntity, CustomerResponse.class);
        String rolesString = String.join(", ", customerEntity.getRoles());
        response.setRoles(rolesString);

        return response;
    }

    @Override
    @Transactional
    public Page<CustomerResponse> getAllCustomer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Customer> customersPage = customerRepo.findAll(pageable);

        Page<CustomerResponse> customerResponsesPage = customersPage.map(customerEntity -> {
            CustomerResponse customerResponse = modelMapper.map(customerEntity, CustomerResponse.class);
            String rolesString = String.join(", ", customerEntity.getRoles());
            customerResponse.setRoles(rolesString);
            return customerResponse;
        });

        return customerResponsesPage;
    }

    @Override
    @Transactional
    public Page<CustomerResponse> getAllCustomerEnable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = customerRepo.findByIsDeleteFalse(pageable);
        return customersPage.map(customerEntity -> {
            CustomerResponse customerResponse = modelMapper.map(customerEntity, CustomerResponse.class);
            String rolesString = String.join(", ", customerEntity.getRoles());
            customerResponse.setRoles(rolesString);
            return customerResponse;
        });
    }

    @Override
    @Transactional
    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customerEntity = customerRepo.findById(customerId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_CUSTOMER));

        CustomerResponse customerResponse = modelMapper.map(customerEntity, CustomerResponse.class);
        String rolesString = String.join(", ", customerEntity.getRoles());
        customerResponse.setRoles(rolesString);

        return customerResponse;
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomer(CustomerRequest request) {
        Customer customerEntity = customerRepo.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CUSTOMER));

        String originalUserName = customerEntity.getUserName();
        if (request.getPassword() == null) {
            request.setPassword(customerEntity.getPassword());
        } else {
            String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
            request.setPassword(hashedPassword);
        }
        modelMapper.map(request, customerEntity);
        customerEntity.setUserName(originalUserName);
        if(request.getImage() != null) {
            String urlImage = cloudinaryImageService.uploadImage(request.getImage());
            customerEntity.setImage(urlImage);
        }
        customerRepo.save(customerEntity);

        CustomerResponse response = modelMapper.map(customerEntity, CustomerResponse.class);
        String rolesString = String.join(", ", customerEntity.getRoles());
        response.setRoles(rolesString);

        return response;
    }

    @Override
    @Transactional
    public void disableCustomer(Integer customerId) {
        Customer customerEntity = customerRepo.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CUSTOMER));
        customerEntity.setDelete(true);

        customerRepo.save(customerEntity);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_CUSTOMER));
        userRepo.deleteById(customerId);
        customerRepo.deleteById(customerId);
        cloudinaryImageService.deleteImage(customer.getImage());
    }
}
