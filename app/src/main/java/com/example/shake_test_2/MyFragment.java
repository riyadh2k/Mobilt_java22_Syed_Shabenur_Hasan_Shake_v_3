package com.example.shake_test_2;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast; // Lägg till Toast här
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment implements SensorEventListener {

    private TextView xValue, yValue, zValue;
    private ImageView accelerometerImage;
    private SensorManager sensorManager;
    private float rotationAngle = 0.0f;
    private static final float ROTATION_THRESHOLD = 30.0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        xValue = rootView.findViewById(R.id.xValue);
        yValue = rootView.findViewById(R.id.yValue);
        zValue = rootView.findViewById(R.id.zValue);
        accelerometerImage = rootView.findViewById(R.id.image_accelerometer);
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            xValue.setText("X-värde: " + x);
            yValue.setText("Y-värde: " + y);
            zValue.setText("Z-värde: " + z);

            rotationAngle = (x + y + z) / 3.0f;

            if (Math.abs(rotationAngle)>ROTATION_THRESHOLD) {
                Log.d("MinApp", "Inuti if-villkoret"); // Lägg till loggmeddelande
                showToast("!");
                accelerometerImage.setColorFilter(Color.BLUE);
            } else {
                Log.d("MinApp", "Inuti else-villkoret"); // Lägg till loggmeddelande
                accelerometerImage.setColorFilter(Color.GREEN);
                showToast("Shaking is stop now!");
            }


            // Uppdatera bildens rotation
            accelerometerImage.setRotation(rotationAngle);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // Metod för att visa ett Toast-meddelande
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        Log.d(" hello ", message);
    }
}