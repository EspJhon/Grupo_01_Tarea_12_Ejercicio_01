package com.example.grupo_01_tarea_12_ejercicio_01;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;

public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText usernameEditText = view.findViewById(R.id.et_username);
        EditText passwordEditText = view.findViewById(R.id.et_password);
        Button loginButton = view.findViewById(R.id.btn_login);
        Button registerButton = view.findViewById(R.id.btn_registrar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateInput(username, password)) {
                    if (authenticateUser(username, password)) {
                        NavController navController = Navigation.findNavController(v);
                        navController.navigate(R.id.action_loginFragment_to_homeFragment);
                    } else {
                        Toast.makeText(getActivity(), "Usuario o Contraseña Inválidos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Por favor, ingrese los datos solicitados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        return view;
    }

    private boolean validateInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private boolean authenticateUser(String username, String password) {
        DBHelper dbHelper = new DBHelper(getActivity());
        return dbHelper.authenticateUser(username, password);
    }


    //MÉTODOS PARA BLOQUEAR RETROCESO A LOGIN
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        onBackPressedCallback.remove();
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
        }
    };
}
