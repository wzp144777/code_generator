package study;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;

import java.util.List;

public class FastJson {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 3, 4, 8, 9);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);

        // 使用匿名类创建TypeReference的子类对象
        List<Integer> list2 = jsonObject.getObject("list", new TypeReference<List<Integer>>() {});
    }
}
