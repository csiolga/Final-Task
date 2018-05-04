package file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FileRead {
    private static final String FILE_PATH = "src/test/resources/credentials.txt";

    public static Object[][] getCredentials() throws IOException {
        Map<String, String> map = new HashMap<String,String>();

        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(reader);

        FileInputStream input= new FileInputStream(FILE_PATH);
        BufferedReader read= new BufferedReader(new InputStreamReader(input));
        String line = read.readLine();

        while(line !=null) {
            String[] split = line.split(" ");
            map.put(split[0], split[1]);
            line = read.readLine();
        }

        Object[][] data = new Object[map.size()][2];
        int index = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }

        return data;
    }
}
