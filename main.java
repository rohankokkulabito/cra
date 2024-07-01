import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManager {
    public static void main(String[] args) {
        String fileName = "test.txt";
        String newFileName = "new_test.txt";
        String data = "Hello, Bito!";

        // Create file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
            writer.flush(); // Bug 1: Unnecessary flush call
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return;
        }
        System.out.println("File created successfully");

        // Read file
        try {
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            System.out.println("File read successfully: " + content);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Append to file
        try (FileWriter writer = new FileWriter(fileName)) { // Bug 2: File opened in overwrite mode instead of append mode
            writer.write("\nAppended text.");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
            return;
        }
        System.out.println("Appended to file successfully");

        // Rename file
        File file = new File(fileName);
        File newFile = new File(newFileName);
        if (!file.renameTo(newFile)) {
            System.out.println("Error renaming file");
            return;
        }
        System.out.println("File renamed successfully");

        // Delete file
        if (!file.delete()) { // Bug 3: Trying to delete the original file object instead of the renamed one
            System.out.println("Error deleting file");
            return;
        }
        System.out.println("File deleted successfully");

        // Create directory (incorrect API usage)
        File directory = new File("testDir");
        if (!directory.mkdir()) { // Bug 4: Should use mkdirs() to create parent directories if they do not exist
            System.out.println("Error creating directory");
            return;
        }
        System.out.println("Directory created successfully");

        // Write to file with NIO (incorrect option)
        try {
            Files.write(Paths.get(fileName), data.getBytes(), StandardOpenOption.CREATE_NEW); // Bug 5: CREATE_NEW will fail if the file already exists
        } catch (IOException e) {
            System.out.println("Error writing to file with NIO: " + e.getMessage());
        }
    }
}
