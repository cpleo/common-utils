package com.pgleo.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : pgleo
 * @version V1.0
 * @Description: 文件工具类
 * @date Date : 15:2415:24
 * @update 2019/5/14 15:24
 * @since JDK 1.8.0.181
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 单文件内容复制
     * @param src 源文件
     * @param dest 目标文件
     */
    public static void fileCopy(File src, File dest) {
        if(src==null || !src.exists()){
            logger.error("src file is not exists");
            return;
        }
        if(src.isDirectory()){
            logger.error("src file is folder");
            return ;
        }

        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(src);
            outputStream = new FileOutputStream(dest);
            byte[] write = new byte[1024];
            int len = 0;
            while((len =inputStream.read(write))>0){
                outputStream.write(write,0,len);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found",e);
        } catch (IOException e) {
            logger.error("IOException",e);
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (Exception e){}
        }
    }

    /**
     * 文件移动到指定位置
     * @param src 源文件
     * @param dest 目标文件
     */
    public static void fileRM(File src,File dest){
        if(src==null || !src.exists()){
            logger.error("src file is not exists");
            return;
        }
        if(src.isDirectory()){
            logger.error("src file is folder");
            return ;
        }

        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        src.renameTo(dest);
    }

    /**
     * 按行读取文件
     * @param file 目标文件
     */
    public static List<String> fileReadLines(File file){
        return fileReadLines(file,0);
    }

    /**
     * 按行读取文件，跳过skiplines不读取
     * @param file 目标文件
     * @param skipLines  跳过的行数
     */
    public static List<String> fileReadLines(File file,int skipLines){
        List<String> lines = new ArrayList<String>();
        if(file==null || !file.exists()){
            logger.error("src file is not exists");
            return null;
        }
        if(file.isDirectory()){
            logger.error("src file is folder");
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            for(int i=0;i<skipLines;i++) {
                reader.readLine();
            }
            String line = "";
            while (!StringUtils.isEmpty(line=reader.readLine())){
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found",e);
        } catch (IOException e) {
            logger.error("IOException",e);
        }finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    /**
     * 文件写入
     * @param lines 待写入数据
     * @param dest 目标文件
     *
     */
    public static void fileWriteLines(List<String> lines,File dest){
        fileWriteLines(lines,dest,100);
    }
    /**
     * 文件写入
     * @param lines 待写入数据
     * @param dest 目标文件
     * @param flushLen 刷入磁盘行数
     */
    public static void fileWriteLines(List<String> lines,File dest,int flushLen){
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter(dest));
            int row = 0;
            for (String line : lines){
                write.write(line+"\r\n");
                row++;
                if(row==flushLen){
                    write.flush();
                    row=0;
                }
            }
            write.flush();
        } catch (FileNotFoundException e) {
            logger.error("File not found",e);
        } catch (IOException e) {
            logger.error("IOException",e);
        }finally {
            try {
                if(write != null) {
                    write.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
