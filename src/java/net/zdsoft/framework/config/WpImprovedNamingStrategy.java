package net.zdsoft.framework.config;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class WpImprovedNamingStrategy extends ImprovedNamingStrategy {
    private static final long serialVersionUID = -5772192638904358420L;

    @Override
    public String classToTableName(String className) {
        // TODO Auto-generated method stub
        return super.classToTableName(className);
    }

    @Override
    public String columnName(String columnName) {
        // TODO Auto-generated method stub
        return super.columnName(columnName);
    }

    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        // TODO Auto-generated method stub
        return super.joinKeyColumnName(joinedColumn, joinedTable);
    }
    
    

    @Override
    public String logicalColumnName(String columnName, String propertyName) {
        String np = "";
        if (StringUtils.isBlank(columnName)) {
            for (int i = 0; i < propertyName.length(); i++) {
                String s = String.valueOf(propertyName.charAt(i));
                if (StringUtils.equals(s, s.toUpperCase())) {
                    np += "_" + s.toLowerCase();
                }
                else
                    np += s;
            }
        }
        else
            np = columnName;
        // TODO Auto-generated method stub
        return super.logicalColumnName(np, propertyName);
    }

    @Override
    public String propertyToColumnName(String propertyName) {
        // TODO Auto-generated method stub
        return super.propertyToColumnName(propertyName);
    }

    @Override
    public String tableName(String tableName) {
        // TODO Auto-generated method stub
        return super.tableName(tableName);
    }

    @Override
    public String collectionTableName(String ownerEntity, String ownerEntityTable,
            String associatedEntity, String associatedEntityTable, String propertyName) {
        // TODO Auto-generated method stub
        return super.collectionTableName(ownerEntity, ownerEntityTable, associatedEntity,
                associatedEntityTable, propertyName);
    }

    @Override
    public String logicalCollectionColumnName(String columnName, String propertyName,
            String referencedColumn) {
        // TODO Auto-generated method stub
        return super.logicalCollectionColumnName(columnName, propertyName, referencedColumn);
    }

    @Override
    public String logicalCollectionTableName(String tableName, String ownerEntityTable,
            String associatedEntityTable, String propertyName) {
        // TODO Auto-generated method stub
        return super.logicalCollectionTableName(tableName, ownerEntityTable, associatedEntityTable,
                propertyName);
    }

}
