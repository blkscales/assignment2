package systemControl;

import java.util.Scanner;

//import org.apache.commons.cli.BasicParser;
//import org.apache.commons.cli.DefaultParser;
//import org.apache.commons.cli.Options;
import Warriors.WarriorType;
import World.*;

public class mainDriver {

    public static World the_world;

    public static void main(String[] args, int task) {
        getInputFromCMD(args);

        //getInputFromConsole();
        the_world = new World(task);
        the_world.runGame();
    }

    private static int myParseInt(String str, int def
    ) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("The value will be set with default value. ");
            return def;
        }
    }

    private static void getInputFromCMD(String[] args) {
        if (args.length == 13) {
            int M, N, T;
            M = myParseInt(args[0], 1000);
            N = myParseInt(args[1], 5);
            T = myParseInt(args[2], 240);

            boolean validM = M >= 1 && M <= 1000;
            boolean validN = N >= 1 && N <= 20;
            boolean validT = T >= 1 && T <= 10000;

            if (!(validM && validN && validT)) {
                System.out.println("Error: M,N,T not valid.");
                System.exit(0);
            }

            int[] HPs = new int[5];
            for (int i = 0; i < 5; i++) {
                HPs[i] = myParseInt(args[3 + i], 10);
                if (HPs[i] < 0 || HPs[i] > 100) {
                    System.out.println("Error HPs.");
                    System.exit(0);
                }
            }

            int[] attackValues = new int[5];
            for (int i = 0; i < 5; i++) {
                attackValues[i] = myParseInt(args[8 + i], 10);
                if (attackValues[i] < 0 || attackValues[i] > 100) {
                    System.out.println("Error Attack values.");
                    System.exit(0);
                }
            }

            WorldProperty.InitLifeElements = M;
            WorldProperty.NumberOfCity = N;
            WorldProperty.MaxMinutes = T;

            WarriorType.HP_LIST = HPs;
            WarriorType.ATTACK_LIST = attackValues;

        } else {
            System.out.println("Error: There should be 13 elements in total.");
            System.exit(0);
        }
    }

    private static void getInputFromConsole() {
        Scanner input = new Scanner(System.in);

        int M, N, T;
        M = input.nextInt();
        N = input.nextInt();
        T = input.nextInt();

        int[] HPs = new int[5];
        for (int i = 0; i < 5; i++) {
            HPs[i] = input.nextInt();
        }

        int[] attackValues = new int[5];
        for (int i = 0; i < 5; i++) {
            attackValues[i] = input.nextInt();
        }

        WorldProperty.InitLifeElements = M;
        WorldProperty.NumberOfCity = N;
        WorldProperty.MaxMinutes = T;

        WarriorType.HP_LIST = HPs;
        WarriorType.ATTACK_LIST = attackValues;
    }

}
