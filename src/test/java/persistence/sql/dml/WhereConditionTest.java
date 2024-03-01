package persistence.sql.dml;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhereConditionTest {

    @Test
    public void createWhereCondition() {
        WhereCondition whereCondition = new WhereCondition();
        String build = whereCondition.eq("name", "Kassy")
                .and()
                .gt("age", 30)
                .build();

        assertEquals("WHERE name = 'Kassy' AND age > 30", build);
    }

}
