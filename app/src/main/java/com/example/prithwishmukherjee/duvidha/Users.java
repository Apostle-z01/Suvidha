package com.example.prithwishmukherjee.duvidha;


import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

/**
 * Created by Manav on 1/10/2015.
 */
@IBMDataObjectSpecialization("Users")
public class Users extends IBMDataObject {
    public static final String CLASS_NAME = "Users";

    /*Gets the name of the user*/
    public String getName() {
        return (String) getObject("username");
    }

    /* Sets the name of the user */
    public void setName(String username) {
        setObject("username", (username != null) ? username : "");
    }

    /*Gets the password of the user*/
    public String getPassword() {
        return (String) getObject("password");
    }

    /* Sets the password of the user */
    public void setPassword(String password) {
        setObject("password", (password != null) ? password : "");
    }

    /*Gets the type of the user*/
    public String getType() {
        return (String) getObject("type");
    }

    /* Sets the type of the user */
    public void setType(String type) {
        setObject("type", (type != null) ? type : "");
    }

    /**
     * When calling toString() for an item, we'd really only want the name.
     *
     * @return String theItemName
     */
    public String toString() {
        String stringName = "";
        stringName = getName();
        return stringName;
    }
}