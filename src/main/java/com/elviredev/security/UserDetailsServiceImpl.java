package com.elviredev.security;

import com.elviredev.entities.AppUser;
import com.elviredev.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // récupère user
        AppUser appUser = accountService.loadUserByUsername(username);
        // si utilisateur n'existe pas => exception
        if(appUser == null) throw new UsernameNotFoundException("Invalid user");
        // gestion des rôles
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // parcourir les roles de l'utilisateur récupéré et ajout du role
        appUser.getRoles().forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
}
