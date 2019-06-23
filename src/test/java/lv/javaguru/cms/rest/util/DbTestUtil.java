package lv.javaguru.cms.rest.util;

import lv.javaguru.cms.model.entities.Tables;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DbTestUtil {

    public static void resetAutoIncrementColumns(ApplicationContext applicationContext) throws SQLException {
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        String resetSqlTemplate = getResetSqlTemplate();
        try (Connection dbConnection = dataSource.getConnection()) {
            for (String resetSqlArgument: tableNames()) {
                try (Statement statement = dbConnection.createStatement()) {
                    String resetSql = String.format(resetSqlTemplate, resetSqlArgument);
                    statement.execute(resetSql);
                }
            }
        }
    }

    private static List<String> tableNames() {
        return Stream.of(Tables.values()).map(Tables::getDbName).collect(Collectors.toList());
    }

    private static String getResetSqlTemplate() {
        return "ALTER TABLE %s ALTER COLUMN id RESTART WITH 1";
    }

}
