package com.example.grupo_01_tarea_12_ejercicio_01.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.databinding.FragmentGalleryBinding;
import com.example.grupo_01_tarea_12_ejercicio_01.db.DBHelper;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Articulo;

import java.util.ArrayList;

public class GalleryFragment extends Fragment  implements View.OnClickListener{

    private FragmentGalleryBinding binding;
    private EditText et_codigo, et_descripcion, et_stock;
    private ListView lv_articulos;
    private DBHelper dbHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dbHelper =new DBHelper(getActivity().getBaseContext());

        root.findViewById(R.id.btn_registrar).setOnClickListener(this::onClick);
        lv_articulos = root.findViewById(R.id.lv_articulos);
        et_codigo = root.findViewById(R.id.et_codigo);
        et_descripcion = root.findViewById(R.id.et_descripcion);
        et_stock = root.findViewById(R.id.et_stock);

        Listar();

        return root;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_registrar){
            int codigo = Integer.parseInt(et_codigo.getText().toString());
            String descripcion = et_descripcion.getText().toString();
            int stock = Integer.parseInt(et_stock.getText().toString());
            Articulo articulo = new Articulo(codigo, descripcion, stock);
            Registrar(articulo);
            Listar();
        }
    }
    private void Listar(){
        ArrayList<Articulo> articulos = dbHelper.get_all_Articulos();
        String[] nombres = new String[articulos.size()];
        for (int i = 0; i < articulos.size(); i++){
            Articulo producto = articulos.get(i);
            nombres[i] = producto.getIdArticulo() + " - " + producto.getDescripcion() + " - " + producto.getStock();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1,
                nombres
        );
        lv_articulos.setAdapter(adapter);
    }
    private void Registrar(Articulo articulo){
        dbHelper.InsertarArticulos(articulo);
        Listar();
        et_stock.setText("");
        et_descripcion.setText("");
        et_codigo.setText("");
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
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}