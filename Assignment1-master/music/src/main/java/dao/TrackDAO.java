package dao;

import org.springframework.jdbc.core.JdbcTemplate;

import model.*;
import java.util.Collection;
import java.util.ArrayList;


public class TrackDAO {
    private JdbcTemplate jdbcTemplate;

    public TrackDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }


    public Track createTrack(Track track){
        String ins ="INSERT INTO tracks (id, title, album) VALUES (?, ?, ?)";
        this.jdbcTemplate.update(ins,track.getId(), track.getTitle(), track.getAlbumId());
        return track;
    }

    public Track getTrack(int id){
        Track track = new Track(id);
        this.jdbcTemplate.query( "SELECT * FROM tracks WHERE id = ?", new Object[] { id },
        (rs, rowNum) -> new Track(rs.getString("title"), rs.getInt("album"))
      ).forEach(addedTrack -> {
          track.setTitle(addedTrack.getTitle());
          track.setAlbumId(addedTrack.getAlbumId());
        }
      );
        return track;
    }

    public Collection<Track> getAllTracks(){
        Collection<Track> tracks = new ArrayList<Track>();
        String trck = "SELECT * FROM tracks";
        this.jdbcTemplate.query(trck,
        (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"), rs.getInt("album"))
        ).forEach(track -> tracks.add(track) );

        return tracks;
    }

    public Collection<Track> getTracksByAlbumId(int albumId){
        Collection<Track> tracks = new ArrayList<Track>();
        String gttrck = "SELECT id, title FROM tracks WHERE album = ?";
        this.jdbcTemplate.query(gttrck, new Object[] { albumId },
                (rs, rowNum) -> new Track(rs.getInt("id"), rs.getString("title"),albumId)
        ).forEach(track -> tracks.add(track) );
        return tracks;
    }

    public Track updateTrack(Track track){
        this.jdbcTemplate.update( "UPDATE tracks SET title = ?, album = ? WHERE id = ? ",
          track.getTitle(), track.getAlbumId(), track.getId()
        );

        return track;
    }

    public boolean deleteTrack(Track track){
        boolean success = false;
        String del = "DELETE FROM tracks WHERE id = ? "; 
        this.jdbcTemplate.update( del,track.getId());
        success = true;
        return success;
    }

}
