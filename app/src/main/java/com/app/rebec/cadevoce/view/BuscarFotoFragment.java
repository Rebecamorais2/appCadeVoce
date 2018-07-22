package com.app.rebec.cadevoce.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.rebec.cadevoce.R;
import com.app.rebec.cadevoce.presenter.PageController;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuscarFotoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuscarFotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarFotoFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscar_foto, container, false);
    }
}
