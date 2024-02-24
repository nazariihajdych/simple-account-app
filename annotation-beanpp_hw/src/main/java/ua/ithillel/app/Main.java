package ua.ithillel.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ua.ithillel.app.beans.TestBean;
import ua.ithillel.app.beans.OtherBean;

@Configuration
@ComponentScan(basePackages = "ua.ithillel.app")
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        TestBean testBean = context.getBean(TestBean.class);
        OtherBean otherBean = context.getBean(OtherBean.class);
        System.out.printf("Random value: %.2f %n", testBean.getValue());
        System.out.printf("Random value: %.2f", otherBean.getVal());

    }
}