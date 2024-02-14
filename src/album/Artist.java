package album;
/**
The Artist class which creates a blueprint for creating Artist objects
@author Rohan Sharma
 */

public class Artist implements Comparable<Artist> {

    private static final int firstNameIndex = 0;
    private static final int BEFORE = -1;
    private static final int AFTER = 1;
    private static final int EQUAL = 0;
    private String name;
    private Date born;

    /**
    Parameterized constructor for the Artist class which creates an Artist object
    The class takes in an instance variables: name
    The class takes in an instance object: born, which is a Date object
    @param name: a string value representing the full name of the artist
    @param born: a Date object representing the birthdate of the artist
     */
    public Artist(String name, Date born){
        setName(name);
        setBorn(born);
    }

    //Setters and Getters
    /**
    Setter method for the name instance variable
    @param name: a string value representing the artist's name
     */
    public void setName(String name){
        this.name = name;
    }
    /**
    Getter method for the name instance variable
    @return name: a string
     */
    public String getName(){
        return this.name;
    }
    /**
    Setter method for the Date object
    @param born: a Date object representing the date of birth of the artist
     */
    public void setBorn(Date born){
        this.born = born;
    }
    /**
    Getter method for the Date object
    @return born: a Date object
     */
    public Date getBorn(){
        return this.born;
    }
    /**
    A method the compares two Artist objects to determine their relationship in terms of name and then birthdate if name is the same
    @param Artist otherArtist: Another Artist object meant to be compared with the Artist object calling the method
    @return -1: The Artist object calling the method's name comes before the other artist's name or in the case of a name tie, their date of birth is earlier
    @return 0: The Artist object calling the method's name and date of birth is the same as the other artist's name
    @return 1: The Artist object calling the method's name comes after the other artist's name or in the case of a name tie, their date of birth is later
     */
    @Override
    public int compareTo(Artist otherArtist){
        String thisName = this.getName();
        String otherName = otherArtist.getName();

        String[] thisNameArr = thisName.split("\\s+");
        String[] otherNameArr = otherName.split("\\s+");

        String thisFirstName = thisNameArr[firstNameIndex].toLowerCase();
        String otherFirstName = otherNameArr[firstNameIndex].toLowerCase();

        int thisLastNameIndex = thisNameArr.length - 1;
        int otherLastNameIndex = otherNameArr.length - 1;

        String thisLastName = thisNameArr[thisLastNameIndex].toLowerCase();
        String otherLastName = otherNameArr[otherLastNameIndex].toLowerCase();

        int firstNameComparison = acsiiComparison(thisFirstName,otherFirstName);
        if(firstNameComparison != EQUAL){
            return firstNameComparison;
        }

        int lastNameComparison = acsiiComparison(thisLastName, otherLastName);
        if (lastNameComparison != EQUAL){
            return lastNameComparison;
        }
        return this.getBorn().compareTo(otherArtist.getBorn());
    }
    /**
    Helper method for the compareTo method
    The method takes in two strings and compares their ascii values to determine the sorting order for the two strings
    @param one: the string of the object calling the method
    @param two: the string of the other object being compared
    @return -1: string one comes before string two
    @return 1: string one comes after string two
    @return 0: the strings are equivalent
    */
    private int acsiiComparison(String one, String two){
        int stop;
        if(one.length() <= two.length()){
            stop = one.length();
        }
        else{
            stop = two.length();
        }
        for(int i = 0; i < stop; i++){
            int asciiOne = (int)one.charAt(i);
            int asciiTwo = (int)two.charAt(i);

            if(asciiOne < asciiTwo){
                return BEFORE;
            } else if (asciiTwo < asciiOne) {
                return AFTER;
            }
        }
        return Integer.compare(one.length(), two.length());

    }
    /**
    The equals method determines if two Artist objects are equivalent
    Equal in this case means that the names of the Artists are the same and the date of their birth is also the same
    @param Object obj: an Artist object to be compared with the Artist object calling the method
    @return true: the Artist objects have the same name and date of birth
    @return false: the Artist objects do not have the same name and date of birth
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Artist otherArtist = (Artist) obj;
        return this.name.equals(otherArtist.name) && (this.born.compareTo(otherArtist.born)==EQUAL);
    }
    /**
    Overrides the toString method from the Object class to provide a more meaningful return string
    @return the format of the return is a string, "{artist's name}:{month}/{day}/{year}"
     */
    @Override
    public String toString(){
        int day = this.getBorn().get_day();
        int month = this.getBorn().get_month();
        int year = this.getBorn().get_year();
        return this.getName() + ":" + month + "/" + day + "/" + year;
    }

    public static void main(String[] args){
        
        Date earlierDate = new Date(1990, 10, 30);
        Date laterDate = new Date(2020, 7, 23);
        Date earlierDateDuplicate = new Date(1990, 10, 30);

        Artist artistOne = new Artist("Jacky Smalls", earlierDate);
        Artist artistTwo = new Artist("Yasmine Ahmed", earlierDate);
        Artist artistThree = new Artist("Jacky Smalls", laterDate);
        Artist artistOneDuplicate = new Artist("Jacky Smalls", earlierDateDuplicate);
        //Test 1
        int testOne = artistOne.compareTo(artistTwo);
        System.out.println("Test 1: " + testOne);
        //Test 2
        int testTwo = artistTwo.compareTo(artistOne);
        System.out.println("Test 2: " + testTwo);
        //Test 3
        int testThree = artistOne.compareTo(artistThree);
        System.out.println("Test 3: " + testThree);
        //Test 4
        int testFour = artistThree.compareTo(artistOne);
        System.out.println("Test 4: " + testFour);
        //Test5
        int testFive = artistOne.compareTo(artistOneDuplicate);
        System.out.println("Test 5: " + testFive);


    }
}


