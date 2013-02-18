package gui;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

//import testing.TestingTools;
import openreg.Openreg;

public class SWTTools {
	public static void initSWT() {
		try {
			String o = System.getProperty("os.name").toLowerCase();

			String osPart = "unknown";
			if (o.contains("win"))
				osPart = "-win32-win32";
			if (o.contains("mac"))
				osPart = "-cocoa-macosx";
			if (o.contains("linux") || o.contains("nix"))
				osPart = "-gtk-linux";

			String a = System.getProperty("os.arch").toLowerCase();

			String architecturePart = "unknown";
			if (a.contains("64"))
				architecturePart = "-x86_64";
			else if (!o.contains("mac"))
				architecturePart = "-x86";

//			TestingTools.inform("Loading swt-3.7.1" + osPart + architecturePart + ".jar...");

			URL swtFileUrl = new URL("rsrc:" + "swt-4.2.1" + osPart + architecturePart + ".jar");
			URLClassLoader c = (URLClassLoader) Openreg.class.getClassLoader();
			Method m = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			m.setAccessible(true);
			m.invoke(c, swtFileUrl);
		} catch (MalformedURLException e) {
			// This fails only within Eclipse, not when executed with java -jar
			// ..., because it adds a jar in a jar to the classpath.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
