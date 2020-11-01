package work.cxlm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.cache.LevelCacheStore;

/**
 * 程序相关配置
 * created 2020/11/1 17:06
 *
 * @author johnniang
 * @author cxlm
 */
@Configuration
@EnableConfigurationProperties(MyFontProperties.class)
@Slf4j
public class MyFontConfiguration {

    private MyFontProperties myFontProperties;

    @Autowired
    public void setMyFontProperties(MyFontProperties myFontProperties) {
        this.myFontProperties = myFontProperties;
    }

    @Bean
    public ObjectMapper objectMapperBean(Jackson2ObjectMapperBuilder builder) {
        builder.failOnEmptyBeans(false);  // 配置此项后，在遇到无法转换的对象时，不会抛出异常
        return builder.build();
    }

    @Bean
    @ConditionalOnMissingBean  // 防止重复注册，出现重复的情况直接抛出异常
    public AbstractStringCacheStore stringCacheStore() {
        AbstractStringCacheStore stringCacheStore = null;  // TODO: DELETE null
        switch (myFontProperties.getCache()) {
            case "level":
                stringCacheStore = new LevelCacheStore(myFontProperties);
                break;
            case "redis":
//              TODO: stringCacheStore = new RedisCacheStore(myFontProperties);
                break;
            case "memory":
            default:
//              TODO: stringCacheStore = new InMemoryCacheStore();
                break;
        }
//        TODO: log.info("正在使用 [{}] 缓存", stringCacheStore.getClass());
        return stringCacheStore;
    }

}
