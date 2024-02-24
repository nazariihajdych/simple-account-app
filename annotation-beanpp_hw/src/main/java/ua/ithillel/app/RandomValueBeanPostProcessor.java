package ua.ithillel.app;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import ua.ithillel.app.customAnnotation.RandomValue;

import java.lang.reflect.Field;

@Component
public class RandomValueBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> aClass = bean.getClass();
        for (Field field: aClass.getDeclaredFields()){
            if (field.isAnnotationPresent(RandomValue.class)){
                field.setAccessible(true);
                try {
                    field.set(bean, Math.random() * 10);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return bean;
    }
}
