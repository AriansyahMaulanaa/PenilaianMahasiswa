package com.mahasiswa.nilaimahasiswa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public interface OnDeleteClickListener {
        void onDeleteClick(Student student, int position);
    }

    private final List<Student> students;
    private final OnDeleteClickListener deleteListener;

    public StudentAdapter(List<Student> students, OnDeleteClickListener deleteListener) {
        this.students = students;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.tvNama.setText(student.getNama());
        holder.tvNim.setText(student.getNim());
        holder.tvMatkul.setText(student.getMataKuliah());
        holder.tvNilaiAkhir.setText(String.format("%.2f", student.getNilaiAkhir()));
        holder.tvGrade.setText(student.getGrade());
        holder.tvKeterangan.setText(student.getKeterangan());

        // Detail values
        holder.tvDetailKehadiran.setText(String.format("%.1f", student.getKehadiran()));
        holder.tvDetailTugas.setText(String.format("%.1f", student.getTugas()));
        holder.tvDetailUts.setText(String.format("%.1f", student.getUts()));
        holder.tvDetailUas.setText(String.format("%.1f", student.getUas()));

        // Set grade badge color
        int color;
        switch (student.getGrade()) {
            case "A": color = holder.itemView.getContext().getResources().getColor(R.color.grade_a); break;
            case "B": color = holder.itemView.getContext().getResources().getColor(R.color.grade_b); break;
            case "C": color = holder.itemView.getContext().getResources().getColor(R.color.grade_c); break;
            case "D": color = holder.itemView.getContext().getResources().getColor(R.color.grade_d); break;
            default:  color = holder.itemView.getContext().getResources().getColor(R.color.grade_e); break;
        }
        holder.cardGradeBadge.setCardBackgroundColor(color);

        holder.btnDelete.setOnClickListener(v ->
                deleteListener.onDeleteClick(student, holder.getAdapterPosition()));

        // Toggle detail visibility
        holder.itemView.setOnClickListener(v -> {
            boolean isVisible = holder.layoutDetail.getVisibility() == View.VISIBLE;
            holder.layoutDetail.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNim, tvMatkul, tvNilaiAkhir, tvGrade, tvKeterangan;
        TextView tvDetailKehadiran, tvDetailTugas, tvDetailUts, tvDetailUas;
        ImageButton btnDelete;
        CardView cardGradeBadge;
        View layoutDetail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvItemNama);
            tvNim = itemView.findViewById(R.id.tvItemNim);
            tvMatkul = itemView.findViewById(R.id.tvItemMatkul);
            tvNilaiAkhir = itemView.findViewById(R.id.tvItemNilaiAkhir);
            tvGrade = itemView.findViewById(R.id.tvItemGrade);
            tvKeterangan = itemView.findViewById(R.id.tvItemKeterangan);
            tvDetailKehadiran = itemView.findViewById(R.id.tvDetailKehadiran);
            tvDetailTugas = itemView.findViewById(R.id.tvDetailTugas);
            tvDetailUts = itemView.findViewById(R.id.tvDetailUts);
            tvDetailUas = itemView.findViewById(R.id.tvDetailUas);
            btnDelete = itemView.findViewById(R.id.btnItemDelete);
            cardGradeBadge = itemView.findViewById(R.id.cardGradeBadge);
            layoutDetail = itemView.findViewById(R.id.layoutDetail);
        }
    }
}
