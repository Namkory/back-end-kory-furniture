package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CustomerRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.dto.response.CustomerResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.AdminService;
import kory.spring.com.bekoryfurniture.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    ApiResponse<CustomerResponse> createNewCustomer(@ModelAttribute @Valid CustomerRequest request){
        CustomerResponse response = customerService.createCustomer(request);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Page<CustomerResponse>> getAllCustomer(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<CustomerResponse> customerResponsessPage = customerService.getAllCustomer(page, size);
        return new ResponseEntity<>(customerResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/enable")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomerEnable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<CustomerResponse> customerResponsessPage = customerService.getAllCustomerEnable(page, size);
        return new ResponseEntity<>(customerResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ApiResponse<CustomerResponse> getCustomerByID(@PathVariable("id") Integer customerId) {
        CustomerResponse response = customerService.getCustomerById(customerId);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @PutMapping
    ApiResponse<CustomerResponse> updateNewCustomer(@ModelAttribute @Valid CustomerRequest request){
        CustomerResponse response = customerService.updateCustomer(request);
        ApiResponse<CustomerResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @PutMapping("/{id}")
    ApiResponse<?> disableCustomer(@PathVariable("id") Integer customerId){
        customerService.disableCustomer(customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DISABLE_ACCOUNT.getCode());
        response.setMessage(ErrorCode.DISABLE_ACCOUNT.getMessage());

        return response;
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteCustomer(@PathVariable("id") Integer customerId){
        customerService.deleteCustomer(customerId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
