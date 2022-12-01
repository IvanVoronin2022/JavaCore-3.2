import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        GameProgress value1 = new GameProgress(10, 10, 10, 0.5);
        GameProgress value2 = new GameProgress(20, 20, 20, 0.7);
        GameProgress value3 = new GameProgress(40, 40, 40, 0.9);
        ArrayList<String> saves = new ArrayList<>();
        saves.add(saveGame("C:\\Users\\Lenovo\\IdeaProjects\\JavaCore_1.3.2\\Games\\savegames\\save1.dat", value1));
        saves.add(saveGame("C:\\Users\\Lenovo\\IdeaProjects\\JavaCore_1.3.2\\Games\\savegames\\save2.dat", value2));
        saves.add(saveGame("C:\\Users\\Lenovo\\IdeaProjects\\JavaCore_1.3.2\\Games\\savegames\\save3.dat", value3));
        zipFiles("C:\\Users\\Lenovo\\IdeaProjects\\JavaCore_1.3.2\\Games\\savegames\\zip_output.zip", saves);
        deleteSaves(saves);
    }

    public static String saveGame(String string, GameProgress gameProgress) throws IOException {
        File file = new File(string);
        try {
            file.createNewFile();
        } catch (IIOException ex) {
            System.out.println(ex.getMessage());
        }

        try (FileOutputStream fos = new FileOutputStream(string);
             ObjectOutputStream cos = new ObjectOutputStream(fos)) {
            cos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return string;
    }

    public static void deleteSaves(ArrayList list) {
        for (int i = 0; i < list.size(); i++) {
            File file = new File((String) list.get(i));
            file.delete();
        }
    }

    public static void zipFiles(String string, ArrayList list) throws IOException {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(string))) {
            for (int i = 0; i < list.size(); i++) {
                String filename = (String) list.get(i);
                try (FileInputStream fis = new FileInputStream(filename)) {
                    ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}