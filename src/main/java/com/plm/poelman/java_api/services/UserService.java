package com.plm.poelman.java_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plm.poelman.java_api.models.User;
import com.plm.poelman.java_api.models.dto.login.LoginRequest;
import com.plm.poelman.java_api.models.dto.users.CreateUserRequest;
import com.plm.poelman.java_api.models.dto.users.UpdateUserPasswordRequest;
import com.plm.poelman.java_api.repositories.UserRepository;
import com.plm.poelman.java_api.security.PasswordUtils;
import com.plm.poelman.java_api.models.dto.users.UserResponse;

@Service
public class UserService {

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

    public UserResponse update(User user) {
        User updatedUser = _userRepository.save(user);
        return new UserResponse(updatedUser);

    }

    public Optional<UserResponse> updatePassword(Long userId, UpdateUserPasswordRequest req) {
        Optional<User> optUser = _userRepository.findById(userId);
        User currentUser = optUser.get();

        byte[] salt = currentUser.getPasswordSalt();
        byte[] expectedHash = currentUser.getPasswordHash();
        boolean ok = _passwordUtils.verify(req.getOldPassword().toCharArray(), salt, expectedHash);
        if (!ok) {
            return Optional.empty();
        }

        char[] newPassword = req.getNewPassword().toCharArray();
        char[] confirmNewPassword = req.getConfirmNewPassword().toCharArray();
        if (!String.valueOf(newPassword).equals(String.valueOf(confirmNewPassword))) {
            return Optional.empty();
        }

        byte[] newSalt = _passwordUtils.generateSalt();
        byte[] newHash = _passwordUtils.hash(newPassword, newSalt);

        currentUser.setPasswordSalt(newSalt);
        currentUser.setPasswordHash(newHash);
        User updatedUser = _userRepository.save(currentUser);

        return Optional.of(new UserResponse(updatedUser));
    }

    public List<UserResponse> getAllUsersResponse() {
        return _userRepository.findAll().stream()
                .map(u -> new UserResponse(u))
                .toList();
    }

    public Optional<User> Login(LoginRequest req) {
        Optional<User> userOpt = _userRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        User user = userOpt.get();

        byte[] salt = user.getPasswordSalt();

        byte[] expectedHash = user.getPasswordHash();


        boolean ok = _passwordUtils.verify(req.getPassword().toCharArray(), salt, expectedHash);

        if (!ok)
            return Optional.empty();

        return Optional.of(user);
    }

}
