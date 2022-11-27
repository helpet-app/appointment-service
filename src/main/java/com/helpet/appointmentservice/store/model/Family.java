package com.helpet.appointmentservice.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "families")
public class Family {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @OneToMany(mappedBy = "family")
    private Set<Pet> pets = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "user_families",
            joinColumns = @JoinColumn(name = "family_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new LinkedHashSet<>();
}