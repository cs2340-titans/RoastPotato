package com.example.wenqixian.myfirstapp;


import com.example.wenqixian.myfirstapp.adapters.MoviesAdapter;
import com.example.wenqixian.myfirstapp.dummy.DummyMovie;
import com.example.wenqixian.myfirstapp.singletons.FirebaseSingleton;
import com.firebase.client.Firebase;

import org.junit.Test;

/**
 * Movie Adapter Unit Test
 *
 * @author Muchen Wu
 * @version 1.2
 */


public class AdapterTest {

    private static final int TIMEOUT = 200;
    private static final int LONG_TIMEOUT = 8000;

    private static int successCounter = 0;
    // endregion

    // region [ SET UP ]


    // region [A] EXCEPTIONS
    @Test(timeout = TIMEOUT)
    public void m01createAdapter() {
        MoviesAdapter adapter = new MoviesAdapter();
    }

    @Test(timeout = TIMEOUT)
    public void m02createAdapterWithList() {
        MoviesAdapter adapter = new MoviesAdapter(DummyMovie.ITEMS);
    }

    @Test(timeout = TIMEOUT)
    public void m03getList() {
        MoviesAdapter adapter = new MoviesAdapter(DummyMovie.ITEMS);
        assert(adapter.mMovies == DummyMovie.ITEMS);
    }

    @Test(timeout = TIMEOUT)
    public void m04addItem() {
        MoviesAdapter adapter = new MoviesAdapter();
        adapter.mMovies.addAll(DummyMovie.ITEMS);
    }

    @Test(timeout = TIMEOUT)
    public void m05ItemCount() {
        MoviesAdapter adapter = new MoviesAdapter();
        adapter.mMovies.add(DummyMovie.ITEMS.get(0));
        assert(adapter.getItemCount() == 1);
    }
}
