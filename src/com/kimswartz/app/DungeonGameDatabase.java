package com.kimswartz.app;

import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DungeonGameDatabase {

    // Replace with your MariaDB connection details
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/Dungeon_Run_Savings";
    private String username = "root";
    private String password = "erik";

    public void openGameDatabase(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save player data
            savePlayerData(connection, player.getID(), player.getName(), player.getStrength(), player.getIntelligence(), player.getAgility(), player.getLevel(), player.getCoins());

            //retrieveAndDisplayPlayers(connection);
            displayPlayerInitialStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void monsterToDatabase(Monster monster, Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save player data
            saveMonsterData(connection, monster.getName(), monster.getStrength(), player.getID());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Save to 'MonsterDefeatedByPlayer' table
    public void saveMonsterToGameDatabase(Monster monster, Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {


            saveDefeatedMonsterData(connection, monster.getName(), player.getID(), player.getName());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save to 'PlayerDefeatedByMonster' table
    public void savePlayerDefeatedByMonsterDatabase(Player player, Monster monster) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save monster data
            saveDefeatedPlayerData(connection, player.getID(), player.getName(), monster.getName(), player.getID());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 'Auto save' player status method
    public void updatePlayerToDatabase(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            updatePlayerStatusToDatabase(connection, player.getID(), player.getName(), player.getStrength(), player.getIntelligence(), player.getAgility(), player.getLevel(), player.getCoins());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Table: PlayerAutoSave
    public void savePlayerData(Connection connection, int PlayerID, String PlayerName, int PlayerStrenght, int PlayerIntelligence, int PlayerAgility, int PlayerLevel, int PlayerCoins) {

        String insertPlayerSql = "INSERT INTO PlayerAutoSave (PlayerID, PlayerName, PlayerStrenght, PlayerIntelligence, PlayerAgility, PlayerLevel, PlayerCoins) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {
            preparedStatement.setInt(1, PlayerID);
            preparedStatement.setString(2, PlayerName);
            preparedStatement.setInt(3, PlayerStrenght);
            preparedStatement.setInt(4, PlayerIntelligence);
            preparedStatement.setInt(5, PlayerAgility);
            preparedStatement.setInt(6, PlayerLevel);
            preparedStatement.setInt(7, PlayerCoins);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Player' successfully added to the database, rows: " + rowsInserted + "]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // Table: Monsters
    public void saveMonsterData(Connection connection, String MonsterName, int MonsterStrength, int PlayerById) {

        String insertMonsterSql = "INSERT INTO GameMonsters (MonsterName, MonsterStrength, PlayerById) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertMonsterSql)) {
            preparedStatement.setString(1, MonsterName);
            preparedStatement.setInt(2, MonsterStrength);
            preparedStatement.setInt(3, PlayerById);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Monster' was successfully added to the database, rows: " + rowsInserted + "]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void saveDefeatedMonsterData(Connection connection, String MonsterName, int PlayerID, String PlayerName) {

        String insertMonsterSql = "INSERT INTO MonsterDefeatedByPlayer (MonsterName, PlayerById, PlayerByName) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertMonsterSql)) {
            preparedStatement.setString(1, MonsterName);
            preparedStatement.setInt(2, PlayerID);
            preparedStatement.setString(3, PlayerName);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Defeated monster' was successfully added to the database, rows: " + rowsInserted + "]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveDefeatedPlayerData(Connection connection, int PlayerID, String PlayerByName, String MonsterName, int PlayerById) {

        String insertMonsterAndPlayerSql = "INSERT INTO PlayerDefeatedByMonster (PlayerID, PlayerByName, MonsterName, PlayerById) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertMonsterAndPlayerSql)) {
            preparedStatement.setInt(1, PlayerID);
            preparedStatement.setString(2, PlayerByName);
            preparedStatement.setString(3, MonsterName);
            preparedStatement.setInt(4, PlayerById);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Defeated player' was successfully added to the database, rows: " + rowsInserted + "]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Display player status
    private static void retrieveAndDisplayPlayers(Connection connection) {
        String selectPlayersSql = "SELECT * FROM PlayerAutoSave";


        try (PreparedStatement preparedStatement = connection.prepareStatement(selectPlayersSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Player Data:");
            System.out.println("Player ID | Player Name | Player Strength | Player Intelligence | Player Agility | Player Level | Player Coins");
            System.out.println("----------------------------------");

            while (resultSet.next()) {
                Integer playerId = resultSet.getInt("PlayerID");
                String playerName = resultSet.getString("PlayerName");
                Integer PlayerStrenght = resultSet.getInt("PlayerStrenght");
                Integer playerIntelligence = resultSet.getInt("PlayerIntelligence");
                Integer playerAgility = resultSet.getInt("PlayerAgility");
                Integer playerLevel = resultSet.getInt("PlayerLevel");
                Integer playerCoins = resultSet.getInt("PlayerCoins");

                System.out.println(playerId + " | " + playerName + " | " + PlayerStrenght + " | " + playerIntelligence + " | " + playerAgility + " | " + playerLevel + " | " + playerCoins);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Display player status
    private static void displayPlayerInitialStatus(Connection connection, Player player) {

        String selectPlayersSql = "SELECT PlayerName, PlayerStrenght, PlayerIntelligence, PlayerAgility, PlayerLevel, PlayerCoins FROM PlayerAutoSave WHERE PlayerID = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectPlayersSql)) {
            // Assuming playerId is the value you want to retrieve
            int playerId = player.getID();  // Replace with the actual PlayerID value

            // Set the parameter value for the placeholder (?)
            selectStatement.setInt(1, playerId);

            // Execute the query
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve and display the data
                    String playerName = resultSet.getString("PlayerName");
                    int playerStrength = resultSet.getInt("PlayerStrenght");
                    int playerIntelligence = resultSet.getInt("PlayerIntelligence");
                    int playerAgility = resultSet.getInt("PlayerAgility");
                    int playerLevel = resultSet.getInt("PlayerLevel");
                    int playerCoins = resultSet.getInt("PlayerCoins");

                    // Perform further processing or display the data as needed
                    System.out.println(CYAN + "Player's properties retrieved from database: ");

                    System.out.println("PlayerName: " + playerName);
                    System.out.println("PlayerStrength: " + playerStrength);
                    System.out.println("PlayerIntelligence: " + playerIntelligence);
                    System.out.println("PlayerAgility: " + playerAgility);
                    System.out.println("PlayerLevel: " + playerLevel);
                    System.out.println("PlayerCoins: " + playerCoins + RESET);
                } else {
                    System.out.println("Player not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    //Update Player for each loop FRÅGA OM WHERE PÅ DENNA ANG ID
    private static void updatePlayerStatusToDatabase(Connection connection, int PlayerID, String PlayerName, int PlayerStrenght, int PlayerIntelligence, int PlayerAgility, int PlayerLevel, int PlayerCoins) throws SQLException {
        // SQL UPDATE statement
        String updateSql = "UPDATE PlayerAutoSave SET PlayerName =?, PlayerStrenght = ?, PlayerIntelligence = ?, PlayerAgility = ?, PlayerLevel = ?, PlayerCoins = ? WHERE PlayerID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            // Set parameters

            preparedStatement.setString(1, PlayerName);
            preparedStatement.setInt(2, PlayerStrenght);
            preparedStatement.setInt(3, PlayerIntelligence);
            preparedStatement.setInt(4, PlayerAgility);
            preparedStatement.setInt(5, PlayerLevel);
            preparedStatement.setInt(6, PlayerCoins);
            preparedStatement.setInt(7, PlayerID);


            // Execute the update
            preparedStatement.executeUpdate();
            System.out.println(CYAN + "'Player Status' auto-saved successfully to database" + RESET);
        }

    }

}