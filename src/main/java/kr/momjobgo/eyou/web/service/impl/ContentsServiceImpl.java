package kr.momjobgo.eyou.web.service.impl;

import kr.momjobgo.eyou.web.jpa.entity.ContentsEntity;
import kr.momjobgo.eyou.web.jpa.repository.ContentsRepository;
import kr.momjobgo.eyou.web.service.ContentsService;
import org.springframework.stereotype.Service;

@Service
public class ContentsServiceImpl implements ContentsService {
    private final ContentsRepository contentsRepository;

    public ContentsServiceImpl(ContentsRepository contentsRepository) {
        this.contentsRepository = contentsRepository;
    }

    @Override
    public ContentsEntity insertContent(ContentsEntity entity) {
        return contentsRepository.save(entity);
    }
}
