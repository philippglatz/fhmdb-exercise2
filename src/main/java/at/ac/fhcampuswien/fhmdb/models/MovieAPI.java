package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;

public class MovieAPI {
        public String id;
        public String title;
        public ArrayList<String> genres;
        public int releaseYear;
        public String description;
        public String imgUrl;
        public int lengthInMinutes;
        public ArrayList<String> directors;
        public ArrayList<String> writers;
        public ArrayList<String> mainCast;
        public double rating;

        public MovieAPI(String id, String title, ArrayList<String> genres, int releaseYear, String description, String imgUrl, int lengthInMinutes, ArrayList<String> directors, ArrayList<String> writers, ArrayList<String> mainCast, double rating) {
                this.id = id;
                this.title = title;
                this.genres = genres;
                this.releaseYear = releaseYear;
                this.description = description;
                this.imgUrl = imgUrl;
                this.lengthInMinutes = lengthInMinutes;
                this.directors = directors;
                this.writers = writers;
                this.mainCast = mainCast;
                this.rating = rating;
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public ArrayList<String> getGenres() {
                return genres;
        }

        public void setGenres(ArrayList<String> genres) {
                this.genres = genres;
        }

        public int getReleaseYear() {
                return releaseYear;
        }

        public void setReleaseYear(int releaseYear) {
                this.releaseYear = releaseYear;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getImgUrl() {
                return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
        }

        public int getLengthInMinutes() {
                return lengthInMinutes;
        }

        public void setLengthInMinutes(int lengthInMinutes) {
                this.lengthInMinutes = lengthInMinutes;
        }

        public ArrayList<String> getDirectors() {
                return directors;
        }

        public void setDirectors(ArrayList<String> directors) {
                this.directors = directors;
        }

        public ArrayList<String> getWriters() {
                return writers;
        }

        public void setWriters(ArrayList<String> writers) {
                this.writers = writers;
        }

        public ArrayList<String> getMainCast() {
                return mainCast;
        }

        public void setMainCast(ArrayList<String> mainCast) {
                this.mainCast = mainCast;
        }

        public double getRating() {
                return rating;
        }

        public void setRating(double rating) {
                this.rating = rating;
        }
}
