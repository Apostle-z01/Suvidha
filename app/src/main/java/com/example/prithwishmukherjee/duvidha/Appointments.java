package com.example.prithwishmukherjee.duvidha;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;
/**
 * Created by Manav on 1/12/2015.
 */

@IBMDataObjectSpecialization("Appointments")
public class Appointments extends IBMDataObject{
    public static final String CLASS_NAME = "Appointments";

    /*Gets the time of the appointment*/
    public String getTime() {
        return (String) getObject("time");
    }

    /* Sets the time of the appointment */
    public void setTme(String time) {
        setObject("time", (time != null) ? time : "");
    }

    /*Gets the date of the appointment*/
    public String getDate() {
        return (String) getObject("date");
    }

    /* Sets the date of the appointment */
    public void setDate(String date) {
        setObject("date", (date != null) ? date : "");
    }

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

    /*Gets the name of the doctor*/
    public String getDocName() {
        return (String) getObject("docName");
    }

    /* Sets the name of the doctor */
    public void setDocName(String docName) {
        setObject("docName", (docName != null) ? docName : "");
    }

    /*Gets the status of the appointment*/
    public String getStatus() {
        return (String) getObject("status");
    }

    /* Sets the status of the appointment */
    public void setStatus(String status) {
        setObject("status", (status != null) ? status : "");
    }
}
