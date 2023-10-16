import java.io.*;
/**
 * Esta es la clase principal que implementa el shell básico.
 */
public class Shell {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Command lastCommand = null;

        while (true) {
            System.out.print("> ");
            try {
                String input = reader.readLine();
                if (input.equals("exit")) {
                    break;
                } else if (input.equals("last-command")) {
                    if (lastCommand != null) {
                        System.out.println(lastCommand.toString());
                    } else {
                        System.out.println("No commands executed yet.");
                    }
                } else {
                    Command command = new Command(input);
                    String output = command.execute();
                    if (!command.outputRedirect.isEmpty()) {
                        try {
                            FileWriter writer = new FileWriter(command.outputRedirect);
                            writer.write(output);
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(output);
                    }
                    lastCommand = command;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}