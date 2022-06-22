package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.Teacher;
import kr.momjobgo.eyou.web.jpa.repository.TeacherRepository;
import kr.momjobgo.eyou.web.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
}
