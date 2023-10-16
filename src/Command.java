import java.io.*;
/**
 * Esta clase representa un comando que puede ser ejecutado en el shell.
 */

class Command {
    private String[] arguments;
    public String outputRedirect;
    private String output;
    /**
     * Constructor para un comando con argumentos y redirección de salida.
     *
     * @param arguments      Los argumentos del comando.
     * @param outputRedirect El nombre del archivo para redirigir la salida estándar.
     */

    public Command(String[] arguments, String outputRedirect) {
        this.arguments = arguments;
        this.outputRedirect = outputRedirect;
    }
    /**
     * Constructor para un comando especificado como una cadena.
     *
     * @param commandString La cadena que representa el comando.
     */
    public Command(String commandString) {
        if (commandString.contains(">")) {
            String[] parts = commandString.split(" > ");
            this.arguments = parts[0].split(" ");
            this.outputRedirect = parts[1];
        } else {
            this.arguments = commandString.split(" ");
            this.outputRedirect = "";
        }
    }
    /**
     * Ejecuta el comando y devuelve su salida estándar.
     *
     * @return La salida estándar del comando.
     */
    public String execute() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(arguments);
            Process process = processBuilder.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            output = outputBuilder.toString();

            int exitCode = process.waitFor();
            output += "Exit Code: " + exitCode;

            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * Devuelve una representación en cadena de la información del comando.
     *
     * @return Una cadena con información detallada sobre el comando.
     */
    public String toString() {
        StringBuilder info = new StringBuilder("Command: ");
        for (String arg : arguments) {
            info.append(arg).append(" ");
        }
        info.append("\nNumber of Arguments: ").append(arguments.length).append("\n");

        if (outputRedirect.isEmpty()) {
            info.append("Output:\n").append(output);
        }

        return info.toString();
    }
}
