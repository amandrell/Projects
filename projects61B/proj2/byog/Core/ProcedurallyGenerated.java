package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.awt.Point;

import java.io.Serializable;
import java.util.Random;
import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class ProcedurallyGenerated implements Serializable {
    static int ROOM_MAX_SIZE = 8;
    static int ROOM_MIN_SIZE = 6;
    static int MAX_ROOMS = 15;
    static int playerX, playerY;
    private static final Random RANDOM = new Random();
    static int X, Y;
    static int xtransport, ytransport;

    private static class Rect {
        private int x1, y1, x2, y2;
        private int centerx;
        private int centery;

        //#a rectangle on the map. used to characterize a room.
        private Rect(int x, int y, int w, int h) {
            this.x1 = x;
            this.y1 = y;
            this.x2 = x + w;
            this.y2 = y + h;
        }

        private Point center() {
            centerx = (this.x1 + this.x2) / 2;
            centery = (this.y1 + this.y2) / 2;
            return new Point(centerx, centery);
        }

        private boolean intersect(Rect o) {
            //#returns true if this rectangle intersects with another one
            if (o == null) {
                return false;
            } else {
                return (this.x1 <= o.x2 && this.x2 >= o.x1
                        && this.y1 <= o.y2 && this.y2 >= o.y1);
            }
        }
    }

    private static void createroom(Rect room, TETile[][] world) {
        // "global map"
        int x, y;
        //#go through the tiles in the rectangle and make them passable
        for (x = room.x1 + 1; x >= room.x1 + 1 && x < room.x2; x++) {
            for (y = room.y1 + 1; y >= room.y1 + 1 && y < room.y2; y++) {

                world[x][y] = Tileset.JUNGLEGROUND;
            }
        }
    }

    private static void htunnel(int x1, int x2, int y, TETile[][] world) {
        int x;
        //global map
        //horizontal tunnel. min() and max() are used in case x1>x2
        for (x = min(x1, x2); x >= min(x1, x2) && x < max(x1, x2); x++) {

            world[x][y] = Tileset.JUNGLEGROUND;
        }
    }

    private static void vtunnel(int y1, int y2, int x, TETile[][] world) {
        int y;
        //global map
        //horizontal tunnel. min() and max() are used in case y1>y2
        for (y = min(y1, y2); y >= min(y1, y2) && y < max(y1, y2); y++) {

            world[x][y] = Tileset.JUNGLEGROUND;
        }
    }

    public static void makemap(TETile[][] world) {
        //@www.roguebasin.com but modified;
        //fill map with "blocked" tiles (blocked is set to true)
        for (int x = 0; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
                //Tileset.NOTHING.blocked = true; Tileset.NOTHING.block_sight = true;
            }
        }
        Rect[] rooms = new Rect[MAX_ROOMS];
        int numrooms = 0;

        for (int i = 0; i < MAX_ROOMS; i++) {
            //random width and height//random position without going out of the boundaries of map
            int w = RandomUtils.uniform(RANDOM, ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int h = RandomUtils.uniform(RANDOM, ROOM_MIN_SIZE, ROOM_MAX_SIZE);
            int x = RandomUtils.uniform(RANDOM, Game.WIDTH - w - 1);
            int y = RandomUtils.uniform(RANDOM, Game.HEIGHT - h - 1);

            Rect newroom = new Rect(x, y, w, h);
            createroom(newroom, world);
            //center coordinates of new room, will be useful later
            Point newcenter = newroom.center();


            if (numrooms > 0) {
                Point prevcenter = rooms[numrooms - 1].center();

                //draw a coin(random number that is either 0 or 1)
                if (RandomUtils.uniform(RANDOM, 0, 2) == 1) {
                    //first move horizontally, then vertically
                    htunnel(prevcenter.x, newcenter.x, prevcenter.y, world);
                    vtunnel(prevcenter.y, newcenter.y, newcenter.x, world);
                } else {
                    //first move vertically, then horizontally
                    vtunnel(prevcenter.y, newcenter.y, prevcenter.x, world);
                    htunnel(prevcenter.x, newcenter.x, newcenter.y, world);
                }
            }
            rooms[numrooms] = newroom;
            numrooms += 1;
        }
        playerX = rooms[0].centerx;
        playerY = rooms[0].centery;
        world[rooms[0].centerx][rooms[0].centery] = Tileset.JUNGLEROGUE;
    }
    public static void fillwalls(TETile[][] tiles) {
        for (int x = 0; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT; y += 1) {
                //now fill in the "corners"
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && y + 1 < Game.HEIGHT && tiles[x + 1][y + 1] == Tileset.NOTHING
                        && tiles[x + 1][y] == Tileset.JUNGLEGROUND) {
                    tiles[x][y + 1] = Tileset.JUNGLEWALL;
                }

                if (tiles[x][y] == Tileset.NOTHING && x + 1 < Game.WIDTH
                        && y + 1 < Game.HEIGHT && tiles[x][y + 1] == Tileset.JUNGLEGROUND
                        && tiles[x + 1][y + 1] == Tileset.NOTHING) {
                    tiles[x + 1][y] = Tileset.JUNGLEWALL;
                }
                if (tiles[x][y] == Tileset.JUNGLEGROUND && x + 1 < Game.WIDTH
                        && y + 1 < Game.HEIGHT && tiles[x + 1][y + 1] == Tileset.NOTHING) {
                    tiles[x + 1][y + 1] = Tileset.JUNGLEWALL;
                }

                if (tiles[x][y] == Tileset.NOTHING && x + 1 < Game.WIDTH
                        && y + 1 < Game.HEIGHT && tiles[x + 1][y] == Tileset.NOTHING
                        && tiles[x][y + 1] == Tileset.NOTHING
                        && tiles[x + 1][y + 1] == Tileset.JUNGLEGROUND) {
                    tiles[x][y] = Tileset.JUNGLEWALL;
                }
                if (tiles[x][y] == Tileset.JUNGLEGROUND && y + 1 < Game.HEIGHT
                        && tiles[x][y + 1] == Tileset.NOTHING) {
                    tiles[x][y + 1] = Tileset.JUNGLEWALL;
                }
                if (tiles[x][y] == Tileset.NOTHING && y + 1 < Game.HEIGHT
                        && tiles[x][y + 1] == Tileset.JUNGLEGROUND) {
                    tiles[x][y] = Tileset.JUNGLEWALL;

                }
                if (tiles[x][y] == Tileset.JUNGLEGROUND && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.NOTHING) {
                    tiles[x + 1][y] = Tileset.JUNGLEWALL;
                }
                if (tiles[x][y] == Tileset.NOTHING && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEGROUND) {
                    tiles[x][y] = Tileset.JUNGLEWALL;
                }
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEGROUND
                        && tiles[x + 1][y + 1] == Tileset.NOTHING) {
                    tiles[x][y + 1] = Tileset.JUNGLEWALL;
                }

            }
        }
    }
    public static void fillTRANSPORTSCREEN(TETile[][] tiles) {
        xtransport = RandomUtils.uniform(RANDOM, 15, Game.WIDTH - 20);
        ytransport = RandomUtils.uniform(RANDOM, 10, Game.HEIGHT - 10);
        for (int x = xtransport; x < Game.WIDTH - 20; x += 1) {
            for (int y = ytransport; y < Game.HEIGHT - 10; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEGROUND) {
                    tiles[x][y] = Tileset.TRANSPORTRED;
                    x = Game.WIDTH - 20;
                    y = Game.HEIGHT - 10;
                    break;
                }
            }
        }
    }
    public static void fillTRANSPORTSCREEN1(TETile[][] tiles) {
        int ztransport = RandomUtils.uniform(RANDOM, 15, Game.WIDTH - 20);
        int xtransport = RandomUtils.uniform(RANDOM, 10, Game.HEIGHT - 10);
        for (int x = ztransport; x < Game.WIDTH - 20; x += 1) {
            for (int y = xtransport; y < Game.HEIGHT - 10; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEGROUND) {
                    tiles[x][y] = Tileset.TRANSPORTBLUE;
                    x = Game.WIDTH - 20;
                    y = Game.HEIGHT - 10;
                    break;
                }
            }
        }
    }
    public static void fillSCREEN(TETile[][] tiles) {
        int rando = RandomUtils.uniform(RANDOM, 0, 2);
        int random = RandomUtils.uniform(RANDOM, 0, 4);
        //method that if random equals that number then we store that x and y coordinate
        //as a true value.....................

        for (int x = 9 * Game.WIDTH / 20; x < Game.WIDTH; x += 1) {
            for (int y = 0; y < Game.HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEWALL
                        && tiles[x][y + 1] == Tileset.JUNGLEGROUND) {
                    if (rando > 0) {
                        tiles[x][y] = Tileset.TIGER;
                        if (random == 0) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;

                    } else {
                        tiles[x][y] = Tileset.TIGER1;
                        if (random == 0) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    }
                }
            }
        }
        for (int x = Game.WIDTH / 2; x < Game.WIDTH; x += 1) {
            for (int y = Game.HEIGHT / 2; y < Game.HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEWALL
                        && tiles[x][y + 1] == Tileset.JUNGLEGROUND) {
                    if (rando == 0) {
                        tiles[x][y] = Tileset.TIGER;
                        if (random == 1) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    } else {
                        tiles[x][y] = Tileset.TIGER1;
                        if (random == 1) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    }
                }
            }
        }
        for (int x = 2 * Game.WIDTH / 5; x < Game.WIDTH; x += 1) {
            for (int y = 2 * Game.HEIGHT / 5; y < Game.HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEWALL
                        && tiles[x][y + 1] == Tileset.JUNGLEGROUND) {
                    if (rando > 0) {
                        tiles[x][y] = Tileset.TIGER;
                        if (random == 2) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    } else {
                        tiles[x][y] = Tileset.TIGER1;
                        if (random == 2) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    }
                }
            }
        }
        for (int x = 3 * Game.WIDTH / 10; x < Game.WIDTH; x += 1) {
            for (int y = 1 * Game.HEIGHT / 5; y < Game.HEIGHT; y += 1) {
                if (tiles[x][y] == Tileset.JUNGLEWALL && x + 1 < Game.WIDTH
                        && tiles[x + 1][y] == Tileset.JUNGLEWALL
                        && tiles[x][y + 1] == Tileset.JUNGLEGROUND) {
                    if (rando == 0) {
                        tiles[x][y] = Tileset.TIGER;
                        if (random == 3) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    } else {
                        tiles[x][y] = Tileset.TIGER1;
                        if (random == 3) {
                            X = x;
                            Y = y;
                        }
                        y = Game.HEIGHT;
                        x = Game.WIDTH;
                    }
                }
            }
        }
    }
}

