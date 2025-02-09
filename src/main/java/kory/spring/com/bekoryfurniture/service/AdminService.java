package kory.spring.com.bekoryfurniture.service;

import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.request.CategoryRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;

public interface AdminService {

    AdminResponse createAdmin(AdminRequest request);

    Page<AdminResponse> getAllAdmin(int page, int size);

    Page<AdminResponse> getAllAdminEnable(int page, int size);

    AdminResponse getAdminById(Integer adminId);

    AdminResponse updateAdmin(AdminRequest request);

    void disableAdmin(Integer adminId);

    void deleteAdmin(Integer adminId);
}
