package ua.ithillel.app.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MockDataTestUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    public static <T> List<T> getMockItems(String filePath, Class<T> clazz) {
        try (InputStream inputStream = clazz
                .getClassLoader().getResourceAsStream(filePath)
        ) {
            TypeReference<List<T>> typeReference = getListTypeReference(clazz);

            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private static <T> TypeReference<List<T>> getListTypeReference(Class<T> clazz) {
        TypeFactory typeFactory = objectMapper.getTypeFactory();

        return new TypeReference<>() {
            @Override
            public Type getType() {
                return typeFactory.constructCollectionType(List.class, clazz);
            }
        };
    }
}
