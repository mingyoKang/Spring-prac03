package spring;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

// @Component 애노테이션에 값을 주지 않았으면 클래스의 이름의 첫글자를 소문자로 바꾼 이름을 빈 이름으로 사용한다.
// @Component 애노테이션에 값을 주면 그 값을 빈으로 사용한다.
@Component
public class MemberDao {
	// 메모리에 회원 데이터를 보관하므로 프로그램을 종료하면 저장한 모든 회원 데이터가 사라진다.
	// 프로그램을 종료해도 회원 데이터를 유지하려면 MySQL, 오라클과 같은 저장소에 보관해야 한다.
	
	private static long nextId = 0;
	
	private Map<String, Member> map = new HashMap<>();
	
	public Member selectByEmail(String email) {
		
		return map.get(email);
		
	}
	
	public void insert(Member member) {
		
		member.setId(++nextId);
		map.put(member.getEmail(), member);
		
	}
	
	public void update(Member member) {
		
		map.put(member.getEmail(), member);
		
	}
	
	public Collection<Member> selectAll(){
		
		return map.values();
		
	}

}
