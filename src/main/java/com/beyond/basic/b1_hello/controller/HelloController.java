package com.beyond.basic.b1_hello.controller;

import com.beyond.basic.b1_hello.domain.Hello;
import com.beyond.basic.b1_hello.domain.StrudentReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
//    pathvariable방식은 url을 통해 자원구조를 명확하게 표현할 때 사용.(좀 더 restful한 방식)
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName,
                            Model model){
        model.addAttribute("modelName",inputName);
        return "helloworld2";
    }

    @GetMapping("/form-view")
//    사용자에게 name, email을 입력할 수 있는 화면을 주는 메서드 정의
    public String formView(){
        return "form-view";
    }

//    case1. form 데이터 형식의 post 요청 처리(텍스트만 있는 application/x-www ~)
//    형식: ?name=xxx&email=yyy 데이터가 body 에 들어옴
    @PostMapping("/form-view")
    @ResponseBody
    public String formPost1(@ModelAttribute Hello hello){
        System.out.println(hello);
        return "ok";
    }

//    case2: form 데이터 형식의 post 요청 처리(file + text 형식)
    @GetMapping("form-file-view")
    public String formFileGet(){
        return "form-file-view";
    }

    @ResponseBody
    @PostMapping("form-file-view")
    public String formFilePost(Hello hello, @RequestParam(value = "photo")MultipartFile photo){
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

//    case3. js를 통한 활용한 form데이터 전송(text만)
    @GetMapping("/axios-form-view")
    public String getFormText(){
        return "axios-form-view";
    }

    @ResponseBody
    @PostMapping("/axios-form-view")
//    js를 통한 form형식도 마찬가지로 ?name=xxx&email=yyy
    public String postFormText(Hello hello){
        System.out.println(hello);
        return "ok";
    }

//    case4. js를 활용한 form데이터 전송(text+file)
    @GetMapping("/axios-form-file-view")
    public String getFormFile(){
        return "axios-form-file-view";
    }

    @ResponseBody
    @PostMapping("/axios-form-file-view")
    public String postFormFile(Hello hello, @RequestParam(value = "photo")MultipartFile photo){
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }

//    case5. js를 활용한 form데이터 전송(text+멀티 file)
    @GetMapping("/axios-form-multi-file-view")
    public String multiGetFormFile(){
        return "axios-form-file-view";
    }

    @ResponseBody
    @PostMapping("/axios-form-multi-file-view")
    public String multiPostFormFile(Hello hello, @RequestParam(value = "photos") List<MultipartFile> photos){
        System.out.println(hello);
        for (int i =0; i< photos.size();i++){
            System.out.println(photos.get(i).getOriginalFilename());
        }
        return "ok";
    }

//    case6.js를 활용한 json 데이터 전송
//    형식: {name:"hongildong, email:"hongildong@naver.com"}
    @GetMapping("axios-json-view")
    public String axiosView(){
        return "axios-json-view";
    }

    @PostMapping("axios-json-view")
    @ResponseBody
    public String axiosJsonViewPost(@RequestBody Hello hello){
        System.out.println(hello);
        return "OK";
    }

//    case7. 중첩된 JSON 데이터처리
//    예시 데이터 (Student 객체) {name: "hongildong", email:"hong@naver.com", scores:[{math:60}, {music:70}, {english:60}]}
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonViewPost(@RequestBody StrudentReqDto strudentReqDto){
        System.out.println(strudentReqDto.getName());
        System.out.println(strudentReqDto.getEmail());
        System.out.println();
        return "OK";
    }

//    case8. json과 file처리
//    file처리는 기본적으로 form형식을 통해 처리
//    그래서, json과 file을 동시에 처리하려면 form형식안에 json과 파일을 넣어 처리
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(){
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view")
    @ResponseBody
    public String axiosJsonFileViewPost(){

        return "ok";
    }

}
