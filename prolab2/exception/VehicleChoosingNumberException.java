/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kaan
 */

package prolab2.exception;

public class VehicleChoosingNumberException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Please choose the right number of vehicles";
    }
}
