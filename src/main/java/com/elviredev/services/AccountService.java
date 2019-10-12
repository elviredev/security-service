package com.elviredev.services;

import com.elviredev.entities.AppRole;
import com.elviredev.entities.AppUser;

public interface AccountService {
    // enregistrer un utilisateur
    AppUser saveAppUser(String username, String password, String confirmedPassword);
    // enregistrer un role
    AppRole saveAppRole(AppRole role);
    // charger un utilisateur via son username
    AppUser loadUserByUsername(String username);
    // ajouter un role à un utilisateur
    void addRoleToUser(String username, String roleName);
}
