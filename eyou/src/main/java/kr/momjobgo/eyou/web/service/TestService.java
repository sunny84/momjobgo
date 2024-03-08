package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.jpa.entity.TestJoinEntity;
import org.aspectj.weaver.ast.Test;

import java.util.List;

public interface TestService {

    List<TestEntity> testJoin();

    List<TestJoinEntity> testJoin2();

    List<TestEntity> getAll();

    TestEntity getTest(Long id);

    TestEntity insertTest(String name);

    TestEntity insertTest2(TestEntity entity);

    TestEntity updateTest(TestEntity entity);
    String deleteTest(Long id);

    List<TestEntity> findByName(String name);

    List<TestEntity> findByName2(String name);

}
