package qj.blog.classreloading.example3;

import static qj.util.ReflectUtil.*;
import qj.util.ThreadUtil;
import qj.util.lang.DynamicClassLoader;

/**
 * Created by Quan on 31/10/2014.
 */
public class ContextReloading {
	public static void main(String[] args) {
		for (;;) {
			Object context = createContext();
			invokeHobbyService(context);
			ThreadUtil.sleep(2000);
		}
	}

	private static Object createContext() {
		Class<?> contextClass = new DynamicClassLoader("target/classes")
			.load("qj.blog.classreloading.example3.ContextReloading$Context");
		Object context = newInstance(contextClass);
		invoke("init", context);
		return context;
	}

	private static void invokeHobbyService(Object context) {
		Object hobbyService = getFieldValue("hobbyService", context);
		invoke("hobby", hobbyService);
	}

	@SuppressWarnings("UnusedDeclaration")
	public static class Context {
		public HobbyService hobbyService = new HobbyService();
		
		public void init() {
			// Init your services here
			hobbyService.user = new User();
		}
	}
	
	public static class HobbyService {

		public User user;
		
		public void hobby() {
			user.hobby();
		}
	}
	
	public static class User {
		public static void hobby() {
//			playFootball(); // Will comment during runtime
			playBasketball(); // Will uncomment during runtime
		}
		
		// Will comment during runtime
//		public static void playFootball() {
//			System.out.println("Play Football");
//		}
		
//		Will uncomment during runtime
		public static void playBasketball() {
			System.out.println("Play Basketball");
		}
	}
}
