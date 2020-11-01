package work.cxlm.cache;

import lombok.extern.slf4j.Slf4j;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;
import work.cxlm.config.MyFontProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Level DB 实现的字符串缓存
 * created 2020/11/1 22:50
 *
 * @author cxlm
 * @author Pencilso
 */
@Slf4j
public class LevelCacheStore extends AbstractStringCacheStore {

    /**
     * 清理器的工作周期
     */
    public static final long PERIOD = 60 * 1000;

    private static DB LEVEL_DB;

    private Timer timer;  // 定时器

    public LevelCacheStore(MyFontProperties properties) {
        super.myFontProperties = properties;
    }

    @PostConstruct
    public void init() {
        if (LEVEL_DB != null) {
            return;
        }
        try{
            // 工作路径
            File folder = new File(myFontProperties.getWorkDir() + ".leveldb");
            DBFactory factory = new Iq80DBFactory();
            Options options = new Options();
            options.createIfMissing(true);

            LEVEL_DB = factory.open(folder, options);
            timer = new Timer();
            timer.scheduleAtFixedRate(new CacheExpiryCleaner(), 0, PERIOD);
            // FIXME: CODING HERE
        } catch (IOException e) {
            log.error("初始化数据库失败", e);
        }
    }

    @Override
    Optional<CacheWrapper<String>> gerInternal(String key) {
        return Optional.empty();
    }

    @Override
    void putInternal(String key, CacheWrapper<String> value) {

    }

    @Override
    Boolean putInternalIfAbsent(String key, CacheWrapper<String> value) {
        return null;
    }

    @Override
    public void delete(String key) {

    }

    // TODO：清理过期缓存
    private class CacheExpiryCleaner extends TimerTask {

        @Override
        public void run() {

        }
    }
}
