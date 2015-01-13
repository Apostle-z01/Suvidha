package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;
/**
 * Created by Manav on 1/12/2015.
 */

@IBMDataObjectSpecialization("Patient")
public class Patient extends IBMDataObject{
    public static final String CLASS_NAME = "Patient";

    /*Gets the name of the patient*/
    public String getName() {
        return (String) getObject("name");
    }

    /* Sets the name of the patient */
    public void setName(String docName) {
        setObject("name", (docName != null) ? docName : "");
    }

    /*Gets the patAddr of the patient*/
    public String getAddress() {
        return (String) getObject("address");
    }

    /* Sets the patAddr of the patient */
    public void setAddress(String patAddr) {
        setObject("address", (patAddr != null) ? patAddr : "");
    }

    /*Gets the patUsername of the patient*/
    public String getUsername() {
        return (String) getObject("username");
    }

    /* Sets the patUsername of the patient */
    public void setUsername(String patUsername) {
        setObject("username", (patUsername != null) ? patUsername : "");
    }
    /*Gets the lat of the patient*/
    public String getLat() {
        return (String) getObject("lat");
    }
    /* Sets the lat of the patient */
    public void setLat(String patLat) {
        setObject("lat", (patLat != null) ? patLat : "");
    }

    /*Gets the lon of the patient*/
    public String getLon() {
        return (String) getObject("lon");
    }

    /* Sets the lon of the patient */
    public void setLon(String patLon) {
        setObject("lon", (patLon != null) ? patLon : "");
    }


    /*Gets the patAge of the patient*/
    public String getAge() {
        return (String) getObject("age");
    }

    /* Sets the patId of the patient */
    public void setAge(String patAge) {
        setObject("age", (patAge != null) ? patAge : "");
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
