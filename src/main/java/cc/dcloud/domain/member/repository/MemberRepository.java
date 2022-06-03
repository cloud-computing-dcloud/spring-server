package cc.dcloud.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cc.dcloud.domain.member.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

	@Query("select m from Member m join fetch m.authorities a where m.username = :username")
	Optional<Member> findByUsernameWithAuthority(@Param("username") String username);

	Optional<Member> findByUsername(String username);
}
