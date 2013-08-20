package kevin.lib.util;

public class StringUtil {

	public static String genRandomString() {
		return null;
	}
	
	
	public static String genRandomString(int min, int max) {
		char[] list = new char[62];
		int count = 0;
		for(int i=48; i<=57; i++) {
			list[count++] = (char)i;
		}
		for(int i=65; i<=90; i++) {
			list[count++] = (char)i;
		}
		for(int i=97; i<122; i++) {
			list[count++] = (char)i;
		}
		int sub = max - min + 1;
		// TODO 长度随机有待改进
		int l = (int)(Math.random() * max * 10) % sub;
		int len = min + l - 1;
		if(len<min)
			len = min;
		String result = "";
		for(int i=0; i<len; i++) {
			int index = (int)(Math.random()*1000) % list.length;
			result += list[index];
		}
		return result;
	}
	
	public static void main(String[] args) {
		char a = 'a';
		char z = 'z';
		char A = 'A';
		char Z = 'Z';
		char n0 = '0';
		char n9 = '9';
		
		System.out.println((int)a);
		System.out.println((int)z);
		System.out.println((int)A);
		System.out.println((int)Z);
		System.out.println((int)n0);
		System.out.println((int)n9);
		
		System.out.println("来");
		System.out.println(genRandomString(1,10));
	}
}
