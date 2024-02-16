package ua.english.school.app;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.english.school.model.entity.*;

public class SessionFactoryMaker {
    private static SessionFactory factory;

    private static void configureFactory()
    {
        try {
            factory = new Configuration()
                    .addAnnotatedClass(User.class)
                    .addAnnotatedClass(UserInfo.class)
                    .addAnnotatedClass(Teacher.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Lesson.class)
                    .addAnnotatedClass(Homework.class)
                    .addAnnotatedClass(Group.class)
                    .addAnnotatedClass(Grade.class)
                    .addAnnotatedClass(Enrollment.class)
                    .addAnnotatedClass(Course.class)
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getFactory() {
        if (factory == null) {
            configureFactory();
        }

        return factory;
    }

}
