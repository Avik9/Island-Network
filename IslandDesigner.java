/**
 * @author Avik Kadakia
 * email:avik.kadakia@stonybrook.edu
 * Stony Brook ID: 111304945
 *
 * Class: CSE 214.02
 * Recitation: CSE 214 - R.14
 *
 * The class creates a <code>IslandDesigner</code> which will serve as the main class for this project.
 */

import java.util.*;

public class IslandDesigner
{
    private static Scanner sc = new Scanner(System.in); // creates a scanner.
    private static ArrayList <String> routePath = new ArrayList(); //Creates an ArrayList of the path.
    public static boolean quitMenu = false; // determines if the code should keep running

    /**
     * Main method of the program.
     */
    public static void main(String args[])
    {
        try
        {
            loadGraph();

            while (!quitMenu) {
                runMenu();
            }
        }
        catch (Exception e)
        {
            System.out.println("The input is invalid. Please try again!");

            while (!quitMenu) {
                runMenu();
            }
        }
    }

    /**
     * Prints the main menu.
     */
    public static void runMenu()
    {
        System.out.println("\nMenu:\n" +
                "\tA) Add map\n" +
                "\tD) Destinations reachable (Depth First Search)\n" +
                "\tF) Maximum Flow\n" +
                "\tQ) Quit\n");

        System.out.print("\nPlease select an option: ");
        char letter = ((sc.next()).toUpperCase()).charAt(0);

        switch (letter)
        {
            case ('A'): loadGraph();
                break;

            case ('D'): destinationsReachable();
                break;

            case ('F'): maximumFlow();
                break;

            case ('Q'):
            {
                quitMenu = true;

                System.out.println("You can go your own way! Goodbye!");
            }
                break;

            default:
            {
                System.out.println("The option selected is incorrect. Please try again.");

                runMenu();
                break;
            }
        }
    }

    public static void loadGraph()
    {
        System.out.print("Please enter an url: ");
        String url = sc.nextLine(); /*"http://www.cs.stonybrook.edu/~cse214/hw/hw7-images/hw7.xml"; */

        IslandNetwork.loadFromFile(url);

    }

    /**
     * Searches through the  graph to look for all the available routes.
     */
    public static void destinationsReachable()
    {
        System.out.print("Please enter the starting city: ");
        sc.nextLine();
        String startingCity = sc.nextLine();


        System.out.println("DFS Starting From " + startingCity + ":");

        routePath = IslandNetwork.depthFirstSearch(startingCity);

        for(int i = 0; i < routePath.size(); i++)
        {
            System.out.println(routePath.get(i));
        }

        IslandNetwork.resetDFSCities();

        routePath.clear();
    }

    /**
     * Finds the maximum from from one city to another.
     */
    public static void maximumFlow()
    {
        System.out.print("Please enter the starting city: ");
        sc.nextLine();
        String startingCity = sc.nextLine();

        System.out.print("Please enter the destination city: ");
        String destination = sc.nextLine();

        System.out.println("\nRouting: ");

        IslandNetwork.maxFlowDFS(startingCity, destination);
    }
}