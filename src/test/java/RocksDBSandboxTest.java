import java.io.File;
import java.util.Map;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RocksDBSandboxTest {
    private static String dbPath = "/tmp/testdb";

    @Test
    public void create() {
        String key = "key1";
        String value = "val1";
        RocksDBSandbox.put(dbPath, key, value);
        Map<String, String> entries = RocksDBSandbox.getAll(dbPath);
        assertThat(entries.containsKey(key) && entries.containsValue(value)).isTrue();
        RocksDBSandbox.removeDatabase(dbPath);
        assertThat(new File(dbPath).exists()).isFalse();
    }
}