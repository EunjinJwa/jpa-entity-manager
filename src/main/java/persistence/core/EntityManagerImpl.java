package persistence.core;

import database.DatabaseServer;
import jdbc.JdbcTemplate;

import java.sql.SQLException;

public class EntityManagerImpl implements EntityManager {
    private final EntityPersister entityPersister;
    private final EntityLoader entityLoader;
    private final PersistenceContext persistenceContext;

    public EntityManagerImpl(DatabaseServer server) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(server.getConnection());
        this.persistenceContext = new DefaultPersistenceContext();
        this.entityLoader = new DefaultEntityLoader(jdbcTemplate);
        this.entityPersister = new DefaultEntityPersister(jdbcTemplate);
    }

    @Override
    public <T> T find(Class<T> clazz, Long id) {
        T entity = (T) persistenceContext.getEntity(clazz, id);
        if (entity != null) {
            entity = entityLoader.find(clazz, id);
            persistenceContext.addEntity(id, entity);
            persistenceContext.getDatabaseSnapshot(id, entity);
        }
        return entity;
    }

    @Override
    public void persist(Object entity) {
        Long id = entityPersister.insert(entity);
        entityPersister.setIdentifier(entity, id);
        persistenceContext.addEntity(id, entity);
        persistenceContext.getDatabaseSnapshot(id, entity);
    }

    @Override
    public void remove(Object entity) {
        persistenceContext.removeEntity(entity);
        entityPersister.delete(entity);
    }

    @Override
    public <T> T merge(T entity) {
        persistenceContext.getEntity(entity.getClass(), 1L);
        return null;
    }

    /**
     *TODO
     * - 모든 엔티티와 스냡샷 비교
     * - 변경된 Entity 찾음
     * 수정 쿼리 생성
      */
    @Override
    public void flush() {
//        persistenceContext.dirtyCheck();


    }

}
