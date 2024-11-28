/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prolab2.service;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import prolab2.exception.NotActiveVehicleChoosingException;
import prolab2.exception.VehicleChoosingNumberException;
import prolab2.model.Advantage;
import prolab2.model.Frigate;
import prolab2.model.Jet;
import prolab2.model.KFS;
import prolab2.model.Obus;
import prolab2.model.Sida;
import prolab2.model.Siha;
import prolab2.model.VehicleType;
import prolab2.model.WarVehicle;


/**
 *
 * @author kaan
 */
public class WarMechanic {

    private static WarMechanic warMechanic;

    private Integer maxStepNumber;

    private Integer currentStepNumber;

    private Random random;

    private Map<Integer, TriFunction<Integer, Long, Boolean, WarVehicle>> valueMethodMap = new HashMap<>(); //hata düzelttim

    private Integer humanScore;

    private Integer computerScore;

    private Integer scoreBound;

    private Integer defaultLevelPoint;

    private Long idOrigin;

    private Long idBound;

    private List<WarVehicle> vehicles;

    private WarMechanic(Integer maxStepNumber, Integer scoreBound, Integer defaultLevelPoint) {

        valueMethodMap = new HashMap<>();

        currentStepNumber = 0;
        valueMethodMap.put(1, Jet::createJet);
        valueMethodMap.put(2, Obus::createObus);
        valueMethodMap.put(3, Frigate::createFrigate);
        valueMethodMap.put(4, Siha::createSiha);
        valueMethodMap.put(5, KFS::createKfs);
        valueMethodMap.put(6, Sida::createSida);
        random = new Random();
        humanScore = 0;
        computerScore = 0;
        vehicles = new ArrayList<>(); //hatayı düzelttim
        idOrigin = 1000L;
        idBound = 10000L;
        this.scoreBound = scoreBound;
        this.defaultLevelPoint = defaultLevelPoint;
        this.maxStepNumber = maxStepNumber;
    }

    public static WarMechanic getInstance(Integer maxStepNumber, Integer scoreBound, Integer defaultLevelPoint) {
        if (warMechanic == null) {
            warMechanic = new WarMechanic(maxStepNumber, scoreBound, defaultLevelPoint);
        }
        return warMechanic;
    }

    /*
    Mekanik su sekilde isleyecek : 
    Once init metodu kullanilacak 6 sar tane iki tarafa da kart dagitilacak 
    Bu kartlari GUI de kullanmak icin vehicles listesi kullanilacak
    Daha sonra ilk round icin once getRandomWarVehiclesForFirstRound metodu kullanilarak bilgisayar icin 3 tane kart sectirilir.
    Daha sonra kullanicinin sectigi 3 kart bir listeye koyulur .
    Olusturulan bu iki liste war metoduna parametre olarak verilir .
    Diger adima gecmeden once createWarVehicleForHuman createWarVehicleForComputer metodu kullanilarak yeni araclar olusturulur bu araclarin GUI de kullanilmasi icin degerler geriye donduruluyo
    1. rounddan sonrasi icin getRandomWarVehicleForOtherRounds metodu kullanilarak bilgisayara bir kart sectirilir ve kullanicinin secmis oldugu kart war metoduna parametre olarak verilir
    war metodu calistiktan sonra isWarOverByStepNumber metodundan degere gore savasin bitip bitmedigi anlasilir.
    Eger bitmisse getWarResultMessage ile savas sonucunun mesaji geriye dondurulur
    egerki bu metottan false donerse diger iki savas bitisini kontrol eden metotlar kontrol edilmeli
    isWarOverByVehiclesForHuman metodundan true deger donerse savas 1 adim sonra bitecektir. 
    Bu adima baslamadan once insan icin olusturulacak vehicle sayisi 2 olmali bu yuzden createWarVehicleForHuman metodu parametre olarak 2 degeri almali
    Daha sonra birdaha son olarak war metodu calistirilmali ve getWarResultMessage metodundan donen deger ekrana basilmali ve savas bitmeli
    isWarOverByVehiclesForHuman metodundan false deger donmusse bu sefer geriye son olarak kalan isWarOverByVehiclesForComputer metodu kontrol edilmeli 
    Eger bu metottan geriye true deger donerse createWarVehicleForComputer metoduna parametre olarak 2 verilmeli son olarak war metodu calistirilmali ve getWarResultMessage metodundan donen deger ekrana basilmali ve savas bitmeli
        
     */
    public void init() {
        makeBeginningDistribution();
    }

