package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class playWithKeyboard implements Serializable {
    private int width;
    private int height;
    private static TETile[][] WORLDFRAME;
    static TETile[][] changes;
    private static final Random RANDOM = new Random();
    static int redX = RandomUtils.uniform(RANDOM, 20, 60);
    static int redY = RandomUtils.uniform(RANDOM, 10, 20);
    static int blueX = RandomUtils.uniform(RANDOM, 20, 60);
    static int blueY = RandomUtils.uniform(RANDOM, 10, 20);


    public playWithKeyboard(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of
        16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the
         * bottom right is (width, height)
         */

        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        drawFrame();
    }

    private void drawFrame() {
        StdDraw.clear(Color.BLACK);
        //creates new fonts and sets font
        Font font = new Font("Game Title", Font.ROMAN_BASELINE, 40);
        Font font1 = new Font("Play Buttons", Font.ITALIC, 25);
        StdDraw.setFont(font);
        StdDraw.setFont(font1);
        StdDraw.setPenColor(Color.green);
        StdDraw.text(this.width / 2, 3 * this.height / 4, "CS61B: The Jungle");
        StdDraw.text(this.width / 2, 13, "New Game (N)");
        StdDraw.text(this.width / 2, 11, "Load Game (L)");
        StdDraw.text(this.width / 2, 9, "Quit (Q)");
        StdDraw.text(this.width / 2, 7, "Travis's Story (T)");
        StdDraw.picture(this.width / 2, 22,
                "C:\\Users\\User01\\Pictures\\JungleGame.jpg",
                10, 10);
        StdDraw.circle(this.width / 2, this.height / 2, 15);
        StdDraw.show();
    }

    public static void newgame() {
        //this handles key events for selecting new game 'N'.
        changes = WORLDFRAME;
        int height = 40;
        int width = 40;
        char c;
        // check breaks
        String sb = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 'T' || c == 't') {
                    StdDraw.clear(Color.BLACK);
                    Font font1 = new Font("Play Buttons", Font.ITALIC, 15);
                    StdDraw.setFont(font1);
                    StdDraw.setPenColor(Color.GREEN);
                    StdDraw.text(7, 37, "CS61B: The Jungle");
                    StdDraw.text(8, 35, "Travis is the ultimate tiger.");
                    StdDraw.text(90 / 2, 30, "There are multiple tigers in the CS61B jungle and " +
                            "back in the day, around 10,000 B.C., there was a kingdom,");
                    StdDraw.text(90 / 2, 28, "the HUG Kingdom, that had a brilliant " +
                            "civilization with humans in command over the tigers that roamed the jungle. Travis the " +
                            "tiger was the infamous pet of Camelot, the King of HUG.");
                    StdDraw.text(90 / 2, 26,"There were rumors that there would be a tiger" + "" +
                            "that would be immortal to the rest of the tigers, yet Travis didn't believe this to be true.");
                    StdDraw.text(90 / 2, 24,"There are thousands of tigers in the kingdom so he weighed his odds. " +
                            "One day, while exploring the jungle for food, he stumbled upon Ronald the Rogue,");
                    StdDraw.text(90 / 2, 22, "who was a jungle rogue " +
                            "with the intention of finding this immortal tiger." +
                            "Ronald, with his jungle expertise, knew that Travis was this tiger.");
                    StdDraw.text(90 / 2, 20, "Travis fled back to his kingdom after Ronald revealed to him the news. " +
                            "As he was prowling into the Kingdom, he immediately noticed that");
                    StdDraw.text(90 / 2, 18, "Camelot's soldiers " +
                            "were searching the kingdom for Travis. He knew that they would take him into");
                    StdDraw.text(90 / 2, 16, "captivity for the rest of his life." +
                            "He fled the Kingdom and he has been hiding in jungle " +
                            "until present day, avoiding everything");
                    StdDraw.text(90 / 2, 14, "he can and blending in with the other tigers. " +
                            "Fortunately, Ronald knows that only he can bring Travis out of hiding and restore Travis" +
                            "to the greatness that he was fated for: The King of the CS61B Jungle.");
                    StdDraw.text(90 / 2, 6, "Thank you for playing!");
                    StdDraw.text(90 / 2, 4, "Press Q to Quit.");
                    StdDraw.text(90 / 2, 2, "Press M for the Main Menu.");
                    //draws a picture
                    StdDraw.picture(90 / 2, 10, "C:\\Users\\User01\\Pictures\\endgameTiger.jpg",
                            6, 6);
                    StdDraw.picture(90 / 2, 35, "C:\\Users\\User01\\Pictures\\Ronald.jpg",
                            7, 7);
                    StdDraw.show();
                    QuitMainLore();
                }
                if (c == 'L' || c == 'l') {
                    System.out.println("Here is your previous world.");
                    loadWorld();
                }
                if (c == 'Q' || c == 'q') {
                    System.out.println();
                    System.out.println("You have exited the game.");
                    System.out.println();
                    saveWorld(changes);
                    System.exit(0);
                }
                if (c == 'N' || c == 'n') {
                    StdDraw.setCanvasSize(width * 16, height * 16);
                    Font font = new Font("Monaco", Font.BOLD, 30);
                    StdDraw.setFont(font);
                    StdDraw.setXscale(0, width);
                    StdDraw.setYscale(0, height);
                    StdDraw.clear(Color.BLACK);
                    StdDraw.enableDoubleBuffering();
                    drawFrameEnter();
                    sb += 'N';
                }
                try {
                    if (c == '0' || c == '1' || c == '2' || c == '1' || c == '3'
                            || c == '4' || c == '5' || c == '6' || c == '7'
                            || c == '8' || c == '9') {
                        if (sb.charAt(0) == 'N') {
                            sb += c + "";
                            System.out.println(sb);
                            //StdDraw.clear();
                            //make it so you can see the seed on the screen
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println();
                    System.out.println("Please select 'N' before entering a seed.");
                }
                try {
                    if (c == 'S' || c == 's' && sb.charAt(0) == 'N') {
                        if (sb.length() > 1) {
                            sb += 'S';
                            //long numeral = Long.parseLong(sb.replaceAll("[\\D]", ""));
                            System.out.println(sb);
                            makeworld();
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println();
                    System.out.println("Please select 'N' and enter a seed" + ""
                            + "before selecting the key 'S'.");
                }
            }
        }
    }

    private static void loadWorld() {
        File f = new File("./keyboard.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);

                //returns 2D array representation
                TETile[][] object = (TETile[][]) os.readObject();
                makeworldSame(object);
                os.close();
                fs.close();
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
    }

    private static void saveWorld(TETile[][] w) {
        File f = new File("./keyboard.txt");
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

    private static void makeworld() {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 40);
        WORLDFRAME = new TETile[Game.WIDTH][Game.HEIGHT];
        ProcedurallyGenerated.makemap(WORLDFRAME);
        ProcedurallyGenerated.fillwalls(WORLDFRAME);
        ProcedurallyGenerated.fillSCREEN(WORLDFRAME);
        ProcedurallyGenerated.fillTRANSPORTSCREEN(WORLDFRAME);
        ProcedurallyGenerated.fillTRANSPORTSCREEN1(WORLDFRAME);
        changes = WORLDFRAME;
        ter.renderFrame(WORLDFRAME);
        saveWorld(changes);
       // set();
        Game();
    }

    private static void makeworldSame(TETile[][] world) {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 40);
        changes = world;
        ter.renderFrame(world);
       // set();
        Game();
    }

    private static void drawFrameEnter() {
        int height = 40;
        int width = 40;
        StdDraw.clear(Color.BLACK);
        //creates new fonts and sets font
        Font font = new Font("Game Title", Font.ROMAN_BASELINE, 40);
        Font font1 = new Font("Play Buttons", Font.ITALIC, 25);
        StdDraw.setFont(font);
        StdDraw.setFont(font1);
        //sets pen color of characters
        StdDraw.setPenColor(Color.green);
        //sets text to be displayed on the screen
        StdDraw.text(width / 2, 3 * height / 4, "CS61B: The Jungle");
        StdDraw.text(width / 2, height / 4, "ENTER A SEED");
        //draws a picture
        StdDraw.picture(width / 2, height / 2,
                "C:\\Users\\User01\\Pictures\\JungleGame.jpg",
                10, 10);
        StdDraw.circle(width / 2, height / 2, 15);
        //show the screen
        StdDraw.show();
    }
    private static void lostgame() {
        StdDraw.clear(Color.BLACK);
        //creates new fonts and sets font
        Font font = new Font("Game Title", Font.ROMAN_BASELINE, 40);
        Font font1 = new Font("Play Buttons", Font.ITALIC, 25);
        StdDraw.setFont(font);
        StdDraw.setFont(font1);
        StdDraw.setPenColor(Color.green);
        StdDraw.text(90 / 2, 3 * 40 / 4, "CS61B: The Jungle");
        StdDraw.text(90 / 2, 40 / 4, "You Have Lost the Game!");
        StdDraw.text(90 / 2, 8 * 40 / 40, "Press R to restart!");
        StdDraw.picture(90 / 2, 40 / 2,
                "C:\\Users\\User01\\Pictures\\endgameTiger.jpg",
                10, 10);
        StdDraw.circle(90 / 2, 40 / 2, 15);

        StdDraw.show();
        Story();
    }
    private static void wingame() {
        StdDraw.clear(Color.BLACK);
        //creates new fonts and sets font
        Font font = new Font("Game Title", Font.ROMAN_BASELINE, 40);
        Font font1 = new Font("Play Buttons", Font.ITALIC, 25);
        StdDraw.setFont(font);
        StdDraw.setFont(font1);
        //sets pen color of characters
        StdDraw.setPenColor(Color.green);
        //sets text to be displayed on the screen
        StdDraw.text(90 / 2, 3 * 40 / 4, "CS61B: The Jungle");
        StdDraw.text(90 / 2, 11 * 40 / 40, "You Have Won the Game!");
        StdDraw.text(90 / 2, 8 * 40 / 40, "Press R to restart!");
        StdDraw.text(90 / 2, 5 * 40 / 40, "Press T for Travis's story");
        //draws a picture
        StdDraw.picture(90 / 2, 40 / 2,
                "C:\\Users\\User01\\Pictures\\endgameTiger.jpg",
                10, 10);
        StdDraw.circle(90 / 2, 40 / 2, 20);
        //show the screen
        StdDraw.show();
        Story();
    }
    public static void Game() {
        int steps = 0;
        char c;
        while (true) {
            Hud();
            if (steps == 70) {
                lostgame();
            }
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 'Q' || c == 'q') {
                    System.out.println();
                    System.out.println("You have exited the game.");
                    System.out.println();
                    saveWorld(changes);
                    System.exit(0);
                }
                if (c == 'R' || c == 'r') {
                    loadWorld();
                }
                if (c == 'W' || c == 'w') {
                    int numXTiles = changes.length;
                    int numYTiles = changes[0].length;
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y].equals(Tileset.JUNGLEROGUE)
                            && !changes[x][y + 1].equals(Tileset.JUNGLEWALL)
                            && y < Game.HEIGHT) {
                                if (changes[x][y + 1].equals(Tileset.TIGER) || changes[x][y + 1].equals(Tileset.TIGER1)) {
                                    if (y + 1 == ProcedurallyGenerated.Y) {
                                        //render a new screen with "you have won the game"
                                        playWithKeyboard.wingame();
                                    }
                                    break;
                                }
                                if (changes[x][y + 1].equals(Tileset.TRANSPORTRED)) {
                                        numXTiles = changes.length;
                                        numYTiles = changes[0].length;
                                        for (int X = redX; X < numXTiles; X += 1) {
                                            for (int Y = redY; Y < numYTiles; Y += 1) {
                                                if (!changes[redX][redY].equals(Tileset.JUNGLEGROUND)) {
                                                    X += 1;
                                                    redX = X;
                                                } else {
                                                    X = numXTiles;
                                                    Y = numYTiles;
                                                }
                                            }
                                        }
                                changes[x][y + 1] = Tileset.JUNGLEGROUND;
                                //changes[x][y] = Tileset.JUNGLEGROUND;
                                changes[redX][redY] = Tileset.JUNGLEROGUE;
                                break;
                                }
                                if (changes[x][y + 1].equals(Tileset.TRANSPORTBLUE)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = blueX; X < numXTiles; X += 1) {
                                        for (int Y = blueY; Y < numYTiles; Y += 1) {
                                            if (!changes[blueX][blueY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                blueX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x][y + 1] = Tileset.JUNGLEGROUND;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[blueX][blueY] = Tileset.JUNGLEROGUE;
                                    break;
                                } else {

                                    changes[x][y + 1] = Tileset.JUNGLEROGUE;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    break;
                                }
                            }
                        }
                    }
                    StdDraw.clear(new Color(0, 0, 0));
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y] == null) {
                                throw new IllegalArgumentException("Tile at position x="
                                        + x + ", y=" + y
                                        + " is null.");
                            }
                            changes[x][y].draw(x + 0, y + 0);
                        }
                    }
                    steps += 1;
                    StdDraw.show();
                }
                if (c == 'A' || c == 'a') {
                    int numXTiles = changes.length;
                    int numYTiles = changes[0].length;
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y].equals(Tileset.JUNGLEROGUE)
                                    && !changes[x - 1][y].equals(Tileset.JUNGLEWALL)) {
                                if (changes[x - 1][y].equals(Tileset.TIGER)
                                        || changes[x - 1][y].equals(Tileset.TIGER1)) {
                                    if (x - 1 == ProcedurallyGenerated.X) {
                                        //render a new screen with "you have won the game"
                                        playWithKeyboard.wingame();
                                    }
                                    break;
                                }
                                if (changes[x - 1][y].equals(Tileset.TRANSPORTRED)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = redX; X < numXTiles; X += 1) {
                                        for (int Y = redY; Y < numYTiles; Y += 1) {
                                            if (!changes[redX][redY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                redX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x - 1][y] = Tileset.JUNGLEGROUND;
                                    //changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[redX][redY] = Tileset.JUNGLEROGUE;
                                    break;
                                }
                                if (changes[x - 1][y].equals(Tileset.TRANSPORTBLUE)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = blueX; X < numXTiles; X += 1) {
                                        for (int Y = blueY; Y < numYTiles; Y += 1) {
                                            if (!changes[blueX][blueY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                blueX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x - 1][y] = Tileset.JUNGLEGROUND;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[blueX][blueY] = Tileset.JUNGLEROGUE;
                                    break;

                                } else {
                                    changes[x - 1][y] = Tileset.JUNGLEROGUE;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    break;
                                }
                            }
                        }
                    }
                    StdDraw.clear(new Color(0, 0, 0));
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y] == null) {
                                throw new IllegalArgumentException("Tile at position x="
                                        + x + ", y=" + y
                                        + " is null.");
                            }
                            changes[x][y].draw(x + 0, y + 0);
                        }
                    }
                    steps += 1;
                    StdDraw.show();
                }
                if (c == 'D' || c == 'd') {
                    int numXTiles = changes.length;
                    int numYTiles = changes[0].length;
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y].equals(Tileset.JUNGLEROGUE)
                                    && !changes[x + 1][y].equals(Tileset.JUNGLEWALL)) {
                                if (changes[x + 1][y].equals(Tileset.TIGER)
                                        || changes[x + 1][y].equals(Tileset.TIGER1)) {
                                    if (x + 1 == ProcedurallyGenerated.X) {
                                        //render a new screen with "you have won the game"
                                        playWithKeyboard.wingame();
                                    }
                                    break;
                                }
                                if (changes[x + 1][y].equals(Tileset.TRANSPORTRED)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = redX; X < numXTiles; X += 1) {
                                        for (int Y = redY; Y < numYTiles; Y += 1) {
                                            if (!changes[redX][redY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                redX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x + 1][y] = Tileset.JUNGLEGROUND;
                                    //changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[redX][redY] = Tileset.JUNGLEROGUE;
                                    break;
                                }
                                if (changes[x + 1][y].equals(Tileset.TRANSPORTBLUE)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = blueX; X < numXTiles; X += 1) {
                                        for (int Y = blueY; Y < numYTiles; Y += 1) {
                                            if (!changes[blueX][blueY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                blueX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x + 11][y] = Tileset.JUNGLEGROUND;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[blueX][blueY] = Tileset.JUNGLEROGUE;
                                    break;

                                } else {
                                    try {
                                        changes[x + 1][y] = Tileset.JUNGLEROGUE;
                                        changes[x][y] = Tileset.JUNGLEGROUND;
                                        x += 1;
                                        break;
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        System.out.println("caught");
                                    }
                                }
                            }
                        }
                    }
                    StdDraw.clear(new Color(0, 0, 0));
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y] == null) {
                                throw new IllegalArgumentException("Tile at position x="
                                        + x + ", y=" + y
                                        + " is null.");
                            }
                            changes[x][y].draw(x + 0, y + 0);
                        }
                    }
                    steps += 1;
                    StdDraw.show();
                }
                if (c == 'S' || c == 's') {
                    int numXTiles = changes.length;
                    int numYTiles = changes[0].length;
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y].equals(Tileset.JUNGLEROGUE)
                                    && !changes[x][y - 1].equals(Tileset.JUNGLEWALL)) {
                                if (changes[x][y - 1].equals(Tileset.TIGER)
                                        || changes[x][y - 1].equals(Tileset.TIGER1)) {
                                    if (y - 1 == ProcedurallyGenerated.Y) {
                                        //render a new screen with "you have won the game"
                                        playWithKeyboard.wingame();
                                    }
                                    break;
                                }
                                if (changes[x][y - 1].equals(Tileset.TRANSPORTRED)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = redX; X < numXTiles; X += 1) {
                                        for (int Y = redY; Y < numYTiles; Y += 1) {
                                            if (!changes[redX][redY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                redX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x][y - 1] = Tileset.JUNGLEGROUND;
                                    //changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[redX][redY] = Tileset.JUNGLEROGUE;
                                    break;
                                }
                                if (changes[x][y - 1].equals(Tileset.TRANSPORTBLUE)) {
                                    numXTiles = changes.length;
                                    numYTiles = changes[0].length;
                                    for (int X = blueX; X < numXTiles; X += 1) {
                                        for (int Y = blueY; Y < numYTiles; Y += 1) {
                                            if (!changes[blueX][blueY].equals(Tileset.JUNGLEGROUND)) {
                                                X += 1;
                                                blueX = X;
                                            } else {
                                                X = numXTiles;
                                                Y = numYTiles;
                                            }
                                        }
                                    }
                                    changes[x][y - 1] = Tileset.JUNGLEGROUND;
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[blueX][blueY] = Tileset.JUNGLEROGUE;
                                    break;
                                } else {
                                    changes[x][y] = Tileset.JUNGLEGROUND;
                                    changes[x][y - 1] = Tileset.JUNGLEROGUE;
                                    break;
                                }
                            }
                        }
                    }
                    StdDraw.clear(new Color(0, 0, 0));
                    for (int x = 0; x < numXTiles; x += 1) {
                        for (int y = 0; y < numYTiles; y += 1) {
                            if (changes[x][y] == null) {
                                throw new IllegalArgumentException("Tile at position x=" + x
                                        + ", y=" + y
                                        + " is null.");
                            }
                            changes[x][y].draw(x + 0, y + 0);
                        }
                    }
                    steps += 1;
                    StdDraw.show();
                }
            }
        }
    }
    public static void Hud() {
        String description;
        int X, Y;
        X = (int) StdDraw.mouseX();
        Y = (int) StdDraw.mouseY();
        if (X < Game.WIDTH && Y < Game.HEIGHT) {
            TETile tile = changes[X][Y];
            description = tile.description();

            int numXTiles = changes.length;
            int numYTiles = changes[0].length;
            StdDraw.clear(new Color(0, 0, 0));
            for (int x = 0; x < numXTiles; x += 1) {
                for (int y = 0; y < numYTiles; y += 1) {
                    if (changes[x][y] == null) {
                        throw new IllegalArgumentException("Tile at position x=" + x
                                + ", y=" + y
                                + " is null.");
                    }
                    changes[x][y].draw(x + 0, y + 0);
                }
            }
            Font font1 = new Font("Play Buttons", Font.ITALIC, 16);
            StdDraw.setFont(font1);
            //sets pen color of characters
            StdDraw.setPenColor(Color.white);
            //sets text to be displayed on the screen
            if (description.equals("nothing")) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(60.0, 38.0, "Stay inside the Jungle.");
                StdDraw.text(60.0, 37.0, "There are humans looking" + "" +
                        "for both of you outside of the jungle.");
            }
            if (description.equals("travis the tiger")) {
                StdDraw.setPenColor(Color.RED);
                StdDraw.text(5.0, 37.0, "WATCH OUT!");
                StdDraw.text(60.0, 38.0, "Choose the correct tiger... There is" + ""
                        + "only one that is immortal.");
            }
            if (description.equals("jungle wall")) {
                StdDraw.setPenColor(Color.green);
                StdDraw.text(60.0, 38.0, "Look closely... and you might spot a tiger" + "" +
                        " in the walls of the jungle.");
                StdDraw.text(60.0, 37.0, "Travis is waiting for you to save him.");
            }
            if (description.equals("jungle ground")) {
                StdDraw.setPenColor(Color.white);
                StdDraw.text(60.0, 38.0, "The jungle ground is used to blend in with your outfit. ");
                StdDraw.text(60.0, 37.0,  "You will only find tigers hiding in the jungle walls.");
            }
            if (description == "rogue ronald") {
                StdDraw.text(60.0, 38.0, "Rogue Ronald has a few items with him...");
                StdDraw.text(60.0, 37.0,
                        "make sure to strategically choose your path to choosing Travis.");
                StdDraw.text(60.0, 36.0,
                        "You have 70 steps to take before Travis is found and taken into captivity forever.");
            }
            StdDraw.text(5.0, 39.0, description.toUpperCase());
            StdDraw.text(30.0, 39.0, "Press R at any time to Restart");
            StdDraw.line(0, 35, 90, 35);
            StdDraw.show();
        }
    }
    public static void Story() {
        char c;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 'R' || c == 'r') {
                    loadWorld();
                }
                if (c == 'T' || c == 't') {
                    StdDraw.clear(Color.BLACK);
                    Font font1 = new Font("Play Buttons", Font.ITALIC, 15);
                    StdDraw.setFont(font1);
                    StdDraw.setPenColor(Color.GREEN);
                    StdDraw.text(5, 37, "CS61B: The Jungle");
                    StdDraw.text(6, 35, "Travis is the ultimate tiger.");
                    StdDraw.text(90 / 2, 30, "There are multiple tigers in the CS61B jungle and " +
                            "back in the day, around 10,000 B.C., there was a kingdom, the HUG Kingdom,");
                    StdDraw.text(90 / 2, 28, "that had a brilliant " +
                            "civilization with humans in command over the tigers that roamed the jungle. Travis the " +
                                    "tiger was the infamous pet of Camelot, the King of HUG.");
                    StdDraw.text(90 / 2, 26,"There were rumors that there would be a tiger" + "" +
                    "that would be immortal to the rest of the tigers, yet Travis didn't believe this to be true.");
                    StdDraw.text(90 / 2, 24,"There are thousands of tigers in the kingdom so he weighed his odds. " +
                            "One day, while exploring the jungle for food, he stumbled upon Ronald the Rogue,");
                    StdDraw.text(90 / 2, 22, "who was a jungle rogue " +
                            "with the intention of finding this immortal tiger." +
                            "Ronald, with his jungle expertise, knew that Travis was this tiger.");
                    StdDraw.text(90 / 2, 20, "Travis fled back to his kingdom after Ronald revealed to him the news. " +
                            "As he was prowling into the Kingdom, he immediately noticed that");
                    StdDraw.text(90 / 2, 18, "Camelot's soldiers " +
                                    "were searching the kingdom for Travis. He knew that they would take him into");
                    StdDraw.text(90 / 2, 16, "captivity for the rest of his life." +
                                    "He fled the Kingdom and he has been hiding in jungle " +
                                    "until present day, avoiding everything he can and blending in with the other tigers.");
                    StdDraw.text(90 / 2, 14, "Fortunately, Ronald knows that only he can bring" + "" +
                            " Travis out of hiding and restore Travis" +
                                    "to the greatness that he was fated for: The King of the CS61B Jungle.");
                    StdDraw.text(90 / 2, 6, "Thank you for playing!");
                    StdDraw.text(90 / 2, 4, "Press Q to Quit.");
                    StdDraw.text(90 / 2, 2, "Press M for the Main Menu.");
                            //draws a picture
                    StdDraw.picture(90 / 2, 10, "C:\\Users\\User01\\Pictures\\endgameTiger.jpg",
                            6, 6);
                    StdDraw.picture(90 / 2, 35, "C:\\Users\\User01\\Pictures\\Ronald.jpg",
                            7, 7);
                    StdDraw.show();
                    QuitMainLore();
                }
            }
        }
    }
    public static void QuitMainLore() {
        char c;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 'Q' || c == 'q') {
                    System.exit(0);
                }
                if (c == 'M' || c == 'm') {
                    Game game = new Game();
                    game.playWithKeyboard();
                }
            }
        }
    }
}