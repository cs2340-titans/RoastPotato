package com.example.wenqixian.myfirstapp.fragments;

import com.example.wenqixian.myfirstapp.fragments.ListQueryFragment;
import com.example.wenqixian.myfirstapp.services.RottenTomatoService;

/**
 * Created by andy on 2/19/16.
 */
public class SearchFragment
        extends ListQueryFragment {
    public SearchFragment() {
    }

    public static ListQueryFragment newInstance(String query) {
        return ListQueryFragment.newInstance(RottenTomatoService.getInstance().getSearchQuery(query));
    }
}
