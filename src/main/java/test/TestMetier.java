package test;

import java.util.List;
import dao.FilmDaoImpl;
import metier.entities.film;

public class TestMetier {
    public static void main(String[] args) {
        FilmDaoImpl fdao = new FilmDaoImpl();
        
        // Ajout d'un film
        film film = fdao.save(new film("Inception", "Science-Fiction", 15.5));
        System.out.println("Film ajouté : " + film);

        // Recherche des films contenant "In"
        List<film> films = fdao.filmsParMC("In");
        for (film f : films) {
            System.out.println(f);
        }
    }
}
