package TRAFFIC__CONTROL__SYSTEM;
import java.util.*;
/*
Flow -> 
         Normal Mode:
                     All routes are open.
                     Traffic lights operate on standard logic (e.g., timers or vehicle count).
                     Routes are computed based on shortest time.

         Disaster Mode:
                     Some roads or junctions are blocked due to flood/accident.
                     System reroutes traffic dynamically.
                     Only allowed paths are considered.

[TrafficControlSystem]
        |
        |-- List<Junction>
        |-- List<Road>
        |-- Modes - Normal , Disaster
        |-- routeVehicle(fromJunction, toJunction)

[Junction]
        |
        |-- List<TrafficLight>
        |-- List<Road> connectedRoads

[ Road]
        |
        |-- Junction source
        |-- Junction destination
        |-- int roadLength
        |-- boolean isBlocked

[VehicleRoutePlanner]
        |
        |-- findRoute(from, to, roads, mode)  using Djikstra or Simple BFS
        
        enum LightColor { RED, YELLOW, GREEN }
        enum Mode { NORMAL, DISASTER }
 
 
=== NORMAL MODE ===
[A, C, D]
[A, B, C, D]

=== DISASTER MODE (A→C blocked) ===
[A, B, C, D]


A --2-- B
|      / |
2    2   5
|   /    |
C ------ D
    2
     
     */
enum Mode { NORMAL, DISASTER }
enum LightColor { RED, YELLOW, GREEN }

class TrafficLight {
    private LightColor color;

    public TrafficLight() {
        this.color = LightColor.RED;
    }

    public void switchTo(LightColor color) {
        this.color = color;
    }

    public LightColor getColor() {
        return color;
    }
}

class Road {
    Junction source;
    Junction destination;
    int distance;
    boolean isBlocked;

    public Road(Junction source, Junction destination, int distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.isBlocked = false;
    }

    public void block() {
        this.isBlocked = true;
    }

    public void unblock() {
        this.isBlocked = false;
    }
}

class Junction {
    String name;
    List<TrafficLight> lights = new ArrayList<>();
    List<Road> connectedRoads = new ArrayList<>();

    public Junction(String name) {
        this.name = name;
    }

    public void addRoad(Road road) {
        connectedRoads.add(road);
        lights.add(new TrafficLight()); // one light per outgoing road
    }

    public List<Road> getAvailableRoads(Mode mode) {
        List<Road> available = new ArrayList<>();
        for (Road r : connectedRoads) {
            if (mode == Mode.NORMAL || !r.isBlocked) {
                available.add(r);
            }
        }
        return available;
    }
    @Override
    public String toString() {
        return name;  // or whatever field represents the junction name
    }
}

class TrafficControlSystem {
    List<Junction> junctions = new ArrayList<>();
    List<Road> roads = new ArrayList<>();
    Mode mode = Mode.NORMAL;

    public void addJunction(Junction j) {
        junctions.add(j);
    }

    public void addRoad(Road r) {
        roads.add(r);
        r.source.addRoad(r);
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public List<List<Junction>> findShortestPaths(Junction start, Junction end) {
        return RoutePlanner.findShortestPaths(start, end, mode);
    }
}

class RoutePlanner {
    static class RouteNode {
        Junction junction;
        int distance;

        public RouteNode(Junction junction, int distance) {
            this.junction = junction;
            this.distance = distance;
        }
    }

    public static List<List<Junction>> findShortestPaths(Junction source, Junction destination, Mode mode) {
    	
        Map<Junction, Integer> distances = new HashMap<>(); // minimum distance from source to each junction
        Map<Junction, List<List<Junction>>> pathsMap = new HashMap<>(); //  Map stores shortest paths from the source (A) to every other junction.
        PriorityQueue<RouteNode> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        distances.put(source, 0);  // Distance from source to itself is 0
        List<Junction> initialPath = new ArrayList<>();
        initialPath.add(source);
        pathsMap.put(source, new ArrayList<>(List.of(initialPath)));

        queue.add(new RouteNode(source, 0));

        while (!queue.isEmpty()) {  // Djikstra Algorithm
            RouteNode currentNode = queue.poll();
            
            Junction currentJunction = currentNode.junction;
            int currentDistance = currentNode.distance;

            for (Road road : currentJunction.getAvailableRoads(mode)) {  // Iterate through all roads (edges) from the current junction
                Junction neighbor = road.destination;
                int newDistance = currentDistance + road.distance;

                int knownDistance = distances.getOrDefault(neighbor, Integer.MAX_VALUE); // if not found then put max as default value

                if (newDistance < knownDistance) {
                    distances.put(neighbor, newDistance);
                    List<List<Junction>> newPaths = new ArrayList<>();
                    for (List<Junction> path : pathsMap.get(currentJunction)) {
                        List<Junction> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        newPaths.add(newPath);
                    }
                    pathsMap.put(neighbor, newPaths);
                    queue.add(new RouteNode(neighbor, newDistance));
                } else if (newDistance == knownDistance) {
                    for (List<Junction> path : pathsMap.get(currentJunction)) {
                        List<Junction> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        pathsMap.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(newPath);
                    }
                }
            }
        }

        return pathsMap.getOrDefault(destination, new ArrayList<>()); // { D=[[A, B, D], [A, B, C, D]], C=[[A, B, C]], A=[[A]], B=[[A, B]] }
    }
}

public class TrafficControlSystemSolution {
    public static void main(String[] args) {
        TrafficControlSystem system = new TrafficControlSystem();

        Junction A = new Junction("A");
        Junction B = new Junction("B");
        Junction C = new Junction("C");
        Junction D = new Junction("D");

        system.addJunction(A);
        system.addJunction(B);
        system.addJunction(C);
        system.addJunction(D);

        Road AB = new Road(A, B, 2);
        Road AC = new Road(A, C, 2);
        Road BC = new Road(B, C, 2);
        Road BD = new Road(B, D, 4);
        Road CD = new Road(C, D, 2);

        system.addRoad(AB);
        system.addRoad(AC);
        system.addRoad(BC);
        system.addRoad(BD);
        system.addRoad(CD);

        System.out.println("=== NORMAL MODE ===");
        List<List<Junction>> normalPaths = system.findShortestPaths(A, D);
        for (List<Junction> path : normalPaths) {
            System.out.println(path);
        }

        AC.block(); // blocking AC road 
        system.setMode(Mode.DISASTER);

        System.out.println("\n=== DISASTER MODE (A→C blocked) ===");
        List<List<Junction>> disasterPaths = system.findShortestPaths(A, D);
        for (List<Junction> path : disasterPaths) {
            System.out.println(path);
        }
    }
}
