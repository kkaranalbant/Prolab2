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
public class Sida extends SeaVehicle {

    private final static int DEFAULT_DEFENCE;
    private final static int DEFAULT_ATTACK;

    static {
        DEFAULT_DEFENCE = 15;
        DEFAULT_ATTACK = 10;
    }

    private Sida(Long id, Integer levelPoint, String name, List<Advantage> advantageTypes, Boolean isForHuman) {
        super(id, levelPoint, DEFAULT_DEFENCE, DEFAULT_ATTACK, VehicleType.SEA, name, advantageTypes, isForHuman, true);
    }

    public static Sida createSida(Integer levelPoint, Long id, Boolean isForHuman) {
        Advantage airAdvantage = new Advantage(VehicleType.AIR, 10);
        Advantage landAdvantage = new Advantage(VehicleType.LAND, 10);
        List<Advantage> advantageTypes = new ArrayList();
        advantageTypes.add(airAdvantage);
        advantageTypes.add(landAdvantage);
        return new Sida(id, levelPoint, "SIDA", advantageTypes, isForHuman);
    }
}
