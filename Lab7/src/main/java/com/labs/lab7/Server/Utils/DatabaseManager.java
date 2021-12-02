package com.labs.lab7.Server.Utils;

import com.labs.lab7.Server.App.Main;
import com.labs.lab7.common.AppObjects.*;
import com.labs.lab7.common.InitData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseManager {

    private final Connection connection;
    private final String DB_COLLECTION_NAME = "repository";

    public DatabaseManager(Connection connection){
        this.connection = connection;
    }

    private void setSpaceMarine(PreparedStatement preparedStatement, SpaceMarine spaceMarine) throws SQLException {
        preparedStatement.setInt(1, spaceMarine.getId());
        preparedStatement.setString(2, spaceMarine.getName());
        preparedStatement.setDouble(3, spaceMarine.getCoordinates().getX());
        preparedStatement.setDouble(4, spaceMarine.getCoordinates().getY());
        preparedStatement.setDate(5, new java.sql.Date(spaceMarine.getCreationDate().getTime()));
        preparedStatement.setInt(6, spaceMarine.getHealth());
        preparedStatement.setBoolean(7, spaceMarine.getLoyal());
        preparedStatement.setString(8, spaceMarine.getCategory().toString());
        preparedStatement.setString(9, spaceMarine.getMeleeWeapon().toString());
        if (spaceMarine.getChapter() != null) {
            preparedStatement.setString(10, spaceMarine.getChapter().getName());
            preparedStatement.setLong(11, spaceMarine.getChapter().getMarinesCount());
        }
        else{
            preparedStatement.setObject(10, null);
            preparedStatement.setObject(11, null);
        }
        preparedStatement.setString(12, spaceMarine.getInitData().getLogin());
        preparedStatement.setString(13, spaceMarine.getInitData().getPassword());
    }

    public boolean replace(Integer key, SpaceMarine spaceMarine){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE " + DB_COLLECTION_NAME + " SET (" +
                    "name = ?," +
                    "x = ?," +
                    "y = ?," +
                    "creationdate = ?," +
                    "health = ?," +
                    "loyal = ?," +
                    "category = ?," +
                    "meleeWeapon = ?," +
                    "chapterName = ?," +
                    "marinesCount = ?," +
                    "login = ?," +
                    "password = ?");
            setSpaceMarine(preparedStatement, spaceMarine);
            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e){
            return false;
        } catch (Throwable e){
            System.out.println("Replace: " + e.getMessage());
            return false;
        }
    }

    public boolean remove(Integer key){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM " + DB_COLLECTION_NAME + " WHERE (id = ?)");
            preparedStatement.setInt(1,key);

            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e){
            return false;
        }
    }

    public boolean clear(){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM " + DB_COLLECTION_NAME);
            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e){
            System.out.println("Clear(SQLException): " + e.getMessage());
            return false;
        } catch (Throwable e){
            System.out.println("Clear: " + e.getMessage());
            return false;
        }
    }

    public boolean upload(SpaceMarine spaceMarine){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO " + DB_COLLECTION_NAME + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");

            setSpaceMarine(preparedStatement, spaceMarine);
            return preparedStatement.executeUpdate() != 0;

        } catch (SQLException e){
            System.out.println("Upload(SQLException): " + e.getMessage());
            return false;
        } catch (Throwable e){
            System.out.println("Upload: " + e.getMessage());
            return false;
        }
    }

    public Repository download(){
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM repository");
            List<SpaceMarine> list = new ArrayList<>();

            while (resultSet.next()) {
                SpaceMarine spaceMarine = new SpaceMarine(
                        resultSet.getString("name"),
                        new Coordinates(
                                resultSet.getDouble("x"),
                                resultSet.getDouble("y")),
                        resultSet.getInt("health"),
                        resultSet.getBoolean("loyal"),
                        AstartesCategory.valueOf(resultSet.getString("category")),
                        MeleeWeapon.valueOf(resultSet.getString("meleeWeapon")),
                        new Chapter(
                                resultSet.getString("chapterName"),
                                resultSet.getLong("marinesCount")
                        ),
                        new InitData(
                                0,
                                "localhost",
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
                spaceMarine.setCreationDate(resultSet.getDate("creationDate"));
                spaceMarine.setId(resultSet.getInt("id"));
                list.add(spaceMarine);
            }
            return new Repository(list);

        } catch (SQLException e){
            System.out.println("DatabaseManager: " + e.getMessage());
            return new Repository();
        }
    }

}
