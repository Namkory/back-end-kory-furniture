package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CustomerRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.CustomerResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.AdminService;
import kory.spring.com.bekoryfurniture.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/customer")
@Tag(name = "Customer Controller")
@Slf4j
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(summary = "Add customer", description = "API create new customer")
    @PostMapping
    ApiResponse<CustomerResponse> createNewCustomer(@ModelAttribute @Valid CustomerRequest request){
        log.info("Request add customer, {}",
                request.toString());
        CustomerResponse response = customerService.createCustomer(request);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get customer list per page", description = "Return customer by page and size")
    @GetMapping()
    public ResponseEntity<Page<CustomerResponse>> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of customers");
        Page<CustomerResponse> customerResponsessPage = customerService.getAllCustomer(page, size);
        return new ResponseEntity<>(customerResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get customer list per page", description = "Return enabled customers by page and size.")
    @GetMapping("/enable")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomerEnable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of enabled customers");
        Page<CustomerResponse> customerResponsessPage = customerService.getAllCustomerEnable(page, size);
        return new ResponseEntity<>(customerResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get customer detail", description = "API get customer detail")
    @GetMapping("/{id}")
    ApiResponse<CustomerResponse> getCustomerByID(@PathVariable("id") Integer customerId) {
        log.info("Request get detail customer by customerId={}", customerId);
        CustomerResponse response = customerService.getCustomerById(customerId);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @Operation(summary = "Update customer", description = "API update customer")
    @PutMapping
    ApiResponse<CustomerResponse> updateNewCustomer(@ModelAttribute @Valid CustomerRequest request){
        log.info("Request update customer, {}", request.toString());
        CustomerResponse response = customerService.updateCustomer(request);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Disable customer", description = "API disable customer")
    @PutMapping("/{id}")
    ApiResponse<?> disableCustomer(@PathVariable("id") Integer customerId){
        log.info("Request disable customer by customerId={}", customerId);
        customerService.disableCustomer(customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DISABLE_ACCOUNT.getCode());
        response.setMessage(ErrorCode.DISABLE_ACCOUNT.getMessage());

        return response;
    }

    @Operation(summary = "Delete customer", description = "API delete customer")
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteCustomer(@PathVariable("id") Integer customerId){
        log.info("Request delete customer by customerId={}", customerId);
        customerService.deleteCustomer(customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
