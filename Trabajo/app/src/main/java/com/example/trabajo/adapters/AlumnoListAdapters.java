package com.example.trabajo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.trabajo.R;
import com.example.trabajo.Utilidades;
import com.example.trabajo.models.Alumno;

import java.util.List;

public class AlumnoListAdapters extends RecyclerView.Adapter<AlumnoListAdapters.ViewHolder>{
    List<Alumno> ShowList;
    Context context;
    int position;
    private OnItemClickListener itemClickListener;


    public AlumnoListAdapters(List<Alumno> showList, OnItemClickListener itemClickListener) {
        ShowList = showList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AlumnoListAdapters.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_lista_alumno,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        context = viewGroup.getContext();
        position=i;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoListAdapters.ViewHolder viewHolder, final int i) {
        viewHolder.bind(ShowList.get(i),itemClickListener);
        position=i;
    }

    @Override
    public int getItemCount() {
        return ShowList.size();
    }



    public void updateList(List<Alumno> data) {
        ShowList = data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textid;
        TextView textRut;
        TextView textNombre;
        TextView textEstado;
        CardView cv;

        public ViewHolder(View itemView)
        {
            super(itemView);

            textid = (TextView)itemView.findViewById(R.id.textid);
            textRut = (TextView)itemView.findViewById(R.id.textRut);
            textNombre = (TextView)itemView.findViewById(R.id.textNombre);
            textEstado = (TextView) itemView.findViewById(R.id.textEstado);


            cv = (CardView)itemView.findViewById(R.id.cardviewItemA);
        }

        public void bind(final Alumno alumno, final OnItemClickListener listener) {

            textNombre.setText(Utilidades.primeraMayuscula(alumno.getNombre()));
            textRut.setText("" + Utilidades.formatearRut(alumno.getRut()));
            textid.setText(Integer.valueOf(alumno.getId()+1)+".-");
            textEstado.setText(alumno.getEstado());


        }
    }

    public interface OnItemClickListener {
        void OnItemClick(Alumno alumno, int position);
        void OnDeleteClick(Alumno alumno, int position);
    }
}