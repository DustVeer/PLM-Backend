package com.plm.poelman.java_api.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.UserRole;
import com.plm.poelman.java_api.repositories.UserRoleRepository;

@Service
public class UserRoleService {

    private final UserRoleRepository _userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this._userRoleRepository = userRoleRepository;
    }
    public Optional<UserRole> findById(Long id) {
        return _userRoleRepository.findById(id);
    }
    
    
}
