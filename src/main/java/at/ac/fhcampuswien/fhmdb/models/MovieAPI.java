package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieAPI {
        @SerializedName("id")
        public String id;

        @SerializedName("title")
        public String title;

        @SerializedName("genres")
        public List<Genre> genres;

        @SerializedName("releaseYear")
        public int releaseYear;

        @SerializedName("description")
        public String description;

        @SerializedName("imgUrl")
        public String imgUrl;

        @SerializedName("lengthInMinutes")
        public int lengthInMinutes;

        @SerializedName("directors")
        public ArrayList<String> directors;

        @SerializedName("writers")
        public ArrayList<String> writers;

        @SerializedName("mainCast")
        public ArrayList<String> mainCast;

        @SerializedName("rating")
        public double rating;

        public MovieAPI(String id, String title, List<Genre> genres, int releaseYear, String description, String imgUrl, int lengthInMinutes, ArrayList<String> directors, ArrayList<String> writers, ArrayList<String> mainCast, double rating) throws IOException {
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

        public List<Genre> getGenres() {
                return genres;
        }

        public void setGenres(List<Genre> genres) {
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

        public static List<MovieAPI> initializeMovies() {
                Gson gson = new Gson();
                List<MovieAPI> movieAPIS = null;
                try {
                        URL url = new URL("https://prog2.fh-campuswien.ac.at/movies");

                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                        //check if connected
                        int responseCode = connection.getResponseCode();

                        // 200 = OK
                        if (responseCode != 200) {
                                throw new RuntimeException("HttpResponseCode: " + responseCode);
                        } else {
                                StringBuilder informationString = new StringBuilder();
                                Scanner scanner = new Scanner(url.openStream());

                                while (scanner.hasNext()) {
                                        informationString.append(scanner.nextLine());
                                }
                                scanner.close();
                                System.out.println(informationString);

                                Type movieType = new TypeToken<ArrayList<MovieAPI>>() {
                                }.getType();
                                movieAPIS = gson.fromJson(String.valueOf(informationString), movieType);
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return movieAPIS;
        }
}
