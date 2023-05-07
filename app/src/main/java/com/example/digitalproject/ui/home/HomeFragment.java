package com.example.digitalproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalproject.databinding.FragmentHomeBinding;
import com.example.digitalproject.globalvar.GlobalVariables;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        token = ((GlobalVariables) requireActivity().getApplication()).getToken_access();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel.setToken(token);
        homeViewModel.name.observe(requireActivity(), s -> binding.textName.setText(s));
        homeViewModel.surname.observe(requireActivity(), s -> binding.textSurname.setText(s));
        homeViewModel.phone.observe(requireActivity(), s -> binding.textPhone.setText(s));
        homeViewModel.email.observe(requireActivity(), s -> binding.textEmail.setText(s));
        homeViewModel.salary.observe(requireActivity(), s -> binding.textSalary.setText(s));
        homeViewModel.position.observe(requireActivity(), s -> binding.textPosition.setText(s));
        homeViewModel.rating.observe(requireActivity(), s -> binding.textRating.setText(s));
        homeViewModel.getInfo();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}