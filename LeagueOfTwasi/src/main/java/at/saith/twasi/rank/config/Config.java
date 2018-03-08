package at.saith.twasi.rank.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import at.saith.twasi.rank.RankPlugin;
import at.saith.twasi.rank.json.SimpleJSONObject;

public final class Config {

	private static final String FILESEPARATOR = File.separator;
	private static final String NEWLINE = "\r\n";
	private static final String FILEPATH = "config.json";
	private static final String CONFIGFOLDER = "plugins/"+RankPlugin.getInstance().getName();
	private static final File CONFIGFILE = new File(CONFIGFOLDER+FILESEPARATOR+FILEPATH);
	private static SimpleJSONObject config;
	static {
		Path folder = Paths.get(CONFIGFOLDER);
		if(!Files.exists(folder)) {
			createConfigFolder(folder);
		}
		Path file = Paths.get(CONFIGFOLDER+FILESEPARATOR+FILEPATH);
		if(!Files.exists(file)) {
				createConfig(file);
		}else {
			Path configPath = Paths.get(CONFIGFOLDER+FILESEPARATOR+FILEPATH);
			readConfig(configPath);
		}
	}

	public static Object get(String key) {
		return config.get(key);
	}
	
	

	private static void createConfig(Path file) {
		try {
			Files.createFile(file);
			System.out.println("test");
			String configString = readResourceConfig();
			System.out.println("test");
			writeConfig(configString);
			System.out.println("test");
			config = new SimpleJSONObject(configString);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static String readResourceConfig() {
		InputStream in = Config.class.getResourceAsStream("/config.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String configString = "";
		String line = "";
		try {
			
			while((line = br.readLine()) != null) {
				configString+=line+NEWLINE;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return configString;
	}
	private static void writeConfig(String text) {
		try {
			FileWriter writer = new FileWriter(Config.CONFIGFILE);
		writer.write(text);
		writer.flush();
		writer.close();
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
	}
	private static void createConfigFolder(Path folder) {
		try {
			Files.createDirectory(folder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void readConfig(Path configPath) {
		try {
		BufferedReader r = Files.newBufferedReader(configPath);
		String line = "";
		String conf = "";
		while((line = r.readLine()) != null) {
			conf+=line+"\n";
		}
			
			config = new SimpleJSONObject(conf);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
