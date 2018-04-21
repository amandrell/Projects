import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private static Map<String, Node> nodes = new LinkedHashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }
    static Map<String, Node> nodes() {
        return nodes;
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        System.out.println(nodes.size());
        Map<String, GraphDB.Node> lst = new LinkedHashMap<>();
        for (String s : nodes.keySet()) {
            GraphDB.Node node = nodes.get(s);
            if (node.connections.size() > 0) {
                lst.put(s, node);
            }
        }
        nodes = lst;
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        ArrayList<Long> iterable = new ArrayList<>(nodes.size());
        int i = 0;
        for (String s : nodes.keySet()) {
            String id = nodes.get(s).id;
            Long lon = Long.parseLong(id);
            iterable.add(i, lon);
            i += 1;
        }
        return iterable;
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {

        GraphDB.Node curr = nodes.get(Long.toString(v));
        ArrayList<String> s = new ArrayList<>();
        for (GraphDB.Node node : curr.connections) {
            s.add(node.id);
        }
        ArrayList<Long> iterable = new ArrayList<>(curr.connections.size());
        for (String t : s) {
            Long toadd = Long.parseLong(t);
            iterable.add(toadd);
        }
        //System.out.println(iterable);
        return iterable;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        GraphDB.Node closest = null;
        Double distance = 11111111111111.0;
        Double compare;
        GraphDB.Node set = null;
        for (String s : nodes.keySet()) {
            GraphDB.Node curr = nodes.get(s);
            String t = curr.id;
            Double longitude = Double.parseDouble(curr.lon);
            Double latitude = Double.parseDouble(curr.lat);
            compare = distance(lon, lat, longitude, latitude);
            if (compare == 0) {
                set = curr;

                break;
            }
            if (compare < distance) {
                distance = compare;
                set = curr;
            }
        }
        long x = Long.parseLong(set.id);
        return x;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        String id = Long.toString(v);
        GraphDB.Node node = nodes.get(id);
        return Double.parseDouble(node.lon);
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {

        String id = Long.toString(v);
        GraphDB.Node node = nodes.get(id);
        return Double.parseDouble(node.lat);
    }
    /**
     * A CS course.
     */
    static class Node implements Comparable<Node> {
        String id;
        String lat;
        String lon;
        GraphDB g;
        ArrayList<GraphDB.Node> connections;
        Node goal;
        Double moves;
        Node previous;
        Map<String, String> extraInfo;
        Double distance;



        Node(String id, String lat, String lon, ArrayList<GraphDB.Node> connections,
             GraphDB g, Node goal, Node previous, Double moves) {
            this.id = id;
            this.moves = moves;
            this.previous = previous;
            this.g = g;
            this.lat = lat;
            this.lon = lon;
            this.goal = goal;
            this.connections = connections;
            this.extraInfo = new HashMap<>();

        }
        @Override
        public int compareTo(Node that) {
            if (that.moves
                    + g.distance(Long.parseLong(that.id), Long.parseLong(that.goal.id))
                    < this.moves
                    + g.distance(Long.parseLong(this.id), Long.parseLong(this.goal.id))) {
                return 1;
            } else if (that.moves
                    + g.distance(Long.parseLong(that.id), Long.parseLong(that.goal.id))
                    > this.moves
                    + g.distance(Long.parseLong(this.id), Long.parseLong(this.goal.id))) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public void addNode(Node n) {
        nodes.put(n.id, n);
    }
    public void addEdges(ArrayList<String> ids, GraphDB g) {
        String id;
        String id0;
        String id1;
        GraphDB.Node curr;
        for (int i = 0; i < ids.size(); i++) {
            id = ids.get(i);
            curr = nodes.get(id);
            if (ids.size() == 1) {
                break;
            }
            if (i == 0) {
                id = ids.get(i + 1);
                GraphDB.Node nextnode = nodes.get(id);
                curr.connections.add(nextnode);
            } else if (i == ids.size() - 1) {
                id = ids.get(i - 1);
                GraphDB.Node lastnode = nodes.get(id);
                curr.connections.add(lastnode);
            } else {
                id0 = ids.get(i + 1);
                GraphDB.Node nextnode = nodes.get(id0);
                id1 = ids.get(i - 1);
                GraphDB.Node lastnode = nodes.get(id1);
                curr.connections.add(lastnode);
                curr.connections.add(nextnode);
            }
        }
        for (String s : ids) {
            GraphDB.Node n = nodes.get(s);
            ArrayList<GraphDB.Node> refactor = new ArrayList<>();
            for (GraphDB.Node x : n.connections) {
                if (!x.equals(n)) {
                    refactor.add(x);
                }
            }
            n.connections = refactor;
        }
        //System.out.println(ids);
    }
}
