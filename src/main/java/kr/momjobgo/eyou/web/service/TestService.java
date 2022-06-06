package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import org.aspectj.weaver.ast.Test;

import java.util.List;

public interface TestService {
    List<TestEntity> getAll();

    TestEntity getTest(Long id);

    TestEntity insertTest(String name);

    TestEntity insertTest2(TestEntity entity);

    TestEntity updateTest(TestEntity entity);
    String deleteTest(Long id);

    List<TestEntity> findByName(String name);

    List<TestEntity> findByName2(String name);

}
