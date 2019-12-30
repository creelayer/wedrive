package com.dev.wedrive;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dev.wedrive.adapters.CarListAdapter;
import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiRoute;
import com.dev.wedrive.service.CarService;

public class CarListActivity extends AppCompatActivity {

    protected CarService carService;

    public CarListActivity(){
        carService = new CarService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        findViewById(R.id.car_add_btn).setOnClickListener((v) ->{
            Intent myIntent = new Intent(this, CreateNewCarActivity.class);
            startActivity(myIntent);
        });

        loadCarList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCarList();
    }

    private void loadCarList() {
        carService.getMyCars( (cars) -> {
            ListView list = findViewById(R.id.car_list);
            list.setAdapter(new CarListAdapter(this, cars, (car) -> {
                carService.setCurrent(car,  (mCar) -> {
                    finish();
                    return null;
                });
            }));
            return null;
        });
    }
}
