package com.mahasiswa.nilaimahasiswa;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private DatabaseHelper dbHelper;
    private View tvEmpty;
    private Button btnDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        Button btnBack = findViewById(R.id.btnBack);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new StudentAdapter(studentList, this::onDeleteStudent);
        recyclerView.setAdapter(adapter);

        btnDeleteAll.setOnClickListener(v -> confirmDeleteAll());
        btnBack.setOnClickListener(v -> finish());

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        studentList.clear();
        studentList.addAll(dbHelper.getAllStudents());
        adapter.notifyDataSetChanged();

        if (studentList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnDeleteAll.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btnDeleteAll.setVisibility(View.VISIBLE);
        }
    }

    private void onDeleteStudent(Student student, int position) {
        new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("Hapus Data")
                .setMessage("Hapus data " + student.getNama() + "?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    dbHelper.deleteStudent(student.getId());
                    loadData();
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void confirmDeleteAll() {
        new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("Hapus Semua Data")
                .setMessage("Apakah Anda yakin ingin menghapus semua data?")
                .setPositiveButton("Hapus Semua", (dialog, which) -> {
                    dbHelper.deleteAllStudents();
                    loadData();
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
