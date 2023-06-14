package com.greenhuecity.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.greenhuecity.R;
import com.greenhuecity.data.model.Cars;
import com.greenhuecity.view.adapter.CarAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvSearch;
    TextView tvResult;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rvSearch = findViewById(R.id.recyclerView_car);
        tvResult = findViewById(R.id.text_result);
        imgBack = findViewById(R.id.img_back);
        imgBack.setOnClickListener(view->onBackPressed());
        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new GridLayoutManager(this,3));
        List<Cars> carsList = (List<Cars>)getIntent().getSerializableExtra("list");
        if(carsList != null){
            CarAdapter carRecyclerViewAdapter = new CarAdapter(carsList,this);
            rvSearch.setAdapter(carRecyclerViewAdapter);
        }
        tvResult.setText(String.valueOf(carsList.size()));
    }
}
