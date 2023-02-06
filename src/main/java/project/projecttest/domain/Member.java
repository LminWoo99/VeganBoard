package project.projecttest.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
public class Member extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

        @NotBlank(message = "아이디를 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "아이디를 3~12자로 입력해주세요. [특수문자 X]")
    private String username;
        @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "비밀번호를 3~12자로 입력해주세요.")
    private String password;
    private String email;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();
//    @Enumerated(EnumType.STRING)
//    private Grade grade;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BoardNews> boardNewsList = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardCommentList=new ArrayList<>();;

    @Builder
    public Member(String username, String password, String email, List<Board> boardList, List<BoardComment> boardCommentList) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.boardList = boardList;
        this.boardCommentList = boardCommentList;
//        this.grade = grade;
    }

    protected Member() {}


}