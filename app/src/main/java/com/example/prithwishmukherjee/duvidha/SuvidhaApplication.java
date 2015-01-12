package com.example.prithwishmukherjee.duvidha;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;

import javax.net.ssl.HostnameVerifier;


/**
 * Created by Manav on 1/10/2015.
 */
public class SuvidhaApplication extends Application{
    private static final String APP_ID = "3f0e1350-71c9-4bd5-b764-c274689cf690";
    private static final String APP_SECRET = "dec60b65a83e47828b9ebb538233852cb8139e5c";
    private static final String APP_ROUTE = "Suvidha.eu-gb.mybluemix.net";

    private static final String CLASS_NAME = SuvidhaApplication.class.getSimpleName();

    public SuvidhaApplication() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                Log.d(CLASS_NAME,
                        "Activity created: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(CLASS_NAME,
                        "Activity started: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(CLASS_NAME,
                        "Activity resumed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity,
                                                    Bundle outState) {
                Log.d(CLASS_NAME,
                        "Activity saved instance state: "
                                + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(CLASS_NAME,
                        "Activity paused: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(CLASS_NAME,
                        "Activity stopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(CLASS_NAME,
                        "Activity destroyed: " + activity.getLocalClassName());
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the IBM core backend-as-a-service.
        IBMBluemix.initialize(this, APP_ID, APP_SECRET, APP_ROUTE);

        // Initialize the IBM Data Service.
        IBMData.initializeService();

        // Register the Specializations.
        Doctor.registerSpecialization(Doctor.class);
        Users.registerSpecialization(Users.class);
        Hospital.registerSpecialization(Hospital.class);
        Patient.registerSpecialization(Patient.class);
        Appointments.registerSpecialization(Appointments.class);
    }
}