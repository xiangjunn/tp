package seedu.address.model.tag;

public class Colours {
    private static final String COLOUR1 = "#CD5C5C"; // red
    private static final String COLOUR2 = "#808F85"; //dark green
    private static final String COLOUR3 = "#DDA0DD"; // light purple
    private static final String COLOUR4 = "#5F9EA0"; // blue
    private static final String COLOUR5 = "#FF7F50"; // coral
    private static final String COLOUR6 = "#556B2F"; // olive green
    private static final String COLOUR7 = "#F39C6B"; // orange
    private static final String COLOUR8 = "#8FBC8F"; // light green
    private static final String COLOUR9 = "#DB7093"; // pink
    private static final String COLOUR10 = "#4B0082"; // purple
    private static final String COLOUR11 = "#D2B48C"; // tan
    private static final String COLOUR12 = "#F4A460"; // light orange
    private static final String COLOUR13 = "#800000"; // maroon
    private static final String COLOUR14 = "#00008B"; // dark blue
    private static final String COLOUR15 = "#008080"; // teal

    private static int colourIndex = 0;

    private static final String[] COLOURS =
            new String[]{COLOUR1, COLOUR2, COLOUR3, COLOUR4, COLOUR5, COLOUR6, COLOUR7, COLOUR8, COLOUR9, COLOUR10,
                         COLOUR11, COLOUR12, COLOUR13, COLOUR14, COLOUR15};

    public static final int NUMBER_OF_COLOURS = COLOURS.length;

    /**
     * Get a color for tag
     * @return color code
     */
    public static String getTagColour() {
        colourIndex++;
        if (colourIndex == NUMBER_OF_COLOURS) {
            colourIndex = 0;
        }
        return COLOURS[colourIndex];
    }
}
