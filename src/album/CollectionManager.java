package album;
/**
The CollectionManager class which manages the inputs of commands and returns whether it is invalid or not
@author Seungjun Bae
 */
import java.util.Scanner;
public class CollectionManager {
    private static final int A_LENGTH = 6; 
    private static final int D_LENGTH = 4; 
    private static final int R_LENGTH = 5; 
    private static final int DATE_LENGTH = 3; 

    /**
     * Processes the 'add' command, attempting to add a new album to the collection.
     * Validates command arguments, creates album and artist objects, and adds the album to the collection if it is not already present.
     * 
     * @param min The array of strings containing command arguments.
     * @param op The collection to which the album will be added.
     */
    private void thingA(String[] min, Collection op){
        if(min.length != A_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        String[] artist = min[3].split("/");
        String[] release = min[5].split("/");
        if (artist.length != DATE_LENGTH || release.length != DATE_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        Date artistDate = new Date(Integer.parseInt(artist[2]), Integer.parseInt(artist[0]), Integer.parseInt(artist[1]));
        Date releaseDate = new Date(Integer.parseInt(release[2]), Integer.parseInt(release[0]), Integer.parseInt(release[1]));
        if (!artistDate.isValid()) {
            System.out.printf("Artist DOB: %s is invalid.\n", artistDate.toString());
            return;
        } else if (!releaseDate.isValid()) {
            System.out.printf("Date Released: %s is invalid.\n", releaseDate.toString());
            return;
        }
        Album album = new Album(min[1], new Artist(min[2], artistDate), Genre.toGenre(min[4]), releaseDate, null); 
        if (op.add(album)) {
            System.out.printf("%s(%s:%s) added to the collection.\n", min[1], min[2], min[3]);
        } else {
            System.out.printf("%s(%s:%s) is already in the collection.\n", min[1], min[2], min[3]);
        }
    }
    /**
     * Processes the delete command, attempting to remove an album from the collection.
     * Validates command arguments and removes the specified album if it exists in the collection.
     * 
     * @param min The array of strings containing command arguments.
     * @param op The collection from which the album will be removed.
     */
    private void thingD(String[] min, Collection op) {
        if (min.length != D_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        String[] artist = min[3].split("/");
        if (artist.length != DATE_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        Date artistDate = new Date(Integer.parseInt(artist[2]), Integer.parseInt(artist[0]), Integer.parseInt(artist[1]));
        Album album = new Album(min[1], new Artist(min[2], artistDate), null, null, null);
        if (op.remove(album)) {
            System.out.printf("%s(%s:%s) removed from the collection.\n", min[1], min[2], min[3]);
        } else {
            System.out.printf("%s(%s:%s) is not in the collection\n", min[1], min[2], min[3]);
        }
    }
    /**
     * Processes the rate command, assigning a rating to an existing album in the collection.
     * Validates command arguments and applies the specified rating to the album if it exists.
     * 
     * @param min The array of strings containing command arguments.
     * @param op The collection containing the album to be rated.
     */
    private void thingR(String[] min, Collection op) {
        if (min.length != R_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        int rating = Integer.parseInt(min[4]);
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating, rating scale is 1 to 5.");
            return;
        }
        String[] artist = min[3].split("/");
        if (artist.length != DATE_LENGTH) {
            System.out.println("Invalid command!");
            return;
        }
        Date artistDate = new Date(Integer.parseInt(artist[2]), Integer.parseInt(artist[0]), Integer.parseInt(artist[1]));
        Album album = new Album(min[1], new Artist(min[2], artistDate), null, null, null);
        if (op.contains(album)) {
            op.rate(album, rating);
            System.out.printf("You rate %o for %s:%s(%s)\n", rating, min[1], op.returnAlbum(album).getReleased(), min[2]);
        } else {
            System.out.printf("%s(%s:%s) is not in the collection\n", min[1], min[2], min[3]);
        }
    }
    /**
     * Starts the collection manager, accepting and processing commands from the console until the quit command is received.
     * Continuously reads lines from the console, interprets them as commands, and calls the corresponding method to handle each command.
     */
    public void run(){
        System.out.println("Collection Manager is up running.");
        Scanner sc = new Scanner(System.in);
        Collection op = new Collection();
        while(true){
            String line = sc.nextLine();
            String[] min = line.split(",");
            String command = min[0];
            if(command.compareTo("A") == 0){
                thingA(min, op);
            }else if(command.compareTo("D") == 0){
                thingD(min, op);
            }else if(command.compareTo("R") == 0){
                thingR(min, op);
            }else if(command.compareTo("PD") == 0){
                op.printByDate();
            }else if(command.compareTo("PG") == 0){
                op.printByGenre();
            }else if(command.compareTo("PR") == 0){
                op.printByRating();
            }else if(command.compareTo("Q") == 0){
                System.out.println("Collection Manager terminated.");
                System.exit(0);
            }else{
                System.out.println("Invalid command!");
            }
        }
    }
    
}
