package com.example.murtaza.firebaseproject;

/**
 * Created by Murtaza on 7/20/2018.
 */

public class AddData {
    String id;
    String date;
    String bsid;
    String Cell_Id;
    String latitude;
    String longititude;
    String Mobilemac;
    String LteData;
    String batteryData;
    String InternetSpeed;

    public AddData(String id, String date, String bsid_cell_id,
                   String Cell_Id, String latitude, String longititude, String Mobilemac, String LteData, String InternetSpeed,String batteryData) {
        this.id = id;
        this.date = date;
        this.bsid = bsid_cell_id;
        this.Cell_Id = Cell_Id;
        this.latitude = latitude;
        this.longititude = longititude;
        this.Mobilemac = Mobilemac;
        this.LteData = LteData;
        this.batteryData = batteryData;

        this.InternetSpeed = InternetSpeed;
    }



    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getBsid_cell_id() {
        return bsid;
    }
    public void setBsid_cell_id(String bsid_cell_id) {
        this.bsid = bsid_cell_id;
    }
    public String getCell_Id() {
        return Cell_Id;
    }
    public void setCell_Id(String cell_Id) {
        Cell_Id = cell_Id;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongititude() {
        return longititude;
    }
    public void setLongititude(String longititude) {
        this.longititude = longititude;
    }
    public String getMobilemac() {
        return Mobilemac;
    }
    public void setMobilemac(String mobilemac) {
        Mobilemac = mobilemac;
    }

    public String getBsid() {
        return bsid;
    }

    public void setBsid(String bsid) {
        this.bsid = bsid;
    }

    public String getLteData() {
        return LteData;
    }

    public void setLteData(String lteData) {
        LteData = lteData;
    }

    public String getInternetSpeed() {
        return InternetSpeed;
    }

    public void setInternetSpeed(String internetSpeed) {
        InternetSpeed = internetSpeed;
    }
    public String getBatteryData() {
        return batteryData;
    }

    public void setBatteryData(String batteryData) {
        this.batteryData = batteryData;
    }
}
