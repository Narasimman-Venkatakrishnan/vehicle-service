package com.warehouse.domain;

import java.math.BigDecimal;

public class VehicleDetails {

    private String warehouseId;
    private String warehouseName;
    private BigDecimal warehouseLocationLatitude;
    private BigDecimal warehouseLocationLongitude;
    private String carLocation;

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public BigDecimal getWarehouseLocationLatitude() {
        return warehouseLocationLatitude;
    }

    public void setWarehouseLocationLatitude(BigDecimal warehouseLocationLatitude) {
        this.warehouseLocationLatitude = warehouseLocationLatitude;
    }

    public BigDecimal getWarehouseLocationLongitude() {
        return warehouseLocationLongitude;
    }

    public void setWarehouseLocationLongitude(BigDecimal warehouseLocationLongitude) {
        this.warehouseLocationLongitude = warehouseLocationLongitude;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }
}
