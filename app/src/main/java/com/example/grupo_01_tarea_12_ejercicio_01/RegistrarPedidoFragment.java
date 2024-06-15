package com.example.grupo_01_tarea_12_ejercicio_01;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo_01_tarea_12_ejercicio_01.adapter.DetalleAdapter;
import com.example.grupo_01_tarea_12_ejercicio_01.adapter.PedidoAdapter;
import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Cliente;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Detalle;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;
import com.example.grupo_01_tarea_12_ejercicio_01.ui.slideshow.SlideshowFragment;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class RegistrarPedidoFragment extends Fragment {

    EditText edt_codigo_pedido, edt_codigo_cliente, edt_fecha_envio, sp_codigo_direccion;
    ListView lv_articulos;

    private List<Detalle> detalleList;
    private LinearLayout hiddenLayout, layotu_agregar_pedido;
    private MaterialButton btnActualizar, btn_agregar_articulo;
    DBHelper dbHelper;
    private DetalleAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RegistrarPedidoFragment() {
        // Required empty public constructor
    }

    public static RegistrarPedidoFragment newInstance(String param1, String param2) {
        RegistrarPedidoFragment fragment = new RegistrarPedidoFragment();
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
        View view = inflater.inflate(R.layout.fragment_registrar_pedido, container, false);

        hiddenLayout = view.findViewById(R.id.hidden_layout);
        layotu_agregar_pedido = view.findViewById(R.id.layotu_agregar_pedido);
        btnActualizar = view.findViewById(R.id.btn_actualizar);
        btn_agregar_articulo = view.findViewById(R.id.btn_agregar_articulo);
        lv_articulos = view.findViewById(R.id.lv_articulos);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHelper = new DBHelper(getActivity());
        ArrayList<Cliente> clientes = dbHelper.get_all_Clientes();
        Map<String, Integer> nombreIdMap = new HashMap<>();
        for (Cliente cliente : clientes) {
            nombreIdMap.put(cliente.getNombre(), cliente.getIdCliente());

        }
        Map<Integer, String> bnombre = new HashMap<>();
        for (Cliente cliente : clientes) {
            bnombre.put(cliente.getIdCliente(), cliente.getNombre());
        }
        Cliente cliente_l = clientes.get(0);

        int rnd = generateRandomNumber(10000000, 99999999);

        edt_codigo_pedido = view.findViewById(R.id.edt_codigo_pedido);
        edt_codigo_cliente = view.findViewById(R.id.edt_codigo_cliente);
        edt_fecha_envio = view.findViewById(R.id.edt_fecha_envio);
        sp_codigo_direccion = view.findViewById(R.id.sp_codigo_direccion);

        edt_codigo_pedido.setText(String.valueOf(rnd));
        edt_codigo_cliente.setText(String.valueOf(cliente_l.getNombre()));

        view.findViewById(R.id.btn_registrar_pedido).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoPedidoStr = edt_codigo_pedido.getText().toString();
                String codigoClienteStr = edt_codigo_cliente.getText().toString();
                String fechaEnvioStr = edt_fecha_envio.getText().toString();
                try {
                    if (codigoPedidoStr.isEmpty() || codigoClienteStr.isEmpty() || fechaEnvioStr.isEmpty()) {
                        Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    } else {
                        int codigo = Integer.parseInt(codigoPedidoStr);
                        int codigo_cliente = nombreIdMap.get(codigoClienteStr);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date fecha_envio = dateFormat.parse(fechaEnvioStr);

                        int codigo_direccion = Integer.parseInt(sp_codigo_direccion.getText().toString());

                        Pedido pedido = new Pedido(codigo, fecha_envio, codigo_cliente, codigo_direccion);
                        dbHelper.Insertar_Pedidos(pedido);
                        Toast.makeText(getContext(), "Pedido registrado correctamente", Toast.LENGTH_SHORT).show();
                        NavController navController = Navigation.findNavController(requireView());
                        navController.popBackStack();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Por favor, ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    Toast.makeText(getContext(), "Por favor, ingrese una fecha válida en el formato yyyy-MM-dd", Toast.LENGTH_SHORT).show();
                }
            }
        });

        boolean modoEdicion = getArguments() != null && getArguments().getBoolean("modo_edicion", false);
        if (modoEdicion) {
            edt_codigo_cliente.setText("");
            edt_codigo_pedido.setText("");
            edt_fecha_envio.setText("");
            sp_codigo_direccion.setText("");

            int idCodigo = getArguments().getInt("id_pedido");
            int codigo_edi = getArguments().getInt("codigo");
            int id_cliente_edi = getArguments().getInt("id_cliente");
            String nombre_cliente = bnombre.get(id_cliente_edi);

            String fecha = getArguments().getString("fechaenvio");
            int direccion = getArguments().getInt("direccion");

            edt_codigo_pedido.setText(String.valueOf(codigo_edi));
            edt_codigo_cliente.setText(String.valueOf(nombre_cliente));
            edt_fecha_envio.setText(String.valueOf(fecha));
            sp_codigo_direccion.setText(String.valueOf(direccion));

            Listar(idCodigo);
            hiddenLayout.setVisibility(View.VISIBLE);
            layotu_agregar_pedido.setVisibility(View.GONE);

            btnActualizar.setOnClickListener(v -> {
                Pedido pedido = dbHelper.getPedidos(codigo_edi);
                if (pedido != null){
                    try {
                        String fechaEnvioStr = edt_fecha_envio.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        Date fecha_envio = dateFormat.parse(fechaEnvioStr);
                        int codigo_direccion = Integer.parseInt(sp_codigo_direccion.getText().toString());

                        pedido.setCodigo(codigo_edi);
                        pedido.setIdCliente(id_cliente_edi);
                        pedido.setFechaEnvio(fecha_envio);
                        pedido.setIdDireccion(codigo_direccion);
                        dbHelper.Actualizar_Pedido(pedido);
                    }  catch (ParseException e) {
                        Toast.makeText(getContext(), "Por favor, ingrese una fecha válida en el formato yyyy-MM-dd", Toast.LENGTH_SHORT).show();
                    }
                    pedido = null;
                    Toast.makeText(getContext(), "Producto actualizado", Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(requireView());
                    navController.popBackStack();
                }else{
                    Toast.makeText(getContext(), "Producto no actualizado", Toast.LENGTH_SHORT).show();
                }
            });

            btn_agregar_articulo.setOnClickListener(v -> {
                NavController navController = Navigation.findNavController(requireView());
                Bundle bundle = new Bundle();
                bundle.putInt("idPedido", idCodigo);
                navController.navigate(R.id.action_registrarPedidoFragment_to_registrarArticuloFragment, bundle);
            });
        } else {
            hiddenLayout.setVisibility(View.GONE);
        }
        lv_articulos.setOnItemLongClickListener((parent, view1, position, id) -> {
            DetalleAdapter adapter = (DetalleAdapter) lv_articulos.getAdapter();
            Detalle detalle = (Detalle) adapter.getItem(position);
            if (detalle != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Opciones")
                        .setItems(new CharSequence[]{"Editar", "Eliminar"}, (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    editarDetalle(detalle);
                                    break;
                                case 1:
                                    eliminarDetalle(detalle);
                                    break;
                            }
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
            return true;
        });

    }
    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
    private void Listar(int codigoPedido) {
        ArrayList<Detalle> detalles = dbHelper.get_all_Detalles(codigoPedido);
        if (lv_articulos.getAdapter() instanceof DetalleAdapter) {
            DetalleAdapter adapter = (DetalleAdapter) lv_articulos.getAdapter();
            adapter.setDetalles(detalles); // Actualiza la lista de detalles en el adaptador
        } else {
            adapter = new DetalleAdapter(getContext(), detalles);
            lv_articulos.setAdapter(adapter);
        }
    }

    private void eliminarDetalle(Detalle detalle) {
        dbHelper.Eliminar_Detalle(detalle);
        if (lv_articulos.getAdapter() instanceof DetalleAdapter) {
            DetalleAdapter detalleAdapter = (DetalleAdapter) lv_articulos.getAdapter();
            List<Detalle> detalles = detalleAdapter.getDetalles();
            detalles.remove(detalle);
            detalleAdapter.setDetalles(detalles);
        }
        Toast.makeText(getContext(), "Detalle eliminado", Toast.LENGTH_SHORT).show();
    }

    private void editarDetalle(Detalle detalle) {
        Articulo articulo = dbHelper.get_Articulos(detalle.getIdArticulo());
        Bundle bundle = new Bundle();
        bundle.putString("nombArt", articulo.getDescripcion());
        bundle.putInt("idArticulo", detalle.getIdArticulo());
        bundle.putInt("idPedido", detalle.getIdPedido());
        bundle.putInt("cantidad", detalle.getCantidad());
        bundle.putBoolean("modo_edicion", true);

        Navigation.findNavController(getView()).navigate(R.id.action_registrarPedidoFragment_to_registrarArticuloFragment, bundle);
    }

}