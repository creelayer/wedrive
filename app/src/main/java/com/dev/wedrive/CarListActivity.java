package com.dev.wedrive;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.dev.wedrive.adapters.CarListAdapter;
import com.dev.wedrive.adapters.RoutesListAdapter;
import com.dev.wedrive.entity.ApiCar;
import com.dev.wedrive.service.CarService;

public class CarListActivity extends AppCompatActivity {

    protected CarService carService;
    private CarListAdapter adapter;

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

            // set up the RecyclerView
            RecyclerView list = findViewById(R.id.car_list);
            list.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CarListAdapter(this, cars);
            adapter.setListener((view, position) -> {

                ApiCar car = adapter.getItem(position);

                if (view.getId() == R.id.smContentView)
                    carService.setCurrent(car, (mCar) -> finish());

                if (view.getId() == R.id.list_item_delete)
                    carService.deleteCar(car, (mCar) -> finish());

                if (view.getId() == R.id.list_item_edit)
                    startActivity(new Intent(this, CreateNewCarActivity.class).putExtra("uuid", car.uuid));

            });

            list.setAdapter(adapter);
        });
    }
}
