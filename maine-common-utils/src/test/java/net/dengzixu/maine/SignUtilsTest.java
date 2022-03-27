package net.dengzixu.maine;

import net.dengzixu.maine.utils.SignUtils;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;


public class SignUtilsTest {

    @Test
    public void testSignA() {
        String secretKey = "123";

        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("user_id", "132123");
        treeMap.put("task_id", "22888888");


        System.out.println(SignUtils.sign(treeMap, secretKey));
        ;
    }
}
