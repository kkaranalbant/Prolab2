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
public class Jet extends AirVehicle {

    private final static int DEFAULT_DEFENCE;
    private final static int DEFAULT_ATTACK;

    static {
        DEFAULT_DEFENCE = 20;
        DEFAULT_ATTACK = 10;
    }

    private Jet(Long id , Integer levelPoint, String name, List<Advantage> advantageTypes , Boolean isForHuman) {
        super(id ,levelPoint, DEFAULT_DEFENCE, DEFAULT_ATTACK, VehicleType.AIR, name, advantageTypes , isForHuman , true);
    }

    public static Jet createJet(Integer levelPoint, Long id  , Boolean isForHuman) {
        Advantage landAdvantage = new Advantage(VehicleType.LAND, 10);
        List<Advantage> advantageTypes = new ArrayList();
        advantageTypes.add(landAdvantage);
        return new Jet(id , levelPoint, "JET", advantageTypes , isForHuman);
    }

}
