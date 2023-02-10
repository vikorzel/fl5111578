package api;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class SendPost{
    public static String getResponseAfterJsonRequest(String inn) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .followRedirects(HttpClient.Redirect.NEVER)
                .proxy(ProxySelector.of(new InetSocketAddress("proxy1.teplo.local", 3128)))
                .build();;
        String jsonStr = new StringBuilder()
                .append("{")
                .append("\"inn\":\"" +inn + "\",")
                .append("\"requestDate\":\"" + getDate() + "\"")
                .append("}")
                .toString();
       // System.out.println(jsonStr);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://statusnpd.nalog.ru/api/v1/tracker/taxpayer_status"))
                .setHeader("User-Agent", "Java 11 HttpClient Bot")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonStr))
                .build();

        HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();


    }

    public static String getDate(){
       return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

        }


