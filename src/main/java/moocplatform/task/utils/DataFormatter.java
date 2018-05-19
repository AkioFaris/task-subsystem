package moocplatform.task.utils;

/**
 * Formats data
 */
public class DataFormatter {

    /**
     * Returns expression in upper case without whitespaces
     * @param expression String
     * @return String
     */
    public static String unifyExpression(String expression) {
        return expression.replaceAll(" ", "").toUpperCase();
    }
}
