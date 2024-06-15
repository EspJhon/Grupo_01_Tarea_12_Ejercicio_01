package com.example.grupo_01_tarea_12_ejercicio_01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo_01_tarea_12_ejercicio_01.R;
import com.example.grupo_01_tarea_12_ejercicio_01.modelo.Pedido;

import java.util.ArrayList;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Pedido> pedidos;
    private OnItemLongClickListener onItemLongClickListener;

    public PedidoAdapter(Context context, ArrayList<Pedido> pedidos, OnItemLongClickListener onItemLongClickListener) {
        this.context = context;
        this.pedidos = pedidos;
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Pedido pedido);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pedido pedido = pedidos.get(position);
        holder.tvPedidoId.setText(String.valueOf(pedido.getIdPedido()));
        holder.tvClienteId.setText(String.valueOf(pedido.getIdCliente()));
        holder.tvFechaEnvio.setText(pedido.getFechaEnvio().toString());
        holder.tvDireccionId.setText(String.valueOf(pedido.getIdDireccion()));

        holder.itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(pedido);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPedidoId, tvClienteId, tvFechaEnvio, tvDireccionId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPedidoId = itemView.findViewById(R.id.tvPedidoId);
            tvClienteId = itemView.findViewById(R.id.tvClienteId);
            tvFechaEnvio = itemView.findViewById(R.id.tvFechaEnvio);
            tvDireccionId = itemView.findViewById(R.id.tvDireccionId);
        }
    }
}