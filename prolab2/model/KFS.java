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
public class KFS extends LandVehicle {
    private final static int DEFAULT_DEFENCE;
    private final static int DEFAULT_ATTACK;

    static {
        DEFAULT_DEFENCE = 10;
        DEFAULT_ATTACK = 10;
    }
    
    private KFS(Long id ,Integer levelPoint, String name, List<Advantage> advantageTypes , Boolean isForHuman) {
        super(id , levelPoint, DEFAULT_DEFENCE, DEFAULT_ATTACK, VehicleType.LAND, name, advantageTypes , isForHuman , true);
    }

    public static KFS createKfs (Integer levelPoint, Long id  , Boolean isForHuman) {
        Advantage seaAdvantage = new Advantage (VehicleType.SEA , 10) ;
        Advantage airAdvantage = new Advantage (VehicleType.AIR , 20) ;
        List<Advantage> advantageTypes = new ArrayList();
        advantageTypes.add(seaAdvantage);
        advantageTypes.add(airAdvantage);
        return new KFS (id , levelPoint, "KFS", advantageTypes , isForHuman);
    }
}
