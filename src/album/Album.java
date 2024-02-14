package album;
/**
The Album class which holds the data for the title, artist, genre, date, and ratings
@author Seungjun Bae
 */
public class Album {
    private static final int EQUAL = 0;
    private static final int BEFORE = -1;
    private static final int AFTER = 1;
    private String title;
    private Artist artist;
    private Genre genre;
    private Date released;
    private Rating ratings; // head to linked list of ratings

    public Album(String title, Artist artist, Genre genre, Date release, Rating ratings) {
        setTitle(title);
        setaArtist(artist);
        setGenre(genre);
        setReleased(release);
        setRatings(ratings);
    }

    public void setTitle(String t){
        this.title = t;
    }

    public String getTitle(){
        return this.title;
    }
    
    public void setaArtist(Artist a){
        this.artist = a;
    }

    public Artist getArtist(){
        return this.artist;
    }
    
    public void setGenre(Genre g){
        this.genre = g;
    }

    public Genre getGenre(){
        return this.genre;
    }

    public void setReleased(Date r){
        this.released = r;
    }

    public Date getReleased(){
        return released;
    }

    public void setRatings(Rating r){
        this.ratings = r;
    }

    public Rating getRatings(){
        return ratings;
    }

    /**
     * Adds a rating to the linked list rating object
     * @param star: an int from 1 to 5
     */
    public void rate(int star) {
        if (ratings != null) {
            Rating rating = new Rating(star, ratings);
            rating.setNext(ratings);
            ratings = rating;
        } else {
            ratings = new Rating(star, null);
        }
    }

    /**
     * Calculates the average rating of an album
     * @return average: a double representing the average rating
     */
    public double avgRatings() {
        double sum = 0, numberOfRatings = 0;
        Rating pointer = ratings;
        while (pointer != null) {
            sum += pointer.getStar();
            numberOfRatings++;
            pointer = pointer.getNext();
        }
        if(numberOfRatings == 0){
            return 0.0;
        }
        return sum / numberOfRatings;
    }

    /**
     * Determines if two Album objects have the same title and same Artist object(same Name and birthdate)
     * @param obj: cast to an Album object later if it is an Album object
     * @return true: the albums are equal
     * @return false: the albums are not equal
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Album otherAlbum = (Album) obj;
        return this.getTitle().equalsIgnoreCase(otherAlbum.getTitle()) && this.getArtist().equals(otherAlbum.getArtist());
    }

    /**
     * Creates a user-friendly string that summarizes information about the album
     * @return String: format of [title] Released mm/dd/yyyy [artist name: mm/dd/yyyy] [Genre] rating
     */
    @Override
    public String toString(){
        String albumTitle = "[" + this.getTitle() + "]";
        String albumReleaseDate = "Released " + this.getReleased().toString();
        String artistInfo = "[" + this.getArtist().toString() + "]";
        String genreString = "[" + String.valueOf(this.getGenre()) + "]";
        String ratingString = this.rating();
        return  albumTitle + " " + albumReleaseDate + " " + artistInfo + " " + genreString + " " + ratingString;
    }

    /**
     * Counts how many one, two, three, four, and five, star ratings exist in the ratings linked list
     * @return String: a string summarizing the rating statistics or none if the linked list is null
     */
    public String rating(){
        int oneStar = 0;
        int twoStar = 0;
        int threeStar = 0;
        int fourStar = 0;
        int fiveStar = 0;
        Rating pointer = ratings;
        //If no ratings have been entered yet
        if(pointer == null){
            return "Rating: none";
        }
        while (pointer != null) {
            int pointerRating = pointer.getStar();
            if(pointerRating == 1){
                oneStar += 1;
            } else if (pointerRating == 2) {
                twoStar += 1;
            } else if (pointerRating == 3) {
                threeStar += 1;
            } else if (pointerRating == 4) {
                fourStar += 1;
            } else if (pointerRating == 5) {
                fiveStar += 1;
            }
            pointer = pointer.getNext();
        }
        String averageRating = String.format("%.2f", this.avgRatings());
        return "Rating: " + "*(" + oneStar + ")" + "**(" + twoStar + ")" + "***(" + threeStar + ")"
                        + "****(" + fourStar + ")" + "*****(" + fiveStar + ")" + "(average rating: " + averageRating + ")";
    }

    /**
     * Compares genre of two albums for sorting purposes
     * Order is: Classical, Country, Pop, Unknown
     * @param otherAlbum: an Album object
     * @return -1: the Album calling the method has a genre that comes before the inputted Album's
     * @return 1: the Album calling the method has a genre that comes after the inputted Album's
     * @return 0: the Album calling the method has a genre that is equal to the inputted Album's
     */
   public int genreCompare(Album otherAlbum){
        String genre = String.valueOf(this.getGenre());
        String otherGenre = String.valueOf(otherAlbum.getGenre());
        int comparison = this.compareWords(genre, otherGenre);
        if(comparison != EQUAL){
            return comparison;
        }
        else{
            return this.getArtist().compareTo(otherAlbum.getArtist());
        }
   }

    /**
     * Compares the titles of two albums to determine which comes first
     * @param otherAlbum: an Album object
     * @return -1: the Album object calling the method has a title that comes before otherAlbum's title
     * @return 1: the Album object calling the method has a title that comes after otherAlbum's title
     * @return 0: the Album object calling the method has a title that is equal to otherAlbum's title
     */
    public int compareTitles(Album otherAlbum) {
        String[] words1 = this.getTitle().split("\\s+");
        String[] words2 = otherAlbum.getTitle().split("\\s+");

        int minLength;
        if(words1.length <= words2.length){
            minLength = words1.length;
        }
        else {
            minLength = words2.length;
        }

        for (int i = 0; i < minLength; i++) {
            int result = compareWords(words1[i].toLowerCase(), words2[i].toLowerCase());
            if (result != EQUAL) {
                return result;
            }
        }
        return Integer.compare(words1.length, words2.length);
    }




    /**
     Helper method for the compareTitles method
     The method takes in two strings and compares their ascii values to determine the sorting order for the two strings
     @param word1: the string of the object calling the method
     @param word2: the string of the other object being compared
     @return -1: string word1 comes before string word2
     @return 1: string word1 comes after string word2
     @return 0: the strings are equivalent
     */
    private int compareWords(String word1, String word2) {
        int stop;
        if(word1.length() <= word2.length()){
            stop = word1.length();
        }
        else{
            stop = word2.length();
        }
        for (int i = 0; i < stop; i++) {
            int ascii1 = (int) word1.charAt(i);
            int ascii2 = (int) word2.charAt(i);

            if (ascii1 < ascii2) {
                return BEFORE;
            } else if (ascii1 > ascii2) {
                return AFTER;
            }
        }

        // Returns -1 if word1 is shorter, 1 is word1 is longer, 0 if the lengths are equal
        return Integer.compare(word1.length(), word2.length());
    }
}
