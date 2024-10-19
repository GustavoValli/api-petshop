package com.gustavo.petshop.controller;

import com.gustavo.petshop.model.Owner;
import com.gustavo.petshop.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {

    @Autowired
    private final OwnerService ownerService;

    @GetMapping("/{email}")
    public ResponseEntity<Owner> findOwner(@PathVariable String email) {
        Owner owner = this.ownerService.findOwner(email);

        return ResponseEntity.ok(owner);
    }

    @GetMapping
    public ResponseEntity<List<Owner>> findAllOwners() {
        List<Owner> owners = this.ownerService.findAllOwners();

        return ResponseEntity.ok(owners);
    }

    @PostMapping
    public ResponseEntity<Owner> registerOwner(@RequestBody Owner owner, UriComponentsBuilder uriComponentsBuilder) {
        this.ownerService.registerOwner(owner);

        var uri = uriComponentsBuilder.path("/owners/{email}").buildAndExpand(owner.getEmail()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity removeOwner(@PathVariable String email) {
        this.ownerService.removeOwner(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{email}")
    public ResponseEntity<Owner> updateOwner(@PathVariable String email, @RequestBody Owner owner, UriComponentsBuilder uriComponentsBuilder) {
        Owner updatedOwner = this.ownerService.updateOwner(email, owner);

        var uri = uriComponentsBuilder.path("/owners/{email}").buildAndExpand(updatedOwner.getEmail()).toUri();

        return ResponseEntity.created(uri).body(updatedOwner);
    }
}
