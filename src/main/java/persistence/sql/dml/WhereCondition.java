package persistence.sql.dml;

import java.util.List;

public class WhereCondition {
    private StringBuilder whereClause;

    private WhereCondition add(String columnName, Object value, ConditionOperator operator) {
        strtWhereClause().append(formWhereCondition(columnName, value, operator));
        return this;
    }

    public WhereCondition eq(String columnName, Object value) {
        return add(columnName, value, ConditionOperator.EQUAL);
    }

    public WhereCondition gt(String columnName, Object value) {
        return add(columnName, value, ConditionOperator.GATHER_THAN);
    }


    public WhereCondition and() {
        if (whereClause == null) {
            throw new IllegalStateException("Where clause is not started yet");
        }
        whereClause.append(" AND ");
        return this;
    }


    private String formWhereCondition(String columnName, Object value, ConditionOperator operator) {

        return String.format("%s %s %s", columnName, operator.getSymbol(), EntityValue.formatValue(value));
    }

    public String build() {

        return whereClause == null ? "" : whereClause.toString();
    }


    private StringBuilder strtWhereClause() {
        if (whereClause == null) {
            whereClause = new StringBuilder("WHERE ");
        }
        return whereClause;
    }






}
