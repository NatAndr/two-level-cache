package com.getjavajob.training.wiley.andrianova.twolevelcache;

import com.getjavajob.training.wiley.andrianova.twolevelcache.utils.Serializer;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 18.02.2016.
 */
public class SerializerTest {
    private static final String DIR = "D:/dev/cache";
    private File dir;

    @Before
    public void init() throws Exception {
        dir = new File(DIR + "/test");
    }

    @Test
    public void testWriteObject() throws Exception {
        String str = "TEST";
        Serializer.writeObject(str, dir);
        assertTrue(dir.exists());
        assertTrue(dir.length() != 0);
    }

    @Test
    public void testReadObject() throws Exception {
        String str = "TEST";
        Serializer.writeObject(str, dir);
        String result = Serializer.readObject(dir);
        assertEquals(str, result);
    }
}
