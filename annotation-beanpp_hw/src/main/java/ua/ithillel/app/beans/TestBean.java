package ua.ithillel.app.beans;

import lombok.Data;
import org.springframework.stereotype.Component;
import ua.ithillel.app.customAnnotation.RandomValue;

@Data
@Component
public class TestBean {
    @RandomValue
    private double value;
}
