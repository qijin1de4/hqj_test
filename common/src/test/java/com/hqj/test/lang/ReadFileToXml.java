package com.hqj.test.lang;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author liutao
 * @date Created in 2020/7/27 17:36
 * @description
 */
public class ReadFileToXml {
    private static final String HEADER_AFTER_NAME = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<components xmlns=\"http://www.imeta.org/meta\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "            xsi:schemaLocation=\"http://www.imeta.org/meta http://upsms.yonyouup.com/meta.xsd\">\n" +
            "    <component moduleName=\"hrjq\" name=\"";
    private static final String HEADER_AFTER_DOMAIN = "\" title=\"----------\" domain=\"hrcloud-";
    private static final String HEADER_AFTER_TABLENAME = "\">\n" +
            "        <class tableName=\"";
    private static final String HEADER_END = "\" title=\"----------\" name=\"----------\" isCoding=\"true\">\n" +
            "            <attributes>";

    private static final String ATTRIBUTE_AFTER_TYPE = "                <attribute type=\"";
    private static final String ATTRIBUTE_AFTER_NAME = "\" name=\"";
    private static final String ATTRIBUTE_AFTER_COLUMNNAME = "\" columnName=\"";
    private static final String ATTRIBUTE_AFTER_TITLE = "\" title=\"";
    private static final String ATTRIBUTE_AFTER_LENGTH = "\" iLength=\"";
    private static final String ATTRIBUTE_AFTER_END = "\"/>\n";


    private static final String FOOTER = "            </attributes>\n" +
            "        </class>\n" + "        <generalizations>\n" +
            "            <generalization parent=\"base.entity.BizObject\" child=\"Renewal\"/>\n" +
            "        </generalizations>\n" +
            "\n" +
            "        <realizations>\n" +
            "            <realization supplier=\"base.itf.ITenant\" client=\"Renewal\"/>\n" +
            "        </realizations>\n" +
            "\n" +
            "        <!--组件之间的组合关系\n" +
            "        type：表示对应关系类型，默认：composition\n" +
            "        typeB：主表组件名，roleB：从表中的主表关联属性\n" +
            "        typeA：从表组件名，roleA：主表中的从表关联属性\n" +
            "        roleAMulti：对应的表示1，1..n，n..n关联关系\n" +
            "        -->\n" +
            "<!--        <associations>\n" +
            "            <association type=\"composition\" typeB=\"AttendPeriodMain\" roleB=\"mainId\" typeA=\"AttendPeriod\" roleA=\"periodList\"\n" +
            "                         roleAMulti=\"1..n\"/>\n" +
            "        </associations>-->\n" +
            "    </component>\n" +
            "</components>";

    private static int DEFAULT_LENGTH = 50;

    /**
     * @param line
     * @return
     * @description 设置 <attribute> 的属性值，位置写死了
     */
    private static String getAttribute(String line) {
        StringBuilder sb = new StringBuilder(ATTRIBUTE_AFTER_TYPE);
        String[] split = line.split("\t");
        String type = split[2];
        int length = 50;
        if (type.contains("(")) {
            if (type.contains(",")) {
                length = Integer.valueOf(type.substring(type.lastIndexOf("(") + 1, type.lastIndexOf(",")));
            } else {
                length = Integer.valueOf(type.substring(type.lastIndexOf("(") + 1, type.lastIndexOf(")")));
            }
            type = getRealType(type.substring(0, type.lastIndexOf("(")).toLowerCase());
        }
        // 数据库字段名
        String columnName = split[0];
        // Java 字段名
        String name = columnName.toLowerCase();
        // 数据库中对字段的注释，有些字段没有注释，所以直接用字段名
        String title = name;
        String tem;
        // 如果有注释
        if (split.length > 3) {
            tem = split[3];
            // 懒得取反
            if (tem == null || tem.length() == 0 || "Y".equals(tem) || "N".equals(tem) || "NULL".equals(tem)) {
            } else {
                // 获取真正的注释
                title = tem;
            }
        }
        sb.append(type).append(ATTRIBUTE_AFTER_NAME + name).append(ATTRIBUTE_AFTER_COLUMNNAME + columnName)
                .append(ATTRIBUTE_AFTER_TITLE + title).append(ATTRIBUTE_AFTER_LENGTH + length + ATTRIBUTE_AFTER_END);
        return sb.toString();
    }

    /**
     * @param type
     * @return
     * @description 通过数据库字段类型获取对应的 Java 类型，后端类型规范不统一，只能做大概
     */
    private static String getRealType(String type) {
        switch (type) {
            case "varchar":
            case "text":
                return "String";
            case "int":
                return "Integer";
            case "datetime":
            case "date":
            case "time":
            case "timestamp":
                return "Date";
            case "decimal":
                return "Decimal";
            case "bigint":
                return "Long";
            default:
                return type;
        }
    }

    private static String getHeader(String tableName, String name) {
        StringBuffer sb = new StringBuffer();
        sb.append(HEADER_AFTER_NAME + name).append(HEADER_AFTER_DOMAIN + "pay").append(HEADER_AFTER_TABLENAME + tableName)
                .append(HEADER_END);
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        File srcFile = new File("/Users/qijinhu/Documents/yonyou/mdd/wa_columns_without_header.txt");

        // 目标文件夹
        String targetDir = "/Users/qijinhu/idea_projects/hqj_test/common/src/test/resources/";
        String fileName = null;
        BufferedWriter writer = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(srcFile);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            boolean end = true;
            OutputStreamWriter outputStreamWriter = null;
            FileOutputStream fileOutputStream = null;
            while ((line = bufferedReader.readLine()) != null) {
                // 拆分每行数据的值
                String[] split = line.split("\t");
                // 当前表信息读取完毕
                if (split != null && split.length == 0) {
                    // 设置读取完毕flag
                    end = true;
                    continue;
                }
                // 如果上张表信息读取完毕
                if (end) {
                    // 如果 writer 不为空则写入 FOOTER 信息
                    if (writer != null) {
                        writer.write(FOOTER);
                        writer.flush();
                        writer.close();
                    }
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    if (fileOutputStream != null) {
                        fileInputStream.close();
                    }
                    String tableName = split[0];
                    Optional<String> reduce = Arrays.stream(tableName.split("_"))
                            .filter(v -> !"ts".equalsIgnoreCase(v)).reduce((a, b) -> a + b);
                    String name = reduce.get();
                    fileName = "hrwa." + name + ".xml";
                    // 开始写下一个新文件
                    fileOutputStream = new FileOutputStream(new File(targetDir + fileName));
                    outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                    writer = new BufferedWriter(outputStreamWriter);
                    String header = getHeader(tableName, name);
                    writer.write(header);
                    writer.flush();
                    // 设置未读取完毕flag
                    end = false;
                } else {
                    // 将表每行的信息设置为对应的 <attribute>
                    writer.write(getAttribute(line));
                    writer.flush();
                }
            }
            // 如果已经读取到末尾，写入 FOOTER 信息并关闭流
            writer.write(FOOTER);
            writer.flush();
            writer.close();
            bufferedReader.close();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}

