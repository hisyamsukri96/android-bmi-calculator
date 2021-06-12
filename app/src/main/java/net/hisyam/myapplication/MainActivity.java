package net.hisyam.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df = new DecimalFormat("0.00");

    float height = 0;
    float weight = 0;
    float heightincm, resultbmi;
    TextView bmi, category, healthrisk;
    EditText inputHeight, inputWeight;
    Button btn_calculate, btn_reset;
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bmi = findViewById(R.id.bmi);
        category = findViewById(R.id.category);
        healthrisk = findViewById(R.id.healthrisk);

        inputHeight = findViewById(R.id.height);
        inputWeight = findViewById(R.id.weight);

        sharedPref = this.getSharedPreferences("height", Context.MODE_PRIVATE);
        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);

        float height1 = sharedPref.getFloat("height", 0);
        inputHeight.setText(String.valueOf(height1));

        float weight1 = sharedPref.getFloat("weight", 0);
        inputWeight.setText(String.valueOf(weight1));

        btn_calculate = findViewById(R.id.btn_calculate);
        btn_reset = findViewById(R.id.btn_reset);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatebmi();

            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculatebmi() {

        try {

            height = Float.parseFloat(inputHeight.getText().toString());
            heightincm = height / 100;

            weight = Float.parseFloat(inputWeight.getText().toString());

            resultbmi = weight / (heightincm * heightincm);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putFloat("height", height);
            editor.putFloat("weight", weight);
            editor.apply();

            bmi.setText(df.format(resultbmi));

            if (resultbmi < 18.5) {
                category.setText("Underweight");
                healthrisk.setText("Malnutrition risk");
            } else if (resultbmi >= 18.50 && resultbmi <= 24.99) {
                category.setText("Normal weight");
                healthrisk.setText("Low risk");
            } else if (resultbmi >= 25 && resultbmi <= 29.99) {
                category.setText("Overweight");
                healthrisk.setText("Enhanced risk");
            } else if (resultbmi >= 30 && resultbmi <= 34.99) {
                category.setText("Moderately obese");
                healthrisk.setText("Medium risk");
            } else if (resultbmi >= 35 && resultbmi <= 39.99) {
                category.setText("Severely obese");
                healthrisk.setText("High risk");
            } else if (resultbmi > 40) {
                category.setText("Very severely obese");
                healthrisk.setText("Very high risk");
            } else {
                category.setText("Not found");
                healthrisk.setText("Not found");
            }
        }catch(Exception e) {
            Toast.makeText(MainActivity.this, "Please enter any value", Toast.LENGTH_SHORT).show();
        }
    }

    private void reset() {

        int reset = 0;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("height", reset);
        editor.putFloat("weight", reset);
        editor.apply();

        inputHeight.setText("");
        inputWeight.setText("");

    }
}