package com.zyf.springmybatis.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyf.springmybatis.bean.UrlContentBean;

public class CreateHTMLHelper {
    public static Logger log = LoggerFactory.getLogger(CreateHTMLHelper.class);

    /**
     * 生成html 详细页面
     *
     * @param path
     * @param fileName
     * @param content
     * @return
     */
    public static boolean createDetailFile(String path, String fileName,
            String content) {
        FileOutputStream fout = null;
        try {
            File f = new File(path);
            if (!f.isDirectory()) {
                if (!f.mkdir()) {
                    CreateHTMLHelper.log.error("mkdir fail ,path:" + path);
                    return false;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            path += "/" + sdf.format(new Date()) + "/";
            File f2 = new File(path);
            if (!f2.isDirectory()) {
                if (!f2.mkdir()) {
                    CreateHTMLHelper.log.error("mkdir fail ,path:" + path);
                    return false;
                }
            }
            String outPutPath = path + fileName;
            fout = new FileOutputStream(outPutPath);
            fout.write(content.getBytes("utf-8"));
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 生成html 详细页面
     *
     * @param path
     * @param fileName
     * @param content
     * @return
     */
    public static boolean createIndexFile(String path, String fileName,
            String content) {
        FileOutputStream fout = null;
        try {
            File f = new File(path);
            if (!f.isDirectory()) {
                CreateHTMLHelper.log.error("mkdir fail ,path:" + path);
                return false;
            }
            String outPutPath = path + "/" + fileName;
            fout = new FileOutputStream(outPutPath);
            fout.write(content.getBytes("utf-8"));
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 向某个路径写文件
     *
     * @param path
     * @param fileName
     * @param content
     * @return
     */
    public static boolean createIndex(String path, String fileName,
            String content) {
        FileWriter fw = null;
        try {
            File f = new File(path);
            if (!f.isDirectory()) {
                return false;
            } else {
                path = path + fileName;
            }
            f = new File(path);
            // 删除源文件
            if (f.exists()) {
                f.delete();
            }
            fw = new FileWriter(path);
            fw.write(content);
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 根据xml本地文件内容，生成list url内容链表
     *
     * @param io
     * @return
     * @throws DocumentException
     */
    public static List<UrlContentBean> doXML(InputStream io)
            throws DocumentException {
        List<UrlContentBean> list = new ArrayList<UrlContentBean>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(io);
        Element root = document.getRootElement();
        List<Element> childElements = root.elements();
        for (Element child : childElements) {
            if (child.elementText("content").length() < 80) {
                continue;
            }
            UrlContentBean bean = new UrlContentBean();
            bean.setRssID(child.attributeValue("RssID"));
            bean.setChartSet(child.elementText("chartSet"));
            String title = child.elementText("Title");
            bean.setTitle(title);
            bean.setDescription(child.elementText("description"));
            bean.setCrawlTime(child.elementText("crawlTime"));
            String content = child.elementText("content");
            if (content.indexOf("\n") != -1) { // 如果正文中有换行
                String oneLine = content.substring(0, content.indexOf("\n"));
                if (oneLine.equals(title) || title.indexOf(oneLine) != -1) { // 如果第一行和标题一样
                    content = content.substring(content.indexOf("\n") + 1);
                    bean.setTitle(oneLine);
                }
                content = content.replaceAll("\n", "<br/>&nbsp;&nbsp;");
            } else {
                content = "&nbsp;&nbsp;" + content;
            }
            bean.setContent(content);
            list.add(bean);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            File f = new File(
                "C:/work/xingmai/workspace/lenovoPortalAdmin/src/main/resources/1.xml");
            InputStream io = new FileInputStream(f);

            new CreateHTMLHelper();
            CreateHTMLHelper.doXML(io);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
