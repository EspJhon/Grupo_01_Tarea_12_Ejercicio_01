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
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;

public class ArticuloFragment extends Fragment {

    EditText edt_codigo_articulo, edt_nombre_articulo, edt_stock;
    Button btn_registrar_articulo;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ArticuloFragment() {
        // Required empty public constructor
    }

    public static ArticuloFragment newInstance(String param1, String param2) {
        ArticuloFragment fragment = new ArticuloFragment();
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
        return inflater.inflate(R.layout.fragment_articulo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edt_codigo_articulo = view.findViewById(R.id.edt_codigo_articulo);
        edt_nombre_articulo = view.findViewById(R.id.edt_nombre_articulo);
        edt_stock = view.findViewById(R.id.edt_stock);
        btn_registrar_articulo = view.findViewById(R.id.btn_registrar_articulo);
        btn_registrar_articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = edt_codigo_articulo.getText().toString();
                String nombre = edt_nombre_articulo.getText().toString();
                String stock = edt_stock.getText().toString();
                if (TextUtils.isEmpty(codigo) || TextUtils.isEmpty(nombre) || TextUtils.isEmpty(stock)) {
                    Toast.makeText(getActivity(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    int idcodigo = Integer.parseInt(codigo);
                    int istock = Integer.parseInt(stock);
                    DBHelper dbHelper = new DBHelper(getActivity());
                    Articulo articulo = new Articulo(idcodigo, nombre, istock);
                    dbHelper.InsertarArticulos(articulo);
                    edt_codigo_articulo.setText("");
                    edt_nombre_articulo.setText("");
                    edt_stock.setText("");
                    Toast.makeText(getActivity(), "Articulo registrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}