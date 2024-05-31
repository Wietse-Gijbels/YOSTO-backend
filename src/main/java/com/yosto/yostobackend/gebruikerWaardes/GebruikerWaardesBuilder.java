package com.yosto.yostobackend.gebruikerWaardes;

import com.yosto.yostobackend.gebruiker.Gebruiker;

import java.util.UUID;

public class GebruikerWaardesBuilder {

    UUID id;

    Gebruiker gebruiker;

    int conventioneel;

    int praktisch;

    int analytisch;

    int kunstzinnig;

    int sociaal;

    int ondernemend;

    public GebruikerWaardesBuilder() {
    }

    public static GebruikerWaardesBuilder gebruikerWaardesBuilder() {
        return new GebruikerWaardesBuilder();
    }

    public GebruikerWaardesBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public GebruikerWaardesBuilder setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
        return this;
    }

    public GebruikerWaardesBuilder setConventioneel(int conventioneel) {
        this.conventioneel = conventioneel;
        return this;
    }

    public GebruikerWaardesBuilder setPraktisch(int praktisch) {
        this.praktisch = praktisch;
        return this;
    }

    public GebruikerWaardesBuilder setAnalytisch(int analytisch) {
        this.analytisch = analytisch;
        return this;
    }

    public GebruikerWaardesBuilder setKunstzinnig(int kunstzinnig) {
        this.kunstzinnig = kunstzinnig;
        return this;
    }

    public GebruikerWaardesBuilder setSociaal(int sociaal) {
        this.sociaal = sociaal;
        return this;
    }

    public GebruikerWaardesBuilder setOndernemend(int ondernemend) {
        this.ondernemend = ondernemend;
        return this;
    }

    public GebruikerWaardes build() {
        return new GebruikerWaardes(this);
    }
}
