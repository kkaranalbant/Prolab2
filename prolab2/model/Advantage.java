/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab2.model;

/**
 *
 * @author kaan
 */
public class Advantage {

    private VehicleType vehicleType;

    private Integer advantageAmount;

    public Advantage(VehicleType vehicleType, Integer advantageAmount) {
        this.vehicleType = vehicleType;
        this.advantageAmount = advantageAmount;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Integer getAdvantageAmount() {
        return advantageAmount;
    }
    
    
    
}
