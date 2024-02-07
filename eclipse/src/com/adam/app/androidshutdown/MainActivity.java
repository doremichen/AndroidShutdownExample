/**
 * There are three button in UI
 */
package com.adam.app.androidshutdown;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity implements OnClickListener {

    // Power manager service
    private PowerManager mPowerManger;

    // UI button
    private Button mBtn_power_off;
    private Button mBtn_reboot;

    private static final String SYSTEM_PROPERTY_CLASS_NAME = "android.os.SystemProperties";

    // Progress dialog for power off
    private ProgressDialog mProgress;

    /**
     * 
     * Reboot device
     *
     */
    private void Reboot() {
        Utils.print(this, "[Reboot] enter");
        mPowerManger.reboot(null);

    }

    /**
     * 
     * Shutdown device
     *
     */
    private void PowerOff() {
        Utils.print(this, "[PowerOff] enter");
        
        mProgress.show();
        
        // Delay 3 sceond
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // dismiss progress
                mProgress.dismiss();
                setSystemProperty("ctl.start", "shutdown");
            }

        }, 3000L);
    }
    
    /**
     * 
     * Set system property
     *
     * @param key
     * @param str
     */
    private void setSystemProperty(String key, String str) {

        Class<?> c = null;
        Method method_set = null;

        Class<?>[] parameterTypes = { String.class, String.class };

        try {
            c = Class.forName(SYSTEM_PROPERTY_CLASS_NAME);
            method_set = c.getDeclaredMethod("set", parameterTypes);
            method_set.setAccessible(true);
            method_set.invoke(null, key, str);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_power_off = (Button) this.findViewById(R.id.btn_power_off);
        mBtn_power_off.setOnClickListener(this);
        mBtn_reboot = (Button) this.findViewById(R.id.btn_reboot);
        mBtn_reboot.setOnClickListener(this);

        mPowerManger = (PowerManager) getSystemService(Context.POWER_SERVICE);

        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Power off");
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    /**
     * 
     * This method is invoked when button is pressed.
     *
     * @param arg0
     */
    @Override
    public void onClick(View arg0) {
        Utils.print(this, "[onClick] enter");
        switch (arg0.getId()) {
        case R.id.btn_power_off:
            PowerOff();
            break;
        case R.id.btn_reboot:
            Reboot();
            break;
        default:
            break;
        }
    }

}
