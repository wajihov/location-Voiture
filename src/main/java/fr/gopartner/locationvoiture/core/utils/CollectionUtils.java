package fr.gopartner.locationvoiture.core.utils;

import java.util.List;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static boolean isNullOrEmpty(List<? extends Object> objects) {
        return objects == null || objects.isEmpty();
    }
}
