package com.mahasiswa.nilaimahasiswa;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etNama, etNim, etMataKuliah;
    private TextInputEditText etKehadiran, etTugas, etUts, etUas;
    private Button btnHitung, btnReset;
    private ImageButton btnHistory;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupListeners();
    }

    private void initViews() {
        etNama = findViewById(R.id.etNama);
        etNim = findViewById(R.id.etNim);
        etMataKuliah = findViewById(R.id.etMataKuliah);
        etKehadiran = findViewById(R.id.etKehadiran);
        etTugas = findViewById(R.id.etTugas);
        etUts = findViewById(R.id.etUts);
        etUas = findViewById(R.id.etUas);
        btnHitung = findViewById(R.id.btnHitung);
        btnReset = findViewById(R.id.btnReset);
        btnHistory = findViewById(R.id.btnHistory);
    }

    private void setupListeners() {
        btnHitung.setOnClickListener(v -> hitungNilai());
        btnReset.setOnClickListener(v -> resetForm());
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateInput() {
        boolean valid = true;

        if (getText(etNama).isEmpty()) {
            etNama.setError("Nama harus diisi");
            valid = false;
        }
        if (getText(etNim).isEmpty()) {
            etNim.setError("NIM harus diisi");
            valid = false;
        }
        if (getText(etMataKuliah).isEmpty()) {
            etMataKuliah.setError("Mata Kuliah harus diisi");
            valid = false;
        }

        valid = validateNilai(etKehadiran, "Kehadiran") && valid;
        valid = validateNilai(etTugas, "Tugas") && valid;
        valid = validateNilai(etUts, "UTS") && valid;
        valid = validateNilai(etUas, "UAS") && valid;

        return valid;
    }

    private boolean validateNilai(TextInputEditText editText, String fieldName) {
        String text = getText(editText);
        if (text.isEmpty()) {
            editText.setError(fieldName + " harus diisi");
            return false;
        }
        try {
            double value = Double.parseDouble(text);
            if (value < 0 || value > 100) {
                editText.setError(fieldName + " harus antara 0-100");
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError(fieldName + " harus berupa angka");
            return false;
        }
        return true;
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() != null ? editText.getText().toString().trim() : "";
    }

    private void hitungNilai() {
        if (!validateInput()) {
            Toast.makeText(this, "Mohon lengkapi semua data dengan benar", Toast.LENGTH_SHORT).show();
            return;
        }

        String nama = getText(etNama);
        String nim = getText(etNim);
        String mataKuliah = getText(etMataKuliah);
        double kehadiran = Double.parseDouble(getText(etKehadiran));
        double tugas = Double.parseDouble(getText(etTugas));
        double uts = Double.parseDouble(getText(etUts));
        double uas = Double.parseDouble(getText(etUas));

        Student student = new Student(nama, nim, mataKuliah, kehadiran, tugas, uts, uas);
        dbHelper.insertStudent(student);

        showResultDialog(student);
    }

    private void showResultDialog(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_result, null);

        TextView tvDialogNama = dialogView.findViewById(R.id.tvDialogNama);
        TextView tvDialogNim = dialogView.findViewById(R.id.tvDialogNim);
        TextView tvDialogMatkul = dialogView.findViewById(R.id.tvDialogMatkul);
        TextView tvDialogKehadiran = dialogView.findViewById(R.id.tvDialogKehadiran);
        TextView tvDialogTugas = dialogView.findViewById(R.id.tvDialogTugas);
        TextView tvDialogUts = dialogView.findViewById(R.id.tvDialogUts);
        TextView tvDialogUas = dialogView.findViewById(R.id.tvDialogUas);
        TextView tvDialogNilaiAkhir = dialogView.findViewById(R.id.tvDialogNilaiAkhir);
        TextView tvDialogGrade = dialogView.findViewById(R.id.tvDialogGrade);
        TextView tvDialogKeterangan = dialogView.findViewById(R.id.tvDialogKeterangan);
        CardView cardGrade = dialogView.findViewById(R.id.cardGrade);
        Button btnOk = dialogView.findViewById(R.id.btnDialogOk);

        tvDialogNama.setText(student.getNama());
        tvDialogNim.setText(student.getNim());
        tvDialogMatkul.setText(student.getMataKuliah());
        tvDialogKehadiran.setText(String.format("%.1f", student.getKehadiran()));
        tvDialogTugas.setText(String.format("%.1f", student.getTugas()));
        tvDialogUts.setText(String.format("%.1f", student.getUts()));
        tvDialogUas.setText(String.format("%.1f", student.getUas()));
        tvDialogNilaiAkhir.setText(String.format("%.2f", student.getNilaiAkhir()));
        tvDialogGrade.setText(student.getGrade());
        tvDialogKeterangan.setText(student.getKeterangan());

        // Set grade card color based on grade
        int gradeColor;
        switch (student.getGrade()) {
            case "A": gradeColor = getResources().getColor(R.color.grade_a); break;
            case "B": gradeColor = getResources().getColor(R.color.grade_b); break;
            case "C": gradeColor = getResources().getColor(R.color.grade_c); break;
            case "D": gradeColor = getResources().getColor(R.color.grade_d); break;
            default:  gradeColor = getResources().getColor(R.color.grade_e); break;
        }
        cardGrade.setCardBackgroundColor(gradeColor);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            resetForm();
        });

        dialog.show();
    }

    private void resetForm() {
        etNama.setText("");
        etNim.setText("");
        etMataKuliah.setText("");
        etKehadiran.setText("");
        etTugas.setText("");
        etUts.setText("");
        etUas.setText("");
        etNama.clearFocus();
        etNama.requestFocus();
    }
}
