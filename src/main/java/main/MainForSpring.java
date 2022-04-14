package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppCtx;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.VersionPrinter;
import spring.WrongIdPasswordException;

public class MainForSpring {
	
	private static ApplicationContext ctx = null;

	public static void main(String[] args) throws IOException {
		
		// ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		
		// AnnotationConfigApplicationContext의 생성자의 인자는 가변 인자이기 때문에
		// 설정 클래스 목록을 콤마로 구분해서 전달하면 된다.
		// ctx = new AnnotationConfigApplicationContext(AppConf01.class, AppConf02.class);
		ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			
			System.out.println("명령어를 입력하세요.");
			
			String command = reader.readLine();
			
			if(command.equalsIgnoreCase("exit")) {
				
				System.out.println("종료합니다.");
				break;
			}
			
			if(command.startsWith("new ")) {
				
				processNewCommand(command.split(" "));
				continue;
				
			}else if(command.startsWith("change ")) {
				processChangeCommand(command.split(" "));
				continue;
				
			}else if(command.equals("list")) {
				
				processListCommand();
				continue;
				
			}else if(command.startsWith("info ")) {
				
				processInfoCommand(command.split(" "));
				continue;
				
			}else if(command.equals("version")) {
				
				processVersionCommand();
				continue;
				
			}
			
			
			printHelp();
			
		}

	}
	
	private static void processNewCommand(String[] arg) {
		
		if(arg.length !=5) {
			printHelp();
			return;
		}
		
		MemberRegisterService regService = 
				ctx.getBean("memberRegService",MemberRegisterService.class);
		RegisterRequest req = new RegisterRequest();
		
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if(!req.isPasswordEqualToConfirmPassword()) {
			
			System.out.println("암호와 확인이 일치하지 않습니다!.");
			return;
			
		}
		try {
			
			regService.regist(req);
			System.out.println("등록했습니다.");
			
		}catch(DuplicateMemberException e) {
			
			System.out.println("이미 존재하는 이메일입니다.");
			
		}
	
	}
	
	private static void processChangeCommand(String[] arg) {
		
		if(arg.length !=4) {
			
			printHelp();
			return;
		}
		
		ChangePasswordService changePwdService = 
				ctx.getBean(ChangePasswordService.class);
		
		try {
			
			changePwdService.changePassword(arg[1], arg[2], arg[3]);
			
			System.out.println("암호를 변경했습니다.");
			
		}catch(MemberNotFoundException e) {
			
			System.out.println("존재하지 않는 이메일입니다.");
			
		}catch(WrongIdPasswordException e) {
			
			System.out.println("이메일과 암호가 일치하지 않습니다.");
			
		}
		
	}
	
	private static void printHelp() {
		
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new 이메일 이름 비번 비번확인");
		System.out.println("change 이메일 현재비번 변경비번");
		System.out.println("list는 모든 회원 정보 조회");
		System.out.println("info email은 해당 회원 정보 조회");
		System.out.println("version은 해당 프로그램 버전 조회");
		System.out.println();
		
	}
	
	private static void processListCommand() {
		
		MemberListPrinter memberListPrinter = 
				ctx.getBean("listPrinter", MemberListPrinter.class);
		
		// getBean() 메소드의 첫 번째 인자는 빈의 이름이고 두 번째 인자는 빈의 타입이다.
		// 존재하지 않는 빈의 이름을 사용하면 익셉션이 발생한다.
		// 빈의 실제 타입과 getBean() 메소드에 지정한 타입이 다르면 익셉션에 실제 타입을 알려준다.
		// MemberListPrinter memberListPrinter =
		// ctx.getBean(MemberListPrinter.class); 처럼 빈 이름을 지정하지 않고도 타입만으로
		// 빈을 구할 수도 있다.
		// 같은 타입의 빈 객체가 두 개 이상 존재할 때는 해당 타입의 빈이 한 개가 아니라는 것을 알려주는
		// 익셉션이 발생한다.
		
		memberListPrinter.printAll();
		
	}
	
	private static void processInfoCommand(String[] arg) {
		
		if(arg.length !=2) {
			
			printHelp();
			return;
			
		}
		
		MemberInfoPrinter memberInfoPrinter =
				ctx.getBean("memberInfoPrinter", MemberInfoPrinter.class);
		
		memberInfoPrinter.printMemberInfo(arg[1]);

	}
	
	private static void processVersionCommand() {
		
		VersionPrinter versionPrinter = 
				ctx.getBean("versionPrinter", VersionPrinter.class);
		
		versionPrinter.print();
		
	}

}
