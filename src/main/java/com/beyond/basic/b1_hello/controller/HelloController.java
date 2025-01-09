package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;

// Component 어노테이션을 통해 별도의 객체를 생성할 필요가 없는 싱글톤 객체 생성
// Controller 어노테이션을 통해 쉽게 사용자의 http 요청을 처리하고, http 응답을 줄 수 있음.
@Controller
// 클래스차원에 url매핑 시 RequestMapping사용
@RequestMapping("/hello")
public class HelloController {

    //    case1.서버가 사용자에게 단순 String 데이터 return (get 요청) - @ResponseBody
//    case2.서버가 사용자에게 화면을 return (get 요청) - ResponseBody가 없을 때
    @GetMapping("")
//    @ResponseBody
//    @ResponseBody가 없고, return타입이 String인 경우 서버는 templates폴더 밑에 helloworld.html화면을 리턴
    public String HelloWorld() {
        return "helloworld";
    }

    //    case3.서버가 사용자에게 json 형식의 데이터를 return (get 요청)
//    메서드 차원에서도 RequestMapping 사용 가능
//    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @GetMapping("/json")
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Hello h1 = new Hello("Hongildong","hongil@naver.com");
//        String value= objectMapper.writeValueAsString(h1);
//        직접 json으로 직렬화 할필요 없이, return타입을 클래스로 지정시에 자동으로 직렬화
        Hello h1 = new Hello("Hongildong", "hongil@naver.com");
        return h1;
    }

    //    case4. 사용자가 json 데이터를 요청하되, parameter 형식으로 특정객체 요청(get 요청)
//    parameter 형식: /hello/param1?name=hongildong
    @GetMapping("/param1")
    @ResponseBody
    public Hello param1(@RequestParam(value = "name") String inputName) {
        Hello h1 = new Hello(inputName, "test@naver.com");
        return h1;
    }

    //    parameter 2개 이상 형식: /hello/param1?name=hongildong&email=test@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello param2(@RequestParam(value = "name") String inputName,
                        @RequestParam(value = "email") String inputEmail) {
        Hello h1 = new Hello(inputName, inputEmail);
        return h1;
    }

    //    case5.parameter 가 많아질 경우, 데이터바인딩을 통해 input 값 처리 (get 요청)
    @GetMapping("/param3")
    @ResponseBody
//    각 파라미터의 값이 Hello 클래스의 각 변수에 mapping: new Hello(hongildong, hong@naver.com)
//    public Hello param3(Hello hello) {
    public Hello param3(@ModelAttribute Hello hello) {
        return hello;
    }

//    case6.화면을 return을 해주되, 특정 변수값을 동적으로 세팅
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name") String inputName,
                             Model model){ // Model 객체는 특정 데이터를 화면에 전달해주는 역할
//        modelName 이라는 키 값에 value 를 세팅하면 modelName 값 형태로 화면에 전달
        model.addAttribute("modelName",inputName);
        return "helloworld2";
    }

//    case7. 화면을 return해주되, 객체를 화면에 동적으로 세팅
    @GetMapping("/model-param2")
    public String modelParam2(@ModelAttribute Hello hello,
                             Model model){
        model.addAttribute("modelHello",hello);
        return "helloworld3";
    }
//    case8. pathvariable 방식을 통해 사용자로부터 값을 받아 화면 return
//    형식: /hello/model/path/1
//    예시: /author/detail/1
//    pathvariable방식은 url을 통해 자우너구조를 명확하게 표현할 때 사용.(좀 더 restful한 방식)
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName,
                            Model model){
        model.addAttribute("modelName",inputName);
        return "helloworld2";
    }
}
