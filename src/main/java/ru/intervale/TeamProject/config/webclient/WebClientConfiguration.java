/*
 * @author Виктор Дробышевский
 * E-mail: akreshios@gmail.com
 * @since "03.04.2022, 14:52"
 * @version V 1.0.0
 */

package ru.intervale.TeamProject.config.webclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebClientConfiguration {

    @Value("${url.bank.alfa}")
    private String urlAlfa;

    @Value("${webclient.time.out}")
    public int TIMEOUT;

    @Bean("alfaBankClient")
    public WebClient webClientWithTimeout() {
        return WebClient.builder()
                .baseUrl(urlAlfa)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClientConfig())))
                .build();
    }


    private TcpClient tcpClientConfig(){
    return TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS));
            });
    }
}
