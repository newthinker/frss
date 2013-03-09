/**
 * 
 */
package bean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Roger Xu
 * 
 */
public class TitleUtils {

    private static List<String> titles = new ArrayList<String>();

    private static void init() {
        try {
            InputStream input = TitleUtils.class.getResourceAsStream("titles.txt");
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                titles.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        init();
    }

    /**
     * Get a random job title.
     * 
     * @param random
     * @return A job title.
     */
    public static String getRandomTitle(Random random) {
        int length = titles.size();
        if (length < 2) {
            return null;
        }

        int titleIndex = random.nextInt(length);
        String title = titles.get(titleIndex);

        return title;
    }
}
