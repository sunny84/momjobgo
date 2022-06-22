package kr.momjobgo.eyou.web.service;

import kr.momjobgo.eyou.web.jpa.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
}
