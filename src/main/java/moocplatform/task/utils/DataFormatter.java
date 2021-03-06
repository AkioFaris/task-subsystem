package moocplatform.task.utils;

/**
 * Formats data
 */
public class DataFormatter {

    /**
     * Returns expression without whitespaces
     * @param expression String
     * @return String
     */
    public static String unifyExpression(String expression) {
        return expression.replaceAll(" ", "").replaceAll("\n", "");
    }
}
