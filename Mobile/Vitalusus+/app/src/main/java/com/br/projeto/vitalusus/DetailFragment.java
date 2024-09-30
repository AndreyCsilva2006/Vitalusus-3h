package com.br.projeto.vitalusus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {

    public static DetailFragment newInstance(int canalId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt("CANAL_ID", canalId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int canalId = getArguments().getInt("CANAL_ID");
            // Use o canalId para carregar os dados necessários
        }
    }

    // Resto do código do fragmento...
}
