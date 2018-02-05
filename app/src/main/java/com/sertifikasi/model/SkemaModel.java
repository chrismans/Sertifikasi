package com.sertifikasi.model;

/**
 * Created by pandu on 1/21/2018.
 */

public class SkemaModel {

    private int id;
    private String kode_skema;
    private String skema_sertifikasi;
    private String kategori_skema;
    private String biaya_skema;
    private String link_download_dokumen;
    private String deskripsi_skema;

    public SkemaModel(int id, String kode_skema, String skema_sertifikasi, String kategori_skema, String biaya_skema, String link_download_dokumen, String deskripsi_skema){
        this.id = id;
        this.kode_skema = kode_skema;
        this.skema_sertifikasi = skema_sertifikasi;
        this.kategori_skema = kategori_skema;
        this.biaya_skema = biaya_skema;
        this.link_download_dokumen = link_download_dokumen;
        this.deskripsi_skema = deskripsi_skema;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBiaya_skema(String biaya_skema) {
        this.biaya_skema = biaya_skema;
    }

    public void setDeskripsi_skema(String deskripsi_skema) {
        this.deskripsi_skema = deskripsi_skema;
    }

    public void setKategori_skema(String kategori_skema) {
        this.kategori_skema = kategori_skema;
    }

    public void setKode_skema(String kode_skema) {
        this.kode_skema = kode_skema;
    }

    public void setLink_download_dokumen(String link_download_dokumen) {
        this.link_download_dokumen = link_download_dokumen;
    }

    public void setSkema_sertifikasi(String skema_sertifikasi) {
        this.skema_sertifikasi = skema_sertifikasi;
    }

    public int getId() {
        return id;
    }

    public String getBiaya_skema() {
        return biaya_skema;
    }

    public String getDeskripsi_skema() {
        return deskripsi_skema;
    }

    public String getKategori_skema() {
        return kategori_skema;
    }

    public String getKode_skema() {
        return kode_skema;
    }

    public String getLink_download_dokumen() {
        return link_download_dokumen;
    }

    public String getSkema_sertifikasi() {
        return skema_sertifikasi;
    }
}
