import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String token;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(System.getProperty("user.dir")+"\\token.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            token = br.readLine();
            br.close();
            System.out.println("Attempting to create FERPBot using token: " + token);
            FERPBot bot = new FERPBot(createClient(token, true));
        } catch (FileNotFoundException e) {
            System.out.println("No token file found. Please put your token in 'token.txt' in the file directory.");
            e.printStackTrace();
        }
    }
    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}
