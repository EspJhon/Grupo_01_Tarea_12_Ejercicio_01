package com.example.grupo_01_tarea_12_ejercicio_01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Detalle;

import java.util.List;

public class DetalleAdapter extends BaseAdapter {

    private Context context;
    private List<Detalle> detalleList;

    public DetalleAdapter(Context context, List<Detalle> detalleList) {
        this.context = context;
        this.detalleList = detalleList;
    }

    @Override
    public int getCount() {
        return detalleList.size();
    }

    @Override
    public Object getItem(int position) {
        return detalleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detalle, parent, false);
            holder = new ViewHolder();
            holder.tvIdPedido = convertView.findViewById(R.id.tvIdPedido);
            holder.tvIdArticulo = convertView.findViewById(R.id.tvIdArticulo);
            holder.tvCantidad = convertView.findViewById(R.id.tvCantidad);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Detalle detalle = detalleList.get(position);

        holder.tvIdPedido.setText(String.valueOf(detalle.getIdPedido()));
        holder.tvIdArticulo.setText(String.valueOf(detalle.getIdArticulo()));
        holder.tvCantidad.setText(String.valueOf(detalle.getCantidad()));

        return convertView;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalleList = detalles;
        notifyDataSetChanged();
    }

    public List<Detalle> getDetalles() {
        return detalleList;
    }

    private static class ViewHolder {
        TextView tvIdPedido;
        TextView tvIdArticulo;
        TextView tvCantidad;
    }
}