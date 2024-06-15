package com.example.grupo_01_tarea_12_ejercicio_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;

public class RegisterFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText codigoEditText = view.findViewById(R.id.et_register_cod);
        EditText nombreEditText = view.findViewById(R.id.et_register_Nombre);
        EditText usernameEditText = view.findViewById(R.id.et_register_username);
        EditText passwordEditText = view.findViewById(R.id.et_register_password);
        EditText confirmPasswordEditText = view.findViewById(R.id.et_register_confirm_password);
        Button registerButton = view.findViewById(R.id.btn_register);

        registerButton.setOnClickListener(v -> {
            String codigoStr = codigoEditText.getText().toString();
            String nombre = nombreEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (TextUtils.isEmpty(codigoStr) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(getActivity(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                int codigo = Integer.parseInt(codigoStr);
                DBHelper dbHelper = new DBHelper(getActivity());
                Cliente newClient = new Cliente(codigo, nombre, username, password);
                dbHelper.insertarCliente(newClient);
                Toast.makeText(getActivity(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireView());
                navController.popBackStack();
            }
        });
    }
}
