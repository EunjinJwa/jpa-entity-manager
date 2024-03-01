package persistence.sql.dml;

public enum ConditionOperator {

    EQUAL("="),
    GATHER_THAN(">"),
    GATHER_THAN_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<=");

    private String symbol;
    ConditionOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
