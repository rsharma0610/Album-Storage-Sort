package album;
/**
 * Enums that represent the genre of the Album object
 * @author Seungjun Bae
 */

public enum Genre {
    Pop,
    Country,
    Classical,
    Jazz,
    Unknown;
    /**
     * @param text The string representation of the genre to convert.
     * @return The enum value corresponding to the provided string, or if no match is found.
     */
    public static Genre toGenre(String text) {
        if (text.equalsIgnoreCase("pop")) {
            return Genre.Pop;
        } else if(text.equalsIgnoreCase("country")) {
            return Genre.Country;
        } else if(text.equalsIgnoreCase("classical")) {
            return Genre.Classical;
        } else if(text.equalsIgnoreCase("jazz")) {
            return Genre.Jazz;
        } else {
            return Genre.Unknown;
        }
    }
}
