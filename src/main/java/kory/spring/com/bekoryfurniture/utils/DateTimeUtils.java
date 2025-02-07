package kory.spring.com.bekoryfurniture.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeUtils() {
        // Private constructor để ngăn việc khởi tạo instance
    }

    public static String getCurrentDate() {
        return LocalDate.now().format(FORMATTER);
    }
}
