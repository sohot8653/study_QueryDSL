package com.qsl.qsl_tutorial;

import com.qsl.qsl_tutorial.boundedContext.user.entity.SiteUser;
import com.qsl.qsl_tutorial.boundedContext.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class QslTutorialApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원 생성")
	void t1() {
		// {noop} : 비밀번호를 암호화하지 않고 그대로 사용
		SiteUser u1 = SiteUser.builder()
						.username("user3")
						.password("{noop}1234")
						.email("user3@test.com")
						.build();
		SiteUser u2 = new SiteUser(null, "user4", "{noop}1234", "user4@test.com");

		userRepository.saveAll(Arrays.asList(u1, u2));

	}

	@Test
	@DisplayName("1번 회원을 Qsl로 가져오기")
	void t2() {
		SiteUser u1 = userRepository.getQslUser(1L);

		assertThat(u1.getId()).isEqualTo(1L);
		assertThat(u1.getUsername()).isEqualTo("user1");
	}

	@Test
	@DisplayName("모든 회원 수")
	void t4() {
		long count = userRepository.getQslCount();

		assertThat(count).isGreaterThan(0);
	}

	@Test
	@DisplayName("가장 오래된 회원")
	void t5() {
		SiteUser u1 = userRepository.getQslUserOrderByIdAscOne();

		assertThat(u1.getId()).isEqualTo(1L);
	}

	@Test
	@DisplayName("가장 오래된 회원")
	void t6() {
		List<SiteUser> userList = userRepository.getQslUserOrderByIdAsc();

		assertThat(userList.get(0).getId()).isEqualTo(1L);
	}

    @Test
    @DisplayName("검색, List 리턴, 검색 대상 : username, email")
    void t7() {
        List<SiteUser> users = userRepository.searchQsl("user1");

        assertThat(users.size()).isEqualTo(1);

        List<SiteUser> users2 = userRepository.searchQsl("user4@test.com");

        assertThat(users2.size()).isEqualTo(1);
    }

	@Test
	@DisplayName("검색, Page 리턴")
	void t8() {
		long totalCount = userRepository.count();
		int pageSize = 1;
		int totalPages = (int)Math.ceil(totalCount / (double)pageSize);
		int page = 1;	// 2페이지
		String text = "user";

		int itemInAPage= 1;
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.asc("id"));
		Pageable pageable = PageRequest.of(page, itemInAPage, Sort.by(sorts));
		Page<SiteUser> usersPage = userRepository.searchQsl(text, pageable);

		assertThat(usersPage.getTotalPages()).isEqualTo(totalPages);
		assertThat(usersPage.getNumber()).isEqualTo(page);
		assertThat(usersPage.getSize()).isEqualTo(pageSize);
	}
}
