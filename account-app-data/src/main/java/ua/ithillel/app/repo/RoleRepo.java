package ua.ithillel.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.app.model.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
}
