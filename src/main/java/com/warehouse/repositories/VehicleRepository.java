package com.warehouse.repositories;

import com.warehouse.domain.Warehouse;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<Warehouse, String> {

}
