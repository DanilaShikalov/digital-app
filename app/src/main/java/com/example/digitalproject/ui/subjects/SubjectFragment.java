package com.example.digitalproject.ui.subjects;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalproject.TasksActivity;
import com.example.digitalproject.adapters.SubjectAdapter;
import com.example.digitalproject.databinding.FragmentSubjectBinding;
import com.example.digitalproject.globalvar.GlobalVariables;

import java.util.List;

public class SubjectFragment extends Fragment {

    private FragmentSubjectBinding binding;
    private SubjectViewModel subjectViewModel;
    private SubjectAdapter subjectAdapter;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        subjectViewModel =
                new ViewModelProvider(this).get(SubjectViewModel.class);

        binding = FragmentSubjectBinding.inflate(inflater, container, false);
        subjectAdapter = new SubjectAdapter(requireContext());
        binding.subbjectsView.setAdapter(subjectAdapter);
        token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();
        subjectViewModel.setToken(token);
        subjectViewModel.subject.observe(requireActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                strings.forEach(x -> subjectAdapter.add(x));
                subjectAdapter.notifyDataSetChanged();
            }
        });
        binding.subbjectsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = (String) subjectAdapter.getItem(i);
                Intent intent = new Intent(requireContext(), TasksActivity.class);
                intent.putExtra("subject", subject);
                requireContext().startActivity(intent);
            }
        });
        subjectViewModel.getSubjects();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}