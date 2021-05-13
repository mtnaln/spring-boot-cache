package com.metin.medium.example.springbootcache;

import com.metin.medium.example.springbootcache.model.Movie;
import com.metin.medium.example.springbootcache.model.Star;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {

    private List<Star> allStars = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();

    @PostConstruct
    public void init() {
        movies.add(new Movie(1L, "Usual Suspects", 8));
        movies.add(new Movie(2L, "Ring", 7.5));
        movies.add(new Movie(3L, "Titanic", 8.5));
        movies.add(new Movie(4L, "Prestige", 9));

        allStars.add(new Star(1L, "Kevin", "Spacey", 1L));
        allStars.add(new Star(2L, "Stephen", "Baldwin", 1L));
        allStars.add(new Star(3L, "Naomi", "Watts", 2L));
        allStars.add(new Star(4L, "Martin", "Henderson", 2L));
        allStars.add(new Star(5L, "Leonardo", "DiCaprio", 3L));
        allStars.add(new Star(6L, "Kate", "Winslet", 3L));
        allStars.add(new Star(7L, "Hugh", "Jackman", 4L));
        allStars.add(new Star(8L, "Christian", "Bale", 4L));
    }

    @Cacheable("movies")
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() throws InterruptedException {
        Thread.sleep(5000);
        return ResponseEntity.ok(movies);
    }

    @CacheEvict(value = "movies")
    @GetMapping("/evict/movies")
    public void evictMovies() {

    }

    @Cacheable(value = "movies", key = "#movieId")
    @GetMapping("/stars/{movieId}")
    public ResponseEntity<List<Star>> getStars(@PathVariable Long movieId) throws InterruptedException {
        List<Star> stars = allStars.stream().filter(star -> star.getMovieId() == movieId).collect(Collectors.toList());
        Thread.sleep(5000);
        return ResponseEntity.ok(stars);
    }

    @CachePut(value = "movies", key = "#movie.id")
    @PostMapping("/movies")
    public Movie createMovie(@RequestBody Movie movie) {
        Movie m = new Movie(movie.getId(), movie.getName(), movie.getPoint());
        movies.add(m);
        return m;
    }

    @CachePut(value = "movies", key = "#id")
    @PutMapping("/movies/{id}")
    public Movie updateMovie(@RequestBody Movie movie, @PathVariable Long id) throws Exception {
        for (Movie movieIns : movies) {
            if (movieIns.getId() == id) {
                movieIns.setName(movie.getName());
                movieIns.setPoint(movie.getPoint());
                return movieIns;
            }
        }
        throw new Exception("Item NOT found...");
    }

    @CachePut(value = "movies", key = "#id")
    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable Long id) {
        int index = -1;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId() == id) {
                index = i;
            }
        }
        if (index >= 0) {
            movies.remove(index);
        }
    }
}
