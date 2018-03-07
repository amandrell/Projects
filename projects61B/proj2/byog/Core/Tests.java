package byog.Core;
import byog.TileEngine.TETile;
import org.junit.Test;

public class Tests {
    @Test
    public void test() {
        Game game = new Game();
        TETile[][] x = game.playWithInputString("n2950953422994074886sdaddawa");
        //TETile[][] y = game.playWithInputString("N1S");
        //TETile[][] z = game.playWithInputString("N123S");
        System.out.println(TETile.toString(x));
        //System.out.println(TETile.toString(y));
        //System.out.println(TETile.toString(z));

    }


}
