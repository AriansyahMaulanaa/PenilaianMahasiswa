package com.mahasiswa.nilaimahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "nilai_mahasiswa.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama";
    private static final String COL_NIM = "nim";
    private static final String COL_MATA_KULIAH = "mata_kuliah";
    private static final String COL_KEHADIRAN = "kehadiran";
    private static final String COL_TUGAS = "tugas";
    private static final String COL_UTS = "uts";
    private static final String COL_UAS = "uas";
    private static final String COL_NILAI_AKHIR = "nilai_akhir";
    private static final String COL_GRADE = "grade";
    private static final String COL_KETERANGAN = "keterangan";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAMA + " TEXT NOT NULL, "
                + COL_NIM + " TEXT NOT NULL, "
                + COL_MATA_KULIAH + " TEXT NOT NULL, "
                + COL_KEHADIRAN + " REAL, "
                + COL_TUGAS + " REAL, "
                + COL_UTS + " REAL, "
                + COL_UAS + " REAL, "
                + COL_NILAI_AKHIR + " REAL, "
                + COL_GRADE + " TEXT, "
                + COL_KETERANGAN + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long insertStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, student.getNama());
        values.put(COL_NIM, student.getNim());
        values.put(COL_MATA_KULIAH, student.getMataKuliah());
        values.put(COL_KEHADIRAN, student.getKehadiran());
        values.put(COL_TUGAS, student.getTugas());
        values.put(COL_UTS, student.getUts());
        values.put(COL_UAS, student.getUas());
        values.put(COL_NILAI_AKHIR, student.getNilaiAkhir());
        values.put(COL_GRADE, student.getGrade());
        values.put(COL_KETERANGAN, student.getKeterangan());
        long id = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return id;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STUDENTS, null, null, null, null, null,
                COL_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                student.setNama(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAMA)));
                student.setNim(cursor.getString(cursor.getColumnIndexOrThrow(COL_NIM)));
                student.setMataKuliah(cursor.getString(cursor.getColumnIndexOrThrow(COL_MATA_KULIAH)));
                student.setKehadiran(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_KEHADIRAN)));
                student.setTugas(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_TUGAS)));
                student.setUts(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_UTS)));
                student.setUas(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_UAS)));
                student.setNilaiAkhir(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_NILAI_AKHIR)));
                student.setGrade(cursor.getString(cursor.getColumnIndexOrThrow(COL_GRADE)));
                student.setKeterangan(cursor.getString(cursor.getColumnIndexOrThrow(COL_KETERANGAN)));
                students.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return students;
    }

    public void deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENTS, null, null);
        db.close();
    }
}
