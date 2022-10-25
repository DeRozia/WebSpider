package jsoup;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;

public class JsoupFirstTest {
    @Test
    public void testUrl() throws Exception {
        //解析url地址，第一个参数是访问url，第二个参数是访问时的超时时间
        Document document = Jsoup.parse(new URL("http://www.itcast.cn"), 10000);

        //使用标签选择器，获取title标签中的内容
        String title = document.getElementsByTag("title").first().text();

        //打印获取的title
        System.out.println(title);
    }

    @Test
    public void testString() throws Exception{
        //使用工具类读取文件，获取字符串
        String content = FileUtils.readFileToString(new File("D:\\桌面\\PersonalWelcomeWithPassword.html"),"utf8");

        //解析字符串
        Document document = Jsoup.parse(content);

        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);

    }
    @Test
    public void testFile() throws Exception{
        //解析文件
        Document document = Jsoup.parse(new File("D:\\桌面\\PersonalWelcomeWithPassword.html"), "utf8");

        String title = document.getElementsByTag("title").first().text();

        System.out.println(title);
    }
    @Test
    public void testDOM()throws Exception{
        //解析文件，获取Document对象
        Document document = Jsoup.parse(new File("D:\\桌面\\PersonalWelcomeWithPassword.html"), "utf8");

        //获取元素
        //根据id查询元素
        Element element1 = document.getElementById("city");

        //根据标签获取元素
        Element element2 = document.getElementsByTag("title").first();

        //根据class获取元素
        Element element3 = document.getElementsByClass("class").first();

        //根据属性获取元素
        Element element4 = document.getElementsByAttribute("herf").first();
        //打印元素的内容
        System.out.println(element1.text());
        System.out.println(element2.text());
        System.out.println(element3.text());
    }
    @Test
    public void testData() throws Exception{
        Document document = Jsoup.parse(new File("D:\\桌面\\PersonalWelcomeWithPassword.html"), "UTF8");

        Element element = document.getElementById("");
        String str="";

        //从元素中获取id
        str=element.id();
        System.out.println(str);

        //从元素中获取className
        str=element.className();
        System.out.println(str);

        //从元素中获取所有属性attributes
        Attributes attributes = element.attributes();
        System.out.println(attributes.toString());

        //从元素中获取文本内容text
        str=element.text();
        System.out.println(str);

    }
    @Test
    public void textSelector()throws Exception{
        //解析HTML文件，获取document对象
        Document document = Jsoup.parse(new File(""), "utf8");

        Elements elements = document.select("");

        Element element = document.select("#id").first(); //加“#”

        Element element1 = document.select(".class").first();//加.

        Element element2 = document.select("[abc]").first();//加[]
    }
}