    public void war(List<WarVehicle> choosenVehicles, List<WarVehicle> choosenComputerVehicles) throws VehicleChoosingNumberException, NotActiveVehicleChoosingException {
        if (choosenVehicles.isEmpty()) {
            throw new VehicleChoosingNumberException();
        }
        throwExceptionIfNotActiveChoosenVehicle(choosenVehicles);
        if (currentStepNumber == 0) {
            if (choosenVehicles.size() != 3) {
                throw new VehicleChoosingNumberException();
            }
            for (WarVehicle vehicle : choosenVehicles) {
                for (WarVehicle choosenComputerVehicle : choosenComputerVehicles) {
                    choosenComputerVehicle.setDefence(choosenComputerVehicle.getDefence() - getAttackAmount(vehicle, choosenComputerVehicle));
                    vehicle.setDefence(vehicle.getDefence() - getAttackAmount(choosenComputerVehicle, vehicle));
                }
            }

        } else {
            if (choosenVehicles.size() != 1) {
                throw new VehicleChoosingNumberException();
            }
            WarVehicle choosenComputerVehicle = choosenComputerVehicles.get(0);
            WarVehicle vehicle = choosenVehicles.get(0);
            vehicle.setDefence(vehicle.getDefence() - getAttackAmount(choosenComputerVehicle, vehicle));
            choosenComputerVehicle.setDefence(choosenComputerVehicle.getDefence() - getAttackAmount(vehicle, choosenComputerVehicle));
            if (vehicle.getDefence() <= 0) {
                int bonusAmount = 0;
                if (vehicle.getLevelPoint() < 10) {
                    bonusAmount = 10;
                } else {
                    bonusAmount = vehicle.getLevelPoint();
                }
                computerScore += bonusAmount;
                choosenComputerVehicle.setLevelPoint(choosenComputerVehicle.getLevelPoint() + bonusAmount);

            }
            if (choosenComputerVehicle.getDefence() <= 0) {
                int bonusAmount = 0;
                if (choosenComputerVehicle.getLevelPoint() < 10) {
                    bonusAmount = 10;
                } else {
                    bonusAmount = vehicle.getLevelPoint();
                }
                humanScore += bonusAmount;
                vehicle.setLevelPoint(vehicle.getLevelPoint() + bonusAmount);
            }
            controlVehicles(List.of(choosenComputerVehicle, vehicle));
        }
        currentStepNumber++;
    }

    public boolean isWarOverByStepNumber() {
        return currentStepNumber >= maxStepNumber;
    }

    public boolean isWarOverByVehiclesForHuman() {
        return getActiveHumanVehicleCount() == 1;
    }

    public boolean isWarOverByVehiclesForComputer() {
        return getActiveComputerVehicleCount() == 1;
    }

