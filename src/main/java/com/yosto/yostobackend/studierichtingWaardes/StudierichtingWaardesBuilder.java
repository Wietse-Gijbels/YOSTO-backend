package com.yosto.yostobackend.studierichtingWaardes;

import com.yosto.yostobackend.studierichting.Studierichting;

import java.util.UUID;

public class StudierichtingWaardesBuilder {

    UUID id;

    Studierichting studierichting;

    int conventioneel;

    int praktisch;

    int analytisch;

    int kunstzinnig;

    int sociaal;

    int ondernemend;

    public StudierichtingWaardesBuilder() {
    }

    public static StudierichtingWaardesBuilder studieRichtingWaardesBuilder() {
        return new StudierichtingWaardesBuilder();
    }

    public StudierichtingWaardesBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public StudierichtingWaardesBuilder setStudierichting(Studierichting studierichting) {
        this.studierichting = studierichting;
        return this;
    }

    public StudierichtingWaardesBuilder setConventioneel(int conventioneel) {
        this.conventioneel = conventioneel;
        return this;
    }

    public StudierichtingWaardesBuilder setPraktisch(int praktisch) {
        this.praktisch = praktisch;
        return this;
    }

    public StudierichtingWaardesBuilder setAnalytisch(int analytisch) {
        this.analytisch = analytisch;
        return this;
    }

    public StudierichtingWaardesBuilder setKunstzinnig(int kunstzinnig) {
        this.kunstzinnig = kunstzinnig;
        return this;
    }

    public StudierichtingWaardesBuilder setSociaal(int sociaal) {
        this.sociaal = sociaal;
        return this;
    }

    public StudierichtingWaardesBuilder setOndernemend(int ondernemend) {
        this.ondernemend = ondernemend;
        return this;
    }

    public StudierichtingWaardes build() {
        return new StudierichtingWaardes(this);
    }


}
