package ua.ithillel.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.app.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
