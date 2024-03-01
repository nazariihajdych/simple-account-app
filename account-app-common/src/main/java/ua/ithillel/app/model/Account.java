package ua.ithillel.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.ithillel.app.model.enums.Gender;

import java.util.Date;
import java.util.List;


@Entity(name = "account")
@Table(name = "account", schema = "manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"payments"})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String country;
    private Double balance;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Payment> payments;
}
