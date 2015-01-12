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

    /*Gets the id of the Hospital*/
    public String getId() {
        return (String) getObject("id");
    }

    /* Sets the hosID of the Hospital */
    public void setArea(String hosID) {
        setObject("id", (hosID != null) ? hosID : "");
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
