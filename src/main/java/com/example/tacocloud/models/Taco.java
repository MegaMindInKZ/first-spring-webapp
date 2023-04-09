package com.example.tacocloud.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;

// end::allButValidation[]
// tag::allButValidation[]

@Data
public class Taco {

    private Long id;
    private Date createdAt;
    @NotBlank
    private String name;
    private List<Ingredient> ingredients;
}