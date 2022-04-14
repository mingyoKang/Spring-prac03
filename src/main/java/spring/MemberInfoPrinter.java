package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("infoPrinter")
public class MemberInfoPrinter {
	// 지정한 이메일을 갖는 Member를 찾아 정보를 콘솔에 출력한다
	
	private MemberDao memberDao;
	private MemberPrinter memberPrinter;
	
	public void printMemberInfo(String email) {
		
		Member member = memberDao.selectByEmail(email);
		
		if(member == null) {
			
			System.out.println("일치하는 회원 정보가 없습니다.");
			return;
			
		}
		
		memberPrinter.print(member);
		System.out.println();
	}
	
	// @Autowired 애노테이션은 메소드에도 붙일 수 있다.
	
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		
		this.memberDao = memberDao;
		
	}
	
	@Autowired
	public void setPrinter(MemberPrinter memberPrinter) {
		
		this.memberPrinter = memberPrinter;
		
	}

}
