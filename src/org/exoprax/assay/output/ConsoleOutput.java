package org.exoprax.assay.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
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

    private static class StringComparator implements Comparator<String> {

        public int compare(String o1, String o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            }

            if (o1 == null) {
                return -1;
            }

            if (o2 == null) {
                return 1;
            }

            final int temp = o1.compareToIgnoreCase(o2);

            if (temp == 0) {
                return o1.compareTo(o2);
            } else {
                return temp;
            }
        }
        
    }
    
    private static StringComparator stringComparator = new StringComparator();
    
    public void run() {
        final Map<String, String> columnTypes = this.assay.getColumnTypes();
        final Map<String, Map<String, Map<String, Long>>> tableAssay = this.assay.getTableAssay();

        System.out.println(this.assay.getTitle());
        System.out.println();

        final List<String> columnList = new ArrayList<String>(tableAssay.keySet());
        Collections.sort(columnList, stringComparator);

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
            Collections.sort(typeSpecList, stringComparator);

            for (final String typeSpec : typeSpecList) {
                final Map<String, Long> valueAssay = columnAssay.get(typeSpec);

                final List<String> valueList = new ArrayList<String>(valueAssay.keySet());

                if (valueList.size() <= 20) {
                    Collections.sort(valueList, stringComparator);

                    for (final String actualValue : valueList) {
                        final String escapedValue = StringEscapeUtils.escapeJava(actualValue);

                        distinctCount++;

                        Long valueCount = valueAssay.get(actualValue);
                        
                        occursCount += valueCount.longValue();

                        String displayValue = escapedValue;
                        
                        if ((displayValue != null) && (displayValue.length() > 30)) {
                            displayValue = displayValue.substring(0, 27) + "...";
                        }

                        if (displayValue == null) {
                            displayValue = "null";
                        } else {
                            displayValue = "\"" + displayValue + "\"";
                        }

                        System.out.printf("  %-30s  %-32s  %8d  %8d\n", typeSpec, displayValue, 1, valueCount);
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
