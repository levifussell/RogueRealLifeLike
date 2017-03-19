import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        //Reading level from text file

        GridWorld.LOAD();
        LoadLevelFromFile("src/TestLevels/Test1.txt");
//        GridWorld.LOAD_LEVEL(3, 3, "....H....");
        GridWorld.DEBUG_DRAW();
    }

    public static void LoadLevelFromFile(String fileName)
    {
        int levelWidth = 0;
        int levelHeight = 0;
        String levelData = "";

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            int lineCount = 0;
            String line;
            while((line = br.readLine()) != null)
            {
                if(lineCount == 0)
                {
                    levelWidth = Integer.parseInt(line);
                }
                else if(lineCount == 1)
                {
                    levelHeight = Integer.parseInt(line);
                }
                else
                {
                    levelData += line;
                }

                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        GridWorld.LOAD_LEVEL(levelWidth, levelHeight, levelData);
    }
}
