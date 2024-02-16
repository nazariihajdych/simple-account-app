package ua.english.school.app;

import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {

        System.out.println();
        SessionFactory factory = SessionFactoryMaker.getFactory();
    }
}
