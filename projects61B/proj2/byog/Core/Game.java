package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Game implements Serializable {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    static int S;
    static String numeral;
    static Map<String, TETile[][]> load = new HashMap<>();
    static Map<String, TETile[][]> map = new HashMap<>();

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        playWithKeyboard game = new playWithKeyboard(90, 40);
        playWithKeyboard.newgame();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] playWithInputString(String input) {
        /* return a 2D tile representation of the world that would have been
         drawn if the same inputs had been given to playWithKeyboard().

        use a hash map to store longs to retrieve later in case
        one types in the same long, the same world should be generated! */
        if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
            S = 0;
        } else {
            S = 1;
        }
        numeral = "";
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '1' || c == '2' || c == '3' || c == '4'
                    || c == '5' || c == '6' || c == '7'
                    || c == '8' || c == '9' || c == '0') {
                numeral += c;
            }
        }
        //System.out.println(numeral);

        if (map.containsKey(numeral)) {
            return map.get(numeral);
        }

        if (input.charAt(0) == 'L' || input.charAt(0) == 'l') {
            if (loadWorld() == null) {
                //System.out.println("Start a New Game!");
                return null;
            } else {
                TETile[][] world = loadWorld();
                playerposition(input, world);
                return world;
            }
        } else {
            TETile[][] finalWorldFrame = new TETile[Game.WIDTH][Game.HEIGHT];
            ProcedurallyGenerated.makemap(finalWorldFrame);
            ProcedurallyGenerated.fillwalls(finalWorldFrame);
            playerposition(input, finalWorldFrame);
            map.put(numeral, finalWorldFrame);
            saveWorld(finalWorldFrame);
            return finalWorldFrame;
        }
    }

    private static TETile[][] loadWorld() {
        TETile[][] object;
        File f = new File("./inputString.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                //returns 2D array representation
                //os.close();
                //fs.close();
                return object = (TETile[][]) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        return null;
    }

    private static void saveWorld(TETile[][] w) {
        File f = new File("./inputString.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(w);
            os.close();
            fs.close();

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
    public static void playerposition(String command, TETile[][] world) {

        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);

            if (c == 'A' || c == 'a') {
                int numXTiles = world.length;
                int numYTiles = world[0].length;
                for (int x = 0; x < numXTiles; x += 1) {
                    for (int y = 0; y < numYTiles; y += 1) {
                        if (world[x][y].equals(Tileset.PLAYER1)
                                && !world[x - 1][y].equals(Tileset.JUNGLEWALL)) {
                            world[x - 1][y] = Tileset.PLAYER1;
                            world[x][y] = Tileset.JUNGLEGROUND;
                            break;
                        }
                    }
                }
            }
            if (c == 'S' || c == 's') {
                S += 1;
                if (S > 1) {
                    int numXTiles = world.length;
                    int numYTiles = world[0].length;
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (world[x][y].equals(Tileset.PLAYER1)
                                    && !world[x][y - 1].equals(Tileset.JUNGLEWALL)) {
                                world[x][y - 1] = Tileset.PLAYER1;
                                world[x][y] = Tileset.JUNGLEGROUND;
                                break;

                            }
                        }
                    }
                }
            }
            if (c == 'D' || c == 'd') {
                int numXTiles = world.length;
                int numYTiles = world[0].length;
                for (int x = 0; x < numXTiles; x += 1) {
                    for (int y = 0; y < numYTiles; y += 1) {
                        if (world[x][y].equals(Tileset.PLAYER1)
                                && !world[x + 1][y].equals(Tileset.JUNGLEWALL)) {
                            world[x + 1][y] = Tileset.PLAYER1;
                            world[x][y] = Tileset.JUNGLEGROUND;
                            x += 1;
                            break;
                        }
                    }
                }
            }
            if (c == 'W' || c == 'w') {
                int numXTiles = world.length;
                int numYTiles = world[0].length;
                for (int x = 0; x < numXTiles; x += 1) {
                    for (int y = 0; y < numYTiles; y += 1) {
                        if (world[x][y].equals(Tileset.PLAYER1)
                                && !world[x][y + 1].equals(Tileset.JUNGLEWALL)) {
                            world[x][y + 1] = Tileset.PLAYER1;
                            world[x][y] = Tileset.JUNGLEGROUND;
                            break;
                        }
                    }
                }
            }
            if (c == ':' || c == ':') {
                saveWorld(world);
            }
        }
    }
}
