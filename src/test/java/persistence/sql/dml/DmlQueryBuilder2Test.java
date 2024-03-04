package persistence.sql.dml;

import persistence.entity.Person;
import persistence.entity.metadata.DefaultEntityMetadataReader;
import persistence.entity.metadata.EntityMetadataReader;

public class DmlQueryBuilder2Test {

    public void test() {

        Person person = new Person();
        DmlQueryBuilder2 dmlQueryBuilder = new DmlQueryBuilder2();

        String select = dmlQueryBuilder.select(Person.class);
        String select2 = dmlQueryBuilder.selectById(Person.class, 1L);
        String insert = dmlQueryBuilder.insert(person);
        String delete = dmlQueryBuilder.delete(person);

    }
}

interface DmlQueryBuilderIfConstructor {
    String createQueryString();

}

class DmlQueryBuilder2{

    EntityMetadataReader entityMetadataReader = new DefaultEntityMetadataReader();

    public String select(Class<?> entityClass) {
        return new SelectQueryBuilder(entityMetadataReader, entityClass).createQueryString();
    }

    public String selectById(Class<?> entityClass, Object id) {
        return new SelectQueryBuilder(entityMetadataReader, entityClass, id).createQueryString();
    }

    public String insert(Person person) {
        return new InsertQueryBuilder(entityMetadataReader, person).createQueryString();
    }

    public String delete(Person person) {
        return new DeleteQueryBuilder(entityMetadataReader, person).createQueryString();
    }
}

class SelectQueryBuilder implements DmlQueryBuilderIfConstructor{

    private final static String SELECT = "SELECT ";
    private final static String FROM = " FROM ";
    private final static String WHERE = " WHERE ";
    private final static String ORDER = " ORDER BY ";
    private final static String LIMIT = " LIMIT ";
    private final EntityMetadataReader entityMetadataReader;
    private final Class<?> entityClass;
    private final Object id;

    public SelectQueryBuilder(EntityMetadataReader entityMetadataReader, Class<?> entityClass) {
        this.entityMetadataReader = entityMetadataReader;
        this.entityClass = entityClass;
        id = null;
    }

    public SelectQueryBuilder(EntityMetadataReader entityMetadataReader, Class<?> entityClass, Object id) {
        this.entityMetadataReader = entityMetadataReader;
        this.entityClass = entityClass;
        this.id = id;
    }

    @Override
    public String createQueryString() {
        return null;
    }
}

class InsertQueryBuilder implements DmlQueryBuilderIfConstructor {
    EntityMetadataReader entityMetadataReader;
    Object entity;

    public InsertQueryBuilder(EntityMetadataReader entityMetadataReader, Object entity) {
        this.entityMetadataReader = entityMetadataReader;
        this.entity = entity;
    }

    @Override
    public String createQueryString() {
        return null;
    }

    private String whereClause() {
        String condition = WhereClause.where().equal("id", 1L).end();
        return null;
    }
}

class DeleteQueryBuilder implements DmlQueryBuilderIfConstructor {
    private final EntityMetadataReader entityMetadataReader;
    Object entity;
    Long id;

    public DeleteQueryBuilder(EntityMetadataReader entityMetadataReader, Object entity) {
        this.entityMetadataReader = entityMetadataReader;
        this.entity = entity;
        this.id = 1L;
    }

    @Override
    public String createQueryString() {
        return null;
    }
}

class WhereClause {
    StringBuilder whereClause;

    public WhereClause() {
        this.whereClause = new StringBuilder(" WHERE ");
    }

    public static WhereClause where() {
        return new WhereClause();
    }

    public WhereClause and() {
        this.whereClause.append(" AND ");

        return this;
    }

    public WhereClause equal(String columnName, Object value) {
        conditionClause(columnName, value, ConditionOperator.EQUALS);

        return this;
    }

    public WhereClause notEqual(String columnName, Object value) {
        conditionClause(columnName, value, ConditionOperator.NOT_EQUALS);

        return this;
    }

    public void conditionClause(String columnName, Object value, ConditionOperator operator) {
        whereClause.append(columnName + operator.getOperator() + value);
    }

    public String end() {
        return whereClause.toString();
    }

}


enum ConditionOperator {
    EQUALS("="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_THAN_OR_EQUALS(">="),
    LESS_THAN_OR_EQUALS("<="),
    NOT_EQUALS("!=");

    private final String operator;

    ConditionOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}

