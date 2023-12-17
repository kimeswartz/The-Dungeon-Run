package com.kimswartz.app;

import com.kimswartz.app.fighters.Monster;
import com.kimswartz.app.fighters.Player;

import static com.kimswartz.app.colors.ChooseColors.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.ToDoubleBiFunction;

public class DungeonGameDatabase {

    // Replace with your MariaDB connection details
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/DungeonRun";
    private String username = "root";
    private String password = "erik";


    public void savePlayer(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save Player
            createPlayer(connection, player.getNumber(), player.getName(), player.getHealth(), player.getStrength(), player.getIntelligence(), player.getAgility(), player.getLevel(), player.getCoins());

            displayPlayerStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Push New Player
    public void createPlayer(Connection connection, int PlayerNumber, String PlayerName, int PlayerHealth, int PlayerStrength, int PlayerIntelligence, int PlayerAgility, int PlayerLevel, int PlayerCoins) {

        String insertPlayerSql = "INSERT INTO AutoSavePlayers (PlayerNumber, PlayerName, PlayerHealth, PlayerStrength, PlayerIntelligence, PlayerAgility, PlayerLevel, PlayerCoins) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {
            preparedStatement.setInt(1, PlayerNumber);
            preparedStatement.setString(2, PlayerName);
            preparedStatement.setInt(3, PlayerHealth);
            preparedStatement.setInt(4, PlayerStrength);
            preparedStatement.setInt(5, PlayerIntelligence);
            preparedStatement.setInt(6, PlayerAgility);
            preparedStatement.setInt(7, PlayerLevel);
            preparedStatement.setInt(8, PlayerCoins);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['New Player' successfully added to the 'Player'- table. " + rowsInserted + " Row]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // 'Auto save' player:
    public void autoSaveAndUpdatePlayer(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            autoSavePlayer(connection, player.getNumber(), player.getName(), player.getHealth(), player.getStrength(), player.getIntelligence(), player.getAgility(), player.getLevel(), player.getCoins());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Auto Save Player
    private static void autoSavePlayer(Connection connection, int PlayerNumber, String PlayerName, int PlayerHealth, int PlayerStrength, int PlayerIntelligence, int PlayerAgility, int PlayerLevel, int PlayerCoins) throws SQLException {

        String updateSql = "UPDATE AutoSavePlayers SET PlayerName = ?, PlayerHealth = ?, PlayerStrength = ?, PlayerIntelligence = ?, PlayerAgility = ?, PlayerLevel = ?, PlayerCoins = ? WHERE PlayerNumber = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql)) {
            // Set parameters

            preparedStatement.setString(1, PlayerName);
            preparedStatement.setInt(2, PlayerHealth);
            preparedStatement.setInt(3, PlayerStrength);
            preparedStatement.setInt(4, PlayerIntelligence);
            preparedStatement.setInt(5, PlayerAgility);
            preparedStatement.setInt(6, PlayerLevel);
            preparedStatement.setInt(7, PlayerCoins);
            preparedStatement.setInt(8, PlayerNumber);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(CYAN + "'Player Status' auto-saved successfully to database" + RESET);
            } else {
                System.out.println(RED + "No rows updated. Player with PlayerNumber " + PlayerNumber + " not found." + RESET);
            }
        }
    }


    // Display player status
    private static void displayPlayerStatus(Connection connection, Player player) {

        String selectPlayersSql = "SELECT PlayerName, PlayerStrength, PlayerIntelligence, PlayerAgility, PlayerLevel, PlayerCoins FROM AutoSavePlayers WHERE PlayerNumber = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectPlayersSql)) {
            // playerNumber is the value we want to retrieve
            int PlayerNumber = player.getNumber();  // Replace with the actual PlayerID value

            // Set the parameter value for the placeholder (?)
            selectStatement.setInt(1, PlayerNumber);

            // Execute the query
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve and display the data
                    String playerName = resultSet.getString("PlayerName");
                    int PlayerStrength = resultSet.getInt("PlayerStrength");
                    int playerIntelligence = resultSet.getInt("PlayerIntelligence");
                    int playerAgility = resultSet.getInt("PlayerAgility");
                    int playerLevel = resultSet.getInt("PlayerLevel");
                    int playerCoins = resultSet.getInt("PlayerCoins");

                    // Perform further processing or display the data as needed
                    System.out.println(CYAN + "Player's properties retrieved from database: ");

                    System.out.println("PlayerName: " + playerName);
                    System.out.println("PlayerStrength: " + PlayerStrength);
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


    // Player win battle
    public void registerPlayerWin(Player player, Monster monster) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save Player
            battleStatus(connection, player.getNumber(), player.getName(), monster.getName(), monster.getStrength(), player.getStrength());

            displayPlayerStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Player win battle
    public void registerMonsterWin(Player player, Monster monster) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save Player
            battleStatus(connection, player.getNumber(), monster.getName(), player.getName(), monster.getStrength(), player.getStrength());

            displayPlayerStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void battleStatus(Connection connection, int PlayerByNumber, String WinnerName, String LooserName, int MonsterStrength, int PlayerStrength) {

        String insertPlayerSql = "INSERT INTO Battles (PlayerByNumber, WinnerName, LooserName, MonsterStrength, PlayerStrength) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {

            preparedStatement.setInt(1, PlayerByNumber);
            preparedStatement.setString(2, WinnerName);
            preparedStatement.setString(3, LooserName);
            preparedStatement.setInt(4, MonsterStrength);
            preparedStatement.setInt(5, PlayerStrength);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['New Battle' successfully added to the 'Battle'- table. " + rowsInserted + " Row]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // Player GameOver
    public void registerDefeatedPlayer(Monster monster, Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save Player
            playerDefeated(connection, monster.getName(), monster.getStrength(), player.getName(), player.getNumber(), player.getHealth());

            displayPlayerStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Register defeated players
    public void playerDefeated(Connection connection, String ByMonsterName, int MonsterStrength, String PlayerName, int PlayerByNumber, int PlayerHealth) {

        String insertPlayerSql = "INSERT INTO PlayersDefeated (ByMonsterName, MonsterStrength, PlayerName, PlayerByNumber, PlayerHealth) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {

            preparedStatement.setString(1, ByMonsterName);
            preparedStatement.setInt(2, MonsterStrength);
            preparedStatement.setString(3, PlayerName);
            preparedStatement.setInt(4, PlayerByNumber);
            preparedStatement.setInt(5, PlayerHealth);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Defeated Player' successfully added to the 'Defeated players'- table. " + rowsInserted + " Row]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    // Player Wins the game
    public void registerWinnerPlayer(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            // Save Player
            playerWinner(connection, player.getName(), player.getNumber(), player.getHealth(), player.getStrength());

            displayPlayerStatus(connection, player);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Register Winners
    public void playerWinner(Connection connection, String PlayerName, int PlayerByNumber, int PlayerHealth, int PlayerStrength) {

        String insertPlayerSql = "INSERT INTO PlayersWinners (PlayerName, PlayerByNumber, PlayerHealth, PlayerStrength) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {

            preparedStatement.setString(1, PlayerName);
            preparedStatement.setInt(2, PlayerByNumber);
            preparedStatement.setInt(3, PlayerHealth);
            preparedStatement.setInt(4, PlayerStrength);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println(CYAN + "['Winner player' successfully added to the 'Player winners'- table. " + rowsInserted + " Row]" + RESET);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}