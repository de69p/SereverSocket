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

        String response = reader.readLine();
        Assertions.assertEquals("Привіт, напиши щось українською мовою:", response);

        writer.write("Привет!\n");
        writer.flush();

        response = reader.readLine();
        Assertions.assertEquals("Що таке паляниця? Напиши відповідь:", response);

        writer.write("Це хліб.\n");
        writer.flush();

        response = reader.readLine();
        String expectedResponse = "Правильно! Ось поточна дата та час: ";
        Assertions.assertTrue(response.startsWith(expectedResponse));

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

        String response = reader.readLine();
        Assertions.assertEquals("Привіт, напиши щось українською мовою:", response);

        writer.write("Добрий день!\n");
        writer.flush();

        response = reader.readLine();
        String expectedResponse = "Дякую за привітання. Ось поточна дата та час: ";
        Assertions.assertTrue(response.startsWith(expectedResponse));

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

        writer.write("Що таке паляниця? Напиши відповідь:\n");
        writer.flush();

        writer.write("Це пиріг.");
        writer.flush();

        String response = reader.readLine();

        Assertions.assertTrue(response.contains("Неправильна відповідь."));
        Assertions.assertEquals(-1, clientSocket.getInputStream().read());
    }
}

