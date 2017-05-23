package World;

import java.util.ArrayList;

import Warriors.Warrior;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import testing.displayConfig;

public class World {

    public static Clock WorldClock;
    //periodic timer
    public Timer timer = new Timer();
    //Cities start from Red Headquarters and end with Blue Headquarters
    public static ArrayList<City> CityList;

    public World(int task) {

        //initialize clock
        WorldClock = new Clock();

        //initialize Cities
        CityList = new ArrayList<City>();

        City c0 = task == 2 ? new PlayerHeadquarters(WorldProperty.RedProductionOrder, WorldProperty.RED, WorldProperty.InitLifeElements, 0)
                : new Headquarters(WorldProperty.RedProductionOrder, WorldProperty.RED, WorldProperty.InitLifeElements, 0);
        CityList.add(c0);

        for (int i = 1; i <= WorldProperty.NumberOfCity; i++) {
            City c = new City(i);
            CityList.add(c);
        }

        City c_last = new Headquarters(WorldProperty.BlueProductionOrder, WorldProperty.BLUE, WorldProperty.InitLifeElements, WorldProperty.NumberOfCity + 1);
        CityList.add(c_last);

    }

    public void runGame() {

        IntegerProperty minutes = new SimpleIntegerProperty(0);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    WorldClock.increase();
                    if (WorldClock.getMinute() == 0) {
                        ((Headquarters) CityList.get(0)).tryToProduceWarrior();
                        ((Headquarters) CityList.get(WorldProperty.NumberOfCity + 1)).tryToProduceWarrior();
                    }
                    // :10 March
                    if (WorldClock.getMinute() == 10) {
                        marchWarriors();

                        //Check End Of Game
                        Headquarters RedHeadquarters = (Headquarters) CityList.get(0);
                        Headquarters BlueHeadquarters = (Headquarters) CityList.get(WorldProperty.NumberOfCity + 1);
                        boolean RedOcuupied = RedHeadquarters.checkOccupied();
                        boolean BlueOcuupied = BlueHeadquarters.checkOccupied();
                        if (RedOcuupied || BlueOcuupied) {                            
                            timer.cancel();
                            return;
                        }
                    }
                    // :20 Produce Life Elements
                    if (WorldClock.getMinute() == 20) {
                        ProduceLifeElements();
                    }

                    // :30 Warriors Fetch Life Elements to their headquarters
                    if (WorldClock.getMinute() == 30) {
                        warriorsFetchLifeElementsFromCity();
                    }

                    // :40 Organize Battels (Core function.)
                    if (WorldClock.getMinute() == 40) {
                        holdBattlesAndWorkAfterBattles();
                    }

                    // :50 Headquarters report Life Elements
                    if (WorldClock.getMinute() == 50) {
                        headquartersReportLifeElements();
                    }

                    displayConfig.updateBoard();
                    WorldClock.getTime();

                    if (minutes.get() >= WorldProperty.MaxMinutes) {
                        timer.cancel();
                    }
                    minutes.set(minutes.get() + 10);
                });
            }
        }, 0, 10000);

    }

    /**
     *
     */
    private void holdBattlesAndWorkAfterBattles() {
        for (int index = 1; index <= WorldProperty.NumberOfCity; index++) {
            City c = CityList.get(index);
            c.organizeBattle();
        }

        //reward army.
        Headquarters RedHeadquarters = (Headquarters) CityList.get(0);
        Headquarters BlueHeadquarters = (Headquarters) CityList.get(WorldProperty.NumberOfCity + 1);
        RedHeadquarters.rewardArmy();
        BlueHeadquarters.rewardArmy();

        //collectMoneyFromCity();
        for (int i = 1; i <= WorldProperty.NumberOfCity; i++) {
            City c = CityList.get(i);
            c.payTribute();
        }
    }

    /**
     *
     */
    private void headquartersReportLifeElements() {
        //000:50 100 elements in red headquarter
        Headquarters RedHeadquarters = (Headquarters) CityList.get(0);
        Headquarters BlueHeadquarters = (Headquarters) CityList.get(WorldProperty.NumberOfCity + 1);
        System.out.format("%s %d elements in red headquarter\n", WorldClock.getTime(), RedHeadquarters.LifeElement);
        System.out.format("%s %d elements in blue headquarter\n", WorldClock.getTime(), BlueHeadquarters.LifeElement);
    }

    //After March
    private void warriorsFetchLifeElementsFromCity() {
        Headquarters RedHeadquarters = (Headquarters) CityList.get(0);
        Headquarters BlueHeadquarters = (Headquarters) CityList.get(WorldProperty.NumberOfCity + 1);

        for (int i = 1; i <= WorldProperty.NumberOfCity; i++) {
            City c = CityList.get(i);
            //Empty City
            if (c.BlueWarriorStation.isEmpty() && c.RedWarriorStation.isEmpty()) {
                continue;
            } //Red Fetch
            else if (c.BlueWarriorStation.isEmpty()) {
                Warrior w = c.RedWarriorStation.get(0);
                //000:30 red iceman 1 earned 10 elements for his headquarter
                System.out.format("%s %s earned %d elements for his headquarter\n", WorldClock.getTime(), w.WarriorNameCard, c.LifeElement);
                RedHeadquarters.addLifeElement(c.popLifeElements());
            } else if (c.RedWarriorStation.isEmpty()) {
                Warrior w = c.BlueWarriorStation.get(0);
                //000:30 red iceman 1 earned 10 elements for his headquarter
                System.out.format("%s %s earned %d elements for his headquarter\n", WorldClock.getTime(), w.WarriorNameCard, c.LifeElement);
                BlueHeadquarters.addLifeElement(c.popLifeElements());
            } else {
                // Two warriors in this city.
            }
        }
    }

    public void ProduceLifeElements() {
        for (City c : CityList) {
            if (!(c instanceof Headquarters)) {
                c.produceLifeElement();
            }
        }
    }

    //TODO: modify operation on CityList to Warrior.move()
    public void marchWarriors() {
        //March Red Warriors.		
        for (int i = WorldProperty.NumberOfCity; i >= 0; i--) {
            City city = CityList.get(i);
            while (!city.RedWarriorStation.isEmpty()) {
                city.RedWarriorStation.get(0).move();
            }
        }
        //March Blue Warriors.
        for (int i = 1; i <= WorldProperty.NumberOfCity + 1; i++) {
            City city = CityList.get(i);
            while (!city.BlueWarriorStation.isEmpty()) {
                city.BlueWarriorStation.get(0).move();
            }
        }
        //Report March Information
        Headquarters RedHeadquarters = (Headquarters) CityList.get(0);
        Headquarters BlueHeadquarters = (Headquarters) CityList.get(WorldProperty.NumberOfCity + 1);

        if (RedHeadquarters.checkNewArrival()) {
            warriorReportMarch(RedHeadquarters, RedHeadquarters.getNewArrivedWarrior());
            RedHeadquarters.clearNewArrival();
        }
        for (int index = 1; index <= WorldProperty.NumberOfCity; index++) {
            City c = CityList.get(index);
            for (Warrior w : c.RedWarriorStation) {
                warriorReportMarch(c, w);
            }
            for (Warrior w : c.BlueWarriorStation) {
                warriorReportMarch(c, w);
            }
        }
        if (BlueHeadquarters.checkNewArrival()) {
            warriorReportMarch(BlueHeadquarters, BlueHeadquarters.getNewArrivedWarrior());
            BlueHeadquarters.clearNewArrival();
        }
    }

    private void warriorReportMarch(City c, Warrior w) {
        if (c instanceof Headquarters) {
            //003:10 red lion 2 reached blue headquarter with 58 elements and force 50
            System.out.format("%s %s reached %s headquarter with %d elements and force %d\n",
                    WorldClock.getTime(), w.WarriorNameCard, WorldProperty.PartyNames[((Headquarters) c).getParty()], w.HP, w.AttackValue);
        } else {
            System.out.format("%s %s marched to city %d with %d elements and force %d\n",
                    WorldClock.getTime(), w.WarriorNameCard, c.CityID, w.HP, w.AttackValue);
        }
    }

}
