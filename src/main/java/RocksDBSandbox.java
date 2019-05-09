import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;

public class RocksDBSandbox {
    // a static method that loads the RocksDB C++ library.
    static {
        org.rocksdb.RocksDB.loadLibrary();
    }

    public static void put(String dbPath, String key, String value) {
        try (final RocksDB db = RocksDB.open(dbPath)) {
            db.put(key.getBytes(), value.getBytes());
        } catch (RocksDBException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static Map<String, String> getAll(String dbPath) {
        Map<String, String> entries = new HashMap<>();
        try (final RocksDB db = RocksDB.open(dbPath)) {
            RocksIterator iterator = db.newIterator();
            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                entries.put(new String(iterator.key()), new String(iterator.value()));
            }

            iterator.close();
        } catch (RocksDBException e) {
            System.out.println(e.getStackTrace());
        }

        return entries;
    }

    public static void removeDatabase(String path) {
        Objects.requireNonNull(path);
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                removeDatabase(f.toString());
            }
        }

        file.delete();
    }
}
