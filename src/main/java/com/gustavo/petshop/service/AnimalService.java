package com.gustavo.petshop.service;

import com.gustavo.petshop.model.Animal;
import com.gustavo.petshop.model.Owner;
import com.gustavo.petshop.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    @Autowired
    private final AnimalRepository animalRepository;

    @Autowired
    private final OwnerService ownerService;

    public Animal findAnimal(String name) {
        Optional<Animal> animal = this.animalRepository.findByName(name);

        return animal.orElseThrow();
    }

    public List<Animal> findAllAnimals() {
        List<Animal> animals = this.animalRepository.findAll();

        if (animals.isEmpty()) throw new RuntimeException("There are not any animal registered");

        return animals;
    }

    public Animal registerAnimal(Animal animal) {
        return this.animalRepository.save(animal);
    }

    public Animal attachOwner(String animalName, String ownerEmail) {
        Animal animal = findAnimal(animalName);
        Owner owner = this.ownerService.findOwner(ownerEmail);

        if (owner == null) throw new RuntimeException("The owner does not exist!");

        animal.setOwner(owner);

        return this.animalRepository.save(animal);
    }

    public void removeAnimal(String name) {
        Animal animal = findAnimal(name);

        this.animalRepository.delete(animal);
    }

    public Animal updateAnimal(String name, Animal newAnimal) {
        Animal animal = findAnimal(name);

        animal.setName(newAnimal.getName());
        animal.setSpecie(newAnimal.getSpecie());
        animal.setBreed(newAnimal.getBreed());
        animal.setAge(newAnimal.getAge());

        return this.animalRepository.save(animal);
    }
}
