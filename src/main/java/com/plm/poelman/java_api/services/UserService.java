package com.plm.poelman.java_api.services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.login.LoginRequest;
import com.plm.poelman.java_api.models.dto.users.CreateUserRequest;
import com.plm.poelman.java_api.repositories.UserRepository;
import com.plm.poelman.java_api.security.PasswordUtils;
import com.plm.poelman.java_api.models.dto.users.UserResponse;

@Service
public class UserService  {

    private final UserRepository _userRepository;
    private final UserRoleService _userRoleService;
    private final PasswordUtils _passwordUtils;

    public UserService(UserRepository userRepository, PasswordUtils passwordUtils, UserRoleService userRoleService) {
        this._userRepository = userRepository;
        this._passwordUtils = passwordUtils;
        this._userRoleService = userRoleService;
    }

    public Optional<User> findByEmail(String email) {
        return _userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return _userRepository.findById(id);
    }

    public User save(CreateUserRequest req) {

        byte[] salt = _passwordUtils.generateSalt();
        byte[] hash = _passwordUtils.hash(req.getPassword().toCharArray(), salt);

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPasswordSalt(salt);
        user.setPasswordHash(hash);
        // user.setRoleId(req.getRoleId()); Change to new user role system later


        return _userRepository.save(user);
    }

    public List<UserResponse> getAllUsersResponse() {
        return _userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getCreatedAt()))
                .toList();
    } 

    public Optional<User> Login(LoginRequest req) {
        Optional<User> userOpt = _userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        byte[] salt = user.getPasswordSalt();

        System.out.println("passwordReact:" + req.getPassword());
        byte[] expectedHash = user.getPasswordHash();

        boolean ok = _passwordUtils.verify(req.getPassword().toCharArray(), salt, expectedHash);

        if (!ok) return Optional.empty();

        return Optional.of(user);
    }

}
