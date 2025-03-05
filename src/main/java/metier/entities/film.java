package metier.entities;

import java.io.Serializable;

public class film implements Serializable {
    private Long idFilm;
    private String nomFilm;
    private String genre;
    private double prixTicket; 

    public film() {
        super();
    }

    public film(String nomFilm, String genre, double prixTicket) {
        this.nomFilm = nomFilm;
        this.genre = genre;
        this.prixTicket = prixTicket;
    }

    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public String getNomFilm() {
        return nomFilm;
    }

    public void setNomFilm(String nomFilm) {
        this.nomFilm = nomFilm;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrixTicket() {
        return prixTicket;
    }

    public void setPrixTicket(double prixTicket) {
        this.prixTicket = prixTicket;
    }
}

