package core.data;

/**
 * This class is used to determine whether the text color should be black or white
 * based on the background color.
 */
public class ContrastColor {

    /**
     * Returns true if the text color should be white, false if it should be black.
     */
    public static Boolean blackText(String hex) {

        String hexColor = hex.replace("#", "");

        // Parse the hexadecimal color into separate RGB components
        int red = Integer.parseInt(hexColor.substring(0, 2), 16);
        int green = Integer.parseInt(hexColor.substring(2, 4), 16);
        int blue = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Calculate luminance
        double luminance = (0.299 * red + 0.587 * green + 0.114 * blue) / 255;

        if (luminance > 0.8) {
            return false;
        } else {
            return true;
        }
    }

}
