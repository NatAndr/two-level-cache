package com.getjavajob.training.wiley.andrianova.twolevelcache.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by user on 18.02.2016.
 */
public class Serializer {
    private static final Logger logger = LoggerFactory.getLogger(Serializer.class);

    public static void writeObject(Object object, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(
                new FileOutputStream(file)))) {
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            logger.error("Cannot write object {} to file {}", object, file.getName());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T readObject(File file) {
        T object = null;
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))) {
            Object obj = ois.readObject();
            object = (T) obj;
        } catch (Exception e) {
            logger.error("Cannot read object from file {}", file.getName());
        }
        return object;
    }
}
