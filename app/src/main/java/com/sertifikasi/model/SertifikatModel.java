package com.sertifikasi.model;

/**
 * Created by pandu on 1/20/2018.
 */

public class SertifikatModel {

    private int id;
    private String nama_lengkap;
    private String no_registrasi;
    private String no_sertifikat;
    private String no_Seri;
    private String tanggal_terbit;
    private String tanggal_rcc;
    private String skema;


    public SertifikatModel(int id, String nama_lengkap, String no_registrasi, String no_sertifikat, String no_Seri, String tanggal_terbit, String tanggal_rcc, String skema){
        this.id = id;
        this.no_registrasi = no_registrasi;
        this.nama_lengkap = nama_lengkap;
        this.no_sertifikat = no_sertifikat;
        this.no_Seri = no_Seri;
        this.tanggal_terbit = tanggal_terbit;
        this.tanggal_rcc = tanggal_rcc;
        this.skema = skema;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public void setNo_registrasi(String no_registrasi) {
        this.no_registrasi = no_registrasi;
    }

    public void setNo_Seri(String no_Seri) {
        this.no_Seri = no_Seri;
    }

    public void setNo_sertifikat(String no_sertifikat) {
        this.no_sertifikat = no_sertifikat;
    }

    public void setSkema(String skema) {
        this.skema = skema;
    }

    public void setTanggal_rcc(String tanggal_rcc) {
        this.tanggal_rcc = tanggal_rcc;
    }

    public void setTanggal_terbit(String tanggal_terbit) {
        this.tanggal_terbit = tanggal_terbit;
    }

    public int getId() {
        return id;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getNo_registrasi() {
        return no_registrasi;
    }

    public String getNo_Seri() {
        return no_Seri;
    }

    public String getNo_sertifikat() {
        return no_sertifikat;
    }

    public String getSkema() {
        return skema;
    }

    public String getTanggal_rcc() {
        return tanggal_rcc;
    }

    public String getTanggal_terbit() {
        return tanggal_terbit;
    }
}
