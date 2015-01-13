package com.example.prithwishmukherjee.duvidha;


import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/10/2015.
 */
@IBMDataObjectSpecialization("Hospital")
public class Hospital extends IBMDataObject{
    public static final String CLASS_NAME = "Hospital";

    /*Gets the name of the Hospital*/
    public String getName() {
        return (String) getObject("name");
    }

    /* Sets the name of the Hospital */
    public void setName(String hosName) {
        setObject("name", (hosName != null) ? hosName : "");
    }

    /*Gets the hosAddr of the Hospital*/
    public String getAddress() {
        return (String) getObject("address");
    }

    /* Sets the hosAddr of the Hospital */
    public void setAddress(String hosAddr) {
        setObject("address", (hosAddr != null) ? hosAddr : "");
    }

    /*Gets the patUsername of the hospital*/
    public String getUsername() {
        return (String) getObject("username");
    }

    /* Sets the patUsername of the hospital */
    public void setUsername(String hosUsername) {
        setObject("username", (hosUsername != null) ? hosUsername : "");
    }
    /*Gets the lat of the hospital*/
    public String getLat() {
        return (String) getObject("lat");
    }
    /* Sets the lat of the hospital */
    public void setLat(String patLat) {
        setObject("lat", (patLat != null) ? patLat : "");
    }

    /*Gets the lon of the hospital*/
    public String getLon() {
        return (String) getObject("lon");
    }

    /* Sets the lon of the hospital */
    public void setLon(String patLon) {
        setObject("lon", (patLon != null) ? patLon : "");
    }

    /*Gets the OnlineAppointment of the hospital*/
    public String getOnlineAppointment() {
        return (String) getObject("takesAppointment");
    }

    /* Sets the OnlineAppointment of the hospital */
    public void setOnlineAppointment(String hosAppoint) {
        setObject("takesAppointment", (hosAppoint != null) ? hosAppoint : "");
    }

    /*Gets the regNum of the hospital*/
    public String getRegNum() {
        return (String) getObject("regNumber");
    }

    /* Sets the regNum of the hospital */
    public void setRegNum(String hosNumber) {
        setObject("regNumber", (hosNumber != null) ? hosNumber : "");
    }


    /*Gets the number of ambulances of the Hospital*/
    public String getNumOfAmb() {
        return (String) getObject("numberOfAmbulances");
    }

    /* Sets the numAmb of the Hospital */
    public void setNumOfAmb(String numAmb) {
        setObject("numberOfAmbulances", (numAmb != null) ? numAmb : "");
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
