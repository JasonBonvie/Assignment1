package dao;

import org.springframework.jdbc.core.JdbcTemplate;

import model.*;
import java.util.Collection;
import java.util.ArrayList;

public class AlbumDAO {
    private JdbcTemplate jdbcTemplate;

    public AlbumDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Album createAlbum(Album album){
       //when user wants to we add album and update the table
        String insertQuery = "INSERT INTO albums (id, title) VALUES (?, ?)";
        this.jdbcTemplate.update(insertQuery, album.getId(), album.getTitle());
    }

    public Album getAlbum(int id){
        Album album = new Album(id, "");
        TrackDAO tracksDAOtemp = new TrackDAO(jdbcTemplate);
        //Part of the insert/update function for albums
        //Get album and set tracks using getTracksByAlbumId(id) in TracksDAO
        

        String getAlb = "SELECT * FROM albums where id = ?";
        this.jdbcTemplate.query(getAlb, new Object[] { id },
                (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(albums -> {
            album.setId(albums.getId());
            album.setTitle(albums.getTitle());
            album.setTracks(track.getTracksByAlbumId(albums.getId()));
        });
        return album;
    }

    public Collection<Album> getAllAlbums(){
        Collection<Album> albums = new ArrayList<Album>();
        String selAl = "SELECT * FROM albums";
        this.jdbcTemplate.query(selAl, new Object[] { },
            (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
        ).forEach(album -> albums.add(album));
        return albums;
    }

    public Album updateAlbum(Album album){
        //changes information in the album depending on what needs to be updated
        String update = "UPDATE albums SET title = ? WHERE id = ? ";
          this.jdbcTemplate.update(update, album.getTitle(), album.getId());
        return album;
    }

    public boolean deleteAlbum(Album album){
        boolean success = false;
        //deletes the album specified by id and notifies us by returning 
        //a boolean
        String dell = "DELETE FROM albums WHERE id = ? ";
        this.jdbcTemplate.update(dell, album.getId());
        success = true;
        return success;
    }



}
