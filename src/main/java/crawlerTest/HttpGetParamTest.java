package crawlerTest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetParamTest {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置请求地址
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        //设置参数
        uriBuilder.setParameter("keys","Java");

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        System.out.println("发起请求的信息"+httpGet);
        CloseableHttpResponse response =null;

        try {
             response = httpClient.execute(httpGet);

            if(response.getStatusLine().getStatusCode()==200){
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                System.out.println(content.length());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            //关闭response
            try {
                response.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
