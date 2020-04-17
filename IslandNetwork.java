/**
 * @author Avik Kadakia
 * email:avik.kadakia@stonybrook.edu
 * Stony Brook ID: 111304945
 *
 * Class: CSE 214.02
 * Recitation: CSE 214 - R.14
 *
 * The class creates a <code>IslandNetwork</code> which will serve as the main class for the city object.
 */

import big.data.DataSource;
import java.util.*;

public class IslandNetwork
{
    private static String[] cityNames; // Creates a string array to store the city names
    private static String[] roadNames; // Creates a string array to store the road names.
    private static HashMap<String, City> graph = new HashMap<String, City>(); // Creates a HashMap with the cities.
    private static ArrayList <String> path = new ArrayList(); //Creates an ArrayList of the path.
    private static ArrayList <Integer> flow = new ArrayList(); //Creates an ArrayList of the flow.
    private static ArrayList <String> routePath = new ArrayList(); //Creates an ArrayList of the path.
    private static Scanner sc = new Scanner(System.in); // Creates a scanner.

    /**
     * This method works as the one to retrieve data from the XML File.
     */
    public static void loadFromFile(String url)
    {
        try
        {
            DataSource ds = DataSource.connectXML(url);

            ds.load();

            String cityNamesStr = ds.fetchString("cities");

            cityNames = cityNamesStr.substring(1, cityNamesStr.length() - 1).replace("\"", "").split(",");

            String roadNamesStr = ds.fetchString("roads");

            roadNames = roadNamesStr.substring(1, roadNamesStr.length() - 1).replace("\"", "").split(",");

            ArrayList<City> cityNamesAL = new ArrayList();

            for (String city : cityNames) {
                City addCity = new City(city);

                cityNamesAL.add(addCity);
            }

            Collections.sort(cityNamesAL, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.compareTo(o2);
                }
            });

            for (int i = 0; i < cityNamesAL.size(); i++){
                cityNames[i] = cityNamesAL.get(i).getName();
            }

            System.out.println("\nMap loaded.");

            System.out.println("\nCities:\n--------------------------------------");

            for (int i = 0; i < cityNames.length; i++)
            {
                String cityName = cityNames[i];

                City x = new City(cityName);

                graph.put(cityName, x);

                System.out.println(cityNames[i].toString());
            }

            System.out.println("\nRoad\t\t\t\t\t\t\t\t\tCapacity\n------------------------------------------------");

            for (int i = 0; i < roadNames.length; i+=3)
            {
                System.out.printf("%-35s %10s\n", roadNames[i] + " to " + roadNames[i + 1], roadNames[i + 2]);
            }

            for (int i = 0; i < roadNames.length; i += 3)
            {
                String cityName = roadNames[i];

                int num = Integer.parseInt(roadNames[i + 2]);

                graph.get(cityName).getNeighbors().put(roadNames[i + 1], num);
            }
        }

        catch (Exception e)
        {
            System.out.println("The map is not available");
        }
    }

    /**
     * Does a depth first search through the graph to look at all the neighbors of city.
     *
     * @param search
     *      Name of the city.
     *
     * @return
     *      Returns an ArrayList of strings consisting.
     */
    public static ArrayList<String> depthFirstSearch(String search)
    {
        try {
            graph.get(search).setVisited(true);

            for (String y : (graph.get(search).getNeighbors()).keySet()) {
                if (!graph.get(y).isDiscovered()) {
                    graph.get(y).setDiscovered(true);
                    routePath.add(y);
                    depthFirstSearch(y);
                }
            }
        }

        catch (Exception e)
        {
            System.out.println("The input entered is incorrect. Please try again!");

            while (!IslandDesigner.quitMenu)
            {
                IslandDesigner.runMenu();
            }
        }
            return routePath;

    }

    /**
     * Finding the maximum flow from a city to another.
     *
     * @param from
     *      Finding the path from this city.
     *
     * @param to
     *      Finding a path to this city.
     */
    public static String maxFlowDFS(String from, String to)
    {
        path = new ArrayList<>();
        flow = new ArrayList<>();

        int max = 0;

        findRoute(path, flow, "", from, to, Integer.MAX_VALUE);

        if(path.isEmpty())
        {
            path.add("There are no routes available!");
        }

        if(from.compareTo(to) == 0)
        {
            System.out.println("You are already at the city.");
            return "";
        }

        try {
            for (int i = 0; i < path.size(); i++) {
                path.set(i, path.get(i) + to + " [" + flow.get(i) + "]");

                System.out.println(path.get(i));

                max += flow.get(i);
            }

            System.out.println("\nMaximum Flow: " + max);
        }

        catch (IndexOutOfBoundsException i)
        {
            System.out.println("The cities you chose cannot have a route. Please try different cities.");

            while (!IslandDesigner.quitMenu)
            {
                IslandDesigner.runMenu();
            }

        }

        return "";
    }

    /**
     * Finding a route from a city to another.
     *
     * @param route
     *      The route of the city.
     *
     * @param flow
     *      The flow from a city to another.
     *
     * @param currentRoute
     *      The current route of a city.
     *
     * @param atCity
     *      The current city in the route.
     *
     * @param toCity
     *      The destination city.
     *
     * @param num
     *      The capacity of the route.
     */
    public static void findRoute(ArrayList <String> route, ArrayList <Integer> flow, String currentRoute, String atCity, String toCity, int num)
    {
        if(toCity.equals(atCity))
        {
            route.add(currentRoute);
            flow.add(num);
        }

        else
        {
            currentRoute += atCity + " -> ";
        }

        for(String fromCity : (graph.get(atCity).getNeighbors()).keySet())
        {
            //flow.add((graph.get(atCity).getNeighbors().get(fromCity)));
            findRoute(route,
                    flow,
                    currentRoute,
                    fromCity,
                    toCity,
                    Math.min(num, graph.get(atCity).getNeighbors().get(fromCity)));
        }
    }

    /**
     * Resets the city to not visited that were marked visited.
     */
    public static void resetDFSCities()
    {
        for(String y : graph.keySet())
        {
            graph.get(y).setDiscovered(false);
        }
    }
}

