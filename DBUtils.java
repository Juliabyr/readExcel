package com.unionpay.tech.db;

import com.unionpay.tech.db.entity.TbExcel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by zhuye on 2019/2/18.
 */
public class DBUtils {
    // 数据库连接地址
    public static String URL;
    // 用户名
    public static String USERNAME;
    // 密码
    public static String PASSWORD;
    // mysql的驱动类
    public static String DRIVER;
    private static ResourceBundle rb = ResourceBundle.getBundle("dbconfig");
    private DBUtils() {
    }

    // 使用静态块加载驱动程序
    static {
        URL = rb.getString("jdbc.url");
        USERNAME = rb.getString("jdbc.username");
        PASSWORD = rb.getString("jdbc.password");
        DRIVER = rb.getString("jdbc.driver");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
        }
        return conn;
    }

    // 关闭数据库连接
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getInputStreamFromFile(String inputFilePath) throws Exception {
        InputStream excelStream = null;
        FileInputStream fis = new FileInputStream(inputFilePath);
        HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(fis));
        fis.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HSSFWorkbook hssWb = wb;
        hssWb.write(out);
        excelStream = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return excelStream;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void outPutFileFromInputStream(InputStream inputStream, String outputFilePath) throws IOException {
        OutputStream outputStream = null;
        outputStream = new FileOutputStream(outputFilePath);
        int ch;
        try {
            while ((ch = inputStream.read()) != -1) {
                outputStream.write(ch);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            //关闭输入流等
            outputStream.close();
            inputStream.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String inputFilePath = "C:\\Users\\Julia\\Desktop\\inputExcel.xls";
        String outputFilePath = "C:\\Users\\Julia\\Desktop\\outputExcel.xls";
        ExcelDao excelDao = new ExcelDao();
        InputStream inputStream = getInputStreamFromFile(inputFilePath);
        //input流转成字节数组
        byte[] inputBytes = toByteArray(inputStream);
        //字节数组存入DB
        excelDao.add(new TbExcel(1, "Julia", inputBytes));
        /*
        * 已经完成将excel存入数据库
        * */
        //从数据库取出字节数组并转成excel导出
        byte[] outputBytes = excelDao.select(1);
        //字节数组转input流
        InputStream inputStream1 = new ByteArrayInputStream(outputBytes);
        outPutFileFromInputStream(inputStream1, outputFilePath);
    }
}

