package net.dengzixu.maine;

import net.dengzixu.maine.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordUtilsTest {
    @Test
    public void testEncrypt() {
        String expect = "e477384d7ca229dd1426e64b63ebf2d36ebd6d7e669a6735424e72ea6c01d3f8b56eb39c36d8232f5427999b8d1a3f9cd1128fc69f4d75b434216810fa367e98";

        String message = "message";
        String key = "key";

        String result = PasswordUtils.encrypt(message, key);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(expect, result);
    }
}
