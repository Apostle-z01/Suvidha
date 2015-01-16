package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/16/2015.
 */
@IBMDataObjectSpecialization("Doctor_Time")
public class Doctor_Time extends IBMDataObject {
    public static final String CLASS_NAME = "Doctor_Time";

    /*Gets the name of the Doctor*/
    public String getName() {
        return (String) getObject("name");
    }

    /* Sets the name of the doctor */
    public void setName(String docName) {
        setObject("name", (docName != null) ? docName : "");
    }

    /*Gets the docUsername of the Doctor*/
    public String getUsername() {
        return (String) getObject("username");
    }

    /* Sets the docUsername of the doctor */
    public void setUsername(String docUsername) {
        setObject("username", (docUsername != null) ? docUsername : "");
    }

    /*Gets the day of the doctor*/
    public String getDay() {
        return (String) getObject("day");
    }

    /* Sets the day of the doctor */
    public void setDay(String docDay) {
        setObject("day", (docDay != null) ? docDay : "");
    }

    /*Gets the time of the doctor*/
    public String getTime() {
        return (String) getObject("time");
    }

    /* Sets the time of the doctor */
    public void setTime(String docTime) {
        setObject("time", (docTime != null) ? docTime : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     * @return String theItemName
     */
    public String toString() {
        String stringName = "";
        stringName = getName();
        return stringName;
    }



}