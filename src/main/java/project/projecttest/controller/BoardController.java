package project.projecttest.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.projecttest.domain.Board;
import project.projecttest.domain.BoardComment;
import project.projecttest.domain.Member;
import project.projecttest.dto.BoardCommentDto;
import project.projecttest.dto.BoardDto;
import project.projecttest.repository.BoardCommentRepository;
import project.projecttest.repository.BoardRepository;
import project.projecttest.repository.MemberRepository;
import project.projecttest.service.BoardCommentService;
import project.projecttest.service.BoardService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentService boardCommentService;

    @GetMapping("/boardForm")
    public String addBoard() {
        return "/board/boardForm";
    }

    //
    @PostMapping("/boardForm")
    public String createBoard(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        boardDto.setCreatedBy(username);
        boardService.saveBoard(boardDto);

        return "redirect:/board/boardList";
    }


    @GetMapping("/boardList")
    public String boardList(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);


        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/boardList";
    }

    @GetMapping("/boardContent/{id}")
    public String boardContent(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.findById(id).get();
        List<BoardComment> comments = boardCommentRepository.findByBoardId(id);
        System.out.println("board = " + board);
        model.addAttribute(board);
        model.addAttribute("comments", comments);
        return "/board/boardContent";
    }
    @PostMapping("/boardContent/{id}")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute BoardCommentDto boardCommentDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Board board = boardRepository.findById(id).get();

        Member member = memberRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();



        BoardCommentDto boardCommentDtoSet = BoardCommentDto.builder()
                .created_by(username)
                .content(boardCommentDto.getContent())
                .created_date(now)
                .delete_check('N')
                .member(member)
                .board(board)
                .build();
        boardCommentDto = boardCommentDtoSet;


        boardCommentService.saveBoardComment(boardCommentDto);

        List<BoardComment> comments = boardCommentRepository.findByBoardId(id);

        model.addAttribute("comments", comments);
        model.addAttribute(board);
        return "/board/boardContent";
    }
}
