package project.projecttest.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "board_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardComment {
    @Id
    @GeneratedValue
    @Column(name = "board_comment_id")
    private Long id;
    private String content;
    private LocalDateTime created_date;
    private String created_by;
    private Character delete_check;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public BoardComment(LocalDateTime created_date, String created_by, String content, Character delete_check, Board board, Member member) {
        this.content = content;
        this.created_date = created_date;
        this.created_by = created_by;
        this.delete_check = delete_check;
        if(this.board != null){
            board.getBoardCommentList().remove(this);
        }else
            this.board = board;
        if(this.member != null){
            member.getBoardCommentList().remove(this);
        }else
            this.member = member;
    }

}
