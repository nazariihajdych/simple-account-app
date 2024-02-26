package ua.ithillel.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.app.model.Account;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
