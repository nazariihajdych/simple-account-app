package ua.ithillel.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.ithillel.app.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
