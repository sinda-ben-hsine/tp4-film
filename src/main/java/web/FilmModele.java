package web;

import java.util.ArrayList;
import java.util.List;

import metier.entities.film;

public class FilmModele {
    private String motCle;
    private List<film> films = new ArrayList<>();

    public String getMotCle() {
        return motCle;
    }

    public void setMotCle(String motCle) {
        this.motCle = motCle;
    }

    public List<film> getFilms() {
        return films;
    }

    public void setFilms(List<film> films) {
        this.films = films;
    }
}
