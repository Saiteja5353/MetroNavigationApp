import java.util.*;
import java.util.Map.Entry;

public class MetroApp2 {

    static class Pair implements Comparable<Pair> {
        int vertex;
        int distance;

        Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int compareTo(Pair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    public static int dijkstra(int adjmat[][], int v, int src, int dest) {
        int dist[] = new int[v];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, 0));

        boolean[] visited = new boolean[v];

        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int currentVertex = current.vertex;

            if (visited[currentVertex]) continue;
            visited[currentVertex] = true;

            for (int j = 0; j < v; j++) {
                if (adjmat[currentVertex][j] != 0 && !visited[j]) {
                    int newDist = dist[currentVertex] + adjmat[currentVertex][j];
                    if (newDist < dist[j]) {
                        dist[j] = newDist;
                        pq.add(new Pair(j, newDist));
                    }
                }
            }
        }

        return dist[dest];
    }

    public static String getVal(HashMap<String, Integer> hm, int value) {
        for (Entry<String, Integer> entry : hm.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static int getCity(HashMap<String, Integer> hm, String s) {
        if (hm.containsKey(s))
            return hm.get(s);
        else
            System.out.println("City Not exist");
        return -1;
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\t\t\t WELCOME TO METRO NAVIGATION APP \t\t\t");

        HashMap<String, Integer> hm = new HashMap<>();
        System.out.println("");
        // Read the number of vertices (cities)
        System.out.print("Enter number of cities:");
        int v = sc.nextInt();
        System.out.println();
        int adjmat[][] = new int[v][v];
        System.out.println("Please Name the cities....");
        System.out.println("-----------------------------");
        int c = 0;
        for (int i = 0; i < v; i++) {
            System.out.print("Name of the city" + (i + 1) + ": ");
            String s = sc.next();
            hm.put(s.toLowerCase(), c);
            c++;
            System.out.println();
        }

        // Read the number of edges
        System.out.print("Enter number of Acutal route exist among " + hm.size() + " cities:");
        int e = sc.nextInt();
        System.out.println();

        // Read the edges
        System.out.println("Enter (Source city,Destination city, distance):");
        System.out.println("------------------------------------------------");
        for (int i = 0; i < e; i++) {
            System.out.print("From city: ");
            String s1 = sc.next().toLowerCase();
            int v1 = getCity(hm, s1);
            System.out.print("Destination city: ");
            String s2 = sc.next().toLowerCase();
            int v2 = getCity(hm, s2);
            System.out.print("distance: ");
            int kms = sc.nextInt();
            adjmat[v1][v2] = kms;
            adjmat[v2][v1] = kms;
            System.out.println();
        }
        System.out.println("\t\t\tENTER OPTION");
        System.out.println();
        System.out.println("1. LIST OF ALL THE STATIONS IN THE MAP");
        System.out.println("2. SHORTEST DISTANCE FROM ONE STATION TO ALL THE STATIONS--DISTANCE");
        System.out.println("3. SHORTEST DISTANCE FROM ONE STATION TO ALL THE STATIONS--TIME");
        System.out.println("4. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATIONS--DISTANCE");
        System.out.println("5. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATION--TIME");
        System.out.println("6. EXIT");
        System.out.println();
        System.out.print("enter option");
        int opp = sc.nextInt();
        String s3, s4;
        int src, dest, dd;
        while (opp != 6) {
            switch (opp) {
                case 1:
                    System.out.println("LIST OF ALL THE STATIONS IN THE MAP");
                    System.out.println();
                    int f=1;
                    for (Entry<String, Integer> entry : hm.entrySet()) {
                        System.out.print( f+". " + entry.getKey());
                        System.out.println();
                        f++;
                    }
                    break;
                case 2:
                    System.out.println("2. FROM ONE STATION TO ALL THE STATIONS--DISTANCE");
                    System.out.print("Enter source station for shortest distance to all stations:");
                    s3 = sc.next().toLowerCase();
                    System.out.println();
                    src = getCity(hm, s3);
                    for (int i = 0; i < v; i++) {
                        dd = dijkstra(adjmat, v, src, i);
                        if (dd != Integer.MAX_VALUE)
                            System.out.println(getVal(hm, src) + " to " + getVal(hm, i) + " distance was " + dd + "kms");
                        else
                            System.out.println(getVal(hm, src) + " to " + getVal(hm, i) + " no route found");
                    }
                    break;

                case 3:
                    System.out.println("3. SHORTEST DISTANCE FROM ONE STATION TO ALL THE STATIONS--TIME");
                    System.out.print("Enter source station for shortest distance to all stations:");
                    s3 = sc.next().toLowerCase();
                    System.out.println();
                    src = getCity(hm, s3);
                    for (int i = 0; i < v; i++) {
                        dd = dijkstra(adjmat, v, src, i);
                        if (dd != Integer.MAX_VALUE)
                            System.out.println(getVal(hm, src) + " to " + getVal(hm, i) + " time was " + (dd * 5) + "minutes");
                        else
                            System.out.println(getVal(hm, src) + " to " + getVal(hm, i) + " no route found");
                    }
                    break;
                case 4:
                    System.out.println("4. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATIONS--DISTANCE");
                    System.out.print("Enter source station:");
                    s3 = sc.next().toLowerCase();
                    System.out.print("Enter destination station:");
                    s4 = sc.next().toLowerCase();
                    System.out.println();
                    src = getCity(hm, s3);
                    dest = getCity(hm, s4);
                    dd = dijkstra(adjmat, v, src, dest);
                    if (dd == Integer.MAX_VALUE)
                        System.out.println("No Route found..");
                    else
                        System.out.println(getVal(hm, src) + " to " + getVal(hm, dest) + " distance was " + dd + "kms");
                    break;
                case 5:
                    System.out.println("5. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATIONS--TIME");
                    System.out.print("Enter source station :");
                    s3 = sc.next().toLowerCase();
                    System.out.print("Enter destination station:");
                    s4 = sc.next().toLowerCase();
                    System.out.println();
                    src = getCity(hm, s3);
                    dest = getCity(hm, s4);
                    dd = dijkstra(adjmat, v, src, dest);
                    if (dd == Integer.MAX_VALUE)
                        System.out.println("No Route found..");
                    else
                        System.out.println(getVal(hm, src) + " to " + getVal(hm, dest) + " time was " + (dd * 5) + "minutes");
                    break;

                default:
                    break;

            }
            System.out.println("\t\t\tENTER OPTION");
            System.out.println();
            System.out.println("1. LIST OF ALL THE STATIONS IN THE MAP");
            System.out.println("2. SHORTEST DISTANCE FROM ONE STATION TO ALL THE STATIONS--DISTANCE");
            System.out.println("3. SHORTEST DISTANCE FROM ONE STATION TO ALL THE STATIONS--TIME");
            System.out.println("4. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATIONS--DISTANCE");
            System.out.println("5. SHORTEST DISTANCE FROM ONE STATION TO ANOTHER STATION--TIME");
            System.out.println("6. EXIT");
            System.out.println();
            System.out.print("enter option");

            opp = sc.nextInt();
        }

        sc.close();
    }
}

