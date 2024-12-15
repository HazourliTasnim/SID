package projet_sid;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class Serveur_francais {

    // Dictionnaire pour la traduction de l'anglais vers le français
    private static final Map<String, String> dictionary = new HashMap<>();

    static {
        // Ajout des mots à traduire dans le dictionnaire
    	
    	    dictionary.put("hello", "bonjour");
    	    dictionary.put("world", "monde");
    	    dictionary.put("book", "livre");
    	    dictionary.put("system", "système");
    	    dictionary.put("distributed", "distribué");
    	    dictionary.put("computer", "ordinateur");
    	    dictionary.put("informatics", "informatique");
    	    dictionary.put("library", "bibliothèque");
    	    dictionary.put("university", "université");
    	    dictionary.put("teacher", "professeur");
    	    dictionary.put("student", "étudiant");
    	    dictionary.put("school", "école");
    	    dictionary.put("science", "science");
    	    dictionary.put("technology", "technologie");
    	    dictionary.put("internet", "internet");
    	    dictionary.put("language", "langue");
    	    dictionary.put("dictionary", "dictionnaire");
    	    dictionary.put("keyboard", "clavier");
    	    dictionary.put("mouse", "souris");
    	    dictionary.put("screen", "écran");
    	    dictionary.put("programming", "programmation");
    	    dictionary.put("software", "logiciel");
    	    dictionary.put("hardware", "matériel");
    	    dictionary.put("data", "données");
    	    dictionary.put("algorithm", "algorithme");
    	    dictionary.put("network", "réseau");
    	    dictionary.put("cloud", "nuage");
    	    dictionary.put("application", "application");
    	}

    // Méthode pour traduire un mot
    public static String translate(String word) {
        // Retourne la traduction en français du mot ou "Mot introuvable" si le mot n'existe pas
        return dictionary.getOrDefault(word, "Mot introuvable");
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

    // Méthode principale pour exécuter le serveur
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Création du serveur écoutant sur le port 8082
            serverSocket = new ServerSocket(8082);
            System.out.println("English to French server listening on port 8082...");

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
