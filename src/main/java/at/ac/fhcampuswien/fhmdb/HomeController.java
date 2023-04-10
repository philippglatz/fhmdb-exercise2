package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox yearComboBox;

    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<MovieAPI> allMovies;

    protected ObservableList<MovieAPI> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;

    private String selectedGenre = "Filter by Genre";
    private String selectedReleaseYear = "Filter by Release Year";
    private String selectedRating = "Filter by Rating";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeState();
        initializeLayout();

    }

    public void initializeState() {
        allMovies = MovieAPI.initializeMovies();
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview

        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");

        yearComboBox.getItems().add("Filter by Release Year");
        yearComboBox.getItems().addAll(releaseYearList());
        yearComboBox.getSelectionModel().select(0);

        ratingComboBox.getItems().add("Filter by Rating");
        ratingComboBox.getItems().addAll(getRatings());
        ratingComboBox.getSelectionModel().select(0);

    }

    public HomeController(){
        this.initializeState();
    }


    public ObservableList<String> releaseYearList(){
        ObservableList<String> releaseYears = FXCollections.observableArrayList();
        for (MovieAPI movie : allMovies){
            int year = movie.getReleaseYear();
            if (!releaseYears.contains(year)){
                releaseYears.add(String.valueOf(year));
            }
        }
        Collections.sort(releaseYears);
        return releaseYears;
    }

    public ObservableList<String> getRatings(){
        ObservableList<String> ratingList = FXCollections.observableArrayList();
        for (MovieAPI movie : allMovies){
            String rating = String.valueOf(movie.getRating());
            if(!ratingList.contains(rating)){
                ratingList.add(rating);
            }
        }
        Collections.sort(ratingList);
        return ratingList;
    }


    // sort movies based on sortedState
    // by default sorted state is NONE
    // afterwards it switches between ascending and descending
    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            observableMovies.sort(Comparator.comparing(MovieAPI::getTitle));
            sortedState = SortedState.ASCENDING;
        } else if (sortedState == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(MovieAPI::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    public List<MovieAPI> filterByQuery(List<MovieAPI> movies, String query){
        if(query == null || query.isEmpty()) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie ->
                    movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    movie.getDescription().toLowerCase().contains(query.toLowerCase())
                )
                .toList();
    }

    public List<MovieAPI> filterByGenre(List<MovieAPI> movies, Genre genre){
        if(genre == null) return movies;

        if(movies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }

        return movies.stream()
                .filter(Objects::nonNull)
                .filter(movie -> movie.getGenres().contains(genre))
                .toList();
    }

    public void applyAllFilters(String searchQuery, Object genre) {
        List<MovieAPI> filteredMovies = allMovies;

        if (!searchQuery.isEmpty()) {
            filteredMovies = filterByQuery(filteredMovies, searchQuery);
        }

        if (genre != null && !genre.toString().equals("No filter")) {
            filteredMovies = filterByGenre(filteredMovies, Genre.valueOf(genre.toString()));
        }

        observableMovies.clear();
        observableMovies.addAll(filteredMovies);
    }

    public void searchBtnClicked(ActionEvent actionEvent) {
        String searchQuery = searchField.getText().trim().toLowerCase();
        Object genre = genreComboBox.getSelectionModel().getSelectedItem();

        applyAllFilters(searchQuery, genre);

        if(sortedState != SortedState.NONE) {
            sortMovies();
        }
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }
    public String getMostPopularActor(List<MovieAPI> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public int getLongestMovieTitle(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .mapToInt(String::length)
                .max()
                .orElse(0);
    }

    public long countMoviesFrom(List<MovieAPI> movies, String director) {
        return movies.stream()
                .filter(movie -> movie.getDirectors().equals(director))
                .count();
    }

    public List<MovieAPI> getMoviesBetweenYears(List<MovieAPI> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
}

