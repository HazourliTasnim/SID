package projet_sid;

import java.io.*;
import java.net.*;

public class client {

    // Méthode pour envoyer une requête de traduction au serveur
    public static void sendTranslationRequest(String word, String lang) {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            // Connexion au serveur de gestion sur le port 8080
            socket = new Socket("127.0.0.1", 8080);

            // Création des flux d'entrée et de sortie pour communiquer avec le serveur
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Envoi de la requête sous la forme "mot:langue"
            String request = word + ":" + lang;
            writer.println(request);

            // Réception de la réponse du serveur
            String response = reader.readLine();
            System.out.println("Réponse reçue : " + response);

        } catch (IOException e) {
            // Gestion des erreurs de communication avec le serveur
            System.err.println("Erreur lors de la communication avec le serveur : " + e.getMessage());
        } finally {
            try {
                // Fermeture des ressources
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                // Gestion des erreurs lors de la fermeture des ressources
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    // Méthode principale pour récupérer l'entrée de l'utilisateur et envoyer la requête
    public static void main(String[] args) {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            // Demander à l'utilisateur le mot à traduire
            System.out.print("Mot à traduire : ");
            String word = consoleReader.readLine();

            // Demander à l'utilisateur la langue cible (arabe ou français)
            System.out.print("Langue cible (ar/fr) : ");
            String lang = consoleReader.readLine();

            // Appel de la méthode pour envoyer la requête de traduction
            sendTranslationRequest(word, lang);
        } catch (IOException e) {
            // Gestion des erreurs lors de la lecture de l'entrée utilisateur
            System.err.println("Erreur lors de la lecture de l'entrée utilisateur : " + e.getMessage());
        }
    }
}
