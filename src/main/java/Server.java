import java.io.*;
import java.net.*;

public class Server {
    /**
     * Нужно создать приложение, которое будет основываться на ServerSocket. При старте приложение слушает порт 8081 и ожидает подключение 1 клиента.
     * После того как клиент подключается к серверу, они обмениваются приветствиями. Пример (hello => привіт ).
     * Если в приветствии клиента есть русские буквы, которых нет в украинском языке, сервис должен спросить "що таке паляниця?".
     * Если клиент ответит правильно, то сервер должен вернуть текущие дату и время и попрощаться. В противном случаи клиент должен быть отключен.....
     *
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8081);
            System.out.println("Сервер запущено. Очікування клієнта...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Клієнта підєднано.");
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

            writer.write("Привіт!\n");
            writer.flush();

            String response = reader.readLine();
            System.out.println(reader.readLine());
            boolean hasRussianLetters = response.matches(".*[А-Яа-яЁёЫыИЪЭэ].*");
            if (hasRussianLetters) {
                writer.write("Що таке паляниця? Напиши відповідь:\n");
                writer.flush();

                String answer = reader.readLine();

                boolean isCorrectAnswer = answer.equalsIgnoreCase("Це хліб.");
                if (isCorrectAnswer) {
                    writer.write("Правильно! Ось поточна дата та час: " + new java.util.Date() + "\n");
                    writer.flush();
                    System.out.println("Клієнта відєднано.");
                    clientSocket.close();
                } else {
                    System.out.println("Неправильна відповідь. Клієнта відєднано.");
                    clientSocket.close();
                }
            } else {
                writer.write("Дякую за привітання. Ось поточна дата та час: " + new java.util.Date() + "\n");
                writer.flush();
                System.out.println("Клієнта відєднано.");
                clientSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

