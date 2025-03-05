package dao;
import java.util.List;
import metier.entities.film;

public interface IFilmDao {
	public film save(film f);
	public List<film> filmsParMC(String mc);
	public film getFilm(Long id);
	public film updateFilm(film f);
	public void deleteFilm(Long id);

}
