package runtime;

import java.io.*;

public class OtherProcess {
    private final String command;

    public OtherProcess(String command) {
        this.command = command;
    }

    public String redirection(BufferedReader reader) throws IOException, InterruptedException{
        var builder = new ProcessBuilder("bash", "-c", this.command);
        var process = builder.start();
        var compilerStdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
        var compilerStdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        var compilerStdIn = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));

        var src = reader.readLine();
        while (src != null) {
            compilerStdIn.write(src);
            compilerStdIn.write("\n");
            src = reader.readLine();
        }
        reader.close();
        compilerStdIn.close();
        var stringBuilder = new StringBuilder();
        var read = compilerStdOut.readLine();
        while (read != null) {
            stringBuilder.append(read);
            stringBuilder.append("\n");
            read = compilerStdOut.readLine();
        }

        process.waitFor();

        if (stringBuilder.toString().equals("")) {
            var errorBuilder = new StringBuilder();
            var errors = compilerStdErr.readLine();
            while (errors != null) {
                errorBuilder.append(errors);
                errorBuilder.append("\n");
                errors = compilerStdErr.readLine();
            }

            throw new RuntimeException(errorBuilder.toString());
        }
        return stringBuilder.toString();
    }
}
