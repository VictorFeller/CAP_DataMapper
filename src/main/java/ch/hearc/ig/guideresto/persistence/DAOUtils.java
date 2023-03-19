package ch.hearc.ig.guideresto.persistence;

public class DAOUtils {
    public static boolean charToJavaBoolean(String value){
        return value.equalsIgnoreCase("T");
    }

    public static String booleanToJavaString(boolean value){
        if(value)
            return "T";
        else
            return "F";
    }
}
