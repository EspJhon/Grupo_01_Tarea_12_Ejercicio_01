package com.example.grupo_01_tarea_12_ejercicio_01.ui.slideshow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.RegisterFragment;
import com.example.grupo_01_tarea_12_ejercicio_01.adapter.PedidoAdapter;
import com.example.grupo_01_tarea_12_ejercicio_01.databinding.FragmentSlideshowBinding;
import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    private FragmentSlideshowBinding binding;
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private PedidoAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper = new DBHelper(getActivity().getBaseContext());

        root.findViewById(R.id.btn_rgitrar_pedido).setOnClickListener(this::onClick);
        recyclerView = root.findViewById(R.id.recycler_view_pedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listarPedidos();
        return root;
    }

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
            // Handle back pressed event
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rgitrar_pedido) {
            NavController navController = Navigation.findNavController(v);
            navController.navigate(R.id.action_nav_slideshow_to_registrarPedidoFragment);
        }
    }

    public void listarPedidos() {
        ArrayList<Pedido> pedidos = dbHelper.get_all_Pedidos();
        adapter = new PedidoAdapter(getActivity(), pedidos, new PedidoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(Pedido pedido) {
                showEditDeleteDialog(pedido);
            }
        });
        recyclerView.setAdapter(adapter);
    }
    private void showEditDeleteDialog(Pedido pedido) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccione una opción")
                .setItems(new CharSequence[]{"Editar", "Eliminar"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Editar
                                editarPedido(pedido);
                                break;
                            case 1: // Eliminar
                                eliminarPedido(pedido);
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void editarPedido(Pedido pedido) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_pedido", pedido.getIdPedido());
        bundle.putInt("codigo", pedido.getCodigo());
        bundle.putInt("id_cliente", pedido.getIdCliente());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEnvioStr = dateFormat.format(pedido.getFechaEnvio());
        bundle.putString("fechaenvio", fechaEnvioStr);
        bundle.putInt("direccion", pedido.getIdDireccion());
        bundle.putBoolean("modo_edicion", true);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_slideshow_to_registrarPedidoFragment, bundle);
    }

    private void eliminarPedido(Pedido pedido) {
        dbHelper.Eliminar_Pedido(pedido);
        adapter.getPedidos().remove(pedido);
        adapter.notifyDataSetChanged();
        listarPedidos(); // Refresh the list
        Toast.makeText(getContext(), "Pedido eliminado", Toast.LENGTH_SHORT).show();
    }
}