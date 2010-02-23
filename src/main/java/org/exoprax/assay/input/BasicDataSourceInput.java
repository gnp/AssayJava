package org.exoprax.assay.input;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp.BasicDataSource;
import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayInput;

/**
 * Copyright 2006-2010 Gregor N. Purdy, Sr.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
public class BasicDataSourceInput implements AssayInput {

    public static String formatTypeString(final String columnType, final int precision, final int scale) {
        if (precision < 1) {
            return columnType;
        } else if (scale < 1) {
            return String.format("%s(%d)", columnType, precision);
        } else {
            return String.format("%s(%d, %d)", columnType, precision, scale);
        }
    }

    private Assay assay;

    private BasicDataSource dataSource;

    private String tableName;

    private String sql;

    private long rowLimit = 0;

    public Assay getAssay() {
        return this.assay;
    }

    public BasicDataSource getDataSource() {
        return this.dataSource;
    }

    public long getRowLimit() {
        return this.rowLimit;
    }

    public String getSql() {
        return this.sql;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void run() {
        try {
            runInner();
        } catch (final SQLException e) {
            throw new RuntimeException("SQLException while running.", e);
        }
    }

    public void runInner() throws SQLException {
        if (this.sql == null) {
            if (this.rowLimit > 0) {
                this.sql = "SELECT * FROM " + this.tableName + " WHERE ROWNUM <= " + this.rowLimit;
            } else {
                this.sql = "SELECT * FROM " + this.tableName;
            }
        }

        final Connection connection = this.dataSource.getConnection();

        try {
            final Statement statement = connection.createStatement();

            try {
                final ResultSet results = statement.executeQuery(this.sql);

                try {
                    final ResultSetMetaData metadata = results.getMetaData();

                    final int columnCount = metadata.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        final String columnName = metadata.getColumnName(i);
                        final String columnType = metadata.getColumnTypeName(i);
                        final int columnPrecision = metadata.getPrecision(i);
                        final int columnScale = metadata.getScale(i);

                        final String columnTypeString = formatTypeString(columnType, columnPrecision, columnScale);

                        this.assay.addAttribute(columnName, columnTypeString);
                    }

                    long rows = 0;
                    boolean hitLimit = false;

                    while (results.next()) {
                        if ((this.rowLimit > 0) && (rows >= this.rowLimit)) {
                            hitLimit = true;
                            break;
                        }

                        for (int i = 1; i <= columnCount; i++) {
                            final String columnName = metadata.getColumnName(i);

                            final String value = results.getString(i);

                            this.assay.addAttributeValue(columnName, value);
                        }

                        rows++;
                    }

                    if (hitLimit) {
                        this.assay.setTitle("Table: " + this.tableName + " (subset of " + rows + " rows)");
                    } else {
                        this.assay.setTitle("Table: " + this.tableName + " (all " + rows + " rows)");
                    }
                } finally {
                    try {
                        results.close();
                    } catch (final Exception e) {
                        // no-op. Closing is best-effort only.
                    }
                }
            } finally {
                try {
                    statement.close();
                } catch (final Exception e) {
                    // no-op. Closing is best-effort only.
                }
            }
        } finally {
            try {
                connection.close();
            } catch (final Exception e) {
                // no-op. Closing is best-effort only.
            }
        }
    }

    public void setAssay(final Assay assay) {
        this.assay = assay;
    }

    public void setDataSource(final BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setRowLimit(final long rowLimit) {
        this.rowLimit = rowLimit;
    }

    public void setSql(final String sql) {
        this.sql = sql;
    }

    public void setTableName(final String tableName) {
        this.tableName = tableName;
    }

}
