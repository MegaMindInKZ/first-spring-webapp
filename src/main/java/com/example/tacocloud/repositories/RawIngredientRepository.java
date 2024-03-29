package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawIngredientRepository implements IngredientRepository{
    private DataSource dataSource;

    public RawIngredientRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }
    @Override
    public Iterable<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet  resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    "select id, name, type from Ingredient");
            resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Ingredient ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type")));
                ingredients.add(ingredient);
            }
        } catch (SQLException e) {
            // ??? What should be done here ???
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return ingredients;
    }

    @Override
    public Ingredient findOne(String id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(
                    "select id, name, type where id = ? from Ingredient");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            Ingredient ingredient = null;
            if(resultSet.next()) {
                ingredient = new Ingredient(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        Ingredient.Type.valueOf(resultSet.getString("type")));
            }
            return ingredient;
        } catch (SQLException e) {
            // ??? What should be done here ???
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {}
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return null;    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return null;
    }
}
