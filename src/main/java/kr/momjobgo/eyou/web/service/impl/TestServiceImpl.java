package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.TestEntity;
import kr.momjobgo.eyou.web.jpa.repository.TestRepository;
import kr.momjobgo.eyou.web.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    // 모두 가져오기.
    @Override
    public List<TestEntity> getAll() {
        return testRepository.findAll();
    }

    @Override
    public TestEntity getTest(Long id) {

        Optional<TestEntity> testEntity = testRepository.findById(id);

        if(testEntity.isPresent()){
            return testEntity.get();
        } else {
            return null;
        }

    }

    @Override
    public TestEntity insertTest(String name) {

        TestEntity testEntity = new TestEntity();
        testEntity.setName(name);

        return testRepository.save(testEntity);
    }

    @Override
    public TestEntity insertTest2(TestEntity entity) {
        return testRepository.save(entity);
    }

    @Override
    public TestEntity updateTest(TestEntity entity) {

        TestEntity result = null;

        Optional<TestEntity> testEntity = testRepository.findById(entity.getId());

        if(testEntity.isPresent()){
            if(testEntity.get().getName().equals("오준혁")){
                result = testRepository.save(entity);
            }
        }
        return result;
    }

    @Override
    public String deleteTest(Long id) {
        testRepository.deleteById(id);
        return "삭제성공";
    }

    @Override
    public List<TestEntity> findByName(String name) {
        return testRepository.findByName(name);
    }

    @Override
    public List<TestEntity> findByName2(String name) {
        return testRepository.findByNameContains(name);
    }


}
