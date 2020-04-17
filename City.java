/**
 * @author Avik Kadakia
 * email:avik.kadakia@stonybrook.edu
 * Stony Brook ID: 111304945
 *
 * Class: CSE 214.02
 * Recitation: CSE 214 - R.14
 *
 * The class creates a <code>City</code> which will serve as the main object for the project.
 */

import java.util.*;

public class City implements Comparable
{
    private HashMap<String, Integer> neighbors; // keeps a HashMap of the city's neighbors.
    private String name; // name of the city.
    private boolean discovered; // states whether the city is discovered or not.
    private boolean visited; // states whether the city is visited or not.

    /**
     * Constructor for the city class.
     *
     * @param nName
     *      Creates a city with the given name.
     */
    public City(String nName)
    {
        name = nName;
        neighbors = new HashMap <>();
        discovered = false;
        visited = false;
    }

    /**
     * Compares the names of the two cities.
     *
     * @param o
     *      City that is being compared.
     *
     * @return
     *      Returns -1, 0, 1 based on whether the names are equal.
     */
    public int compareTo(Object o)
    {
        String name1 = this.name;
        String name2 = ((City) o).name;

        if (name1.compareTo(name2) < 0)
        {
            return -1;
        }

        if (name1.compareTo(name2) > 0)
        {
            return 1;
        }

        return 0;
    }

    /**
     * Gets the name of the city.
     *
     * @return
     *      Returns a string with the name of the integer.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the discovered city as the entered boolean.
     *
     * @param bool
     *      Sets whether the city is discovered or not based on the entered boolean.
     */
    public void setDiscovered(boolean bool)
    {
        discovered = bool;
    }

    /**
     *Returns whether the city is discovered or not.
     *
     * @return
     *      Returns whether the city has been discovered or not.
     */
    public boolean isDiscovered()
    {
        return discovered;
    }

    /**
     * Sets the visited city as the entered boolean.
     *
     * @param bool
     *      Sets whether the city is visited or not based on the entered boolean.
     */
    public void setVisited(boolean bool)
    {
        visited = bool;
    }

    /**
     * Returns the neighbors of the city.
     *
     * @return
     *      Returns a HashMap of all the cities that are the neighbors.
     */
    public  HashMap<String, Integer> getNeighbors()
    {
        return neighbors;
    }
}
