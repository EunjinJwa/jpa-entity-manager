package persistence.core;

import jdbc.JdbcTemplate;
import persistence.entity.metadata.EntityMetadata;
import persistence.sql.dml.DMLQueryBuilder;

/**
 * EntityManager - EntityEntry - EntityPersister
 * EntityManager - EntityEntry - EntityLoader
 * EntityPersister는 엔터티의 메타데이터와 데이터베이스 매핑 정보를 제공
 * 변경된 엔터티를 데이터베이스에 동기화하는 역할
 */
public class EntityPersister {

    // entity의 메타데이터

    private JdbcTemplate jdbcTemplate;
    private DMLQueryBuilder dmlQueryBuilder;
    private EntityMetadata entityMetadata;
    private Object entity;

    public boolean update() {
        return false;
    }

    public void insert() {
        String sql = dmlQueryBuilder.insertSql(entity);
        jdbcTemplate.execute(sql);
    }

    public void delete() {
        String sql = dmlQueryBuilder.deleteSql(entity);
        jdbcTemplate.execute(sql);
    }

}
