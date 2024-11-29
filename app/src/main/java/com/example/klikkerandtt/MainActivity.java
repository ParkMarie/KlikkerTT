package com.example.klikkerandtt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.ImageButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    SharedPreferences themeSettings;
    SharedPreferences.Editor settingsEditor;
    private int Score = 0;
    private TextView score;
    private ImageView changeTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startGameButton = findViewById(R.id.startTtBtn);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TikTakActivity.class);
                startActivity(intent);
            }
        });

        score = findViewById(R.id.score);
        ImageButton clickImageButton = findViewById(R.id.clickImageButton);
        changeTheme = findViewById(R.id.Tema);

        clickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseScore();
            }
        });

        themeSettings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        settingsEditor = themeSettings.edit();

        if (!themeSettings.contains("MODE_NIGHT_ON")) {
            settingsEditor.putBoolean("MODE_NIGHT_ON", false);
            settingsEditor.apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Toast.makeText(this, "Ура! Поехали!", Toast.LENGTH_SHORT).show();
        } else {
            setCurrentTheme();
        }
        updateThemeIcon();

        changeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleTheme();
            }
        });
    }

    private void increaseScore() {
        Score++;
        score.setText("Кол-во очков: " + Score);
        Toast.makeText(this, "Вы получили + 1 очко!", Toast.LENGTH_SHORT).show();
    }

    private void setCurrentTheme() {
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void updateThemeIcon() {
        if (!themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            changeTheme.setImageResource(R.drawable.polumesac);
        } else {
            changeTheme.setImageResource(R.drawable.sun);
        }
    }

    private void toggleTheme() {
        boolean isNightMode = themeSettings.getBoolean("MODE_NIGHT_ON", false);
        settingsEditor.putBoolean("MODE_NIGHT_ON", !isNightMode);
        settingsEditor.apply();

        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}