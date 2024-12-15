package projet_sid;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class serveur_arabe {

    // Dictionnaire pour la traduction de l'anglais vers l'arabe
    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        // Ajout des mots à traduire dans le dictionnaire
    	
    	    dictionary.put("hello", "مرحبا");
    	    dictionary.put("world", "عالم");
    	    dictionary.put("book", "كتاب");
    	    dictionary.put("system", "نظام");
    	    dictionary.put("distributed", "موزع");
    	    dictionary.put("computer", "حاسوب");
    	    dictionary.put("informatics", "معلوماتية");
    	    dictionary.put("library", "مكتبة");
    	    dictionary.put("university", "جامعة");
    	    dictionary.put("teacher", "معلم");
    	    dictionary.put("student", "طالب");
    	    dictionary.put("school", "مدرسة");
    	    dictionary.put("science", "علم");
    	    dictionary.put("technology", "تكنولوجيا");
    	    dictionary.put("internet", "إنترنت");
    	    dictionary.put("language", "لغة");
    	    dictionary.put("dictionary", "قاموس");
    	    dictionary.put("keyboard", "لوحة المفاتيح");
    	    dictionary.put("mouse", "فأرة");
    	    dictionary.put("screen", "شاشة");
    	    dictionary.put("programming", "برمجة");
    	    dictionary.put("software", "برمجيات");
    	    dictionary.put("data", "بيانات");
    	    dictionary.put("algorithm", "خوارزمية");
    	    dictionary.put("network", "شبكة");
    	    dictionary.put("cloud", "سحابة");
    	    dictionary.put("application", "تطبيق");
    }

    // Méthode pour traduire un mot
    public static String translate(String word) {
        // Retourne la traduction ou "Not Found" si le mot n'existe pas
        return dictionary.getOrDefault(word, "Not Found");
    }

    // Méthode pour gérer la communication avec le client
    public static void handleClient(Socket clientSocket) {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            // Création des flux d'entrée et de sortie pour communiquer avec le client
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Réception du mot à traduire depuis le client
            String word = reader.readLine();
            if (word == null || word.isEmpty()) {
                writer.println("Invalid input"); // Si le mot est vide ou invalide
                return;
            }

            // Traduction du mot
            String response = translate(word);

            // Envoi de la réponse (traduction) au client
            writer.println(response);

        } catch (IOException e) {
            // En cas d'erreur lors de la gestion du client
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                // En cas d'erreur lors de la fermeture des ressources
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Création du serveur écoutant sur le port 8081
            serverSocket = new ServerSocket(8081);
            System.out.println("English to Arabic server listening on port 8081...");

            // Boucle pour accepter les connexions des clients
            while (true) {
                // Accepte la connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                // Gère le client dans un nouveau thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            // En cas d'erreur lors de la création du serveur
            System.err.println("Server error: " + e.getMessage());
        } finally {
            // Fermeture du serveur
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                // En cas d'erreur lors de la fermeture du serveur
                System.err.println("Error closing server: " + e.getMessage());
            }
        }
    }
}
