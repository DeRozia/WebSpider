package crawlerTest;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostParamTest {
    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.itheima.com/search");
        //声明List集合，封装表单中的参数
        List<NameValuePair> params=new ArrayList<>();
        params.add(new BasicNameValuePair("keys","Java"));
        //创建表单的Entity对象
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "utf8");
        //设置表单的Entity对象到Post请求中
        httpPost.setEntity(urlEncodedFormEntity);
        CloseableHttpResponse response =null;
        try {
            response = httpClient.execute(httpPost);

          if(response.getStatusLine().getStatusCode()==200) {
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
