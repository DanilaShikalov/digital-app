package com.example.digitalproject.ui.documents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalproject.adapters.DocumentAdapter;
import com.example.digitalproject.databinding.FragmentDocumentBinding;
import com.example.digitalproject.globalvar.GlobalVariables;
import com.example.digitalproject.models.DocumentModel;

import java.util.List;

public class DocumentFragment extends Fragment {

    private FragmentDocumentBinding binding;
    private DocumentAdapter documentAdapter;
    private String token;
    private DocumentViewModel documentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentViewModel =
                new ViewModelProvider(this).get(DocumentViewModel.class);

        binding = FragmentDocumentBinding.inflate(inflater, container, false);
        documentAdapter = new DocumentAdapter(requireContext());
        binding.documentsView.setAdapter(documentAdapter);
        token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();
        documentViewModel.setToken(token);
        documentViewModel.documents.observe(requireActivity(), new Observer<List<DocumentModel>>() {
            @Override
            public void onChanged(List<DocumentModel> documentModels) {
                documentAdapter.clear();
                documentAdapter.notifyDataSetChanged();
                documentModels.forEach(x -> documentAdapter.add(x));
                documentAdapter.notifyDataSetChanged();
            }
        });
        documentViewModel.getDocuments();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}