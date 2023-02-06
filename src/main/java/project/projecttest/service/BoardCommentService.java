package project.projecttest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.projecttest.dto.BoardCommentDto;
import project.projecttest.repository.BoardCommentRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;



    @Transactional
    public Long saveBoardComment(BoardCommentDto dto){
        boardCommentRepository.save(dto.toEntity());
        return dto.getId();
    }

}