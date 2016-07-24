package com.dohko.core.mybatis.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiangbin on 2016/7/14.
 */
public class VelocityUtils {

    private static String[] TABLENAMES = {"tbl_base_city", "tbl_base_area"};
    private static String DBNAME = "db_dohko";
    private static String APPNAME = "blxq";
    private static String PNAME = "dohko";

    private static String[] MAPPERNAMES = {};

    @Test
    public void start() throws Exception {
        for (int i = 0; i < TABLENAMES.length; i++) {
            genMappSql(TABLENAMES[i]);
        }
    }

    private void genMappSql(String tableName)  throws Exception, ParseErrorException {
        List<String> fieldList = queryFieldList(DBNAME, tableName);
        VelocityEngine ve = getVelocityEngine();
        VelocityContext context = new VelocityContext();
        context.put("fieldLst", fieldList);
        context.put("tableName", tableName);
        context.put("appName", APPNAME);
        context.put("pName", PNAME);
        String className = "";
        String mapperNameStr = tableName.startsWith("tbl_") ? tableName.substring(4) : tableName;
        int startIndex = 0;
        int index = mapperNameStr.indexOf("_", startIndex);
        while (index > -1) {
            className = className + Character.toUpperCase(mapperNameStr.charAt(startIndex)) + mapperNameStr.substring(startIndex + 1, index);
            startIndex = index + 1;
            index = mapperNameStr.indexOf("_", startIndex);
        }
        className = className + Character.toUpperCase(mapperNameStr.charAt(startIndex)) + mapperNameStr.substring(startIndex + 1);
        String mapperName = className + "Mapper";
        String testName = className + "Test";
        context.put("mapperName", mapperName);
        context.put("testName", testName);
        context.put("mapperNameField", Character.toLowerCase(mapperName.charAt(0)) + mapperName.substring(1));
        context.put("queryByPKStart", className);
        context.put("queryByPKEnd", Character.toUpperCase(mapperNameStr.charAt(0)) + mapperNameStr.substring(1, mapperNameStr.indexOf("_") > 0 ? mapperNameStr.indexOf("_") : mapperNameStr.length()) + "Code");


        Template template = ve.getTemplate("config/template-sql");
        File output = new File("output/config/" + APPNAME + "/mybatis/");
        if (output.exists()) {
            output.delete();
        }
        output.mkdirs();
        File outFile = new File("output/config/" + APPNAME + "/mybatis/" + tableName + "-sql.xml");
        if (outFile.exists()) {
            outFile.delete();
        }
        OutputStream outputStream = new FileOutputStream(outFile);
        OutputStreamWriter outWriter = new OutputStreamWriter(outputStream);
        if(template != null){
            template.merge(context, outWriter);
            outWriter.flush();
            outWriter.close();
        }
        System.out.println("success gen mysql");
        Template templateMapper = ve.getTemplate("config/template-mapper");
        File output1 = new File("output/com/dohko/app/" + APPNAME + "/mapper/");
        if (output1.exists()) {
            output1.delete();
        }
        output1.mkdirs();
        File mapperOutFile = new File("output/com/dohko/app/" + APPNAME + "/mapper/" + mapperName + ".java");
        if (mapperOutFile.exists()) {
            mapperOutFile.delete();
        }
        OutputStream outputMapperStream = new FileOutputStream(mapperOutFile);
        OutputStreamWriter outMapperWriter = new OutputStreamWriter(outputMapperStream);
        if(templateMapper != null){
            templateMapper.merge(context, outMapperWriter);
            outMapperWriter.flush();
            outMapperWriter.close();
        }
        System.out.println("success gen mapper");

        Template testTemplate = ve.getTemplate("config/template-test");
        File testOutput = new File("output/com/dohko/app/" + APPNAME + "/service/");;
        if (testOutput.exists()) {
            testOutput.delete();
        }
        testOutput.mkdirs();
        File testOutFile = new File("output/com/dohko/app/" + APPNAME + "/service/" + testName + ".java");
        if (testOutFile.exists()) {
            testOutFile.delete();
        }
        OutputStream testOutputStream = new FileOutputStream(testOutFile);
        OutputStreamWriter testOutWriter = new OutputStreamWriter(testOutputStream);
        if(testTemplate != null){
            testTemplate.merge(context, testOutWriter);
            testOutWriter.flush();
            testOutWriter.close();
        }
        System.out.println("success gen test class");
        context.put("serviceName", className);
    }



    public VelocityEngine getVelocityEngine() throws Exception{
        VelocityEngine ve = new VelocityEngine();
        Properties properties = new Properties();
        String fileDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//      ve.setProperty(Velocity.RESOURCE_LOADER, "class");
//      ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,fileDir);
        properties.setProperty(Velocity.ENCODING_DEFAULT, "utf-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "utf-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
        ve.init(properties);
        return ve;

    }

    public List<String> queryFieldList(String database, String tableName) {
        try {
            Connection conn = getConnection();
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            String sql = "select * from columns where table_schema = '" + database + "' and table_name='" + tableName + "'";
            ResultSet rs = statement.executeQuery(sql);
            List<String> list = new ArrayList<>();
            while(rs.next()) {
                list.add(rs.getString("COLUMN_NAME"));
            }
            rs.close();
            statement.close();
            conn.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Connection getConnection() throws Exception {
        String driver = "com.mysql.jdbc.Driver";
        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://101.201.237.113:3306/information_schema";
        // MySQL配置时的用户名
        String user = "root";
        // Java连接MySQL配置时的密码
        String password = "123456";
        // 加载驱动程序
        Class.forName(driver);
        // 连续数据库
        return DriverManager.getConnection(url, user, password);
    }
}
