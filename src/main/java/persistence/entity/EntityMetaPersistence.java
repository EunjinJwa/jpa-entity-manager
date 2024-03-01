package persistence.entity;

import persistence.core.EntityContextManager;
import persistence.entity.metadata.EntityMetadata;
import persistence.entity.metadata.EntityMetadataBuilder;
import persistence.inspector.EntityInfoExtractor;
import persistence.sql.dml.DMLQueryBuilder;

import java.lang.reflect.Field;

public class EntityMetaPersistence {

    private Object entity;
    private EntityMetadata entityMetadata;

    public EntityMetaPersistence(Object entity) {
        entityMetadata = EntityContextManager.getEntityMetadata(entity.getClass());
        this.entity = entity;
    }

    public Object getColumnValue(String columnName) {

        return EntityInfoExtractor.getColumnValue(entity, columnName);
    }
}
