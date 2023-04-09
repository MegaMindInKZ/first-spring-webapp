package com.example.tacocloud.repositories;

import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Taco;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository{
    private JdbcTemplate jdbc;
    public JdbcTacoRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        for (Ingredient ingredient: taco.getIngredients()){
            saveIngredientTaco(ingredient, tacoId);
        }
        return taco;
    }
    private long saveTacoInfo(Taco taco){
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory("insert into Taco(name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
        factory.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = factory.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);
        taco.setId(keyHolder.getKey().longValue());
        return keyHolder.getKey().longValue();
    }
    private void saveIngredientTaco(Ingredient ingredient, long tacoID){
        jdbc.update("insert into Taco_Ingredients(taco, ingredient) values (?, ?)", tacoID, ingredient.getId());
    }

}
