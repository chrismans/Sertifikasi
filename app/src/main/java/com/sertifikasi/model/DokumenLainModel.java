package com.sertifikasi.model;

import java.io.File;

/**
 * Created by pandu on 1/22/2018.
 */

public class DokumenLainModel {

    private String nama_dokumen;
    private File file_image;

    public DokumenLainModel(String nama_dokumen, File file_image){
        this.nama_dokumen = nama_dokumen;
        this.file_image = file_image;
    }

    public void setFile_image(File file_image) {
        this.file_image = file_image;
    }

    public void setNama_dokumen(String nama_dokumen) {
        this.nama_dokumen = nama_dokumen;
    }

    public File getFile_image() {
        return file_image;
    }

    public String getNama_dokumen() {
        return nama_dokumen;
    }
}
