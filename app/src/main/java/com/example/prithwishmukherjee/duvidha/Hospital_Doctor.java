package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/17/2015.
 */

@IBMDataObjectSpecialization("Hospital_Doctor")
public class Hospital_Doctor extends IBMDataObject{
    public static final String CLASS_NAME = "Hospital_Doctor";

    /*Gets the name of the Doctor*/
    public String getDocName() {
        return (String) getObject("docName");
    }

    /* Sets the name of the doctor */
    public void setDocName(String docName) {
        setObject("docName", (docName != null) ? docName : "");
    }

    /*Gets the docUsername of the Doctor*/
    public String getDocUsername() {
        return (String) getObject("docUsername");
    }

    /* Sets the docUsername of the doctor */
    public void setDocUsername(String docUsername) {
        setObject("docUsername", (docUsername != null) ? docUsername : "");
    }

    /*Gets the name of the Doctor*/
    public String getHosName() {
        return (String) getObject("hosName");
    }

    /* Sets the name of the doctor */
    public void setHosName(String hosName) {
        setObject("hosName", (hosName != null) ? hosName : "");
    }

    /*Gets the docUsername of the Doctor*/
    public String getHosUsername() {
        return (String) getObject("hosUsername");
    }

    /* Sets the docUsername of the doctor */
    public void setHosUsername(String hosUsername) {
        setObject("hosUsername", (hosUsername != null) ? hosUsername : "");
    }

    /*Gets the docUsername of the Doctor*/
    public String getArea() {
        return (String) getObject("docArea");
    }

    /* Sets the docUsername of the doctor */
    public void setArea(String docArea) {
        setObject("docArea", (docArea != null) ? docArea : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     * @return String theItemName
     */
    public String toString() {
        String stringName = "";
        stringName = getDocUsername();
        return stringName;
    }


}
