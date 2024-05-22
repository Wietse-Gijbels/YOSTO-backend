package com.yosto.yostobackend.gebruiker;

import com.yosto.yostobackend.geschenk.Geschenk;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

  private Status status;

  @OneToMany(mappedBy = "gebruiker", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Geschenk> geschenken = new ArrayList<>();

  // TODO Studies moeten opgehaald worden uit database van studierichtingen waarschijnlijk
  // private String huidigeStudie;

  // TODO Studies moeten opgehaald worden uit database van studierichtingen waarschijnlijk
  // private List<String> behaaldeDiplomas = new ArrayList<>();

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  @CollectionTable(
    name = "gebruiker_roles",
    joinColumns = @JoinColumn(name = "gebruiker_id")
  )
  @Column(name = "rol")
  private Set<Rol> rollen;

  public void disconnect() {
    this.status = Status.OFFLINE;
  }

  public void connect() {
    this.status = Status.ONLINE;
  }

  public Gebruiker() {}

  public Gebruiker(GebruikerBuilder builder) {
    this.voornaam = builder.voornaam;
    this.achternaam = builder.achternaam;
    this.gebruikersnaam = builder.gebruikersnaam;
    this.email = builder.email;
    this.wachtwoord = builder.wachtwoord;
    this.geslacht = builder.geslacht;
    this.leeftijd = builder.leeftijd;
    this.woonplaats = builder.woonplaats;
    this.rollen = Collections.singleton(builder.rol);
    this.status = builder.status;
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

  public Set<Rol> getRollen() {
    return rollen;
  }

  public Status getStatus() {
    return status;
  }

  public List<Geschenk> getGeschenken() {
    return geschenken;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return rollen
      .stream()
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
