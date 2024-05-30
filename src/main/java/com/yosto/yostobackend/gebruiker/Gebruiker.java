package com.yosto.yostobackend.gebruiker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

  private int xpAantal;

  @OneToMany(mappedBy = "gebruiker", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference(value = "gebruiker-geschenken")
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

  @Column(name = "actieve_rol")
  private Rol actieveRol;

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
    this.rollen = builder.rol;
    this.status = builder.status;
    this.xpAantal = builder.xpAantal;
    this.actieveRol = builder.actieveRol;
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

  public int getXpAantal() {
    return xpAantal;
  }

  public List<Geschenk> getGeschenken() {
    return geschenken;
  }

    public Rol setActieveRol() {
        return actieveRol;
    }

  public void addGeschenk(Geschenk geschenk, int xpAantalNew) {
    geschenken.add(geschenk);
    geschenk.updateGebruiker(this);
    this.xpAantal = xpAantalNew;
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

  public Rol getActieveRol() {
    return actieveRol;
  }
}
