package com.developerstaff.services;

import com.developerstaff.model.Usuario;

public interface UserService {
    void save(Usuario user);

    Usuario findByLogin(String nome);
}
