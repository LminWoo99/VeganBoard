package project.projecttest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.projecttest.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
    List<Member> findAll();
}
