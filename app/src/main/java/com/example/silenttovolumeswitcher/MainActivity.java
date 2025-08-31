package com.example.silenttovolumeswitcher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NumberPicker timePicker;
    private SettingsManager settingsManager;

    //Timer values in minutes
    private static final int[] TIME_VALUES = {1, 5, 10, 15, 30, 60, 120,180, 240,300, 360,420,480};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "GIVE PERMISSION IN ACCESSIBILITY SETTINGS FIRST!", Toast.LENGTH_LONG).show();

        settingsManager = new SettingsManager(this);

        timePicker = findViewById(R.id.timePicker);
        Button saveButton = findViewById(R.id.saveButton);
        Button accessibilityButton = findViewById(R.id.accessibilitySettingsButton);

        setTimePicker();

        //Giving the set time to SettingsManager which will then Pass it to RingerAccesibilityService
        saveButton.setOnClickListener(v -> {
            int index = timePicker.getValue();
            long time = (long) TIME_VALUES[index] * 60 *1000;
            settingsManager.setTimeDelay(time);
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });

        //Open Accessbility Settings to take permissions
        accessibilityButton.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        });





    }


    // Taking user Input from the settingsManager, then mapping it to the desired time in the array, and returning the index of chosen time
    private void setTimePicker(){
        String[] values =  new String[TIME_VALUES.length];
        for(int i = 0; i < values.length; i++){
            int value = TIME_VALUES[i];
            if(TIME_VALUES[i] < 60){
                values[i] = value+"minutes";
            }else{
                value = value/60;
                values[i] = value+"hours";

            }
        }

        timePicker.setMinValue(0);  //min value
        timePicker.setMaxValue(TIME_VALUES.length-1); //8hours can be adjusted
        timePicker.setDisplayedValues(values);
        timePicker.setWrapSelectorWheel(false);

        long savedTime = settingsManager.getTimeDelay();
        int index = 0;
        for(int i = 0; i < TIME_VALUES.length; i++){
            if(savedTime == (long)TIME_VALUES[i]*60*1000){
                index = i;
                break;
            }
        }
        timePicker.setValue(index);



    }

    }
