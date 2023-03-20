package fr.gopartner.locationvoiture.core.utils;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isNotNullOrNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isEqual(String str1, String str2) {
        return str1.equals(str2);
    }

    public static boolean isNotNullOrNotEmpty(Long str) {
        return str != null;
    }

}
