package com.dev.wedrive;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.dev.wedrive.adapters.CarListAdapter;
import com.dev.wedrive.service.CarService;

public class CarListActivity extends AppCompatActivity {

    protected CarService carService;

    public CarListActivity() {
        carService = new CarService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        findViewById(R.id.car_add_btn).setOnClickListener((v) -> startActivity(new Intent(this, CreateNewCarActivity.class)));

        loadCarList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCarList();
    }

    private void loadCarList() {
        carService.getMyCars((cars) -> {
            ListView list = findViewById(R.id.car_list);
            list.setAdapter(new CarListAdapter(this, cars, (id, car) -> {

                if (id == R.id.smContentView)
                    carService.setCurrent(car, (mCar) -> finish());

                if (id == R.id.list_item_delete)
                    carService.deleteCar(car, (mCar) -> finish());

                if (id == R.id.list_item_edit) {
                    Intent intent = new Intent(this, CreateNewCarActivity.class);
                    intent.putExtra("uuid", car.uuid);
                    startActivity(intent);
                }

            }));
        });
    }
}
