package album;
import java.util.Calendar;

/**
The Date class which creates a blueprint for creating date objects
@author Rohan Sharma
 */
public class Date implements Comparable<Date>{
    //Constants
    //For determining leap years
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    //For determining chronology
    private static final int BEFORE = -1;
    private static final int AFTER = 1;
    private static final int EQUAL = 0;
    //Month constants
    private static final int JANUARY = 1;
    private static final int FEBRUARY = 2;
    private static final int MARCH = 3;
    private static final int APRIL = 4;
    private static final int MAY = 5;
    private static final int JUNE = 6;
    private static final int JULY = 7;
    private static final int AUGUST = 8;
    private static final int SEPTEMBER = 9;
    private static final int OCTOBER = 10;
    private static final int NOVEMBER = 11;
    private static final int DECEMBER = 12;
    //Day constants
    private static final int MIN_DAY = 1;
    private static final int MAX_DAYS_IN_LONG_MONTH = 31;
    private static final int MAX_DAYS_IN_SHORT_MONTH = 30;
    private static final int MAX_DAYS_IN_FEB_NON_LEAP_YEAR = 28;
    private static final int MAX_DAYS_IN_FEB_LEAP_YEAR = 29;
    //Date constraints
    Calendar calendar = Calendar.getInstance();
    private static final int MIN_YEAR = 1900;
    private int currentYear = calendar.get(Calendar.YEAR);
    private int currentMonth = calendar.get(Calendar.MONTH) + 1; // Note: Calendar.MONTH is zero-based
    private int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

    //Instance Variables
    private int year;
    private int month;
    private int day;

    /**
    Parameterized constructor for the Date class which creates a Date object
    The class takes in three instance variables: year, month, and day
    @param year: an integer value representing the year
    @param month: an integer value representing the month
    @param day: an integer value representing the day
     */
    public Date(int year, int month, int day){
        setYear(year);
        setMonth(month);
        setDay(day);
    }

    /**
    A method the compares two date objects to determine their relationship in terms of chronology
    @param Date o: Another Date object meant to be compared with the Date object calling the method
    @return -1: The Date object calling the method is chronologically before the Date it is being compared with
    @return 0: The Date object calling the method is chronologically the same as the Date it is being compared with
    @return 1: The Date object calling the method is chronologically after the Date it is being compared with
     */
    @Override
    public int compareTo(Date other) {
        if(this.year < other.year){
            return BEFORE;
        } else if (this.year > other.year) {
            return AFTER;
        } else if (this.month < other.month) {
            return BEFORE;
        } else if (this.month > other.month) {
            return AFTER;
        } else if (this.day < other.day) {
            return BEFORE;
        } else if (this.day > other.day) {
            return AFTER;
        }
        else{
            return EQUAL;
        }
    }

    /**
     * A toString method for the Date class that displays the date object in a user-friendly manner
     * @return a string formatted as month/day/year
     */
    @Override
    public String toString(){
        int day = this.get_day();
        int month = this.get_month();
        int year = this.get_year();
        return month + "/" + day + "/" + year;
    }
    // Setters and Getters
    /**
    Setter method for the year instance variable
    @param year: an integer value representing the year
     */
    public void setYear(int year){
        this.year = year;
    }
    /**
    Getter method for the year instance variable
    @return year: an integer
     */
    public int get_year(){
        return this.year;
    }
    /**
    Setter method for the month instance variable
    @param month: an integer value representing the month
     */
    public void setMonth(int month){
        this.month = month;
    }
    /**
    Getter method for the month instance variable
    @return month: an integer
     */
    public int get_month(){
        return this.month;
    }
    /**
    Setter method for the day instance variable
    @param day: an integer value representing the day
     */
    public void setDay(int day){
        this.day = day;
    }
    /**
    Getter method for the day instance variable
    @return day: an integer
     */
    public int get_day(){
        return this.day;
    }
    /**
    A method to determine if the Date is valid
    @return true, the date is valid
    @return false, the date is not valid
     */
    public boolean isValid(){
        if (year < 1900) {
            return false;
        }
        if (year > currentYear || (year == currentYear && (month > currentMonth || (month == currentMonth && day > currentDay)))) {
            return false;
        }
        if(is_leap_year()){
            return isValid_month() && isValid_day_leap_year();
        }
        else{
            return isValid_month() && isValid_day();
        }
    }


    /**
    A helper function for the isValid method to determine if the year is a leap year
    Knowing if the date is a leap year effects whether February can have 29 days or 28 days
    @return true: the year is a leap year
    @return false: the year is not a leap year
     */
    private boolean is_leap_year(){
        if(this.year % QUADRENNIAL == 0){
            if(this.year % CENTENNIAL == 0){
                return this.year % QUATERCENTENNIAL == 0;
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }


    /**
    Helper method for the isValid method to determine if the month value is valid
    A month value between 1 and 12 is considered valid
    @return true: the month is valid
    @return false: the month is not valid
     */
    private boolean isValid_month(){
        return this.month >= JANUARY && this.month <= DECEMBER;
    }

    /**
    A helper method for the isValid method to determine if the day is valid
    The day's validity is dependent on the current month value and, in some cases, if it is a leap year
    This method is for when it is not a leap year so for February the max day value is 28
    @return true: the day is valid
    @return false: the day is not valid
     */
    private boolean isValid_day(){
        return switch (this.month) {

            case JANUARY, MARCH, MAY, JULY, AUGUST, OCTOBER, DECEMBER -> // January, March, May, July, August, October, December
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_LONG_MONTH);

            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> // April, June, September, November
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_SHORT_MONTH);

            case FEBRUARY ->  // February
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_FEB_NON_LEAP_YEAR); // Assuming a non-leap year
            default -> {
                yield false;
            }
        };
    }

    /**
    A helper method for the isValid method to determine if the day is valid
    The day's validity is dependent on the current month value and, in some cases, if it is a leap year
    This method is for when it is a leap year so for February the max day value is 29
    @return true: the day is valid
    @return false: the day is not valid
     */
    private boolean isValid_day_leap_year(){
        return switch (this.month) {

            case JANUARY, MARCH, MAY, JULY, AUGUST, OCTOBER, DECEMBER -> // January, March, May, July, August, October, December
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_LONG_MONTH);

            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> // April, June, September, November
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_SHORT_MONTH);

            case FEBRUARY ->  // February
                    (day >= MIN_DAY && day <= MAX_DAYS_IN_FEB_LEAP_YEAR); // Assuming a non-leap year
            default -> {
                yield false;
            }
        };
    }
    public static void main(String[] args){
        //Test 1
        Date testOne = new Date(1899, 10, 10);
        System.out.println("Test 1: " + testOne.isValid());
        //Test 2
        Date testTwo = new Date(2025, 7, 7);
        System.out.println("Test 2: " + testTwo.isValid());
        //Test 3
        Date testThree = new Date(2021, 2, 29);
        System.out.println("Test 3: " + testThree.isValid());
        //Test 4
        Date testFour = new Date(2020, 2, 29);
        System.out.println("Test 4: " + testFour.isValid());
        //Test 5
        Date testFive = new Date(2021, 2, 0);
        System.out.println("Test 5: " + testFive.isValid());
        //Test 6
        Date testSix = new Date(2023, 13, 1);
        System.out.println("Test 6: " + testSix.isValid());
        //Test 7
        Date testSeven = new Date(2023, 0, 1);
        System.out.println("Test 7: " + testSeven.isValid());
    }
}

