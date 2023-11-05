package com.digiledge.app;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();

    private String[] array1;
    private String[] array2;

    private Integer i=0,j=0;



    private DataHolder() {
        // Private constructor to prevent instantiation
        array1 = new String[1];
        array1[0]="phone";
        array2 = new String[1];
        array2[0]="password";
    }

    public static DataHolder getInstance() {
        return instance;
    }

    public String[] getArray1() {
        return array1;
    }

    public void setArray1(String[] array1) {
        this.array1 = array1;
    }

    public String[] getArray2() {
        return array2;
    }

    public void setArray2(String[] array2) {
        this.array2 = array2;
    }
}