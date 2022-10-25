import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HttpCrawler_WHUTeacher {
    //声明连接池
    private PoolingHttpClientConnectionManager cm;
    private RequestConfig getconfig;

    public HttpCrawler_WHUTeacher() {
        this.cm =new PoolingHttpClientConnectionManager();

        //设置最大连接数
        this.cm.setMaxTotal(100);

        //设置每个主机最大连接数
        this.cm.setDefaultMaxPerRoute(10);

    }


    public String doGetHtml(String url) {
        //获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();

        //创建HttpGet请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);

        //设置请求信息
        httpGet.setConfig(this.getconfig);

        CloseableHttpResponse response = null;

        try {
            //使用HttpClient发起请求，获取响应
            response = httpClient.execute(httpGet);

            //解析响应，返回结果

                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //返回空串
        return "";
    }

    private RequestConfig getGetconfig(){
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(100)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)
                .build();
        return config;
    }


    public static void main(String[] args) throws IOException, WriteException {

        String url1 = "http://cs.whu.edu.cn/teacher.aspx?showtype=jobtitle&typename=%E6%95%99%E6%8E%88";

        HttpCrawler_WHUTeacher httpUtils = new HttpCrawler_WHUTeacher();
        String html = httpUtils.doGetHtml(url1);

        //解析页面，获得教师信息
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("tr");
//        List<Map<String, Object>> datapojo = new ArrayList<>();
        
        ArrayList<String> nameList=new ArrayList<>();
        ArrayList<String> researchList = new ArrayList<>();
        ArrayList<String> positionList=new ArrayList<>();

        for (Element el : elements) {
            String name = el.getElementsByClass("w1").eq(0).text();
            nameList.add(name);
            String position = el.getElementsByClass("w4").eq(0).text();
            positionList.add(position);
            String research = el.getElementsByClass("w5").eq(0).text();
            researchList.add(research);
            System.out.printf("%-15s\t%-15s\t%s\n%n", name, position, research);
            
            
        }
        
        File file=new File ("D:\\桌面\\武汉大学计算机学院教师名单.xlsx");
        WritableWorkbook workbook=Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("Sheet1",0);

        Label label;
        for(int nameIndex=0;nameIndex< nameList.size();nameIndex++){
            label=new Label(0,nameIndex+1, nameList.get(nameIndex));
            sheet.addCell(label);
        }

        for(int positionIndex=0;positionIndex< positionList.size();positionIndex++){
            label=new Label(1,positionIndex+1, positionList.get(positionIndex));
            sheet.addCell(label);
        }


        for(int researchIndex=0;researchIndex< researchList.size();researchIndex++){
            label=new Label(2,researchIndex+1, researchList.get(researchIndex));
            sheet.addCell(label);
        }

        //写入excel表格
        workbook.write();
        //关闭excel工作薄
        workbook.close();

    }
}
//        Elements nameElements =dociment.getElementsByClass("w1");
//        Elements positionElements = document.getElementsByClass("w4");
//        Elements studyElements = document.getElementsByClass("w5");
//        System.out.println("武汉大学计算机学院教师名单如下：");
//        for(int index=0;index< nameElements.size();index++){
//            System.out.println(
//                    String.format("%-15s",nameElements.get(index).text())+
//                    String.format("%-15s",positionElements.get(index).text())+
//                    String.format("%-15s",studyElements.get(index).text())
//            );
//        }

//        String url2 = "https://csse.szu.edu.cn/pages/teacherTeam/index?p=1&zc=2&sx=&s=&i=";
//
//        HttpUtils httpUtils2 = new HttpUtils();
//        String html2 =httpUtils2.doGetHtml(url2);
//
//        //解析页面，获得教师信息
//        Document document2 = Jsoup.parse(html2);
//        Elements nameElements_1 = document2.getElementsByClass("name");
//        Elements positionElements_2 = document2.getElementsByClass("c_333");
//        Elements studyElements_3 = document2.getElementsByClass("skill");
//        System.out.println("深圳大学计算机学院教师名单如下：");
//        for(int index=0;index< nameElements_1.size();index++){
//            System.out.println(
//                    nameElements_1.get(index).text()+"\t\t"+
//                   positionElements_2.get(index).text()+"\t\t"+
//                    studyElements_3.get(index).text()
//            );
//        }
//
//     }

//    public static void main(String[] args){
//        //获取数据
//        HtmlJsoupData jsoupData = new HtmlJsoupData();
//        List<Map<String,Object>> list=jsoupData.main();
//
//
//        ExcelWriter writer = ExcelUtil.getWriter("D:\\桌面\\爬虫结果.xlsx");
//
//        writer.passCurrentRow();
//
//        writer.setColumnWidth(0,20);
//        writer.setColumnWidth(1,40);
//        writer.setColumnWidth(2,100);
//        writer.setDefaultRowHeight(17);
//        writer.write(list,true);
//
//        writer.close();
//
//        System.out.println("成功");
//
//    }
//}

