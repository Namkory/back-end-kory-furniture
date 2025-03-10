package kory.spring.com.bekoryfurniture.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.ApiResponse;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/admin")
@Tag(name = "Admin Controller")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "Add admin", description = "API create new admin")
    @PostMapping
    ApiResponse<AdminResponse> createNewAdmin(@ModelAttribute @Valid AdminRequest request){
        log.info("Request add admin, {}",
                request.toString());
        AdminResponse response = adminService.createAdmin(request);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Get admin list per page", description = "Return admin by page and size")
    @GetMapping()
    public ResponseEntity<Page<AdminResponse>> getAllAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of admins");
        Page<AdminResponse> adminResponsessPage = adminService.getAllAdmin(page, size);
        return new ResponseEntity<>(adminResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get admin list per page", description = "Return enabled admins by page and size.")
    @GetMapping("/enable")
    public ResponseEntity<Page<AdminResponse>> getAllAdminEnable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        log.info("Request get all of enabled admins");
        Page<AdminResponse> adminResponsessPage = adminService.getAllAdminEnable(page, size);
        return new ResponseEntity<>(adminResponsessPage, HttpStatus.OK);
    }

    @Operation(summary = "Get admin detail", description = "API get admin detail")
    @GetMapping("/{id}")
    ApiResponse<AdminResponse> getAdminByID(@PathVariable("id") Integer adminId) {
        log.info("Request get detail admin by adminId={}", adminId);
        AdminResponse response = adminService.getAdminById(adminId);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.GET_BY_ID_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.GET_BY_ID_SUCCESSFUL.getMessage());
        res.setResult(response);

        return res;
    }

    @Operation(summary = "Update admin", description = "API update admin")
    @PutMapping
    ApiResponse<AdminResponse> updateNewAdmin(@ModelAttribute @Valid AdminRequest request){
        log.info("Request update admin, {}", request.toString());
        AdminResponse response = adminService.updateAdmin(request);
        ApiResponse<AdminResponse> res = new ApiResponse<>();
        res.setCode(ErrorCode.UPDATE_SUCCESSFUL.getCode());
        res.setMessage(ErrorCode.UPDATE_SUCCESSFUL.getMessage());
        res.setResult(response);
        return res;
    }

    @Operation(summary = "Disable admin", description = "API disable admin")
    @PutMapping("/{id}")
    ApiResponse<?> disableAdmin(@PathVariable("id") Integer adminId){
        log.info("Request disable admin by adminId={}", adminId);
        adminService.disableAdmin(adminId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DISABLE_ACCOUNT.getCode());
        response.setMessage(ErrorCode.DISABLE_ACCOUNT.getMessage());

        return response;
    }

    @Operation(summary = "Delete admin", description = "API delete admin")
    @DeleteMapping("/{id}")
    ApiResponse<?> deleteAdmin(@PathVariable("id") Integer adminId){
        log.info("Request delete admin by adminId={}", adminId);
        adminService.deleteAdmin(adminId);
        ApiResponse<?> response = new ApiResponse<>();
        response.setCode(ErrorCode.DELETE_SUCCESSFUL.getCode());
        response.setMessage(ErrorCode.DELETE_SUCCESSFUL.getMessage());

        return response;
    }
}
