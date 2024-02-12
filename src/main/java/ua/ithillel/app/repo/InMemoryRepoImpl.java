package ua.ithillel.app.repo;

import jakarta.persistence.EntityManager;
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
        EntityManager entityManager = sessionFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            entityManager.persist(account);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }finally {
            entityManager.close();
        }
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        String queryString = "SELECT a FROM account a";
        List<Account> resultList = entityManager.createQuery(queryString, Account.class).getResultList();
        entityManager.close();
        return resultList;

    }

    @Override
    public Account getAccountById(Long id) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        Account account = entityManager.find(Account.class, id);
        entityManager.close();
        return account;
    }

    @Override
    public Account deleteAccount(Long id) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Account accountToDelete = entityManager.find(Account.class, id);
            entityManager.remove(accountToDelete);

            entityManager.getTransaction().commit();

            return accountToDelete;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new AccountNotFoundException("Account with id = " + id + " not found");
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Account editAccount(Long id, Account account) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Account accountToUpdate = entityManager.find(Account.class, id);
            accountToUpdate.setFirstName(account.getFirstName());
            accountToUpdate.setLastName(account.getLastName());
            accountToUpdate.setBalance(account.getBalance());
            accountToUpdate.setCountry(account.getCountry());
            accountToUpdate.setGender(account.getGender());

            entityManager.getTransaction().commit();

            return accountToUpdate;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new AccountNotFoundException("Account with id = " + id + " not found");
        } finally {
            entityManager.close();
        }
    }
}