    private Integer getActiveHumanVehicleCount() {
        Integer result = 0;
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getIsActive() && vehicle.getIsForHuman()) {
                result++;
            }
        }
        return result;
    }

    private Integer getActiveComputerVehicleCount() {
        Integer result = 0;
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getIsActive() && !vehicle.getIsForHuman()) {
                result++;
            }
        }
        return result;
    }

    public String getWarResultMessage() {
        StringBuilder message = new StringBuilder();
        if (humanScore > computerScore) {
            message.append("Tebrikler ! Kazandiniz.\n").append("Skorunuz : ")
                    .append(humanScore).append("\n Karsi tarafin skoru : ").append(computerScore)
                    .append("\n");
        } else if (humanScore < computerScore) {
            message.append("Kaybettiniz\n").append("Skorunuz : ")
                    .append(humanScore).append("\n Karsi tarafin skoru : ").append(computerScore)
                    .append("\n");
        } else {
            Integer humanDefence = getTotalDefenceAmountOfActiveHumanVehicles();
            Integer computerDefence = getTotalDefenceAmountOfActiveComputerVehicles();
            if (humanDefence > computerDefence) {
                humanScore += (humanDefence - computerDefence);
                message.append("Tebrikler ! Kazandiniz.\n").append("Skorunuz : ")
                        .append(humanScore).append("\n Karsi tarafin skoru : ").append(computerScore)
                        .append("\n");
            } else if (humanDefence < computerDefence) {
                computerScore += (computerDefence - humanDefence);
                message.append("Kaybettiniz\n").append("Skorunuz : ")
                        .append(humanScore).append("\n Karsi tarafin skoru : ").append(computerScore)
                        .append("\n");
            } else {
                message.append("Berabere\n").append("Skorunuz : ")
                        .append(humanScore).append("\n Karsi tarafin skoru : ").append(computerScore)
                        .append("\n");
            }
        }
        return message.toString();
    }

    private Integer getTotalDefenceAmountOfActiveHumanVehicles() {
        Integer result = 0;
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getIsActive() && vehicle.getIsForHuman()) {
                result += vehicle.getDefence();
            }
        }
        return result;
    }

    private Integer getTotalDefenceAmountOfActiveComputerVehicles() {
        Integer result = 0;
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getIsActive() && !vehicle.getIsForHuman()) {
                result += vehicle.getDefence();
            }
        }
        return result;
    }

    private Integer getAttackAmount(WarVehicle attacker, WarVehicle defencer) {
        Integer totalAttackAmount = attacker.getAttack();
        for (Advantage advantage : attacker.getAdvantageTypes()) {
            if (defencer.getVehicleType().equals(advantage.getVehicleType())) {
                totalAttackAmount += advantage.getAdvantageAmount();
                break;
            }
        }
        return totalAttackAmount;
    }

    private void controlVehicles(List<WarVehicle> vehicles) {
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getDefence() <= 0) {
                vehicle.setIsActive(Boolean.FALSE);
            }
        }
    }

    private void throwExceptionIfNotActiveChoosenVehicle(List<WarVehicle> vehicles) throws NotActiveVehicleChoosingException {
        for (WarVehicle vehicle : vehicles) {
            if (!vehicle.getIsActive()) {
                throw new NotActiveVehicleChoosingException();
            }
        }
    }

    public List<WarVehicle> createWarVehicleForHuman(Integer count) {
        List<WarVehicle> newVehicles = new LinkedList();
        for (int i = 0; i < count; i++) {
            int randomNumber = 0;
            if (humanScore.intValue() < scoreBound.intValue()) {
                randomNumber = random.nextInt(1, 4);
            } else {
                randomNumber = random.nextInt(1, 7);
            }
            TriFunction<Integer, Long, Boolean, WarVehicle> function = valueMethodMap.get(randomNumber);
            WarVehicle vehicle = function.apply(defaultLevelPoint, createId(), Boolean.TRUE);
            vehicles.add(vehicle);
            newVehicles.add(vehicle);
        }
        return newVehicles;
    }

    public List<WarVehicle> createWarVehicleForComputer(Integer count) {
        List<WarVehicle> newVehicles = new LinkedList();
        for (int i = 0; i < count; i++) {
            int randomNumber = 0;
            if (computerScore.intValue() < scoreBound.intValue()) {
                randomNumber = random.nextInt(1, 4);
            } else {
                randomNumber = random.nextInt(1, 7);
            }
            TriFunction<Integer, Long, Boolean, WarVehicle> function = valueMethodMap.get(randomNumber);
            WarVehicle vehicle = function.apply(defaultLevelPoint, createId(), Boolean.FALSE);
            vehicles.add(vehicle);
            newVehicles.add(vehicle);
        }
        return newVehicles;
    }

    private Long createId() {
        Long id = random.nextLong(idOrigin, idBound);
        while (!isValidId(id)) {
            id = random.nextLong(idOrigin, idBound);
        }
        return id;
    }

    private boolean isValidId(Long id) {
        for (WarVehicle vehicle : vehicles) {
            if (vehicle.getId().longValue() == id.longValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidDistributionForBeginningHuman() {
        boolean foundAirVehicle = false;
        boolean foundLandVehicle = false;
        boolean foundSeaVehicle = false;
        for (int i = 0; i < 6; i++) {
            WarVehicle vehicle = vehicles.get(i);
            if (vehicle.getVehicleType().equals(VehicleType.AIR)) {
                foundAirVehicle = true;
            } else if (vehicle.getVehicleType().equals(VehicleType.SEA)) {
                foundSeaVehicle = true;
            } else if (vehicle.getVehicleType().equals(VehicleType.LAND)) {
                foundLandVehicle = true;
            }
        }
        return foundAirVehicle && foundLandVehicle && foundSeaVehicle;
    }

    private boolean isValidDistributionForBeginningComputer() {
        boolean foundAirVehicle = false;
        boolean foundLandVehicle = false;
        boolean foundSeaVehicle = false;
        for (int i = 6; i < 12; i++) {
            WarVehicle vehicle = vehicles.get(i);
            if (vehicle.getVehicleType().equals(VehicleType.AIR)) {
                foundAirVehicle = true;
            } else if (vehicle.getVehicleType().equals(VehicleType.SEA)) {
                foundSeaVehicle = true;
            } else if (vehicle.getVehicleType().equals(VehicleType.LAND)) {
                foundLandVehicle = true;
            }
        }
        return foundAirVehicle && foundLandVehicle && foundSeaVehicle;
    }

    private void makeBeginningDistribution() {
        for (int i = 0; i < 6; i++) {
            createWarVehicleForHuman(1);
        }
        while (!isValidDistributionForBeginningHuman()) {
            vehicles.clear();
            for (int i = 0; i < 6; i++) {
                createWarVehicleForHuman(1);
            }
        }
        for (int i = 0; i < 6; i++) {
            createWarVehicleForComputer(1);
        }
        while (!isValidDistributionForBeginningComputer()) {
            for (int i = 6; i < 12; i++) {
                vehicles.remove(i);
            }
            for (int i = 0; i < 6; i++) {
                createWarVehicleForComputer(1);
            }
        }
    }

    public List<WarVehicle> getRandomWarVehiclesForFirstRound() {
        List<WarVehicle> randomVehiclesForFirstRound = new LinkedList();
        while (randomVehiclesForFirstRound.size() != 3) {
            int randomIndex = random.nextInt(0, vehicles.size() + 1);
            WarVehicle vehicle = vehicles.get(randomIndex);
            if (!vehicle.getIsForHuman() && vehicle.getIsActive()) {
                randomVehiclesForFirstRound.add(vehicle);
            }
        }
        return randomVehiclesForFirstRound;
    }

    public WarVehicle getRandomWarVehicleForOtherRounds() {
        WarVehicle randomVehicle = null;
        boolean found = false;
        while (!found) {
            int randomIndex = random.nextInt(0, vehicles.size() + 1);
            WarVehicle vehicle = vehicles.get(randomIndex);
            if (!vehicle.getIsForHuman() && vehicle.getIsActive()) {
                randomVehicle = vehicle;
                found = true;
            }
        }
        return randomVehicle;
    }

    public List<WarVehicle> getVehicles() {
        return vehicles;
    }

    public Integer getMaxStepNumber() {
        return maxStepNumber;
    }

    public Integer getCurrentStepNumber() {
        return currentStepNumber;
    }

    public Integer getHumanScore() {
        return humanScore;
    }

    public Integer getComputerScore() {
        return computerScore;
    }


}
