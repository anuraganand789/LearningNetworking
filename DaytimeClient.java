import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DaytimeClient {
    public static void main(String[] args) throws IOException {
        final String hostname = "time.nist.gov";

        try(final Socket socket = new Socket(hostname, 13)) {
            socket.setSoTimeout(15000);
            final InputStream in = socket.getInputStream();
            final StringBuilder time = new StringBuilder();
            final InputStreamReader isr = new InputStreamReader(in, "ASCII");
            int c;

            while((c = isr.read()) != -1) time.append((char)c);

            System.out.println(
                    time
            );
        }
    }
}
