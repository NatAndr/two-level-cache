package com.getjavajob.training.wiley.andrianova.twolevelcache.cache.cachelevel;

import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.Cache;
import com.getjavajob.training.wiley.andrianova.twolevelcache.cache.CachedObject;
import com.getjavajob.training.wiley.andrianova.twolevelcache.utils.Serializer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.UUID;

import static com.getjavajob.training.wiley.andrianova.twolevelcache.utils.CacheUtils.getProperty;

/**
 * Created by user on 16.02.2016.
 */
public class FileSystemCache<K extends Serializable, V extends Serializable> implements Cache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(FileSystemCache.class);
    private static final String CACHE_FILESYSTEM_DISK_PATH = "cache.filesystem.DiskPath";
    private static final String FILE_EXTENSION = ".dat";
    private Cache<K, String> fileNameStorage;
    private String path;

    public FileSystemCache(String strategy, int capacity) {
        logger.debug("File system cache strategy: " + strategy);
        logger.debug("File system cache capacity: " + capacity);
        initFileNameStorage(strategy, capacity);
        initDirectory();
        logger.debug("File system cache created");
    }

    private void initFileNameStorage(final String strategy, int capacity) {
        this.fileNameStorage = new MemoryCache<K, String>(CacheStrategyType.valueOf(strategy.toUpperCase()), capacity) {
            @Override
            protected void additionalAction(K key) {
                deleteFile(key);
            }
        };
    }

    private void initDirectory() {
        path = getProperty(CACHE_FILESYSTEM_DISK_PATH);
        logger.debug("TwoLevelCache filesystem disk path: " + path);
        File directory = new File(path);
        if (directory.exists()) {
            try {
                FileUtils.cleanDirectory(directory);
            } catch (IOException e) {
                logger.error("Cannot clean directory: " + path);
            }
        } else {
            directory.mkdirs();
        }
    }

    protected void deleteFile(K key) {
        logger.debug("Going to delete file for key: {}", key);
        String fileName = fileNameStorage.get(key);
        try {
            Files.deleteIfExists(new File(fileName).toPath());
            logger.debug("Deleted file: " + fileName);
        } catch (IOException e) {
            logger.error("Cannot delete file: " + fileName);
        }
    }

    @Override
    public V get(K key) {
        logger.debug("Going to get object with key: {}", key);
        V val = null;
        if (fileNameStorage.containsKey(key)) {
            String fulFilePath = fileNameStorage.get(key);
            File file = new File(fulFilePath);
            CachedObject<K, V> cachedObject = Serializer.readObject(file);
            val = cachedObject.getVal();
            logger.debug("Got object, value: {}", val);
        } else {
            logger.debug("There is no such object with key: {}", key);
        }
        return val;
    }

    @Override
    public void put(K key, V val) {
        String fileName = UUID.randomUUID().toString();
        logger.debug("Going to add object");
        String fulFilePath = path + '/' + fileName + FILE_EXTENSION;
        File file = new File(fulFilePath);
        CachedObject<K, V> cachedObject = new CachedObject<>(key, val);
        if (cachedObject.isValid()) {
            fileNameStorage.put(key, fulFilePath);
            Serializer.writeObject(cachedObject, file);
            logger.debug("Added object: {}", cachedObject);
        } else {
            logger.error("Object {} is not valid", cachedObject);
        }
    }

    @Override
    public int getSize() {
        return fileNameStorage.getSize();
    }

    @Override
    public boolean containsKey(K key) {
        return fileNameStorage.containsKey(key);
    }

    @Override
    public boolean remove(K key) {
        deleteFile(key);
        return fileNameStorage.remove(key);
    }

    @Override
    public String toString() {
        return fileNameStorage.toString();
    }
}
