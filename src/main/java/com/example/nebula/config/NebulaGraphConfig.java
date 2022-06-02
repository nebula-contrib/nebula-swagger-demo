package com.example.nebula.config;

import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({NebulaGraphProperties.class})
public class NebulaGraphConfig {
    @Autowired
    private NebulaGraphProperties nebulaGraphProperties;

    @Bean(destroyMethod = "close")
    public NebulaPool nebulaPool() throws UnknownHostException {
        final List<HostAddress> addressList = nebulaGraphProperties.getHostAddresses().stream().map(s -> {
            final String[] split = s.split(":");
            return new HostAddress(split[0], Integer.parseInt(split[1]));
        }).collect(Collectors.toList());

        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(10);
        NebulaPool pool = new NebulaPool();
        pool.init(addressList, nebulaPoolConfig);
        return pool;
    }


/*    public static void main(String[] args) throws UnknownHostException, IOErrorException, AuthFailedException, ClientServerIncompatibleException, NotValidConnectionException {
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(10);
        List<HostAddress> addresses = Collections.singletonList(new HostAddress("test.ht.cdh.worker4", 9669));

        NebulaPool pool = new NebulaPool();
        pool.init(addresses, nebulaPoolConfig);
        Session session = pool.getSession("root", "nebula", false);
        session.execute("SHOW HOSTS;");
        System.out.println(session.executeJson("SHOW HOSTS;"));
        System.out.println(session.executeJson("USE basketballplayer;MATCH (n) return n limit 2000"));
        System.out.println(session.executeJson("USE basketballplayer;MATCH ()<-[e]-() RETURN e LIMIT 3;"));
        // MATCH ()<-[e]-() RETURN e LIMIT 3;
        session.release();
        pool.close();
    }*/
}
