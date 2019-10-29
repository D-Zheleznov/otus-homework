import org.junit.Test;
import ru.otus.l12.json.JsonObject;
import ru.otus.l12.json.JsonObjectWriter;
import ru.otus.l12.model.Pojo;
import ru.otus.l12.model.SimplePojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class JsonTester {

    @Test
    public void jsonTest() {
        Pojo object = new Pojo();
        object.setInteger(5);
        object.setString("Строка");
        object.setSimplePojo(new SimplePojo("Объект1", 1));
        object.setInts(new int[]{1, 2, 3});
        object.setStrings(new String[]{"Массив1", "Массив2"});
        object.setSimplePojos(new SimplePojo[]{new SimplePojo("Объект1", 1), new SimplePojo("Объект2", 2)});
        object.setCollection(Arrays.asList(new SimplePojo("Коллекция1", 1), new SimplePojo("Коллекция2", 2)));

        Map<Object, Object> map = new HashMap<>();
        map.put("Карта1", 1);
        map.put("Карта2", 2);
        object.setMap(map);

        JsonObject jsonObject = JsonObjectWriter.toJsonObject(object);
        assertNotNull(jsonObject);
        System.out.println(jsonObject.asJsonString());
    }
}

