package com.warehouse.controllers;

import com.warehouse.domain.Vehicle;
import com.warehouse.domain.VehicleDetails;
import com.warehouse.services.VehicleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/shopping/cart/vehicles")
public class VehiclesController {

    private final static Logger LOGGER = LoggerFactory.getLogger(VehiclesController.class);

    private VehicleService vehicleService;

    @Autowired
    public void setVehicleService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @ApiOperation(value = "Retrieves vehicles across all warehouse")
    @ApiResponses({@ApiResponse(code = 200, message = "User is authorised. Collection is returned"), @ApiResponse(code = 404, message = "Vehicles not found.")})
    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehicles() {
        LOGGER.info("getVehicles invoked");
        return new ResponseEntity<>(vehicleService.fetchVehicles(), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves vehicle based on vehicle id")
    @ApiResponses({@ApiResponse(code = 200, message = "Vehicle found and returned"),
                   @ApiResponse(code = 400, message = "Vehicle id is invalid"),
                   @ApiResponse(code = 404, message = "Vehicle not found.")})
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable String vehicleId) {
        LOGGER.info("getVehicleDetails invoked for Vehicle Id {}", vehicleId);
        return new ResponseEntity<>(vehicleService.fetchVehicleById(vehicleId), HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves vehicle location details based on vehicle id")
    @ApiResponses({@ApiResponse(code = 200, message = "Vehicle details found and details returned"),
                   @ApiResponse(code = 400, message = "Vehicle id is invalid"),
                   @ApiResponse(code = 404, message = "Vehicle details not found.")})
    @GetMapping("/{vehicleId}/details")
    public ResponseEntity<VehicleDetails> getVehicleDetails(@PathVariable String vehicleId) {
        LOGGER.info("getVehicleDetails invoked for Vehicle Id {}", vehicleId);
        return new ResponseEntity<>(vehicleService.fetchVehicleLocationDetails(vehicleId), HttpStatus.OK);
    }
}
