package com.warehouse.services;

import com.warehouse.domain.Vehicle;
import com.warehouse.domain.VehicleDetails;
import com.warehouse.domain.Warehouse;

import java.util.List;

public interface VehicleService {

    List<Vehicle> fetchVehicles();

    Vehicle fetchVehicleById(String vehicleId);

    VehicleDetails fetchVehicleLocationDetails(String vehicleId);

    void saveWarehouseList(List<Warehouse> warehouseList);

}

