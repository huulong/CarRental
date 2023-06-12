package com.greenhuecity.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.greenhuecity.R;

public class LeaseActivity extends AppCompatActivity {
    ImageView imgBack;
    Button btnAdd;
    RecyclerView recyclerView;
    TextView tvTotalCarRental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease);
        initGUI();
        eventClickButton();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("current",3);
        startActivity(intent);
    }

    private void initGUI(){
        imgBack = findViewById(R.id.img_back);
        btnAdd = findViewById(R.id.button_addCar);
        recyclerView = findViewById(R.id.recyclerView_car);
        tvTotalCarRental = findViewById(R.id.textView_quantity);
    }
    private void eventClickButton(){
        imgBack.setOnClickListener(view->onBackPressed());
        btnAdd.setOnClickListener(view1->startActivity(new Intent(this, UploadCarsActivity.class)));
    }
}