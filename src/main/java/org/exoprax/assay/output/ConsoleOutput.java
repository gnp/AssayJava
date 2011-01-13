/*
 * Copyright 2006-2011 Gregor N. Purdy, Sr.
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

package org.exoprax.assay.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.exoprax.assay.Assay;
import org.exoprax.assay.AssayOutput;

public class ConsoleOutput implements AssayOutput {

    private static class StringComparator implements Comparator<String> {

        public int compare(final String o1, final String o2) {
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

    private Assay assay;

    private int valueWidth = 30;

    private int showValuesLimit = 20;

    private static final StringComparator stringComparator = new StringComparator();

    public ConsoleOutput() {
        super();
    }

    /**
     * @return the assay
     */
    public Assay getAssay() {
        return this.assay;
    }

    public int getShowValuesLimit() {
        return this.showValuesLimit;
    }

    public int getValueWidth() {
        return this.valueWidth;
    }

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

            System.out.printf("  %-30s  %s  %8s  %8s\n", "PATTERN", StringUtils.rightPad("VALUE", valueWidth), "DISTINCT", "OCCURS");
            System.out.printf("  %-30s  %s  %8s  %8s\n", "------------------------------",
                    StringUtils.rightPad("", valueWidth, '-'), "--------", "--------");

            final List<String> typeSpecList = new ArrayList<String>(columnAssay.keySet());
            Collections.sort(typeSpecList, stringComparator);

            for (final String typeSpec : typeSpecList) {
                final Map<String, Long> valueAssay = columnAssay.get(typeSpec);

                final List<String> valueList = new ArrayList<String>(valueAssay.keySet());

                if (valueList.size() <= showValuesLimit) {
                    Collections.sort(valueList, stringComparator);

                    for (final String actualValue : valueList) {
                        final String escapedValue = StringEscapeUtils.escapeJava(actualValue);

                        distinctCount++;

                        final Long valueCount = valueAssay.get(actualValue);

                        occursCount += valueCount.longValue();

                        String displayValue = escapedValue;

                        if ((displayValue != null) && (displayValue.length() > valueWidth - 2)) {
                            displayValue = displayValue.substring(0, valueWidth - 5) + "...";
                        }

                        if (displayValue == null) {
                            displayValue = "null";
                        } else {
                            displayValue = "\"" + displayValue + "\"";
                        }

                        System.out.printf("  %-30s  %s  %,8d  %,8d\n", typeSpec, StringUtils.rightPad(displayValue, valueWidth), 1, valueCount);
                    }
                } else {
                    long typeSpecCount = 0;

                    for (final String value : valueList) {
                        distinctCount++;

                        final Long valueCount = valueAssay.get(value);

                        occursCount += valueCount.longValue();
                        typeSpecCount += valueCount.longValue();
                    }

                    System.out.printf("  %-30s  %s  %,8d  %,8d\n", typeSpec, StringUtils.rightPad("*", valueWidth), valueList.size(), typeSpecCount);
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

            System.out.printf("  %-30s  %s  %8s  %8s\n", "------------------------------",
                    StringUtils.rightPad("", valueWidth, '-'), "--------", "--------");
            System.out.printf("  %-30s  %s  %,8d  %,8d\n", "", StringUtils.rightPad("", valueWidth), distinctCount, occursCount);

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

    public void setShowValuesLimit(final int showValuesLimit) {
        this.showValuesLimit = showValuesLimit;
    }

    public void setValueWidth(final int valueWidth) {
        this.valueWidth = valueWidth;
    }

}
