package album;
/**
 * The Collection class which creates an array to store the Album objects
 * Has the functionality of sorting, rating, adding, and removing albums in the array
 @author Rohan Sharma
 */

public class Collection {
    private Album[] albums; //list of albums
    private int size;

    public static final int INITIAL_CAPACITY = 4;
    public static final int ARRAY_GROWTH_SIZE = 4;

    public static final int NOT_FOUND = -1;

    private static final int COMES_BEFORE = -1;
    private static final int EQUAL = 0;


    /**
     * Constructor for the collection class that initializes the array to store albums, named albums
     * The array is initialized to have a constant size of 4 which is stored as a constant named INITIAL_CAPACITY
     * The size of the array is initially set to 0 because the array does not yet house any album objects
     */
    public Collection(){
        this.albums = new Album[INITIAL_CAPACITY];
        this.size = 0;
    }

    //Setters and Getters

    /**
     * Getter method for the size variable
     * Useful for determining the stopping point of for loops iterating through the albums array
     * @return size: an int representing how many albums are in the array, not the length of the array
     */
    public int getSize(){
        return this.size;
    }

    /**
     * Setter method for the size variable
     * Useful when the albums array's capacity needs to be incremented by 4
     * @param size: an integer value representing how many albums are in the albums array
     */
    public void setSize(int size){
        this.size = size;
    }

    /**
     * Getter method for the Album[] albums data structure
     * Important in accessing the albums because iterating through the array is the only way to directly access them
     * @return albums: an array of type Album holding all the albums added
     */
    public Album[] getAlbums(){
        return this.albums;
    }

    /**
     * Setter method for Album[] albums data structure
     * Useful in updating the albums variable after the capacity is increased by 4
     * @param albums: an array of type Album
     */
    public void setAlbums(Album[] albums) {
        this.albums = albums;
    }


