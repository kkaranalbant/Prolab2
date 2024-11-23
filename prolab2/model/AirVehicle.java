/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab2.model;

import java.util.List;

/**
 *
 * @author kaan
 */
public abstract class AirVehicle extends WarVehicle {

    public AirVehicle(Long id, Integer levelPoint, Integer defence, Integer attack, VehicleType vehicleType, String name, List<Advantage> advantageTypes, Boolean isForHuman, Boolean isActive) {
        super(id, levelPoint, defence, attack, vehicleType, name, advantageTypes, isForHuman, isActive);
    }

}
