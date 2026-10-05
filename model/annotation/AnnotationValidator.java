package model.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {
    public static String[] validate(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj must not be null");
        }

        List<String> errors = new ArrayList<>();
        Class<?> currentClass = obj.getClass();

        while (currentClass != null) {
            for (Field field : currentClass.getDeclaredFields()) {
                boolean hasPositive = field.isAnnotationPresent(Positive.class);
                boolean hasMaxLength = field.isAnnotationPresent(MaxLength.class);

                if (!hasPositive && !hasMaxLength) {
                    continue;
                }

                field.setAccessible(true);

                try {
                    Object value = field.get(obj);

                    if (hasPositive) {
                        if (!(value instanceof Number)) {
                            throw new IllegalStateException(
                                    "@Positive requires a numeric field: " + field.getName());
                        }

                        Positive positive = field.getAnnotation(Positive.class);
                        if (((Number) value).doubleValue() <= 0) {
                            errors.add(field.getName() + " " + positive.message());
                        }
                    }

                    if (hasMaxLength) {
                        MaxLength maxLength = field.getAnnotation(MaxLength.class);
                        String text = value == null ? "" : value.toString();
                        if (text.length() > maxLength.value()) {
                            errors.add(field.getName() + " must have at most "
                                    + maxLength.value() + " characters");
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(
                            "Unable to validate field: " + field.getName(), e);
                }
            }

            currentClass = currentClass.getSuperclass();
        }

        return errors.toArray(new String[0]);
    }
}
