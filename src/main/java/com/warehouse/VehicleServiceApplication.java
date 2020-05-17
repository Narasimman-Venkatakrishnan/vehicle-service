package com.warehouse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.domain.Warehouse;
import com.warehouse.services.VehicleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class VehicleServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(VehicleService service) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Warehouse>> typeReference = new TypeReference<List<Warehouse>>() {
            };
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data.json");
            try {
                List<Warehouse> warehouseList = mapper.readValue(inputStream, typeReference);
                service.saveWarehouseList(warehouseList);
                System.out.println("Warehouse Saved!");
            } catch (IOException e) {
                System.out.println("Unable to save Warehouse: " + e.getMessage());
            }
        };
    }
}
