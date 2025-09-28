package com.andres.curso.springboot.app.springbootcrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andres.curso.springboot.app.springbootcrud.entities.Role;
import com.andres.curso.springboot.app.springbootcrud.entities.User;
import com.andres.curso.springboot.app.springbootcrud.repositories.RoleRepository;
import com.andres.curso.springboot.app.springbootcrud.repositories.UserRespository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>)userRespository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {

        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(role -> {
            roles.add(role);
        });

        if(user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role ->  {
                roles.add(role);
            });
        }
        user.setRoles(roles);
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);
        
        return userRespository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRespository.existsByUsername(username);
    }

}
