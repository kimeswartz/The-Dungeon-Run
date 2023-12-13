package com.kimswartz.app;

import com.kimswartz.app.fighters.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DungeonGameDatabase {

    // Replace with your MariaDB connection details
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/Dungeon_Run";
    private String username = "root";
    private String password = "erik";

    public void openGameDatabase(Player player) {

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Create the players table if it doesn't exist
            createPlayersTable(connection);

            // Save player data
            savePlayerData(connection, player.getName(), "GameData1");
            savePlayerData(connection, "Player2", "GameData2");

            // Retrieve and display player data
            retrieveAndDisplayPlayers(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createPlayersTable(Connection connection) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS players ("
                + "player_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "player_name VARCHAR(255) NOT NULL,"
                + "game_data VARCHAR(255) NOT NULL)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void savePlayerData(Connection connection, String playerName, String gameData) {
        String insertPlayerSql = "INSERT INTO players (player_name, game_data) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayerSql)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.setString(2, gameData);

            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void retrieveAndDisplayPlayers(Connection connection) {
        String selectPlayersSql = "SELECT * FROM players";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectPlayersSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("Player Data:");
            System.out.println("Player ID | Player Name | Game Data");
            System.out.println("----------------------------------");

            while (resultSet.next()) {
                int playerId = resultSet.getInt("player_id");
                String playerName = resultSet.getString("player_name");
                String gameData = resultSet.getString("game_data");

                System.out.println(playerId + " | " + playerName + " | " + gameData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public class PlayerDAO {
        private static final String UPDATE_PLAYER_LEVEL = "UPDATE players SET level = ? WHERE player_id = ?";

        public void updatePlayerLevel(int playerId, int newLevel) {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PLAYER_LEVEL)) {

                preparedStatement.setInt(1, newLevel);
                preparedStatement.setInt(2, playerId);

                preparedStatement.executeUpdate();
                System.out.println("Player level updated successfully.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



}