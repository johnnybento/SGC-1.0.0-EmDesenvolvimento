package com.developerstaff.services;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.developerstaff.model.Usuario;
import com.developerstaff.repository.RoleDAO;
import com.developerstaff.repository.UsuarioDAO;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsuarioDAO userRepository;
    @Autowired
    private RoleDAO roleRepository;
/*    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;*/

    @Override
    public void save(Usuario user) {
        user.setPassword(user.getPassword());
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public Usuario findByLogin(String nome) {
        return userRepository.findByLogin(nome);
    }
}
