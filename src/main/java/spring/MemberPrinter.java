package spring;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class MemberPrinter {
	private DateTimeFormatter dateTimeFormatter;
	
	public void print(Member member) {
		// dateTimeFormatter 필드가 null이면 날짜 형식을 %tF로 출력하고
		// 이 필드가 null이 아니면 dateTimeFormatter를 이용해서 날짜 형식을 맞춰 출력하도록 했다.
		// 세터 메소드는 @Autowired 애노테이션을 이용해서 자동 주입하도록 설정했다.
		
		// print() 메소드는 dateTimeFormatter가 null인 경우에도 알맞게 동작한다.
		// 즉, 반드시 setDateFormatter()를 통해서 의존 객체를 주입할 필요는 없다.
		
		// 그런데 @Autowired 애노테이션은 기본적으로 해당 애노테이션을 붙인 타입에 해당하는 빈이 존재하지 않으면
		// 익셉션을 발생시킨다.
		// 따라서 이를 막기 위해선 자동 주입할 대상이 필수가 아닌 경우에 @Autowired 애노테이션의
		// required 속성을 false로 지정하면 된다.
		
		if(dateTimeFormatter == null) {
			
			System.out.printf(
					"회원 정보: 아이디 - %d, 이메일 - %s, 이름 - %s, 등록일 - %tF\n",
					member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime());			
			
		}else {
			
			System.out.printf("회원 정보: 아이디 - %d, 이메일 - %s, 이름 - %s, 등록일 = %s\n",
					member.getId(), member.getEmail(),
					member.getName(),
					dateTimeFormatter.format(member.getRegisterDateTime()));
		}

		
	}
	
	@Autowired(required = false)
	// @Autowired 애노테이션의 required 속성을 false로 지정하면 매칭되는 빈이 없어도
	// 익셉션이 발생하지 않으며 자동 주입을 수행하지 않는다.
	// 일치하는 빈이 존재하지 않을 때 자동 주입 대상이 되는 필드나 메소드에 null을 전달하지 않는다. 
	// (<->@Nuallble)
	// 이런 필수여부를 지정하는 방법에는 세가지가 있다.
	// 스프링 5버전부터는 자바 8의 Optional을 사용할 수도 있고
	// @Nullable 애노테이션을 사용할 수도 있다.
	public void setDateFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	// @Autowired
	// public void setDateFormatter(Optional<DateTimeFormatter> formatterOpt) {
		// 자동 주입 대상이 Optional인 경우, 일치하는 빈이 존재하지 않으면 값이 없는
		// Optional을 인자로 전달하고(익셉션이 발생하지 않는다), 일치하는 빈이 존재하면 해당 빈을 
		// 값으로 갖는 Optional을 인자로 전달한다.
		// Optional을 사용하는 코드는 값 존재 여부에 따라 알맞게 의존 객체를 사용하면 된다.
		// Optional#isPresent() 메소드가 true이면 값이 존재하므로 해당 값을 dateTimeFormatter 필드에
		// 할당한다. 즉 DateTimeFormatter 타입 빈을 주입 받아 dateTimteFormatter 필드에 할당한다.
		// 값이 존재하지 않으면 주입 받은 빈 객체가 없으므로 dateTimeFormatter 필드에 null을 할당한다.
		
		// if(formatterOpt.isPresent()) {
		//	this.dateTimeFormatter = formatterOpt.get();
		// }else {
		//	this.dateTimeFormatter = null;
		// }
		
	// }
	
	//@Autowired
	//public void setDateFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		// @Autowired 애노테이션을 붙인 세터 메소드에서 @Nullable 애노테이션을
		// 의존 주입 대상 파라미터에 붙이면, 스프링 컨테이너는 세터 메소드를 호출할 때 자동 주입할 빈이
		// 존재하면 해당 빈을 인자로 전달하고, 존재하지 않으면 인자로 null을 전달한다. (<-> required=false)
		//this.dateTimeFormatter = dateTimeFormatter;
		
	//}
	
	public MemberPrinter() {
		dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
	}
	
}
