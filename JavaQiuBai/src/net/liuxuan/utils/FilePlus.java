package net.liuxuan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FilePlus {

    public static String ReadTextFileToString(String path) {
        return ReadTextFileToString(path,null);
    }

    public static String ReadTextFileToStringLn(String path) {
        return ReadTextFileToString(path,"\r\n");
    }

    public static String ReadTextFileToString(String path, String suffix) {
        File file = new File(path);
//		System.out.println(file.getAbsolutePath());
//		System.out.println(file.getCanonicalPath());
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            // System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                if (suffix != null) {
                    sb.append(suffix);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

    /**
     * 读取文本内容
     * @param filename 文本名称
     * @return
     */
    public static String readText(String filename) {
        File file = new File(filename);

        try {
            BufferedReader br   = new BufferedReader(new java.io.FileReader(file));
            StringBuffer   sb   = new StringBuffer();
            String         line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            br.close();

            return sb.toString();
        } catch (IOException e) {

//          LogInfo.error(this.getClass().getName(), e.getLocalizedMessage(), e);
            e.printStackTrace();

            return null;
        }
    }

    /**
     * 将内容写到文本中
     * @param textPath　文件路径
     * @param textname　文件名称
     * @param date 写入的内容
     * @return
     */
    public static boolean writeText(String textPath, String textname, String date) {
        boolean flag     = false;
        File    filePath = new File(textPath);

        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        try {
            FileWriter fw = new FileWriter(textPath + File.separator + textname);

            fw.write(date);
            flag = true;

            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 将内容写到文本中
     * @param filename　文件名称
     * @param data 写入的内容
     * @return
     */
    public static boolean writeText(String filename, String data) {
        boolean flag     = false;
        try {
            FileWriter fw = new FileWriter(filename);

            fw.write(data);
            flag = true;

            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 在文档后附加内容
     * @param textName
     * @param date
     * @return
     */
    public static boolean appendText(String textPath, String textName, String date) {
        boolean flag     = false;
        File    filePath = new File(textPath);

        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        try {
            FileWriter fw = new FileWriter(textPath + File.separator + textName, true);

            fw.append(date);
            flag = true;

            if (fw != null) {
                fw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

}
