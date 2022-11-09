package com.github.iut.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringRegUtils {
    private static final Pattern PREPEND_BLANK_REG = Pattern.compile("(^\\s+)", Pattern.DOTALL);
    private static final Pattern APPEND_BLANK_REG = Pattern.compile("(\\s+$)", Pattern.DOTALL);
    private static final Pattern SHARP_VARIABLE_REG = Pattern.compile("#([\\w\\[\\]\\.]+)(:?)(\\w*)#");
    private static final List<String> VALID_JDBCTYPE_LIST = Arrays.asList(
            "BIT",
            "TINYINT",
            "SMALLINT",
            "INTEGER",
            "BIGINT",
            "FLOAT",
            "REAL",
            "DOUBLE",
            "NUMERIC",
            "DECIMAL",
            "CHAR",
            "VARCHAR",
            "LONGVARCHAR",
            "DATE",
            "TIME",
            "TIMESTAMP",
            "BINARY",
            "VARBINARY",
            "LONGVARBINARY",
            "NULL",
            "OTHER",
            "JAVA_OBJECT",
            "DISTINCT",
            "STRUCT",
            "ARRAY",
            "BLOB",
            "CLOB",
            "REF",
            "DATALINK",
            "BOOLEAN"
    );
    private static final Map<String, String> INVALID_TO_VALID_MAP = Stream.of(new String[][] {
            { "VARCHAR2", "VARCHAR" },
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

    public static String prepend(final String inputString, final String prependText) {
        final Matcher matcher = PREPEND_BLANK_REG.matcher(inputString);
        final String blankString = (matcher.find()) ? matcher.group(1) : "";
        final String restString = inputString.substring(blankString.length());
        return (blankString.length() == 0) ? prependText + " " + restString : blankString + prependText + " " + restString;
    }

    public static String append(final String inputString, final String appendText) {
        final Matcher matcher = APPEND_BLANK_REG.matcher(inputString);
        final String blankString = (matcher.find()) ? matcher.group(1) : "";
        String restString = inputString;
        if (blankString.length() > 0) {
            restString = inputString.substring(0, inputString.length() - blankString.length());
        }
        return (blankString.length() == 0) ? restString + " " + appendText : restString + " " + appendText + blankString;
    }

    public static String convertSharpVariable(String content) {
        Matcher matcher = SHARP_VARIABLE_REG.matcher(content);
        String ret = content;
        while (matcher.find()) {
            final String type = matcher.group(3);
            if (VALID_JDBCTYPE_LIST.contains(type)) {
                ret = matcher.replaceFirst("#{$1,jdbcType=$3}");
            } else if (INVALID_TO_VALID_MAP.containsKey(type)) {
                ret = matcher.replaceFirst(String.format("#{$1,jdbcType=%s}",INVALID_TO_VALID_MAP.get(type)));
            } else {
                ret = matcher.replaceFirst("#{$1}");
            }
            matcher = SHARP_VARIABLE_REG.matcher(ret);
        }
        return ret;
    }

    public static String convertDollarVariable(String content) {
        return content.replaceAll("\\$([^$ ]+?)\\$","\\${$1}");
    }
}



