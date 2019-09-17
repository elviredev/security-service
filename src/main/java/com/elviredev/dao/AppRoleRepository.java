package com.elviredev.dao;

        import com.elviredev.entities.AppRole;
        import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByRoleName(String roleName);
}
