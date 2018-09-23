package io.francoisbotha.bdgaswebproxy;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BdgaswebproxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BdgaswebproxyApplication.class, args);
    }

    @Bean(name = "resttemplatebean")
    public RestTemplate GetRestTemplate(){

        ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setMessageConverters(getMessageConverters());

        return restTemplate;
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 20000;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
//                .setDefaultHeaders(headers)
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    private List<HttpMessageConverter<?>> getMessageConverters() {
        List<HttpMessageConverter<?>> converters =
                new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }

}
