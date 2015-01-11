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

