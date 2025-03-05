package web;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.IFilmDao;
import dao.FilmDaoImpl;
import metier.entities.film;

@WebServlet(name = "cs", urlPatterns = { "/controleur", "*.do" })
public class ControleurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IFilmDao metier;

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
        } else if (path.equals("/chercher.do")) {
            String motCle = request.getParameter("motCle");
            FilmModele model = new FilmModele();
            model.setMotCle(motCle);
            
            List<film> films = metier.filmsParMC(motCle);
            model.setFilms(films);
            
            request.setAttribute("model", model);
            request.getRequestDispatcher("films.jsp").forward(request, response);
        } else if (path.equals("/saisie.do")) {
            request.getRequestDispatcher("saisieFilm.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        if (path.equals("/save.do")) {
            try {
                String nom = request.getParameter("nom");
                String genre = request.getParameter("genre"); // Ajout du champ genre
                double prix = Double.parseDouble(request.getParameter("prix"));

                film f = new film(nom, genre, prix); // Correction de la création du film
                metier.save(f);

                request.setAttribute("film", f);
                request.getRequestDispatcher("confirmation.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Le prix doit être un nombre valide.");
                request.getRequestDispatcher("saisieFilm.jsp").forward(request, response);
            }
        } else {
            doGet(request, response);
        }
    }
}
