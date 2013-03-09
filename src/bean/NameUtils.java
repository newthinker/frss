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
public class NameUtils {

    private static List<String> names = new ArrayList<String>();

    private static void init() {
        try {
            InputStream input = NameUtils.class.getResourceAsStream("names.txt");
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                names.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        init();
    }

    /**
     * Get a random full name.
     * 
     * @param random
     * @return A full name.
     */
    public static String getRandomName(Random random) {
        int length = names.size();
        if (length < 2) {
            return null;
        }

        int firstNameIndex = random.nextInt(length);
        int lastNameIndex = firstNameIndex;
        while (lastNameIndex == firstNameIndex) {
            lastNameIndex = random.nextInt(length);
        }

        String fullName = String.format("%s %s", names.get(firstNameIndex), names.get(lastNameIndex));

        return fullName;
    }
}
