import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    public Rasterer() {
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        Map<String, Object> results = new HashMap<>();

        /** Reads data from user input. **/
        int depth = -1;
        double rasterlrlon = params.get("lrlon");
        double rasterullon = params.get("ullon");
        double rasterullat = params.get("ullat");
        double rasterlrlat = params.get("lrlat");
        double rasterw = params.get("w");
        double rasterh = params.get("h");
        double querylondpp = (rasterlrlon - rasterullon) / rasterw;

        boolean querySuccess = true;
        double rootullat = MapServer.ROOT_ULLAT;
        double rootullon = MapServer.ROOT_ULLON;
        double rootlrlat = MapServer.ROOT_LRLAT;
        double rootlrlon = MapServer.ROOT_LRLON;
        double z = rootullon;
        double lonDPP = 0.0;
        if (rasterullon > rasterlrlon || rasterullat < rasterlrlat) {
            querySuccess = false;
        }
        for (int dep = 0; dep <= 7; dep++) {
            if (dep == 0) {
                lonDPP = ((rootlrlon - rootullon) / MapServer.TILE_SIZE);
                if (lonDPP <= querylondpp) {
                    depth = dep;
                    break;
                }
            } else {
                z = ((rootlrlon + z) / 2);
                lonDPP = ((rootlrlon - z) / MapServer.TILE_SIZE);
                if (lonDPP <= querylondpp) {
                    depth = dep;
                    break;
                }
            }
        }
        if (depth == -1) {
            depth = 7;
        }

        double changelon = Math.abs(rootullon - rootlrlon) / Math.pow(2, depth);
        double changelat = Math.abs(rootullat - rootlrlat) / Math.pow(2, depth);
        int startX = (int) (Math.abs(rasterullon - rootullon) / changelon);
        int startY = (int) (Math.abs(rasterullat - rootullat) / changelat);
        int endX = (int) ((Math.abs(rootullon - rasterlrlon)) / changelon);
        int endY = (int) ((Math.abs(rootullat - rasterlrlat)) / changelat);
        //System.out.println(endX);
        String[][] renderGrid = new String[endY - startY + 1][endX - startX + 1];
        double rasterUllon = rootullon + changelon * startX;
        double rasterUllat = rootullat - changelat * startY;
        double rasterLrlon = rootlrlon - changelon * (Math.pow(2, depth) - endX - 1);
        double rasterLrlat = rootlrlat + changelat * (Math.pow(2, depth) - endY - 1);
        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                String filename = "d" + String.valueOf(depth)
                        + "_x" + String.valueOf(j) + "_y" + String.valueOf(i) + ".png";
                renderGrid[i - startY][j - startX] = filename;
            }
        }
        results.put("render_grid", renderGrid);
        results.put("raster_ul_lon", rasterUllon);
        results.put("raster_ul_lat", rasterUllat);
        results.put("raster_lr_lon", rasterLrlon);
        results.put("raster_lr_lat", rasterLrlat);
        results.put("depth", depth);
        results.put("query_success", querySuccess);
        return results;
    }
}
