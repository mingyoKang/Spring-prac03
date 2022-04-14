package spring;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("listPrinter")
public class MemberListPrinter {
	
	private MemberDao memberDao;
	private MemberPrinter memberPrinter;
	
	// 자동 주입을 하려면 해당 타입을 가진 빈이 어떤 빈인지 정확하게 한정할 수 있어야한다.
	public MemberListPrinter() {
		// 인자없는 기본 생성자 추가
	}
	
	public MemberListPrinter(MemberDao memberDao, MemberPrinter memberPrinter) {
		
		this.memberDao = memberDao;
		this.memberPrinter = memberPrinter;
		
	}
	
	public void printAll() {
		
		Collection<Member> members = memberDao.selectAll();
		members.forEach(m -> memberPrinter.print(m));
		
	}
	
	// 세터 메소드를 추가하고 세터 메소드에 @Autowired 애노테이션 추가

	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Autowired
	// @Qualifier("printer")
	public void setMemberPrinter(MemberPrinter memberPrinter) {
		this.memberPrinter = memberPrinter;
	}

}
