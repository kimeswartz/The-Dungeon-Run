import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

public class PlayerTest {

    static Player player = new Player();

    // Instantiation of monsters, these are saved in our ArrayList 'monsterList'.
    static Monster monster1 = new Monster(10, 10, 0, "Monster1");
    static Monster monster2 = new Monster(10, 10, 0, "Monster2");
    static Monster monster3 = new Monster(10, 10, 0, "Monster3");

    public static void setPlayer() {

        player.setHealth(10);
        player.setStrength(0);
        player.setAgility(10);
        player.setIntelligence(10);
        player.setExperience(200);
        player.setAgility(10);
        player.setLevel(2);
        player.getDamage();
        player.increaseLevel(0);

    }

    @Test
    @DisplayName("Remove Monster from MonsterList when Health is below >= 0")
    public void TestMonsterListEmpty() {

        List<Monster> monsterList = new ArrayList<>();

        // We add three test monsters to ArrayList
        monsterList.add(monster1);
        monsterList.add(monster2);
        monsterList.add(monster3);

        // We damage monster2 to set its health to '<= 0'
        int damageToMonster = 30;
        monster2.setHealth(monster2.getHealth() - damageToMonster);

        // Remove monster from List if health is '<= 0'.
        if (monster2.getHealth() <= 0) {
            monsterList.remove(monster2);
        }

        assertEquals(2, monsterList.size());

    }


    @Test
    @DisplayName("My level up test")
    public void TestPlayerLevelUp() {

        player.increaseLevel(50);
        player.getExperience();

        int LevelStatusWithExperience = player.getLevel();

        assertEquals(1, LevelStatusWithExperience);

    }

    @Test
    @DisplayName("Check if damage on a player is true")

    public void TestPlayerHealth() {

        player.setHealth(5);
        player.takeDamage(2);

        int healthAfterDamage = player.getHealth();

        assertEquals(3, healthAfterDamage);

    }

}