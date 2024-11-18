package com.petposting.springbootmypet.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller // 컨트롤러 명시적으로 표시
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    public String thymeleadExample(Model model) { // 뷰로 데이터를 넘겨주는 model 객체
        Person examplePerson = new Person();
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동", "독서"));

        model.addAttribute("person", examplePerson); // Person 객체 person이라는 키에 저장(사람 관련 정보)
        model.addAttribute("today", LocalDate.now()); // 날짜 정보를 today라는 키에 저장

        return "example"; // example.html라는 뷰 조회
        // 스프링 부트는 @Controller 애너테이션으로 반환값의 이름으로 된 뷰의 파일(html) 찾음.
        // resources/templates 디렉터리에서 examlple.html 파일 내용을 웹 브라우저에서 보여줌.
    }

    @Setter
    @Getter
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }
}
