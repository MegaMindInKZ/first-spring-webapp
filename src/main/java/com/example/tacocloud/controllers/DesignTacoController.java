package com.example.tacocloud.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tacocloud.models.Order;
import com.example.tacocloud.models.Taco;
import com.example.tacocloud.repositories.IngredientRepository;
import com.example.tacocloud.repositories.TacoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.example.tacocloud.models.Ingredient;
import com.example.tacocloud.models.Ingredient.Type;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository designRepository;


    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository designRepository){
        this.ingredientRepository = ingredientRepository;
        this.designRepository = designRepository;
    }
    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("design", new Taco());
        return "design";
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @PostMapping
    public String processDesign(
            @Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            System.out.println("errors");
            return "design";
        }
        Taco saved = designRepository.save(design);
        order.addDesign(saved);
        System.out.println("Processing design: " + saved);

        return "redirect:/orders/current";
    }


    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }
}