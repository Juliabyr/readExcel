package com.unionpay.tech.db.entity;

public class TbExcel {
    private int id;
    private String name;
    private byte[] excel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getExcel() {
        return excel;
    }

    public void setExcel(byte[] movie) {
        this.excel = movie;
    }

    public TbExcel(int id, String name, byte[] excel) {
        this.id = id;
        this.name = name;
        this.excel = excel;
    }
}
