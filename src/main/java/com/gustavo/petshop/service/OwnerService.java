package com.gustavo.petshop.service;

import com.gustavo.petshop.model.Owner;
import com.gustavo.petshop.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerService {

    @Autowired
    private final OwnerRepository ownerRepository;

    public Owner findOwner(String email) {
        return this.ownerRepository.findByEmail(email).orElseThrow();
    }

    public List<Owner> findAllOwners() {
        return this.ownerRepository.findAll();
    }

    public Owner registerOwner(Owner owner) {
        return this.ownerRepository.save(owner);
    }

    public void removeOwner(String email){
        Owner owner = findOwner(email);

        this.ownerRepository.delete(owner);
    }

    public Owner updateOwner(String email, Owner newOwner) {
        Owner owner = findOwner(email);

        owner.setName(newOwner.getName());
        owner.setEmail(newOwner.getEmail());

        return this.ownerRepository.save(owner);
    }
}
