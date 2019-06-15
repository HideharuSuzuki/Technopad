package com.technopad;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedTomcatConfiguration {

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        Connector ajpConnector = new Connector("org.apache.coyote.ajp.AjpNioProtocol");
        ajpConnector.setScheme("https");
        ajpConnector.setSecure(true);
        
        ajpConnector.setProtocol("AJP/1.3");
        ajpConnector.setPort(8009);
        ajpConnector.setRedirectPort(8443);
        // その他Tomcatの設定を実施する
        // 参考
        // https://tomcat.apache.org/tomcat-8.5-doc/api/org/apache/catalina/connector/Connector.html
        tomcat.addAdditionalTomcatConnectors(ajpConnector);
        
        return tomcat;
    }
}