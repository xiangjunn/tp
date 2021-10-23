package seedu.address.model.tag;

public class TagColors {
    public static final String COLOUR1 = "#CD5C5C"; // red
    public static final String COLOUR2 = "#808F85"; //dark green
    public static final String COLOUR3 = "#DDA0DD"; // purple
    public static final String COLOUR4 = "#5F9EA0"; // blue
    public static final String COLOUR5 = "#ef767a"; // coral
    public static final String COLOUR6 = "#556B2F"; // olive green
    public static final String COLOUR7 = "#FF8C00"; // orange
    public static final String COLOUR8 = "#8FBC8F"; // light green
    public static final String COLOUR9 = "#BC8F8F"; // rose brown
    public static final String COLOUR10 = "##483D8B"; // lavender
    public static final String COLOUR11 = "#D2B48C"; // tan
    public static final String COLOUR12 = "#F4A460"; // light orange
    public static final String COLOUR13 = "#800000"; // maroon
    public static final String COLOUR14 = "#FFE4E1"; // mint rose
    public static final String COLOUR15 = "#008080"; // teal

    private static int colourIndex = 0;

    public static final String[] COLOURS =
            new String[]{COLOUR1, COLOUR2, COLOUR3, COLOUR4, COLOUR5, COLOUR6, COLOUR7, COLOUR8, COLOUR9, COLOUR10,
                         COLOUR11, COLOUR12, COLOUR13, COLOUR14, COLOUR15};

    /**
     * Get a color for tag
     * @return color code
     */
    public static String getTagColour() {
        if (colourIndex > 14) {
            colourIndex = 0;
        }
        colourIndex++;
        return COLOURS[colourIndex];
    }
}
