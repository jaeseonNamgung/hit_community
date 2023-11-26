package com.hit.community.controller;

import com.hit.community.dto.TestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping(path = "/hello")
    public String hello(){
        return "get hello";
    }

    @GetMapping("/path-variable/{id}")
    public String pathVariable(@PathVariable(name = "id") String pathName){
        System.out.println("PathVariable : "+pathName);
        return pathName;
    }

    // http://localhost:9090/test/query-param?user=steve@gmail.com&age=24
    @GetMapping(path = "/query-param")
    public String queryParam(@RequestParam Map<String,String> queryParam){
        StringBuilder stringBuilder = new StringBuilder();
        queryParam.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            stringBuilder.append(key).append(" = ").append(value).append("\n");
        });
        System.out.println();
        return stringBuilder.toString();
    }

    @GetMapping("query-param02")
    public String queryParam02(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int age
    ){
        System.out.println(name);
        System.out.println(email);
        System.out.println(age);
        System.out.println();
        return name+" "+email+" "+age;
    }

    @GetMapping("query-param03")
    public String queryParam03(TestDTO userRequest){

        System.out.println(userRequest.getName());
        System.out.println(userRequest.getEmail());
        System.out.println(userRequest.getAge());
        System.out.println();

        return userRequest.toString();
    }

}
