import java.util.List;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baizhi.lg.entity.User;
import com.baizhi.lg.service.UserService;


public class UserTest {
	@Test
	public void getALlUser(){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		UserService us=(UserService) context.getBean("userService");
		List<User> wl = us.getAllUser();
		for (User user : wl) {
			System.out.println(user);
		}
	}
}
