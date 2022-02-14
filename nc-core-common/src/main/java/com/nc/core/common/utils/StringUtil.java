package com.nc.core.common.utils;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/19
 * @package: com.nc.auth.core.utils
 */
public class StringUtil extends StringUtils {
    public static final int INDEX_NOT_FOUND = -1;
    private static final Pattern SPECIAL_CHARS_REGEX = Pattern.compile("[`'\"|/,;()-+*%#·•�　\\s]");

    public StringUtil() {
    }

    public static boolean isBlank(final CharSequence cs) {
        return !hasText(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return hasText(cs);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        return ObjectUtil.isEmpty(css) || Stream.of(css).anyMatch(StringUtil::isBlank);
    }

    public static boolean isNoneBlank(final CharSequence... css) {
        return !ObjectUtil.isEmpty(css) && Stream.of(css).allMatch(StringUtil::isNotBlank);
    }

    public static boolean isAllBlank(final CharSequence... css) {
        return Stream.of(css).allMatch(StringUtil::isBlank);
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isBlank(cs)) {
            return false;
        } else {
            try {
                new BigDecimal(String.valueOf(cs));
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static String random(int count) {
        return random(count, RandomType.ALL);
    }

    public static String random(int count, RandomType randomType) {
        if (count == 0) {
            return "";
        } else {
            Assert.isTrue(count > 0, "Requested random string length " + count + " is less than 0.");
            Random random = new Random();
            char[] buffer = new char[count];

            for(int i = 0; i < count; ++i) {
                String factor = randomType.getFactor();
                buffer[i] = factor.charAt(random.nextInt(factor.length()));
            }

            return new String(buffer);
        }
    }
    public static Boolean isNormalIndicator(String indicator) {
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(indicator);
        return isNum.matches();
    }

    public static String format(@Nullable String message, @Nullable Map<String, ?> params) {
        if (message == null) {
            return "";
        } else if (params != null && !params.isEmpty()) {
            StringBuilder sb = new StringBuilder((int)((double)message.length() * 1.5D));

            int cursor;
            int start;
            int end;
            for(cursor = 0; (start = message.indexOf("${", cursor)) != -1 && (end = message.indexOf("}", start)) != -1; cursor = end + 1) {
                sb.append(message, cursor, start);
                String key = message.substring(start + 2, end);
                Object value = params.get(trimWhitespace(key));
                sb.append(value == null ? "" : value);
            }

            sb.append(message.substring(cursor));
            return sb.toString();
        } else {
            return message;
        }
    }

    public static String format(@Nullable String message, @Nullable Object... arguments) {
        if (message == null) {
            return "";
        } else if (arguments != null && arguments.length != 0) {
            StringBuilder sb = new StringBuilder((int)((double)message.length() * 1.5D));
            int cursor = 0;
            int index = 0;

            int start;
            int end;
            for(int argsLength = arguments.length; (start = message.indexOf(123, cursor)) != -1 && (end = message.indexOf(125, start)) != -1 && index < argsLength; ++index) {
                sb.append(message, cursor, start);
                sb.append(arguments[index]);
                cursor = end + 1;
            }

            sb.append(message.substring(cursor));
            return sb.toString();
        } else {
            return message;
        }
    }

    public static String format(long nanos) {
        if (nanos < 1L) {
            return "0ms";
        } else {
            double millis = (double)nanos / 1000000.0D;
            return millis > 1000.0D ? String.format("%.3fs", millis / 1000.0D) : String.format("%.3fms", millis);
        }
    }

    public static String join(Collection<?> coll) {
        return collectionToCommaDelimitedString(coll);
    }

    public static String join(Collection<?> coll, String delim) {
        return collectionToDelimitedString(coll, delim);
    }

    public static String join(Object[] arr) {
        return arrayToCommaDelimitedString(arr);
    }

    public static String join(Object[] arr, String delim) {
        return arrayToDelimitedString(arr, delim);
    }

    public static boolean simpleMatch(@Nullable String pattern, @Nullable String str) {
        return PatternMatchUtils.simpleMatch(pattern, str);
    }

    public static boolean simpleMatch(@Nullable String[] patterns, String str) {
        return PatternMatchUtils.simpleMatch(patterns, str);
    }

    public static String randomUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return (new UUID(random.nextLong(), random.nextLong())).toString().replace("-", "");
    }

    public static String escapeHtml(String html) {
        return isBlank(html) ? "" : HtmlUtils.htmlEscape(html);
    }

    public static String cleanChars(String txt) {
        return txt.replaceAll("[ 　`·•�\u0001\\f\\t\\v\\s]", "");
    }

    @Nullable
    public static String cleanText(@Nullable String txt) {
        return txt == null ? null : SPECIAL_CHARS_REGEX.matcher(txt).replaceAll("");
    }

    @Nullable
    public static String cleanIdentifier(@Nullable String param) {
        if (param == null) {
            return null;
        } else {
            StringBuilder paramBuilder = new StringBuilder();

            for(int i = 0; i < param.length(); ++i) {
                char c = param.charAt(i);
                if (Character.isJavaIdentifierPart(c)) {
                    paramBuilder.append(c);
                }
            }

            return paramBuilder.toString();
        }
    }

    public static String indexedFormat(CharSequence pattern, Object... arguments) {
        return MessageFormat.format(pattern.toString(), arguments);
    }


    public static boolean contains(CharSequence str, char searchChar) {
        return indexOf(str, searchChar) > -1;
    }


    public static boolean containsIgnoreCase(CharSequence str, CharSequence testStr) {
        if (null == str) {
            return null == testStr;
        } else {
            return str.toString().toLowerCase().contains(testStr.toString().toLowerCase());
        }
    }



    public static String subAfter(CharSequence string, CharSequence separator, boolean isLastSeparator) {
        if (isEmpty(string)) {
            return null == string ? null : string.toString();
        } else if (separator == null) {
            return "";
        } else {
            String str = string.toString();
            String sep = separator.toString();
            int pos = isLastSeparator ? str.lastIndexOf(sep) : str.indexOf(sep);
            return pos == -1 ? "" : str.substring(pos + separator.length());
        }
    }

    public static String subBetween(CharSequence str, CharSequence before, CharSequence after) {
        if (str != null && before != null && after != null) {
            String str2 = str.toString();
            String before2 = before.toString();
            String after2 = after.toString();
            int start = str2.indexOf(before2);
            if (start != -1) {
                int end = str2.indexOf(after2, start + before2.length());
                if (end != -1) {
                    return str2.substring(start + before2.length(), end);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String subBetween(CharSequence str, CharSequence beforeAndAfter) {
        return subBetween(str, beforeAndAfter, beforeAndAfter);
    }


    public static String firstCharToLower(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] + 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static String firstCharToUpper(String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] - 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static int indexOf(final CharSequence str, char searchChar) {
        return indexOf(str, searchChar, 0);
    }

    public static int indexOf(final CharSequence str, char searchChar, int start) {
        return str instanceof String ? ((String)str).indexOf(searchChar, start) : indexOf(str, searchChar, start, -1);
    }

    public static int indexOf(final CharSequence str, char searchChar, int start, int end) {
        int len = str.length();
        if (start < 0 || start > len) {
            start = 0;
        }

        if (end > len || end < 0) {
            end = len;
        }

        for(int i = start; i < end; ++i) {
            if (str.charAt(i) == searchChar) {
                return i;
            }
        }

        return -1;
    }

    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    public static int indexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int fromIndex) {
        return indexOf(str, searchStr, fromIndex, true);
    }

    public static int indexOf(final CharSequence str, CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (str != null && searchStr != null) {
            if (fromIndex < 0) {
                fromIndex = 0;
            }

            int endLimit = str.length() - searchStr.length() + 1;
            if (fromIndex > endLimit) {
                return -1;
            } else if (searchStr.length() == 0) {
                return fromIndex;
            } else if (!ignoreCase) {
                return str.toString().indexOf(searchStr.toString(), fromIndex);
            } else {
                for(int i = fromIndex; i < endLimit; ++i) {
                    if (isSubEquals(str, i, searchStr, 0, searchStr.length(), true)) {
                        return i;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr) {
        return lastIndexOfIgnoreCase(str, searchStr, str.length());
    }

    public static int lastIndexOfIgnoreCase(final CharSequence str, final CharSequence searchStr, int fromIndex) {
        return lastIndexOf(str, searchStr, fromIndex, true);
    }

    public static int lastIndexOf(final CharSequence str, final CharSequence searchStr, int fromIndex, boolean ignoreCase) {
        if (str != null && searchStr != null) {
            if (fromIndex < 0) {
                fromIndex = 0;
            }

            fromIndex = Math.min(fromIndex, str.length());
            if (searchStr.length() == 0) {
                return fromIndex;
            } else if (!ignoreCase) {
                return str.toString().lastIndexOf(searchStr.toString(), fromIndex);
            } else {
                for(int i = fromIndex; i > 0; --i) {
                    if (isSubEquals(str, i, searchStr, 0, searchStr.length(), true)) {
                        return i;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public static int ordinalIndexOf(String str, String searchStr, int ordinal) {
        if (str != null && searchStr != null && ordinal > 0) {
            if (searchStr.length() == 0) {
                return 0;
            } else {
                int found = 0;
                int index = -1;

                do {
                    index = str.indexOf(searchStr, index + 1);
                    if (index < 0) {
                        return index;
                    }

                    ++found;
                } while(found < ordinal);

                return index;
            }
        } else {
            return -1;
        }
    }

    public static boolean isSubEquals(CharSequence str1, int start1, CharSequence str2, int start2, int length, boolean ignoreCase) {
        return (null != str1 && null != str2) && str1.toString().regionMatches(ignoreCase, start1, str2.toString(), start2, length);
    }

    public static boolean equals(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, false);
    }

    public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
        return equals(str1, str2, true);
    }

    public static boolean equals(CharSequence str1, CharSequence str2, boolean ignoreCase) {
        if (null == str1) {
            return str2 == null;
        } else if (null == str2) {
            return false;
        } else {
            return ignoreCase ? str1.toString().equalsIgnoreCase(str2.toString()) : str1.equals(str2);
        }
    }

    public static StringBuilder builder() {
        return new StringBuilder();
    }

    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }

    public static StringBuilder builder(CharSequence... strs) {
        StringBuilder sb = new StringBuilder();
        CharSequence[] var2 = strs;
        int var3 = strs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            CharSequence str = var2[var4];
            sb.append(str);
        }

        return sb;
    }

    public static StringBuilder appendBuilder(StringBuilder sb, CharSequence... strs) {
        CharSequence[] var2 = strs;
        int var3 = strs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            CharSequence str = var2[var4];
            sb.append(str);
        }

        return sb;
    }


    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        int var4 = a.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = a[var5];
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    public static String humpToUnderline(String para) {
        para = firstCharToLower(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;

        for(int i = 0; i < para.length(); ++i) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                ++temp;
            }
        }

        return sb.toString().toLowerCase();
    }

    public static String lineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("-");
        String[] var3 = a;
        int var4 = a.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    public static String humpToLine(String para) {
        para = firstCharToLower(para);
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;

        for(int i = 0; i < para.length(); ++i) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "-");
                ++temp;
            }
        }

        return sb.toString().toLowerCase();
    }

    public enum RandomType {
        INT("0123456789"),
        STRING("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"),
        ALL("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

        private final String factor;
        private static final String INT_STR = "0123456789";
        private static final String STR_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final String ALL_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        public String getFactor() {
            return this.factor;
        }

        private RandomType(final String factor) {
            this.factor = factor;
        }
    }
}
