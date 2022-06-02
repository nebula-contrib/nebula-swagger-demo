package com.example.nebula.config;

import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * @Description 配置全局唯一session链接
 **/
@Component
public class NebulaSession {


    @Autowired
    NebulaPool nebulaPool;

    @Autowired
    NebulaGraphProperties nebulaGraphProperties;

    @Bean
    public Session session() throws Exception {
        return nebulaPool.getSession(nebulaGraphProperties.getUserName(),nebulaGraphProperties.getPassword(),false);
    }


}
