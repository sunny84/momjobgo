package kr.momjobgo.eyou.web.controller;

import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.service.TestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    /**
     * Integer 숫자 8byte
     * String 글자
     * Double
     * @return
     */
    @GetMapping("/join/test")   // table join을 위한 예제
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(testService.testJoin());
    }

    @GetMapping("/test")    // url로 넘겨받은 param을 그대로 return해줌
    public String test(@RequestParam String param) {
        return param;
    }

    @GetMapping("/join/test2")
    public ResponseEntity<?> test2() {
        return ResponseEntity.ok(testService.testJoin2());
    }

    @GetMapping("/test/all")
    public ResponseEntity<?> getAll() {
       return ResponseEntity.ok().body( testService.getAll() );
    }

    @GetMapping("/test/{id}")   // id에 해당되는 name 받아오기
    public ResponseEntity<?> getTest(@PathVariable Long id) {
        return ResponseEntity.ok().body( testService.getTest(id) );
    }

    @PostMapping("/test")   // name을 가진 새 데이터 생성
    public ResponseEntity<?> postTest(@RequestParam String name){
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.insertTest(name));
    }

    @PostMapping("/test2")  // id가 존재하면 update, id가 없으면 auto_increment한 id로 생성
    public ResponseEntity<?> postTest(@RequestBody TestEntity testEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(testService.insertTest2(testEntity));
    }

    @PatchMapping("/test/{id}") // 하드코딩으로 수정할 id의 name이 "수정해봅시다"이면 아이디 변경
    public ResponseEntity<?> updateTest(@RequestBody TestEntity testEntity){
        return ResponseEntity.ok().body(testService.updateTest(testEntity));
    }

    @DeleteMapping("/test/{id}")    // id에 해당되는 데이터 삭제. 없는 id일 경유 500 에러 발생
    public ResponseEntity<?> deleteTest(@PathVariable Long id) {
        return ResponseEntity.ok().body(testService.deleteTest(id));
    }

    @GetMapping("/test/name")
    public ResponseEntity<?> getTestByName(@RequestParam String name){
        return ResponseEntity.ok().body(testService.findByName(name));
    }

    @GetMapping("/test/name2")
    public ResponseEntity<?> getTestByName2(@RequestParam String name){
        return ResponseEntity.ok().body(testService.findByName2(name));
    }

}
