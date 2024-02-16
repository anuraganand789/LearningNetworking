import java.io.*;
import java.net.Socket;

public class DictionaryClient {
    public static void main(String[] args) throws IOException {
        final String[] words = {"gold", "uranium", "silver", "copper", "lead"};

        final Socket socket = new Socket("dict.org", 2628);
        socket.setSoTimeout(15000);

        final OutputStream outputStream = socket.getOutputStream();
        final Writer writer = new OutputStreamWriter(outputStream);
        final BufferedWriter bufferedWriter = new BufferedWriter(writer);

        final InputStream inputStream = socket.getInputStream();
        final Reader reader = new InputStreamReader(inputStream, "UTF-8");
        final BufferedReader bufferedReader = new BufferedReader(reader);

        for(final String word : words) {
            System.out.println("Search word " + word);
            writer.write("DEFINE fd-eng-lat " + word + "\r\n");
//            writer.write("SHOW DB\r\n");
            writer.flush();
            String line;
            while((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                if(line.startsWith("250")) break;

                if(line.startsWith("552")) {
                    System.out.println("No definition found for" + word);
                    break;
                }

                if(line.matches("\\d\\d\\d .*") || line.trim().equals(".")) break;

                System.out.println(line);
            }
        }
    }
}
