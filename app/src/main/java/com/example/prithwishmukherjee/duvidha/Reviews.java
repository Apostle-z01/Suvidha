package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/17/2015.
 */
@IBMDataObjectSpecialization("Reviews")
public class Reviews extends IBMDataObject{
    public static final String CLASS_NAME = "Reviews";

    /*Gets the patId of the patient*/
    public String getPatUsername() {
        return (String) getObject("patUsername");
    }

    /* Sets the patId of the patient */
    public void setPatUsername(String patId) {
        setObject("patUsername", (patId != null) ? patId : "");
    }

    public String getPatName(){return (String) getObject("patName");
    }

    /* Sets the patName of the patient */
    public void setPatName(String patName) {
        setObject("patName", (patName != null) ? patName : "");
    }


    /*Gets the docId of the doctor*/
    public String getDocUsername() {
        return (String) getObject("docUsername");
    }

    /* Sets the docId of the doctor */
    public void setDocUsername(String docId) {
        setObject("docUsername", (docId != null) ? docId : "");
    }

    /*Gets the docId of the doctor*/
    public String getDocName() {
        return (String) getObject("docName");
    }

    /* Sets the docId of the doctor */
    public void setDocName(String docName) {
        setObject("docName", (docName != null) ? docName : "");
    }

    /*Gets the review of the doctor*/
    public String getReview() {
        return (String) getObject("review");
    }

    /* Sets the review of the doctor */
    public void setReview(String review) {
        setObject("review", (review != null) ? review : "");
    }

}
