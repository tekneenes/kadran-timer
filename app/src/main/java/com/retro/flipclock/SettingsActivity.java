package com.retro.flipclock;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Button;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private Switch switchShowVideo;
    private Spinner spinnerVideoQuality;
    private Switch switchShowGlass;
    private Switch switchShowSeconds;
    private Switch switchUse24Hour;
    private Switch switchBigClock;
    private Switch switchTickSound;
    private EditText editWeatherCity;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Bind Views
        switchShowVideo = findViewById(R.id.switchShowVideo);
        spinnerVideoQuality = findViewById(R.id.spinnerVideoQuality);
        switchShowGlass = findViewById(R.id.switchShowGlass);
        switchShowSeconds = findViewById(R.id.switchShowSeconds);
        switchUse24Hour = findViewById(R.id.switchUse24Hour);
        switchBigClock = findViewById(R.id.switchBigClock);
        switchTickSound = findViewById(R.id.switchTickSound);
        editWeatherCity = findViewById(R.id.editWeatherCity);
        btnSave = findViewById(R.id.btnSave);

        // Populate Video Quality Spinner
        String[] qualities = {"1080p", "4K"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, qualities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVideoQuality.setAdapter(adapter);

        // Load Saved Settings from SharedPreferences
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        switchShowVideo.setChecked("true".equals(prefs.getString("showVideoBackground", "false")));
        
        String savedQuality = prefs.getString("videoQuality", "1080p");
        if ("4K".equals(savedQuality)) {
            spinnerVideoQuality.setSelection(1);
        } else {
            spinnerVideoQuality.setSelection(0);
        }

        switchShowGlass.setChecked("true".equals(prefs.getString("showGlassBackground", "false")));
        switchShowSeconds.setChecked(!"false".equals(prefs.getString("showSeconds", "true"))); // default true
        switchUse24Hour.setChecked(!"false".equals(prefs.getString("use24Hour", "true"))); // default true
        switchBigClock.setChecked("true".equals(prefs.getString("isBigClockMode", "false")));
        switchTickSound.setChecked("true".equals(prefs.getString("isTickingEnabled", "false")));
        editWeatherCity.setText(prefs.getString("selectedCityName", "İstanbul"));

        // Save Button Click Listener
        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("showVideoBackground", switchShowVideo.isChecked() ? "true" : "false");
            editor.putString("videoQuality", spinnerVideoQuality.getSelectedItem().toString());
            editor.putString("showGlassBackground", switchShowGlass.isChecked() ? "true" : "false");
            editor.putString("showSeconds", switchShowSeconds.isChecked() ? "true" : "false");
            editor.putString("use24Hour", switchUse24Hour.isChecked() ? "true" : "false");
            editor.putString("isBigClockMode", switchBigClock.isChecked() ? "true" : "false");
            editor.putString("isTickingEnabled", switchTickSound.isChecked() ? "true" : "false");
            editor.putString("selectedCityName", editWeatherCity.getText().toString().trim());

            editor.apply();

            Toast.makeText(SettingsActivity.this, "Ayarlar Kaydedildi!", Toast.LENGTH_SHORT).show();
            finish(); // Close activity
        });
    }
}
