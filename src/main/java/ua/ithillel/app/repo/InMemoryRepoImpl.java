package ua.ithillel.app.repo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.ithillel.app.exeption.AccountNotFoundException;
import ua.ithillel.app.model.Account;

import java.util.List;

@Repository
public class InMemoryRepoImpl implements InMemoryRepo {
    private final SessionFactory sessionFactory;

    public InMemoryRepoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Account addAccount(Account account) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            session.persist(account);

            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Account> criteria = criteriaBuilder.createQuery(Account.class);
        criteria.from(Account.class);
        List<Account> accounts = session.createQuery(criteria).getResultList();
        session.close();
        return accounts;

    }

    @Override
    public Account getAccountById(Long id) {
        Session session = sessionFactory.openSession();
        Account account = session.get(Account.class, id);
        session.close();
        return account;
    }

    @Override
    public Account deleteAccount(Long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Account accountToDelete = session.get(Account.class, id);
            session.remove(accountToDelete);

            session.getTransaction().commit();

            return accountToDelete;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new AccountNotFoundException("Account with id = " + id + " not found");
        } finally {
            session.close();
        }
    }

    @Override
    public Account editAccount(Long id, Account account) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Account accountToUpdate = session.get(Account.class, id);
            accountToUpdate.setFirstName(account.getFirstName());
            accountToUpdate.setLastName(account.getLastName());
            accountToUpdate.setBalance(account.getBalance());
            accountToUpdate.setCountry(account.getCountry());
            accountToUpdate.setGender(account.getGender());

            session.getTransaction().commit();

            return accountToUpdate;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new AccountNotFoundException("Account with id = " + id + " not found");
        } finally {
            session.close();
        }
    }
}
