package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/10/2015.
 */
@IBMDataObjectSpecialization("Doctor")
public class Doctor extends IBMDataObject{
    public static final String CLASS_NAME = "Doctor";

    /*Gets the name of the Doctor*/
    public String getName() {
        return (String) getObject("name");
    }

    /* Sets the name of the doctor */
    public void setName(String docName) {
        setObject("name", (docName != null) ? docName : "");
    }

    /*Gets the docAddr of the Doctor*/
    public String getAddress() {
        return (String) getObject("address");
    }

    /* Sets the docAddr of the doctor */
    public void setAddress(String docAddr) {
        setObject("address", (docAddr != null) ? docAddr : "");
    }

    /*Gets the docArea of the Doctor*/
    public String getArea() {
        return (String) getObject("area");
    }

    /* Sets the docArea of the doctor */
    public void setArea(String docArea) {
        setObject("area", (docArea != null) ? docArea : "");
    }

    /*Gets the lat of the doctor*/
    public String getLat() {
        return (String) getObject("lat");
    }
    /* Sets the lat of the doctor */
    public void setLat(String docLat) {
        setObject("lat", (docLat != null) ? docLat : "");
    }

    /*Gets the lon of the doctor*/
    public String getLon() {
        return (String) getObject("lon");
    }

    /* Sets the lon of the doctor */
    public void setLon(String docLon) {
        setObject("lon", (docLon != null) ? docLon : "");
    }


    /*Gets the docFees of the Doctor*/
    public String getFees() {
        return (String) getObject("area");
    }

    /* Sets the docFees of the doctor */
    public void setFees(String docFees) {
        setObject("fees", (docFees != null) ? docFees : "");
    }

    /*Gets whether the doctor takes online appointment*/
    public String getOnlineAppointment() {
        return (String) getObject("takesAppointments");
    }

    /* Sets the docAppoint of the doctor */
    public void setOnlineAppointment(String docAppoint) {
        setObject("takesAppointments", (docAppoint != null) ? docAppoint : "");
    }

    /*Gets the docUsername of the Doctor*/
    public String getUsername() {
        return (String) getObject("username");
    }

    /* Sets the docUsername of the doctor */
    public void setUsername(String docUsername) {
        setObject("username", (docUsername != null) ? docUsername : "");
    }

    /*Gets the regNum of the doctor*/
    public String getRegNum() {
        return (String) getObject("regNumber");
    }

    /* Sets the regNum of the doctor */
    public void setRegNum(String docNumber) {
        setObject("regNumber", (docNumber != null) ? docNumber : "");
    }

    /*Gets the docRating of the doctor*/
    public String getRating() {
        return (String) getObject("docRating");
    }

    /* Sets the docRating of the doctor */
    public void setRating(String docRating) {
        setObject("docRating", (docRating != null) ? docRating : "");
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

