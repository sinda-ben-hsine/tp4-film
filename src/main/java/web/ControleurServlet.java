package web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import dao.IFilmDao;
import dao.FilmDaoImpl;
import metier.entities.film;

@WebServlet(name = "cs", urlPatterns = { "/controleur", "*.do" })
public class ControleurServlet extends HttpServlet {

	IFilmDao metier;

	@Override
	public void init() throws ServletException {
		metier = new FilmDaoImpl();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		if (path.equals("/index.do")) {
			request.getRequestDispatcher("films.jsp").forward(request, response);
		} 
		else if (path.equals("/chercher.do")) {
			String motCle = request.getParameter("motCle");
			FilmModele model = new FilmModele();
			model.setMotCle(motCle);
			List<film> films = metier.filmsParMC(motCle);
			model.setFilms(films);
			request.setAttribute("model", model);
			request.getRequestDispatcher("films.jsp").forward(request, response);
		} 
		else if (path.equals("/saisie.do")) {
			request.getRequestDispatcher("saisieFilm.jsp").forward(request, response);
		} 
		else if (path.equals("/save.do") && request.getMethod().equals("POST")) {
			String nomFilm = request.getParameter("nomFilm");
			String genre = request.getParameter("genre");
			double prixTicket = Double.parseDouble(request.getParameter("prixTicket"));
			
			film f = metier.save(new film(nomFilm, genre, prixTicket));
			request.setAttribute("film", f);
			response.sendRedirect("chercher.do?motCle=");
		} 
		else if (path.equals("/supprimer.do")) {
			Long id = Long.parseLong(request.getParameter("id"));
			metier.deleteFilm(id);
			response.sendRedirect("chercher.do?motCle=");
		} 
		else if (path.equals("/editer.do")) {
			Long id = Long.parseLong(request.getParameter("id"));
			film f = metier.getFilm(id);
			request.setAttribute("film", f);
			request.getRequestDispatcher("editerFilm.jsp").forward(request, response);
		} 
		else if (path.equals("/update.do")) {
			Long id = Long.parseLong(request.getParameter("id"));
			String nomFilm = request.getParameter("nomFilm");
			String genre = request.getParameter("genre");
			double prixTicket = Double.parseDouble(request.getParameter("prixTicket"));

			film f = new film();
			f.setIdFilm(id);
			f.setNomFilm(nomFilm);
			f.setGenre(genre);
			f.setPrixTicket(prixTicket);

			metier.updateFilm(f);
			request.setAttribute("film", f);
			response.sendRedirect("chercher.do?motCle=");
		} 
		else {
			response.sendError(Response.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
