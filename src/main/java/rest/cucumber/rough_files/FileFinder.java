package rest.cucumber.rough_files;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileFinder {

	private static final Logger LOGGER = LogManager.getLogger(FileFinder.class);

	public static void findTextInThisWorkSpace(String workSpace, String textToFind) {

		List<File> l = (List<File>) FileUtils.listFiles(new File(workSpace), new String[]{"java"}, true);
		l.parallelStream().filter(a -> {
			String allData = "";
			try {
				allData = Files.readString(a.toPath());
			} catch (IOException e) {
				System.err.println("Error Here : " + a.getAbsolutePath());
			}
			return allData.toLowerCase().contains(textToFind.toLowerCase());
		}).forEach(x -> System.out.println(x.getAbsolutePath()));
	}

	public static void main(String[] args) {

		findTextInThisWorkSpace("F:\\ABD", "cron");
	}
}
