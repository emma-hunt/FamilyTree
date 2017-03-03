package Model;

/**
 * Created by emmag on 2/10/2017.
 * Person class, corresponding to the Person table in the database
 *
 * @author emmag
 * @version 1.0 Feb 10, 2017
 */

public class Person {
    /** String containing the unique person id */
    private String person_id;
    /** String containing the username of the user to which this person is related */
    private String descendant;
    /** String containing the person's first name */
    private String first_name;
    /** String containing the person's last name */
    private String last_name;
    /** char containing the person's gender, must be m of f */
    private char gender;
    /** String containing the person id of the father, if they exist */
    private String father;
    /** String containing the person id of the mother, if they exist */
    private String mother;
    /** String containing the person id of the spouse, if they exist */
    private String spouse;

    /**
     * Constructor for the person class
     * @param person_id
     * @param descendant
     * @param first_name
     * @param last_name
     * @param gender
     * @param father
     * @param mother
     * @param spouse
     */
    public Person(String person_id, String descendant, String first_name, String last_name, char gender, String father, String mother, String spouse) {
        this.person_id = person_id;
        this.descendant = descendant;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.father = father;
        this.mother = mother;
        this.spouse = spouse;
    }

    /**
     * determines whether a person is an ancestor of a user
     * @param u user object
     * @return boolean
     */
    public boolean isAncestorOf(User u) {
        return true;
    }
    /**
     * returns the id of the person
     * @return person_id
     */
    public String getPerson_id() {
        return person_id;
    }

    /**
     * returns the user the person is connected to
     * @return
     */
    public String getDescendant() {
        return descendant;
    }

    /**
     * returns the first name of the person
     * @return first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * returns the last name of the person
     * @return last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * returns the gender of the person, either m or f
     * @return gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * returns the father of the person, if the exist
     * @return father
     */
    public String getFather() {
        return father;
    }

    /**
     * returns the mother of the person, if they exist
     * @return mother
     */
    public String getMother() {
        return mother;
    }

    /**
     * returns the spouse of the person, if they exist
     * @return spouse
     */
    public String getSpouse() {
        return spouse;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(this == null && o != null) {
            return false;
        }
        if(this != null && o == null) {
            return false;
        }
        if(this.getClass() != o.getClass()){
            return false;
        }
        Person that = (Person) o;
        if(!this.getPerson_id().equals(that.getPerson_id())){
            return false;
        }
        if(!this.getDescendant().equals(that.getDescendant())){
            return false;
        }
        if(!this.getFirst_name().equals(that.getFirst_name())){
            return false;
        }
        if(!this.getLast_name().equals(that.getLast_name())){
            return false;
        }
        if(this.getGender() != that.getGender()){
            return false;
        }

        if(this.getFather() != null || that.getFather() != null) {
            if (this.getFather() == null && that.getFather() != null) {
                return false;
            } else if (!this.getFather().equals(that.getFather())) {
                return false;
            }
        }

        if(this.getMother() != null || that.getMother() != null) {
            if(this.getMother() == null && that.getMother() != null) {
                return false;
            }
            if(!this.getMother().equals(that.getMother())){
                return false;
            }

        }
        if(this.getSpouse() != null || that.getSpouse() != null) {
            if(this.getSpouse() == null && that.getSpouse() != null) {
                return false;
            }
            if (!this.getSpouse().equals(that.getSpouse())) {
                return false;
            }
        }
        return true;
    }
}
