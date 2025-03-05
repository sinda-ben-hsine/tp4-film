package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metier.entities.film;

public class FilmDaoImpl implements IFilmDao {
    @Override
    public film save(film f) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO FILMS(NOM_FILM, GENRE, PRIX_TICKET) VALUES(?, ?, ?)"
            );
            ps.setString(1, f.getNomFilm());
            ps.setString(2, f.getGenre());
            ps.setDouble(3, f.getPrixTicket());
            ps.executeUpdate();
            
            // Récupération de l'ID généré
            PreparedStatement ps2 = conn.prepareStatement("SELECT MAX(ID_FILM) as MAX_ID FROM FILMS");
            ResultSet rs = ps2.executeQuery();
            if (rs.next()) {
                f.setIdFilm(rs.getLong("MAX_ID"));
            }
            ps.close();
            ps2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    public List<film> filmsParMC(String mc) {
        List<film> films = new ArrayList<>();
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM FILMS WHERE NOM_FILM LIKE ?"
            );
            ps.setString(1, "%" + mc + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                film f = new film();
                f.setIdFilm(rs.getLong("ID_FILM"));
                f.setNomFilm(rs.getString("NOM_FILM"));
                f.setGenre(rs.getString("GENRE"));
                f.setPrixTicket(rs.getDouble("PRIX_TICKET"));
                films.add(f);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return films;
    }

    @Override
    public film getFilm(Long id) {
        Connection conn = SingletonConnection.getConnection();
        film f = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM FILMS WHERE ID_FILM = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                f = new film();
                f.setIdFilm(rs.getLong("ID_FILM"));
                f.setNomFilm(rs.getString("NOM_FILM"));
                f.setGenre(rs.getString("GENRE"));
                f.setPrixTicket(rs.getDouble("PRIX_TICKET"));
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    public film updateFilm(film f) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE FILMS SET NOM_FILM=?, GENRE=?, PRIX_TICKET=? WHERE ID_FILM=?"
            );
            ps.setString(1, f.getNomFilm());
            ps.setString(2, f.getGenre());
            ps.setDouble(3, f.getPrixTicket());
            ps.setLong(4, f.getIdFilm());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return f;
    }

    @Override
    public void deleteFilm(Long id) {
        Connection conn = SingletonConnection.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM FILMS WHERE ID_FILM = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
