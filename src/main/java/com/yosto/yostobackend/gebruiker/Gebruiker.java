package com.yosto.yostobackend.gebruiker;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "gebruiker")
public class Gebruiker implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String voornaam;

    private String achternaam;

    private String gebruikersnaam;

    private String email;

    private String wachtwoord;

    private String geslacht;

    private int leeftijd;

    private String woonplaats;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "gebruiker_roles", joinColumns = @JoinColumn(name = "gebruiker_id"))
    @Column(name = "rol")
    private Set<Rol> rollen;


    public Gebruiker() {
    }

    public Gebruiker(GebruikerBuilder builder) {
        this.voornaam = builder.voornaam;
        this.achternaam = builder.achternaam;
        this.gebruikersnaam = builder.gebruikersnaam;
        this.email = builder.email;
        this.wachtwoord = builder.wachtwoord;
        this.geslacht = builder.geslacht;
        this.leeftijd = builder.leeftijd;
        this.woonplaats = builder.woonplaats;
    }

    public UUID getId() {
        return id;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public String getEmail() {
        return email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rollen.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return wachtwoord;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
