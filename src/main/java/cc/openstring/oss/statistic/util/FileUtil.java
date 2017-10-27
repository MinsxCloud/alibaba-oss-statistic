package cc.openstring.oss.statistic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * File operate util
 *
 * @Author Joker
 * @Date 2017年2月22日
 */
public class FileUtil {

	/**
	 * Replace text file to make string
	 *
	 * @param file
	 *            Files that need to be manipulated
	 * @param oldString
	 *            Old string
	 * @param newString
	 *            New String
	 * @throws IOException
	 */
	public static void replace(File file, String oldString, String newString) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
		File outfile = new File(file + ".tmp");
		PrintWriter out = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), "utf-8")));
		String reading;
		while ((reading = in.readLine()) != null) {
			out.println(reading.replaceAll(oldString, newString));
		}
		out.close();
		in.close();
		file.delete();
		outfile.renameTo(file);
	}

	/**
	 * Read file content
	 *
	 * @param file
	 *            Files that need to be manipulated
	 * @return The content of the file read
	 */
	public static String getFileContent(File file) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * Create files: automatically create folders and files without the specified
	 * path
	 *
	 * @param filePath
	 *            Folder path
	 * @return Upload success
	 */
	public static boolean createFile(String filePath) {
		boolean isSuccess = false;
		try {
			String path = filePath.substring(0, filePath.lastIndexOf("/"));
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
			File file = new File(f, fileName);
			if (!file.exists())
				file.createNewFile();
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * Copy all the files and folders in a directory to another directory note: this method is only suitable for Windows systems, copying in CMD mode
	 *
	 * @param pathA
	 *            Original catalogue
	 * @param pathB
	 *            Target directory
	 */
	public static void copyAllFilesAndContentToAnother(String pathA, String pathB) {
		try {
			String lastStrA = pathA.substring(pathA.length() - 1, pathA.length());
			if (lastStrA.equalsIgnoreCase("\\") || lastStrA.equalsIgnoreCase("/")) {
				pathA = pathA.substring(0, pathA.length() - 1);
			}
			String lastStrB = pathB.substring(pathB.length() - 1, pathB.length());
			if (lastStrB.equalsIgnoreCase("\\") || lastStrB.equalsIgnoreCase("/")) {
				pathB = pathB.substring(0, pathB.length() - 1);
			}
			String cmd = "cmd /c echo d|xcopy /s /e \"" + pathA + "\"  \"" + pathB + "\" /y ";
			System.out.println(cmd);
			Process getSyncInfoProcess = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(getSyncInfoProcess.getInputStream()));
			while ((in.readLine()) != null) {
			} 
			in.close();
			getSyncInfoProcess.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Specify a file suffix to fetch a file
	 *
	 * @param fileDir
	 *            File path
	 * @param fileType
	 *            file type
	 * @return
	 */
	public static List<File> getFiles(File fileDir, String fileType) {
		List<File> lfile = new ArrayList<File>();
		if (!fileDir.exists()) {
			return lfile;
		}
		File[] fs = fileDir.listFiles();
		for (File f : fs) {
			if (f.isFile()) {
				if (fileType.equals("*")) {
					lfile.add(f);
				} else if (fileType.equalsIgnoreCase(
						f.getName().substring(f.getName().lastIndexOf(".") + 1, f.getName().length()))) {
					lfile.add(f);
				}
			} else {
				List<File> ftemps = getFiles(f, fileType);
				lfile.addAll(ftemps);
			}
		}
		return lfile;
	}

	/**
	 * Writes specified content to a specified file
	 *
	 * @param filePath
	 *           File path
	 * @param content
	 *            Content to write
	 * @throws IOException
	 */
	public static void writeToFile(String filePath, String content) throws IOException {
		BufferedWriter bufferWritter = null;
		FileWriter fileWritter = null;
		File file = new File(filePath);
		// if folder doesn't exists, then create it
		if (!file.exists()) {
			String path = file.getAbsolutePath();
			File folder = new File(path.substring(0, path.lastIndexOf("\\")));
			if (!folder.exists()) {
				folder.mkdirs();
			}
		}
		// if file doesn't exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		// true = append file
		fileWritter = new FileWriter(filePath, false);
		bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(content);
		bufferWritter.close();
		fileWritter.close();
	}

	/**
	 * Gets all the directory names in the specified directory
	 *
	 * @param fileDir
	 *            Specified directory
	 * @return All directory names
	 */
	public static List<String> getDirectoryNames(File fileDir) {
		List<String> directoryNames = new ArrayList<>();
		File[] fs = fileDir.listFiles();
		if (fs == null) {
			return directoryNames;
		}
		for (File f : fs) {
			if (f.isDirectory()) {
				directoryNames.add(f.getName());
			}
		}
		return directoryNames;
	}

	/**
	 * Delete specified file
	 *
	 * @param path
	 *            File path
	 * @return Delete successfully
	 */
	public static boolean deleteFileByPath(String path) {
		File file = new File(path);
		if (!file.exists()) { 
			return false;
		} else {
			return file.delete();
		}
	}

	/**
	 * Take all the files in the specified directory (except directories)
	 *
	 * @param dir
	 *           Specified directory
	 * @return All files
	 */
	public static List<File> getFileList(File dir) {
		List<File> filelist = new ArrayList<>();
		File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					filelist.add(files[i]);
				}
			}
		}
		return filelist;
	}

	/**
	 * Is it an empty directory
	 * @param directory File directory
	 * @return Is it an empty directory
	 */
	public static boolean isEmptyDirectory(File directory) {
		boolean isEmpty = false;
		if (directory != null && directory.exists() && directory.isDirectory()) {
			File[] files = directory.listFiles();
			if (files != null && files.length == 0) {
				isEmpty = true;
			}
		}
		return isEmpty;
	}

}
