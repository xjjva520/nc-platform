package com.nc.xhq.auth.support;

import com.nc.component.redis.client.RedisOprClient;
import lombok.SneakyThrows;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/1/27
 * @package: com.nc.xhq.auth.service
 */
public class NcClientDetailService extends JdbcClientDetailsService {

    private RedisOprClient redisOprClient;
    private String http_client_check_authenticate_info = "http:client:authenticate:%s";


    public NcClientDetailService(DataSource dataSource,RedisOprClient redisOprClient) {
        super(dataSource);
        this.redisOprClient = redisOprClient;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        String redisKey = String.format(http_client_check_authenticate_info, clientId);
        BaseClientDetails details = null;
        try{
             details = (BaseClientDetails) redisOprClient.getObj(redisKey);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(details==null){
            ClientDetails clientDetails = super.loadClientByClientId(clientId);
            redisOprClient.setObj(redisKey,clientDetails,10080, TimeUnit.MINUTES);
            return clientDetails;
        }
        return details;
    }


}
