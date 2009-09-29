package org.exoprax.assay.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayOutput;

/**
 * Copyright (C) 2008 Gregor N. Purdy Sr. All rights reserved.
 * 
 * PROPRIETARY AND CONFIDENTIAL TO GREGOR N. PURDY SR. DO NOT DUPLICATE OR DISTRIBUTE.
 */
public class ConsoleOutput implements AssayOutput {

    private Assay assay;

    public ConsoleOutput() {
        super();
    }

    /**
     * @return the assay
     */
    public Assay getAssay() {
        return this.assay;
    }

    public void run() {
        final Map<String, String> columnTypes = this.assay.getColumnTypes();
        final Map<String, Map<String, Map<String, Long>>> tableAssay = this.assay.getTableAssay();

        System.out.println(this.assay.getTitle());
        System.out.println();

        final List<String> columnList = new ArrayList<String>(tableAssay.keySet());
        Collections.sort(columnList);

        for (final String columnName : columnList) {
            long distinctCount = 0;
            long occursCount = 0;

            final String columnTypeName = columnTypes.get(columnName);
            final Map<String, Map<String, Long>> columnAssay = tableAssay.get(columnName);

            System.out.printf("%s [ specified type %s ]:\n\n", columnName, columnTypeName);

            System.out.printf("  %-30s  %-32s  %8s  %8s\n", "PATTERN", "VALUE", "DISTINCT", "OCCURS");
            System.out.printf("  %-30s  %-32s  %8s  %8s\n", "------------------------------", "--------------------------------",
                    "--------", "--------");

            final List<String> typeSpecList = new ArrayList<String>(columnAssay.keySet());
            Collections.sort(typeSpecList);

            for (final String typeSpec : typeSpecList) {
                final Map<String, Long> valueAssay = columnAssay.get(typeSpec);

                final List<String> valueList = new ArrayList<String>(valueAssay.keySet());

                if (valueList.size() <= 20) {
                    Collections.sort(valueList);

                    for (final String actualValue : valueList) {
                        String value = actualValue.replaceAll("\\n", "\\n");
                        value = value.replaceAll("\\r", "\\r");
                        value = value.replaceAll("\\t", "\\t");

                        distinctCount++;

                        final Long valueCount = valueAssay.get(value);

                        occursCount += valueCount.longValue();

                        if ((value != null) && (value.length() > 30)) {
                            value = value.substring(0, 27) + "...";
                        }

                        if (value == null) {
                            value = "null";
                        } else {
                            value = "\"" + value + "\"";
                        }

                        System.out.printf("  %-30s  %-32s  %8d  %8d\n", typeSpec, value, 1, valueCount);
                    }
                } else {
                    long typeSpecCount = 0;

                    for (final String value : valueList) {
                        distinctCount++;

                        final Long valueCount = valueAssay.get(value);

                        occursCount += valueCount.longValue();
                        typeSpecCount += valueCount.longValue();
                    }

                    System.out.printf("  %-30s  %-32s  %8d  %8d\n", typeSpec, "*", valueList.size(), typeSpecCount);
                }
            }

            final String note;

            if (distinctCount == occursCount) {
                note = "[CANDIDATE KEY]";
            } else if (distinctCount == 1) {
                note = "[CONSTANT]";
            } else {
                note = null;
            }

            System.out.printf("  %-30s  %-32s  %8s  %8s\n", "------------------------------", "--------------------------------",
                    "--------", "--------");
            System.out.printf("  %-30s  %-32s  %8d  %8d\n", "", "", distinctCount, occursCount);

            if (note != null) {
                System.out.println(note);
            }

            System.out.println();
            System.out.println();
        }
    }

    /**
     * @param assay
     *            the assay to set
     */
    public void setAssay(final Assay assay) {
        this.assay = assay;
    }

}
