package projet_sid;

import java.io.*;
import java.net.*;

public class serveur_gestion {

    // Méthode pour gérer la connexion avec le client
    public static void handleClient(Socket clientSocket) {
        BufferedReader reader = null;
        PrintWriter writer = null;
        Socket targetSocket = null;

        try {
            // Création des flux pour lire et écrire avec le client
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Réception de la requête du client (mot et langue)
            String buffer = reader.readLine();
            if (buffer == null || !buffer.contains(":")) {
                writer.println("Requête invalide"); // Si la requête n'est pas valide, on renvoie une erreur
                return;
            }

            // Découpage de la requête en mot et langue
            String[] parts = buffer.split(":");
            String word = parts[0];
            String lang = parts[1];

            // Détermination du serveur cible en fonction de la langue
            int targetPort;
            if ("ar".equals(lang)) {
                targetPort = 8081; // Serveur Anglais → Arabe
            } else if ("fr".equals(lang)) {
                targetPort = 8082; // Serveur Anglais → Français
            } else {
                writer.println("Langue non supportée"); // Langue non supportée
                return;
            }

            // Connexion au serveur cible
            targetSocket = new Socket("127.0.0.1", targetPort);
            PrintWriter targetWriter = new PrintWriter(targetSocket.getOutputStream(), true);
            BufferedReader targetReader = new BufferedReader(new InputStreamReader(targetSocket.getInputStream()));

            // Envoi de la requête au serveur cible
            targetWriter.println(word);

            // Réception de la réponse du serveur cible
            String response = targetReader.readLine();
            if (response != null) {
                writer.println(response); // Envoi de la réponse au client
            } else {
                writer.println("Erreur lors de la réception de la réponse"); // Erreur si aucune réponse
            }

            // Fermeture des flux et de la connexion avec le serveur cible
            targetReader.close();
            targetWriter.close();
        } catch (IOException e) {
            // Gestion des erreurs d'entrée/sortie
            System.err.println("Erreur lors de la gestion du client : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (targetSocket != null) targetSocket.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                // Gestion des erreurs de fermeture des ressources
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    // Méthode principale pour exécuter le serveur
    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        try {
            // Création du serveur qui écoute sur le port 8080
            serverSocket = new ServerSocket(8080);
            System.out.println("Serveur de gestion en attente de connexions...");

            // Boucle pour accepter les connexions des clients
            while (true) {
                // Acceptation de la connexion d'un client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion acceptée.");

                // Gestion du client dans un thread séparé pour chaque connexion
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            // Gestion des erreurs liées au serveur
            System.err.println("Erreur lors de l'exécution du serveur : " + e.getMessage());
        } finally {
            // Fermeture du serveur
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                // Gestion des erreurs lors de la fermeture du serveur
                System.err.println("Erreur lors de la fermeture du serveur : " + e.getMessage());
            }
        }
    }
}
