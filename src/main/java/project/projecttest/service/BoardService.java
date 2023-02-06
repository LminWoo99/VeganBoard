package project.projecttest.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.projecttest.domain.Board;
import project.projecttest.dto.BoardDto;
import project.projecttest.repository.BoardRepository;
import project.projecttest.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long saveBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
        return boardDto.getId();
    }
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Page<Board> getBoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

    public Page<Board> paging(int page) {
        return boardRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

    }

//    @Transactional
//    public void updateVisit(Long id, BoardDto boardDto) {
//        Board board = boardRepository.findById(id).orElseThrow((() ->
//                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));
//    }

    public Board findById(Long id){
        Board board = boardRepository.findById(id).get();
        return board;
    }

//    @Transactional
//    public Long BeforeCountVisitLogic(Long id) {
//
//        Board board = boardRepository.findById(id).orElseThrow((() ->
//                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));
//
//        board.updateVisit(countVisitStore.get());
//        sleep(100);
//
//    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

