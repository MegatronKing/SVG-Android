import java.io.File;
import java.lang.System;
import java.lang.String;

public class Test {
	
	public static void main(String[] args) {
		
		File file = new File("/Users/android/release_apks/tongcheng_tinker_dev/base-apk/R-tinker_dev-tinker_dev-tinker_dev-00000000.txt");
		System.out.println("file exist " + file.exists());
		
	}
	
} 