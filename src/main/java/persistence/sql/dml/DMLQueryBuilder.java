package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;
import persistence.core.EntityContextManager;
import persistence.entity.metadata.EntityColumn;
import persistence.entity.metadata.EntityMetadata;
import persistence.inspector.EntityInfoExtractor;

public class DMLQueryBuilder extends EntityContextManager {

    private DMLQueryBuilder() {
    }

    public static DMLQueryBuilder getInstance() {
        return new DMLQueryBuilder();
    }

    private final static String COLUMN_SEPARATOR = ", ";

    public String insertSql(Object entity) {
        EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());

        String tableName = entityMetadata.getTableName();
        String columns = getColumnNamesClause(entityMetadata.getInsertTargetColumns());
        String columnValues = getColumnValueClause(entity, entityMetadata.getInsertTargetColumns());

        return DMLQueryFormatter.createInsertQuery(tableName, columns, columnValues);
    }

    private String getColumnValueClause(Object entity, List<EntityColumn> insertTargetColumns) {

        return insertTargetColumns.stream()
            .map(column -> getColumnValueWithSqlFormat(entity, column.getName()))
            .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    public String selectAllSql(Class<?> clazz) {
        EntityMetadata entityMetadata = getEntityMetadata(clazz);
        String columnsClause = entityMetadata.getColumns().stream()
            .map(EntityColumn::getName)
            .collect(Collectors.joining(COLUMN_SEPARATOR));

        return DMLQueryFormatter.createSelectQuery(columnsClause, entityMetadata.getTableName());
    }

    public String selectByIdQuery(Class<?> clazz, Object id) {
        EntityMetadata entityMetadata = getEntityMetadata(clazz);

        String sql = selectAllSql(clazz);
        String condition = createCondition(entityMetadata.getIdColumn().getName(), id);

        return DMLQueryFormatter.createSelectByConditionQuery(sql, condition);
    }

    public String deleteSql(Object entity) {
        EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());

        String tableName = entityMetadata.getTableName();
        String name = entityMetadata.getIdColumn().getName();
        Object value = getColumnValue(entity, name);
        String whereClauses = createCondition(name, value);

        return DMLQueryFormatter.createDeleteQuery(tableName, whereClauses);
    }

    public String updateSql(Object entity) {
        EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());

        String tableName = entityMetadata.getTableName();
        String idColumnName = entityMetadata.getIdColumn().getName();
        Object idValue = getColumnValue(entity, idColumnName);
        String valueSetClause = getUpdateColumnValueClause(entityMetadata, entity);
        String whereClauses = createCondition(idColumnName, idValue);

        return DMLQueryFormatter.createUpdateQuery(tableName, valueSetClause, whereClauses);
    }

    private String getUpdateColumnValueClause(EntityMetadata entityMetadata, Object entity) {

        return entityMetadata.getInsertTargetColumns().stream()
            .map(column -> column.getName() + " = " + getColumnValueWithSqlFormat(entity, column.getName()))
            .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    private String createCondition(String columnName, Object value) {
        WhereCondition whereCondition = new WhereCondition();
        whereCondition.eq(columnName, value);

        return whereCondition.build();
    }


    private String getColumnNamesClause(List<EntityColumn> insertTargetColumns) {

        return insertTargetColumns.stream()
            .map(EntityColumn::getName)
            .collect(Collectors.joining(COLUMN_SEPARATOR));
    }

    private String getColumnValueWithSqlFormat(Object entity, String columnName) {

        return EntityValue.formatValue(getColumnValue(entity, columnName));
    }

    private Object getColumnValue(Object entity, String columnName) {

        return EntityInfoExtractor.getColumnValue(entity, columnName);
    }

}
