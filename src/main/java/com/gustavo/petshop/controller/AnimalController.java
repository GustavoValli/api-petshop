package com.gustavo.petshop.controller;

import com.gustavo.petshop.model.Animal;
import com.gustavo.petshop.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    @Autowired
    private final AnimalService animalService;

    @GetMapping("/{name}")
    public ResponseEntity<Animal> findAnimal(@PathVariable String name) {
        Animal animal = this.animalService.findAnimal(name);

        return ResponseEntity.ok(animal);
    }

    @GetMapping
    public ResponseEntity<List<Animal>> findAllAnimals() {
        List<Animal> animals = this.animalService.findAllAnimals();

        return ResponseEntity.ok(animals);
    }

    @PostMapping
    public ResponseEntity<Animal> registerAnimal(@RequestBody Animal animal, UriComponentsBuilder uriComponentsBuilder) {
        this.animalService.registerAnimal(animal);

        var uri = uriComponentsBuilder.path("/animals/{name}").buildAndExpand(animal.getName()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{animalName}/{ownerEmail}")
    public ResponseEntity<Animal> attachOwner(@PathVariable String animalName, @PathVariable String ownerEmail, UriComponentsBuilder uriComponentsBuilder) {
        this.animalService.attachOwner(animalName, ownerEmail);

        var uri = uriComponentsBuilder.path("/animals/{name}").buildAndExpand(animalName).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{name}")
    public ResponseEntity removeAnimal(@PathVariable String name) {
        this.animalService.removeAnimal(name);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable String name, @RequestBody Animal animal, UriComponentsBuilder uriComponentsBuilder) {
        Animal updatedAnimal = this.animalService.updateAnimal(name, animal);

        var uri = uriComponentsBuilder.path("/animals/{name}").buildAndExpand(updatedAnimal.getName()).toUri();

        return ResponseEntity.created(uri).body(updatedAnimal);
    }
}
