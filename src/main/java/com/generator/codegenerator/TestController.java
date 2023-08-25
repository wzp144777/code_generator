package com.generator.codegenerator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cov")
@Api(tags = "测试类")
public class TestController {

    @PostMapping("/postTest")
    @ApiOperation(value = "测试post请求", notes = "测试post请求", httpMethod = "POST")
    public String postTest(@RequestParam Map<String, String> params) throws Exception {
        return "post接口测试成功";
    }

    @GetMapping("/getTest")
    @ApiOperation(value = "测试get请求", notes = "测试get请求", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTest() {
        return "get接口测试成功";
    }
}
