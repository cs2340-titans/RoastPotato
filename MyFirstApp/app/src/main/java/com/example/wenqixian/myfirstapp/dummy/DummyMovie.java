package com.example.wenqixian.myfirstapp.dummy;

import com.example.wenqixian.myfirstapp.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyMovie {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Movie> ITEMS = new ArrayList<Movie>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Movie> ITEM_MAP = new HashMap<String, Movie>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Movie item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Movie createDummyItem(int position) {
        return new Movie(String.valueOf(position), "Item " + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
