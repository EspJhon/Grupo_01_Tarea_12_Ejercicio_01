package com.example.grupo_01_tarea_12_ejercicio_01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.db.DBAdapter;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Direccion;

public class DireccionFragment extends Fragment {

    private EditText et_codigoD, et_numeroD, et_calleD, et_comunaD, et_ciudadD;
    private DBAdapter dbAdapter;
    private Cliente objCliente;
    private Direccion objDireccion;
    private int opcion;

    private static final String ARG_PARAM1 = "cliente";
    private static final String ARG_PARAM2 = "direccion";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opcion = getArguments().getInt("i", 0);
            objCliente = (Cliente) getArguments().getSerializable(ARG_PARAM1);
            objDireccion = (Direccion) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_direccion, container, false);
        dbAdapter = new DBAdapter(getActivity());
        et_codigoD = view.findViewById(R.id.et_codigoD);
        et_numeroD = view.findViewById(R.id.et_numeroD);
        et_calleD = view.findViewById(R.id.et_calleD);
        et_comunaD = view.findViewById(R.id.et_comunaD);
        et_ciudadD = view.findViewById(R.id.et_ciudadD);

        if (objDireccion != null) {
            et_codigoD.setText(String.valueOf(objDireccion.getCodigo()));
            et_numeroD.setText(String.valueOf(objDireccion.getNumero()));
            et_calleD.setText(objDireccion.getCalle());
            et_comunaD.setText(objDireccion.getComuna());
            et_ciudadD.setText(objDireccion.getCiudad());
        }

        view.findViewById(R.id.btn_registrarD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarActualizarDireccion();
            }
        });

        view.findViewById(R.id.btn_eliminarD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarEliminarDireccion();
            }
        });

        return view;
    }

    private void confirmarEliminarDireccion() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar esta dirección?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarDireccion();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void eliminarDireccion() {
        dbAdapter.open();
        dbAdapter.Eliminar_Direccion(objDireccion);
        dbAdapter.close();
        Toast.makeText(getActivity(), "Dirección eliminada", Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getView());
        navController.popBackStack();
    }

    private void registrarActualizarDireccion() {
        Direccion direccion = new Direccion(
                Integer.parseInt(et_codigoD.getText().toString()),
                Integer.parseInt(et_numeroD.getText().toString()),
                et_calleD.getText().toString(),
                et_comunaD.getText().toString(),
                et_ciudadD.getText().toString(),
                objCliente.getIdCliente()
        );

        dbAdapter.open();
        if (opcion == 1) {
            long id = dbAdapter.insertarDireccion(direccion);
            if (id != -1) {
                Toast.makeText(getActivity(), "Dirección registrada", Toast.LENGTH_SHORT).show();
                NavController navController = Navigation.findNavController(getView());
                navController.popBackStack();
            } else {
                Toast.makeText(getActivity(), "Error al registrar dirección", Toast.LENGTH_SHORT).show();
            }
        } else if (opcion == 2) {
            direccion.setIdDireccion(objDireccion.getIdDireccion());
            dbAdapter.Actualizar_Direccion(direccion);
            Toast.makeText(getActivity(), "Dirección actualizada", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(getView());
            navController.popBackStack();
        }
        dbAdapter.close();
    }
}
