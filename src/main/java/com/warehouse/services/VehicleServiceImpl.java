package com.warehouse.services;

import com.warehouse.domain.Vehicle;
import com.warehouse.domain.VehicleDetails;
import com.warehouse.domain.Warehouse;
import com.warehouse.exception.VehicleNotFoundException;
import com.warehouse.repositories.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class VehicleServiceImpl implements VehicleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Warehouse> fetchWarehouseList() {
        LOGGER.info("fetchWarehouseList() invoked");
        List<Warehouse> warehouseList = new ArrayList<>();
        vehicleRepository.findAll().forEach(warehouseList::add);
        return warehouseList;
    }

    @Override
    public List<Vehicle> fetchVehicles() {
        LOGGER.info("fetchVehicles() invoked");
        List<Vehicle> vehicles = new ArrayList<>();
        for (Warehouse warehouse : fetchWarehouseList()) {
            warehouse.getCars().getVehicles().forEach(vehicles::add);
        }
        Collections.sort(vehicles);
        return vehicles;
    }

    @Override
    public Vehicle fetchVehicleById(String vehicleId) {
        LOGGER.info("Get vehicle by id {}", vehicleId);
        return fetchVehicles().stream()
                              .filter(vehicle -> vehicle.get_id().contentEquals(vehicleId))
                              .findFirst()
                              .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));

    }

    @Override
    public VehicleDetails fetchVehicleLocationDetails(String vehicleId) {
        LOGGER.info("Get vehicle details by id {}", vehicleId);
        VehicleDetails vehicleDetails = new VehicleDetails();
        for (Warehouse warehouse : fetchWarehouseList()) {
            if (warehouse.getCars().getVehicles().stream().anyMatch(vehicle -> vehicle.get_id().contentEquals(vehicleId))) {
                vehicleDetails.setWarehouseId(warehouse.get_id());
                vehicleDetails.setWarehouseName(warehouse.getName());
                vehicleDetails.setWarehouseLocationLatitude(warehouse.getLocation().getLatitude());
                vehicleDetails.setWarehouseLocationLongitude(warehouse.getLocation().getLongitude());
                vehicleDetails.setCarLocation(warehouse.getCars().getLocation());
                break;
            }
        }
        if (Objects.isNull(vehicleDetails.getWarehouseId())) {
            throw new VehicleNotFoundException("vehicle details not found");
        }
        return vehicleDetails;
    }

    @Override
    public void saveWarehouseList(List<Warehouse> warehouseList) {
        vehicleRepository.saveAll(warehouseList);
    }
}
