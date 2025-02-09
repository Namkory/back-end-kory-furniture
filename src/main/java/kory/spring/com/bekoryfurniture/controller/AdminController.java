package kory.spring.com.bekoryfurniture.controller;

import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping
    ApiResponse<AdminResponse> createNewAdmin(@ModelAttribute @Valid AdminRequest request){
        AdminResponse response = adminService.createAdmin(request);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @GetMapping()
    public ResponseEntity<Page<AdminResponse>> getAllAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<AdminResponse> adminResponsessPage = adminService.getAllAdmin(page, size);
        return new ResponseEntity<>(adminResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/enable")
    public ResponseEntity<Page<AdminResponse>> getAllAdminEnable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Page<AdminResponse> customerResponsessPage = adminService.getAllAdminEnable(page, size);
        return new ResponseEntity<>(customerResponsessPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ApiResponse<AdminResponse> getAdminByID(@PathVariable("id") Integer adminId) {
        AdminResponse response = adminService.getAdminById(adminId);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @PutMapping
    ApiResponse<AdminResponse> updateNewAdmin(@ModelAttribute @Valid AdminRequest request){
        AdminResponse response = adminService.updateAdmin(request);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @PutMapping("/{id}")
    ApiResponse<?> disableAdmin(@PathVariable("id") Integer adminId){
        adminService.disableAdmin(adminId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DISABLE_ACCOUNT.getCode());
        response.setMessage(ErrorCode.DISABLE_ACCOUNT.getMessage());

        return response;
    }

    @DeleteMapping("/{id}")
    ApiResponse<?> deleteAdmin(@PathVariable("id") Integer adminId){
        adminService.deleteAdmin(adminId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
