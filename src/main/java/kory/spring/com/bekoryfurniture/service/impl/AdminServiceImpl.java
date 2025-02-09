package kory.spring.com.bekoryfurniture.service.impl;

import kory.spring.com.bekoryfurniture.dto.request.AdminRequest;
import kory.spring.com.bekoryfurniture.dto.response.AdminResponse;
import kory.spring.com.bekoryfurniture.entity.Admin;
import kory.spring.com.bekoryfurniture.enums.Role;
import kory.spring.com.bekoryfurniture.exception.AppException;
import kory.spring.com.bekoryfurniture.exception.ErrorCode;
import kory.spring.com.bekoryfurniture.repository.AdminRepo;
import kory.spring.com.bekoryfurniture.repository.UserRepo;
import kory.spring.com.bekoryfurniture.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static kory.spring.com.bekoryfurniture.utils.DateTimeUtils.getCurrentDate;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepo adminRepo;
    private ModelMapper modelMapper;
    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CloudinaryImageServiceImpl cloudinaryImageService;

    @Override
    @Transactional
    public AdminResponse createAdmin(AdminRequest request) {
        String urlImage = cloudinaryImageService.uploadImage(request.getImage());
        if (request.getUserName() == null || request.getUserName().length() < 3){
            throw new AppException(ErrorCode.USERNAME_INVALID);
        }
        boolean exists = adminRepo.existsByUserName(request.getUserName());
        if (exists) {
            throw new AppException(ErrorCode.NOT_FOUND_ADMIN);
        }
        if (request.getPassword() == null || request.getPassword().length() < 6){
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(hashedPassword);

        Admin adminEntity = new Admin();
        modelMapper.map(request, adminEntity);
        adminEntity.setImage(urlImage);
        adminEntity.setCreatedAt(getCurrentDate());
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.ADMIN.name());
        adminEntity.setRoles(roles);
        adminEntity.setDelete(false);
        adminRepo.save(adminEntity);

        AdminResponse response = modelMapper.map(adminEntity, AdminResponse.class);
        String rolesString = String.join(", ", adminEntity.getRoles());
        response.setRoles(rolesString);

        return response;
    }

    @Override
    @Transactional
    public Page<AdminResponse> getAllAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Admin> adminsPage = adminRepo.findAll(pageable);

        Page<AdminResponse> adminResponsesPage = adminsPage.map(adminEntity -> {
            AdminResponse adminResponse = modelMapper.map(adminEntity, AdminResponse.class);
            String rolesString = String.join(", ", adminEntity.getRoles());
            adminResponse.setRoles(rolesString);
            return adminResponse;
        });

        return adminResponsesPage;
    }

    @Override
    @Transactional
    public Page<AdminResponse> getAllAdminEnable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Admin> adminsPage = adminRepo.findByIsDeleteFalse(pageable);
        return adminsPage.map(adminEntity -> {
            AdminResponse adminResponse = modelMapper.map(adminEntity, AdminResponse.class);
            String rolesString = String.join(", ", adminEntity.getRoles());
            adminResponse.setRoles(rolesString);
            return adminResponse;
        });
    }

    @Override
    @Transactional
    public AdminResponse getAdminById(Integer adminId) {
        Admin adminEntity = adminRepo.findById(adminId).orElseThrow(() ->
                new AppException(ErrorCode.NOT_FOUND_ADMIN));

        AdminResponse adminResponse = modelMapper.map(adminEntity, AdminResponse.class);
        String rolesString = String.join(", ", adminEntity.getRoles());
        adminResponse.setRoles(rolesString);

        return adminResponse;
    }

    @Override
    @Transactional
    public AdminResponse updateAdmin(AdminRequest request) {
        Admin adminEntity = adminRepo.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ADMIN));

        String originalUserName = adminEntity.getUserName();
        if (request.getPassword() == null) {
            request.setPassword(adminEntity.getPassword());
        } else {
            String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
            request.setPassword(hashedPassword);
        }
        modelMapper.map(request, adminEntity);
        adminEntity.setUserName(originalUserName);
        if(request.getImage() != null) {
            String urlImage = cloudinaryImageService.uploadImage(request.getImage());
            adminEntity.setImage(urlImage);
        }
        adminRepo.save(adminEntity);

        AdminResponse response = modelMapper.map(adminEntity, AdminResponse.class);
        String rolesString = String.join(", ", adminEntity.getRoles());
        response.setRoles(rolesString);

        return response;
    }

    @Override
    @Transactional
    public void disableAdmin(Integer adminId) {
        Admin adminEntity = adminRepo.findById(adminId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ADMIN));
        adminEntity.setDelete(true);

        adminRepo.save(adminEntity);
    }

    @Override
    @Transactional
    public void deleteAdmin(Integer adminId) {
        adminRepo.findById(adminId).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND_ADMIN));
        userRepo.deleteById(adminId);
        adminRepo.deleteById(adminId);
    }
}
