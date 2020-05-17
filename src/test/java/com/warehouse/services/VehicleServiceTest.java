package com.warehouse.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.domain.Vehicle;
import com.warehouse.domain.VehicleDetails;
import com.warehouse.domain.Warehouse;
import com.warehouse.exception.VehicleNotFoundException;
import com.warehouse.repositories.VehicleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataMongoTest
public class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;
    Iterable<Warehouse> warehouseIterable;
    @MockBean
    VehicleServiceImpl vehicleService;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleServiceImpl(vehicleRepository);

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Iterable<Warehouse>> typeReference = new TypeReference<Iterable<Warehouse>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
        try {
            warehouseIterable = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            System.out.println("Unable to save Warehouse: " + e.getMessage());
        }

        when(vehicleRepository.findAll()).thenReturn(warehouseIterable);

    }

    @Test
    public void shouldReturnVehiclesList() {
        List<Vehicle> vehicles = vehicleService.fetchVehicles();
        Assert.assertNotNull(vehicles);
        Assert.assertEquals(80, vehicles.size());
    }

    @Test
    public void shouldReturnVehicle() {
        Vehicle vehicle = vehicleService.fetchVehicleById("55");
        Assert.assertNotNull(vehicle);
        Assert.assertEquals("55", vehicle.get_id());
    }

    @Test
    public void shouldReturnVehicleDetails() {
        VehicleDetails vehicleDetails = vehicleService.fetchVehicleLocationDetails("55");
        Assert.assertNotNull(vehicleDetails);
        Assert.assertEquals("3", vehicleDetails.getWarehouseId());
    }

    @Test(expected = VehicleNotFoundException.class)
    public void shouldNotReturnVehicle() {
        vehicleService.fetchVehicleById("85");
    }

    @Test(expected = VehicleNotFoundException.class)
    public void shouldNotReturnVehicleDetails() {
        vehicleService.fetchVehicleLocationDetails("85");
    }

}
