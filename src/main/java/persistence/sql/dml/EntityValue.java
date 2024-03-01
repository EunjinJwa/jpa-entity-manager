package persistence.sql.dml;

public class EntityValue {

    public static String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value == null ? "" : value.toString();
    }

}
