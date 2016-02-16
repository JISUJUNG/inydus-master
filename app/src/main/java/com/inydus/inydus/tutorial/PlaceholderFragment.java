package com.inydus.inydus.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.inydus.inydus.R;

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);
        ImageView imageView = (ImageView)rootView.findViewById(R.id.imgTutorial);
        switch (getArguments().getInt(ARG_SECTION_NUMBER)){
            case 0 :
                imageView.setImageResource(R.drawable.tutorial_test1);
                break;
            case 1 :
                imageView.setImageResource(R.drawable.tutorial_test2);
                break;
            case 2 :
                imageView.setImageResource(R.drawable.tutorial_test3);
                break;
        }

        return rootView;
    }
}
