/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab2.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kaan
 */
public class Siha extends AirVehicle {
    
    private final static int DEFAULT_DEFENCE;
    private final static int DEFAULT_ATTACK;

    static {
        DEFAULT_DEFENCE = 15;
        DEFAULT_ATTACK = 10;
    }
    
    private Siha(Long id ,Integer levelPoint, String name, List<Advantage> advantageTypes , Boolean isForHuman) {
        super(id , levelPoint, DEFAULT_DEFENCE, DEFAULT_ATTACK, VehicleType.AIR, name, advantageTypes , isForHuman , true);
    }

    public static Siha createSiha (Integer levelPoint, Long id , Boolean isForHuman) {
        Advantage landAdvantage = new Advantage(VehicleType.LAND, 10);
        Advantage seaAdvantage = new Advantage (VehicleType.SEA , 10) ;
        List<Advantage> advantageTypes = new ArrayList();
        advantageTypes.add(landAdvantage);
        advantageTypes.add(seaAdvantage);
        return new Siha (id , levelPoint, "SIHA", advantageTypes , isForHuman);
    }
    
}
