package edu.chalmers.vaporwave.util;

/**
 * A simple class to keep track of if an Error should be shown, meaning ErrorMessage class.
 */
public class ErrorHandler {
    private static boolean errorIsInvoked = false;

    public static void setError(boolean e) {
        errorIsInvoked = e;
    }

    public static boolean getErrorIsInvoked() {
        return errorIsInvoked;
    }

}
