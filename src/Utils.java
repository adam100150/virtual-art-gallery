package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Utils {
    static String readContentsAsString(File f) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(f);
        String content = "";
        while (fileScanner.hasNextLine()) {
            content += fileScanner.nextLine();
            content += "\n";
        }

        return content;
    }
}
