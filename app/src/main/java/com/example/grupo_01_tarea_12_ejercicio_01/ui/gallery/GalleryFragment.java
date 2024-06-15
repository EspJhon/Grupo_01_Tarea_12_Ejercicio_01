package com.example.grupo_01_tarea_12_ejercicio_01.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.grupo_01_tarea_12_ejercicio_01.MyApp;
import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.databinding.FragmentGalleryBinding;
import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Direccion;

import java.util.ArrayList;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private FragmentGalleryBinding binding;
    private Cliente objcliente;
    private EditText et_nombre, et_usuario, et_password;
    private ArrayList<Direccion> objDirecciones = new ArrayList<>();
    private TextView et_direccion1, et_direccion2, et_direccion3;
    private DBHelper dbHelper;

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity());
        String Nombre = MyApp.getUsuario_codigo();
        String password = MyApp.getUsuario_passwoor();
        objcliente = dbHelper.objclientevalidar(Nombre, password);

        et_nombre = root.findViewById(R.id.et_nombreP);
        et_usuario = root.findViewById(R.id.et_UsuarioP);
        et_password = root.findViewById(R.id.et_passwordP);
        et_direccion1 = root.findViewById(R.id.et_direccion1);
        et_direccion2 = root.findViewById(R.id.et_direccion2);
        et_direccion3 = root.findViewById(R.id.et_direccion3);

        et_nombre.setText(objcliente.getNombre()+"");
        et_usuario.setText(objcliente.getUsername()+"");
        et_password.setText(objcliente.getPassword()+"");

        root.findViewById(R.id.btn_direccion1).setOnClickListener(this);
        root.findViewById(R.id.btn_direccion2).setOnClickListener(this);
        root.findViewById(R.id.btn_direccion3).setOnClickListener(this);
        root.findViewById(R.id.btn_actualizarC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cliente objclientes = new Cliente(objcliente.getCodigo(),et_nombre.getText().toString(),et_usuario.getText().toString(),et_password.getText().toString());
                objclientes.setIdCliente(objcliente.getIdCliente());
                dbHelper.ActualizarCliente(objclientes);

            }
        });

        Listar();

        return root;
    }

    @Override
    public void onClick(View v) {
        NavController navController = Navigation.findNavController(v);
        Bundle bundle = new Bundle();
        int opcion = 0;

        Direccion direccionSeleccionada = null;

        if (v.getId() == R.id.btn_direccion1) {
            if (et_direccion1.getText().toString().equals("Direccion 1")) {
                opcion = 1;
                direccionSeleccionada = obtenerDireccion(0);
            } else {
                opcion = 2;
                direccionSeleccionada = obtenerDireccion(0);
            }
        } else if (v.getId() == R.id.btn_direccion2) {
            if (et_direccion2.getText().toString().equals("Direccion 2")) {
                opcion = 1;
                direccionSeleccionada = obtenerDireccion(1);
            } else {
                opcion = 2;
                direccionSeleccionada = obtenerDireccion(1);
            }
        } else if (v.getId() == R.id.btn_direccion3) {
            if (et_direccion3.getText().toString().equals("Direccion 3")) {
                opcion = 1;
                direccionSeleccionada = obtenerDireccion(2);
            } else {
                opcion = 2;
                direccionSeleccionada = obtenerDireccion(2);
            }
        }

        bundle.putInt("i", opcion);
        bundle.putSerializable("cliente", objcliente);
        bundle.putSerializable("direccion", direccionSeleccionada);

        navController.navigate(R.id.direccionFragment, bundle);
    }

    private Direccion obtenerDireccion(int index) {
        if (objDirecciones.size() > index) {
            return objDirecciones.get(index);
        } else {
            return null;
        }
    }

    private void Listar() {
        objDirecciones = buscar_Direccion();

        if (!objDirecciones.isEmpty()) {
            et_direccion1.setText(formatoDireccion(objDirecciones, 0));
            if (objDirecciones.size() > 1) {
                et_direccion2.setText(formatoDireccion(objDirecciones, 1));
            }
            if (objDirecciones.size() > 2) {
                et_direccion3.setText(formatoDireccion(objDirecciones, 2));
            }
        }
    }

    private ArrayList<Direccion> buscar_Direccion() {
        ArrayList<Direccion> direcciones = dbHelper.get_all_Direcciones();
        ArrayList<Direccion> direccionesFiltradas = new ArrayList<>();
        for (Direccion direccion : direcciones) {
            if (direccion.getIdCliente() == objcliente.getIdCliente()) {
                direccionesFiltradas.add(direccion);
            }
        }
        return direccionesFiltradas;
    }

    private String formatoDireccion(ArrayList<Direccion> direcciones, int index) {
        Direccion direccion = direcciones.get(index);
        return "Código: " + direccion.getCodigo() + "  ||  " + direccion.getCalle() + " - " + direccion.getComuna() + " - " + direccion.getCiudad();
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressedCallback.setEnabled(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        onBackPressedCallback.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onBackPressedCallback.remove();
        binding = null;
    }
}