    /**
     * A method that determines whether the albums array contains a specific album object inputted as a parameter
     * @param album: An album object
     * @return true: the album exists in the albums array
     * @return false: the album does not exist in the albums array
     */
    public boolean contains(Album album){
        for(int i = 0; i < getSize(); i++) {
            boolean title = album.getTitle().equalsIgnoreCase(albums[i].getTitle());
            boolean artistName = album.getArtist().getName().equalsIgnoreCase(albums[i].getArtist().getName());
            int artistDate = album.getArtist().getBorn().compareTo(albums[i].getArtist().getBorn());
            if(title && artistName && artistDate == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * A method to add an album objects to the albums array, an album that already exists in the array is not added
     * @param album: an album object
     * @return true: the album does not already exist in the array and was added
     * @return false: the album already exists in the array and was not added
     */
    public boolean add(Album album){
        if(this.contains(album)){
            return false;
        }
        int albumsArrayLength = this.getAlbums().length;
        if(this.getSize() == albumsArrayLength){
            this.grow();
        }
        this.getAlbums()[getSize()] = album;
        setSize(getSize() + 1);
        return true;
    }

    /**
     * A method used to grow the capacity of the albums array once it is full
     * The capacity is increased by 4 stored as the constant ARRAY_GROWTH_SIZE
     * It is a helper method of the add method
     */
    private void grow(){
        //this.setAlbums(Arrays.copyOf(this.getAlbums(), this.getSize() + ARRAY_GROWTH_SIZE));
        Album[] expandedAlbums = new Album[this.getSize() + ARRAY_GROWTH_SIZE];
        for(int i = 0; i < getSize(); i++){
            expandedAlbums[i] = getAlbums()[i];
        }
        setAlbums(expandedAlbums);
    }

    /**
     * A helper method of the rate and remove class used to find the specific album object inputted
     * @param album: an Album object
     * @return i: returns the index that the album is located at in the albums array
     * @return NOT_FOUND: a constant representing -1, meaning the album does not exist in the arrau
     */
    private int find(Album album){
        for(int i = 0; i < getSize(); i++) {
            boolean title = album.getTitle().equalsIgnoreCase(albums[i].getTitle());
            boolean artistName = album.getArtist().getName().equalsIgnoreCase(albums[i].getArtist().getName());
            int artistDate = album.getArtist().getBorn().compareTo(albums[i].getArtist().getBorn());
            if(title && artistName && artistDate == 0) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public Album returnAlbum(Album album) {
        int index = this.find(album);
        if (index != NOT_FOUND) {
            return albums[index];
        } else {
            return null;
        }
    }
    /**
     * A method that removes the inputted album from the albums array if it is present
     * @param album: an Album object
     * @return true: the album existed in the albums array and was removed
     * @return false: the album does not exist in the albums array
     */
    public boolean remove(Album album){
        Album[] albums = this.getAlbums();
        int size = this.getSize();
        if(this.contains(album)){
            int index = this.find(album);
            if(index == size - 1){
                albums[index] = null;
            }
            else{
                for(int i = index; i < size - 1; i++){
                    albums[i] = albums[i + 1];
                }
                albums[size - 1] = null;
            }
            this.setSize(size - 1);
            return true;
        }
        return false;
    }

    /**
     * A method that assigns a rating to an album if it exists in the albums array
     * @param album: an Album object
     * @param rating: an int ranging from 1 to 5
     */
    public void rate(Album album, int rating){
        if(this.contains(album)){
            int index = this.find(album);
            this.getAlbums()[index].rate(rating);
        }
    }

    /**
     * A method that implements the selection sort algorithm prioritizing the average rating
     * If average rating is equal then the title is used to break the tie
     * @param albums: an array holding Album objects
     */
    public void selectionSortRating(Album[] albums){
        int lastIndexOfArray = getSize() - 1;

        for(int i = 0; i < lastIndexOfArray ; i++){
            Album max = albums[i];
            int indexOfMax = i;
            for(int j = i + 1; j < getSize(); j++){
                Album currentAlbum = albums[j];
                boolean currentAlbumRatingsGreaterThanMax = currentAlbum.avgRatings() > max.avgRatings();
                boolean ratingsEqualButTitleComesBefore = currentAlbum.avgRatings() == max.avgRatings() && currentAlbum.compareTitles(max) == COMES_BEFORE;
                boolean maxRatingsNaNAndCurrentAlbumRatingsNotNaN = Double.isNaN(max.avgRatings()) && !Double.isNaN(currentAlbum.avgRatings());
                boolean bothRatingsNaNAndTitleComesBefore = Double.isNaN(max.avgRatings()) && Double.isNaN(currentAlbum.avgRatings()) && currentAlbum.compareTitles(max) == COMES_BEFORE;

                if(currentAlbumRatingsGreaterThanMax || ratingsEqualButTitleComesBefore ||
                        maxRatingsNaNAndCurrentAlbumRatingsNotNaN || bothRatingsNaNAndTitleComesBefore)
                {
                    max = currentAlbum;
                    indexOfMax = j;
                    }
                }
            swap(albums, i, indexOfMax);
            }

        }
    /**
     * A method that implements the selection sort algorithm prioritizing the Genre
     * If Genre is equal then the artist is used to break the tie
     * @param albums: an array holding Album objects
     */
    public void selectionSortGenre(Album[] albums){
        int lastIndexOfArray = getSize() - 1;

        for(int i = 0; i < lastIndexOfArray ; i++){
            Album max = albums[i];
            int indexOfMax = i;
            for(int j = i + 1; j < getSize(); j++){
                Album currentAlbum = albums[j];
                if(currentAlbum.genreCompare(max) == COMES_BEFORE){
                    max = currentAlbum;
                    indexOfMax = j;
                }
            }
            swap(albums, i, indexOfMax);
        }
    }
    /**
     * A method that implements the selection sort algorithm prioritizing the release date
     * If release date is equal then the album title is used to break the tie
     * @param albums: an array holding Album objects
     */
    public void selectionSortDate(Album[] albums){
        int lastIndexOfArray = getSize() - 1;

        for(int i = 0; i < lastIndexOfArray ; i++){
            Album max = albums[i];
            int indexOfMax = i;
            for(int j = i + 1; j < getSize(); j++){
                Album currentAlbum = albums[j];
                if(currentAlbum.getReleased().compareTo(max.getReleased()) == -1){
                    max = currentAlbum;
                    indexOfMax = j;
                }
                if(currentAlbum.getReleased().compareTo(max.getReleased()) == 0){
                    if(currentAlbum.compareTitles(max) == -1){
                        max = currentAlbum;
                        indexOfMax = j;
                    }
                }
            }
            swap(albums, i, indexOfMax);
        }
    }

    /**
     * A helper method for the selection sort algorithm to swap values at two indices
     * @param albums: an array of Album objects
     * @param index1: index of an Album object
     * @param index2: index of another Album object
     */
    private void swap(Album[] albums, int index1, int index2) {
        Album temp = albums[index1];
        albums[index1] = albums[index2];
        albums[index2] = temp;
    }

    /**
     * A method to print out all Album objects in the albums array
     */
    public void displayAlbums(){
        int arrSize = this.getSize();
        for(int i = 0; i < arrSize; i++){
            System.out.println(this.getAlbums()[i].toString());
        }
    }

    /**
     * Sorts by Rating and then prints all albums
     */
    public void printByRating(){
        if (getSize() == 0) {
            System.out.println("Collection is empty!");
            return;
        }
        System.out.println("* Collection sorted by Rating/Title *");
        selectionSortRating(this.getAlbums());
        displayAlbums();
        System.out.println("* end of list *");
    }

    /**
     * Sorts by Genre then prints all the albums
     */
    public void printByGenre(){
        if (getSize() == 0) {
            System.out.println("Collection is empty!");
            return;
        }
        System.out.println("* Collection sorted by Genre/Artist *");
        selectionSortGenre(this.getAlbums());
        displayAlbums();
        System.out.println("* end of list *");
    }
    
    /**
     * Sorts by release date then prints all the albums
     */
    public void printByDate(){
        if (getSize() == 0) {
            System.out.println("Collection is empty!");
            return;
        }
        System.out.println("* Collection sorted by Released Date/Title *");
        selectionSortDate(this.getAlbums());
        displayAlbums();
        System.out.println("* end of list *");
    }
}
