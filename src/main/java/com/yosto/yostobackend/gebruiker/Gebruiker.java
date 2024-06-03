package com.yosto.yostobackend.gebruiker;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yosto.yostobackend.geschenk.Geschenk;
import com.yosto.yostobackend.studierichting.Studierichting;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
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

    private Status status;

    private int xpAantal;

    @ManyToMany
    @JoinTable(
            name = "favoriete_studierichting",
            joinColumns = @JoinColumn(name = "gebruiker_id"),
            inverseJoinColumns = @JoinColumn(name = "studierichting_id")
    )
    @JsonManagedReference
    private List<Studierichting> favorieteStudierichtingen = new ArrayList<>();

    @OneToMany(mappedBy = "gebruiker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "gebruiker-geschenken")
    private List<Geschenk> geschenken = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "huidige_studierichting_id")
  private Studierichting huidigeStudie;

  @ManyToMany
  @JoinTable(
          name = "gebruiker_behaalde_diplomas",
          joinColumns = @JoinColumn(name = "gebruiker_id"),
          inverseJoinColumns = @JoinColumn(name = "studierichting_id")
  )
  private Set<Studierichting> behaaldeDiplomas = new HashSet<>();

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
    this.rollen = Collections.singleton(builder.rol);
    this.status = builder.status;
    this.xpAantal = builder.xpAantal;
    this.huidigeStudie = builder.huidigeStudie;
    this.behaaldeDiplomas = builder.behaaldeDiplomas;
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

    public List<Studierichting> getFavorieteStudierichtingen() {
        return favorieteStudierichtingen;
    }
  public Studierichting getHuidigeStudie() {

    return huidigeStudie;
  }

  public Set<Studierichting> getBehaaldeDiplomas() {
    return behaaldeDiplomas;
  }

  public void addGeschenk(Geschenk geschenk, int xpAantalNew) {
    geschenken.add(geschenk);
    geschenk.updateGebruiker(this);
    this.xpAantal = xpAantalNew;
  }

    public void addFavorieteStudierichting(Studierichting studierichting) {
        favorieteStudierichtingen.add(studierichting);
    }

    public void removeFavorieteStudierichting(Studierichting studierichting) {
        favorieteStudierichtingen.remove(studierichting);
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
