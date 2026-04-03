package com.mahasiswa.nilaimahasiswa;

public class Student {
    private int id;
    private String nama;
    private String nim;
    private String mataKuliah;
    private double kehadiran;
    private double tugas;
    private double uts;
    private double uas;
    private double nilaiAkhir;
    private String grade;
    private String keterangan;

    public Student() {}

    public Student(String nama, String nim, String mataKuliah,
                   double kehadiran, double tugas, double uts, double uas) {
        this.nama = nama;
        this.nim = nim;
        this.mataKuliah = mataKuliah;
        this.kehadiran = kehadiran;
        this.tugas = tugas;
        this.uts = uts;
        this.uas = uas;
        hitungNilaiAkhir();
    }

    private void hitungNilaiAkhir() {
        this.nilaiAkhir = (kehadiran * 0.10) + (tugas * 0.20) + (uts * 0.30) + (uas * 0.40);
        tentukanGrade();
    }

    private void tentukanGrade() {
        if (nilaiAkhir >= 85) {
            this.grade = "A";
            this.keterangan = "Sangat Baik";
        } else if (nilaiAkhir >= 70) {
            this.grade = "B";
            this.keterangan = "Baik";
        } else if (nilaiAkhir >= 56) {
            this.grade = "C";
            this.keterangan = "Cukup";
        } else if (nilaiAkhir >= 41) {
            this.grade = "D";
            this.keterangan = "Kurang";
        } else {
            this.grade = "E";
            this.keterangan = "Sangat Kurang";
        }
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getMataKuliah() { return mataKuliah; }
    public void setMataKuliah(String mataKuliah) { this.mataKuliah = mataKuliah; }

    public double getKehadiran() { return kehadiran; }
    public void setKehadiran(double kehadiran) { this.kehadiran = kehadiran; }

    public double getTugas() { return tugas; }
    public void setTugas(double tugas) { this.tugas = tugas; }

    public double getUts() { return uts; }
    public void setUts(double uts) { this.uts = uts; }

    public double getUas() { return uas; }
    public void setUas(double uas) { this.uas = uas; }

    public double getNilaiAkhir() { return nilaiAkhir; }
    public void setNilaiAkhir(double nilaiAkhir) { this.nilaiAkhir = nilaiAkhir; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }
}
