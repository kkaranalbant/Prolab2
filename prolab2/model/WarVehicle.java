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
public abstract class WarVehicle {

    private Long id;
    private Integer levelPoint;
    private Integer defence;
    private Integer attack;
    private VehicleType vehicleType;
    private String name;
    private List<Advantage> advantageTypes;
    private Boolean isForHuman;
    private Boolean isActive;

    public WarVehicle(Long id, Integer levelPoint, Integer defence, Integer attack, VehicleType vehicleType, String name, List<Advantage> advantageTypes, Boolean isForHuman, Boolean isActive) {
        this.id = id;
        this.levelPoint = levelPoint;
        this.defence = defence;
        this.attack = attack;
        this.vehicleType = vehicleType;
        this.name = name;
        this.advantageTypes = advantageTypes;
        this.isForHuman = isForHuman;
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id : ").append(id).append("\n").append("Name : ").append(name).append("\n")
                .append("Level Point : ").append(levelPoint.intValue()).append("\n")
                .append("Defence : ").append(defence.intValue()).append("\n")
                .append("Attack : ").append(attack.intValue()).append("\n")
                .append("Vehicle Type : ").append(vehicleType.name()).append("\n");
        for (Advantage advantage : advantageTypes) {
            sb.append("Advantage Type : ").append(advantage.getVehicleType().name()).append("\n")
                    .append("Advantage Amount : ").append(advantage.getAdvantageAmount()).append("\n");
        }
        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public Integer getLevelPoint() {
        return levelPoint;
    }

    public Integer getDefence() {
        return defence;
    }

    public Integer getAttack() {
        return attack;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getName() {
        return name;
    }

    public List<Advantage> getAdvantageTypes() {
        return advantageTypes;
    }

    public Boolean getIsForHuman() {
        return isForHuman;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevelPoint(Integer levelPoint) {
        this.levelPoint = levelPoint;
    }

    public void setDefence(Integer defence) {
        this.defence = defence;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdvantageTypes(List<Advantage> advantageTypes) {
        this.advantageTypes = advantageTypes;
    }

    public void setIsForHuman(Boolean isForHuman) {
        this.isForHuman = isForHuman;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    

}
