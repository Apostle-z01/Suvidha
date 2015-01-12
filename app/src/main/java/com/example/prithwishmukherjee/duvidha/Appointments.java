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
    public String getPatId() {
        return (String) getObject("patId");
    }

    /* Sets the patId of the patient */
    public void setPatId(String patId) {
        setObject("patId", (patId != null) ? patId : "");
    }

    /*Gets the docId of the doctor*/
    public String getDocId() {
        return (String) getObject("docId");
    }

    /* Sets the docId of the doctor */
    public void setDocId(String docId) {
        setObject("docId", (docId != null) ? docId : "");
    }

}
