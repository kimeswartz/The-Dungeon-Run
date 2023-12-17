package com.kimswartz.app.gamePlay;

import com.kimswartz.app.DungeonGameDatabase;
import com.kimswartz.app.fighters.Monster;

import static com.kimswartz.app.colors.ChooseColors.*;

import java.util.ArrayList;
import java.util.List;

public class DefeatedMonsters {



    static List<Monster> listOfDeadMonsters = new ArrayList<>();

    public static void addDefeatedMonsters(Monster monster) {

        listOfDeadMonsters.add(monster);

    }

    public static void printAllDefeatedMonsters() {

        // Print all names in the ArrayList
        System.out.println(YELLOW + "Defeated monsters:" + RESET);
        for (Monster name : listOfDeadMonsters) {
            System.out.println(name);
        }

    }

}
