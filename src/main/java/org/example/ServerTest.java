package org.example;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    private static final String HOST = "localhost";
    private static final int PORT = 8081;

    @Test
    public void testRussianLetters() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        // Отримуємо привітання від сервера
        String response = reader.readLine();
        Assertions.assertEquals("Привіт, напиши щось українською мовою:", response);

        // Надсилаємо повідомлення з російськими буквами
        writer.write("Привет!\n");
        writer.flush();

        // Отримуємо запит на визначення "паляниці"
        response = reader.readLine();
        Assertions.assertEquals("Що таке паляниця? Напиши відповідь:", response);

        // Надсилаємо правильну відповідь
        writer.write("Це хліб.\n");
        writer.flush();

        // Отримуємо поточну дату та час
        response = reader.readLine();
        String expectedResponse = "Правильно! Ось поточна дата та час: ";
        Assertions.assertTrue(response.startsWith(expectedResponse));

        // Закриваємо з'єднання з сервером
        serverSocket.close();
    }

    @Test
    public void testUkrainianLetters() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        // Отримуємо привітання від сервера
        String response = reader.readLine();
        Assertions.assertEquals("Привіт, напиши щось українською мовою:", response);

        // Надсилаємо повідомлення з українськими буквами
        writer.write("Добрий день!\n");
        writer.flush();

        // Отримуємо поточну дату та час
        response = reader.readLine();
        String expectedResponse = "Дякую за привітання. Ось поточна дата та час: ";
        Assertions.assertTrue(response.startsWith(expectedResponse));

        // Закриваємо з'єднання з сервером
        serverSocket.close();
    }

    @Test
    public void testIncorrectAnswer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8081);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
        // надсилаємо клієнту рядок для відповіді
        writer.write("Що таке паляниця? Напиши відповідь:\n");
        writer.flush();

        // надсилаємо неправильну відповідь
        writer.write("Це пиріг.");
        writer.flush();

        // читаємо відповідь від сервера
        String response = reader.readLine();

        // перевіряємо, що сервер повідомив про неправильну відповідь та відключив клієнта
        Assertions.assertTrue(response.contains("Неправильна відповідь."));
        Assertions.assertEquals(-1, clientSocket.getInputStream().read());
    }
}

