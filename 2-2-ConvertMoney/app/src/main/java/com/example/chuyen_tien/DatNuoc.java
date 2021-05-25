package com.example.chuyen_tien;

// Tạo ra 1 kiểu dữ liệu giống y như json để nhận lấy json khi parser về
public class DatNuoc {
    String tenDN, quocKi, danSo, areaInSqKm;


    public DatNuoc(String tenDN, String quocKi, String danSo, String areaInSqKm) {
        this.tenDN = tenDN;
        this.quocKi = quocKi;
        this.danSo = danSo;
        this.areaInSqKm = areaInSqKm;
    }

    public String getdanSo() {
        return danSo;
    }

    public void setdanSo(String danSo) {
        this.danSo = danSo;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getQuocKi() {
        return quocKi;
    }

    public void setQuocKi(String flag) {
        this.quocKi = quocKi;
    }

    public String gettenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

}
