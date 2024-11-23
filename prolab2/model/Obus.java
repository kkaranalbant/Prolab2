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
public class Obus extends LandVehicle {
    
    private final static int DEFAULT_DEFENCE;
    private final static int DEFAULT_ATTACK;

    static {
        DEFAULT_DEFENCE = 20;
        DEFAULT_ATTACK = 10;
    }
    
    private Obus(Long id ,Integer levelPoint, String name, List<Advantage> advantageTypes , Boolean isForHuman ) {
        super(id , levelPoint, DEFAULT_DEFENCE, DEFAULT_ATTACK, VehicleType.LAND, name, advantageTypes , isForHuman , true);
    }

    public static Obus createObus (Integer levelPoint, Long id , Boolean isForHuman) {
        Advantage seaAdvantage = new Advantage (VehicleType.SEA , 5) ;
        List<Advantage> advantageTypes = new ArrayList();
        advantageTypes.add(seaAdvantage);
        return new Obus (id  , levelPoint, "OBUS", advantageTypes , isForHuman);
    }
}
