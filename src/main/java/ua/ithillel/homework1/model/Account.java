package ua.ithillel.homework1.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Account {
    private String firstName;
    private String lastName;
    private String country;
    private LocalDateTime birthday;
    private Double balance;
    private String gender;

    public Account(String firstName, String lastName, String country, LocalDateTime birthday, Double balance, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.birthday = birthday;
        this.balance = balance;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(firstName, account.firstName) && Objects.equals(lastName, account.lastName) && Objects.equals(country, account.country) && Objects.equals(birthday, account.birthday) && Objects.equals(balance, account.balance) && Objects.equals(gender, account.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, country, birthday, balance, gender);
    }

    @Override
    public String toString() {
        return "Account{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", birthday=" + birthday +
                ", balance=" + balance +
                ", gender='" + gender + '\'' +
                '}';
    }
}
