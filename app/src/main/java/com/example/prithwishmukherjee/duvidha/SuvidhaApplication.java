package com.example.prithwishmukherjee.duvidha;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

import javax.net.ssl.HostnameVerifier;

import bolts.Continuation;
import bolts.Task;


/**
 * Created by Manav on 1/10/2015.
 */
public class SuvidhaApplication extends Application{

    public static IBMPush push = null;
    private Activity mActivity;
    private static final String deviceAlias = "TargetDevice";
    private static final String consumerID = "MBaaSListApp";

    private static final String APP_ID = "4767ba33-484b-46f6-a74d-e7eab6335e24";
    private static final String APP_SECRET = "8aa7f29ad24faca1da4f4adf01c98da87abb3117";
    private static final String APP_ROUTE = "suvidhanew.mybluemix.net";

    private static final String CLASS_NAME = SuvidhaApplication.class.getSimpleName();

    private IBMPushNotificationListener notificationListener = null;

    public SuvidhaApplication() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity,
                                          Bundle savedInstanceState) {
                Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
                mActivity = activity;

                // Define IBMPushNotificationListener behavior on push notifications.
                notificationListener = new IBMPushNotificationListener() {
                    @Override
                    public void onReceive(final IBMSimplePushNotification message) {
                        mActivity.runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                Class<? extends Activity> actClass = mActivity.getClass();
                                //if (actClass == MainActivity.class) {
                                Log.e(CLASS_NAME, "Notification message received: " + message.toString());
                                // Present the message when sent from Push notification console.
                                //if(!message.getAlert().contains("ItemList was updated")){
                                mActivity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        new AlertDialog.Builder(mActivity)
                                                .setTitle("New notification received")
                                                .setMessage(message.getAlert())
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int whichButton) {
                                                    }
                                                })
                                                .show();
                                    }
                                });

                                //}
                                //}
                            }
                        });
                    }
                };
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
                Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {mActivity = activity;
                Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
                if (push != null) {
                    push.listen(notificationListener);
                }
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
                if (push != null) {
                    push.hold();
                }
                Log.d(CLASS_NAME, "Activity paused: " + activity.getLocalClassName());
                if (activity != null && activity.equals(mActivity))
                    mActivity = null;
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
        Doctor_Time.registerSpecialization(Doctor_Time.class);

        //Initialize the IBM push service
        IBMPush.initializeService();
        push = IBMPush.getService();
        push.register(deviceAlias, consumerID).continueWith(new Continuation<String, Void>() {

            @Override
            public Void then(Task<String> task) throws Exception {
                if (task.isCancelled()) {
                    Log.e(CLASS_NAME, "Exception : Task " + task.toString() + " was cancelled.");
                } else if (task.isFaulted()) {
                    Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
                } else {
                    Log.d(CLASS_NAME, "Device Successfully Registered");
                }

                return null;
            }

        });
        //push.subscribe(username);
    }
}