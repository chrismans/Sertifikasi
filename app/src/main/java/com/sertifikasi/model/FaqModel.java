package com.sertifikasi.model;

/**
 * Created by macpro on 05/02/18.
 */

public class FaqModel {

    // Model untuk menentukan parameter data dari server

    private String pertanyaan;
    private String jawaban;

    // Buat Constructor untuk input data
    public FaqModel(String pertanyaan, String jawaban){
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;
    }

    // Membuat Seter & Geter


    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public String getJawaban() {
        return jawaban;
    }
}
