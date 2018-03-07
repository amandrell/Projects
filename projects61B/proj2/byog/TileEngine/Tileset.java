package byog.TileEngine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    //public static TETile PLAYER = new TETile('@', Color.black, Color.yellow, "player");
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall");
    public static final TETile FLOOR = new TETile('·', new Color(52, 16, 3), Color.black,
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");

    //my tiles
    public static final TETile PLAYER1 = new TETile('℗', Color.red, Color.black, "gameplayer");
    public static final TETile JUNGLEWALL = new TETile('M',
            null, null, "jungle wall",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\grass1_0.PNG");
    public static final TETile JUNGLEGROUND = new TETile('-', null, null,
            "jungle ground",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\JungleGround.PNG");
    public static final TETile ENDGAME = new TETile(';',
            null, null, "trap door",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\WaterWorldWall.PNG");
    public static final TETile JUNGLEROGUE = new TETile(';',
            null, null, "rogue ronald",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\gameplayer.png");
    public static final TETile TIGER = new TETile('L',
            null, null, "travis the tiger",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\monster.png");
    public static final TETile TIGER1 = new TETile('L',
            null, null, "travis the tiger",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\golem.PNG");
    public static final TETile TRANSPORTRED = new TETile('g',
            null, null, "multiplied",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\red_drapes.png");
    public static final TETile TRANSPORTBLUE = new TETile('l',
            null, null, "transported",
            "C:\\Users\\User01\\Documents\\cs61b\\sp18-proj2-apu-bri\\proj2\\blue_drapes.png");

}


