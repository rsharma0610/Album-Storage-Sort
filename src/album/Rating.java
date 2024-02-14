package album;
/**
The Rating class which creates and maintains a list of the ratings
@author Seungjun Bae
 */
public class Rating {
    private int star;
    private Rating next;

    /**
     * @param star The star rating to be assigned to this node.
     * @param next The next object in the list, or if this is the last node.
     */
    public Rating(int star, Rating next) {
        setStar(star);
        setNext(next);
    }
    /**
    Setter method for the star
    @param star: a rating to be assigned
     */
    public void setStar(int s){
        this.star = s;
    }
    /**
    Getter method for the star
    @return star
     */
    public int getStar(){
        return this.star;
    }
    /**
    Setter method for the next node
    @param n the next object
     */
    public void setNext(Rating n){
        this.next = n;
    }
/**
    Getter method for the next node
    @return The next object or if this is the last node
     */
    public Rating getNext(){
        return next;
    }
}
