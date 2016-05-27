package edu.chalmers.vaporwave.util;

public class ErrorHandler {
    private static boolean errorIsInvoked = false;

    public static void setError(boolean e) {
        errorIsInvoked = e;
    }

    public static boolean getErrorIsInvoked() {
        return errorIsInvoked;
    }

}
