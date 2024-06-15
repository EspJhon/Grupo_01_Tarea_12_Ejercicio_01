package com.example.grupo_01_tarea_12_ejercicio_01;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegistrarArticuloFragment extends Fragment {

    private LinearLayout hidden_layout_articulo, layotu_agregar_articulo;
    private List<String> nombreArticulos;
    private MaterialButton btn_actualizar_articulo, btn_eliminar_articulo;
    EditText edt_cantidad_articulo;
    TextView tv_stock;
    Spinner sp_articulo;
    DBHelper dbHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RegistrarArticuloFragment() {
        // Required empty public constructor
    }

    public static RegistrarArticuloFragment newInstance(String param1, String param2) {
        RegistrarArticuloFragment fragment = new RegistrarArticuloFragment();
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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_articulo, container, false);

        hidden_layout_articulo = view.findViewById(R.id.hidden_layout_articulo);
        btn_actualizar_articulo = view.findViewById(R.id.btn_actualizar_articulo);
        btn_eliminar_articulo = view.findViewById(R.id.btn_eliminar_articulo);
        layotu_agregar_articulo = view.findViewById(R.id.layotu_agregar_articulo);
        return view;
    }
    private int idArticuloSeleccionado;
    private int idPedido;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());

        ArrayList<Articulo> articulos = dbHelper.get_all_Articulos();
        ArrayList<String> nombreArticulos = new ArrayList<>();
        for (Articulo articulo : articulos) {
            nombreArticulos.add(articulo.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombreArticulos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tv_stock = view.findViewById(R.id.tv_stock);
        edt_cantidad_articulo = view.findViewById(R.id.edt_cantidad_articulo);
        sp_articulo = view.findViewById(R.id.sp_articulo);

        sp_articulo.setAdapter(adapter);
        sp_articulo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Articulo articuloSeleccionado = articulos.get(position);
                idArticuloSeleccionado = articuloSeleccionado.getIdArticulo();
                tv_stock.setText(String.valueOf(articuloSeleccionado.getStock()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Código a ejecutar cuando no se selecciona ningún artículo
            }
        });
        Bundle argument = getArguments();
        if (argument != null) {
            idPedido = argument.getInt("idPedido",0);
        }
        view.findViewById(R.id.btn_agregar_articulo_detalle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stock_actual = edt_cantidad_articulo.getText().toString();
                if (TextUtils.isEmpty(stock_actual)) {
                    Toast.makeText(getActivity(), "Registra la cantidad", Toast.LENGTH_SHORT).show();
                } else {
                    int id_articulo = idArticuloSeleccionado;
                    int stock = Integer.parseInt(edt_cantidad_articulo.getText().toString());
                    Articulo limite = dbHelper.get_Articulos(id_articulo);
                    int max = limite.getStock();
                    if (stock > max) {
                        Toast.makeText(getActivity(), "Limite Superado", Toast.LENGTH_SHORT).show();
                    } else {
                        Detalle detalle = new Detalle(idPedido, id_articulo, stock);
                        int stock_actualizado = max - stock;
                        int codArt = limite.getCodigo();
                        String desc = limite.getDescripcion();
                        if (limite != null) {
                            limite.setCodigo(codArt);
                            limite.setDescripcion(desc);
                            limite.setStock(stock_actualizado);
                            dbHelper.Actualizar_Articulo(limite);
                            limite = null;
                        }
                        dbHelper.Insertar_Detalles(detalle);
                        Toast.makeText(getContext(), "Detalle registrado", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireView());
                        navController.popBackStack();
                    }
                }
            }
        });


        boolean modoEdicion = getArguments() != null && getArguments().getBoolean("modo_edicion", false);
        if (modoEdicion) {
            edt_cantidad_articulo.setText("");
            tv_stock.setText("");

            String C_Stock = getArguments().getString("nombArt");
            int Id_Pedido = getArguments().getInt("idPedido");
            int Id_Articulo = getArguments().getInt("idArticulo");
            int Cantidad_Stock = getArguments().getInt("cantidad");

            int index = nombreArticulos.indexOf(C_Stock);
            sp_articulo.setSelection(index);

            edt_cantidad_articulo.setText(String.valueOf(Cantidad_Stock));

            hidden_layout_articulo.setVisibility(View.VISIBLE);
            layotu_agregar_articulo.setVisibility(View.GONE);

            btn_actualizar_articulo.setOnClickListener(v -> {
                Detalle detalle = dbHelper.get_Detalles(Id_Pedido, Id_Articulo,Cantidad_Stock);
                if (detalle != null) {
                    int Stock_F = Integer.parseInt(edt_cantidad_articulo.getText().toString());
                    Articulo limite = dbHelper.get_Articulos(Id_Articulo);
                    int max = limite.getStock();
                    if (Stock_F > max + Cantidad_Stock) {
                        Toast.makeText(getActivity(), "Limite Superado", Toast.LENGTH_SHORT).show();
                    } else {
                        int stock_actualizado = 0;
                        if (Stock_F > Cantidad_Stock) {
                            int valor = Stock_F - Cantidad_Stock;
                            stock_actualizado = max - valor;
                        } else {
                            int valor = Cantidad_Stock - Stock_F;
                            stock_actualizado = max + valor;
                        }
                        int codArt = limite.getCodigo();
                        String desc = limite.getDescripcion();
                        if (limite != null) {
                            limite.setCodigo(codArt);
                            limite.setDescripcion(desc);
                            limite.setStock(stock_actualizado);
                            dbHelper.Actualizar_Articulo(limite);
                            limite = null;
                        }

                        detalle.setIdPedido(Id_Pedido);
                        detalle.setIdPedido(Id_Pedido);
                        detalle.setCantidad(Stock_F);
                        dbHelper.Actualizar_Detalle(detalle);
                        detalle = null;
                        Toast.makeText(getContext(), "Detalle actualizado", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireView());
                        navController.popBackStack();
                    }
                }else{
                    Toast.makeText(getContext(), "Producto no actualizado", Toast.LENGTH_SHORT).show();
                }
            });

            btn_eliminar_articulo.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.action_registrarPedidoFragment_to_registrarArticuloFragment);
            });
        } else {
            hidden_layout_articulo.setVisibility(View.GONE);
        }

    }
}