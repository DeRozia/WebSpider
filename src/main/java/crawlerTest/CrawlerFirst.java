package crawlerTest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CrawlerFirst {
    public static void main(String[] args) throws Exception {
        //打开浏览器，创建一个HttpClient对象
        CloseableHttpClient httpClient= HttpClients.createDefault();
        //输入网址
        HttpGet httpGet=new HttpGet("http://www.itcast.cn");
        //回车发起请求，返回相应
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //解析响应，获取数据
        if(response.getStatusLine().getStatusCode()==200){
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity, "utf8");
            System.out.println(content);
        }
    }
}
