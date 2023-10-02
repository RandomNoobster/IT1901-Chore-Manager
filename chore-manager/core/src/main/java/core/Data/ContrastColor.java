package core.Data;

public class ContrastColor {

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
