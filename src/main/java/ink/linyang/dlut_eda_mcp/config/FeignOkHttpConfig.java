package ink.linyang.dlut_eda_mcp.config;

import feign.okhttp.OkHttpClient;
import okhttp3.ConnectionPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FeignOkHttpConfig {

    @Bean
    public OkHttpClient feignClient() {
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)   // 连接超时
                .readTimeout(8, TimeUnit.SECONDS)      // 读取超时
                .writeTimeout(8, TimeUnit.SECONDS)     // 写入超时
                .connectionPool(new ConnectionPool())
                .build();
        return new OkHttpClient(okHttpClient);
    }
}

