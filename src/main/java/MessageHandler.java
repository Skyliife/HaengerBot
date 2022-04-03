import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import tictactoe.MyTicTacToe;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageHandler {

    static MyTicTacToe ttt = null;

    public static void playAudio(MessageReceivedEvent event) {
        // This makes sure we only execute our code when someone sends a message with "!play"
        if (!event.getMessage().getContentRaw().startsWith("!play")) return;
        // Now we want to exclude messages from bots since we want to avoid command loops in chat!
        // this will include own messages as well for bot accounts
        // if this is not a bot make sure to check if this message is sent by yourself!
        if (event.getAuthor().isBot()) return;
        Guild guild = event.getGuild();
        // This will get the first voice channel with the name "music"
        // matching by voiceChannel.getName().equalsIgnoreCase("music")
        VoiceChannel channel = guild.getVoiceChannelsByName("Musik", true).get(0);
        AudioManager manager = guild.getAudioManager();

        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);

        AudioPlayer player = playerManager.createPlayer();


        // MySendHandler should be your AudioSendHandler implementation
        manager.setSendingHandler(new CostumAudioHandler(player));
        // Here we finally connect to the target voice channel
        // and it will automatically start pulling the audio from the MySendHandler instance
        manager.openAudioConnection(channel);
    }

    public static void playPong(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (content.equals("!ping")) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }

    public static void playTicTacToe(MessageReceivedEvent event) {


        if (event.getAuthor().isBot()) return;

        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if (content.equals("!tictactoe")) {

            ttt = new MyTicTacToe();

            Stream<char[]> charStream = Arrays.stream(ttt.introboard);
            String introboard = "```" + charStream.map(String::valueOf).collect(Collectors.joining()) + "```";

            channel.sendMessage("Welcome to DiscordTicTacToe - Enter a number (1-9)").queue();
            channel.sendMessage(introboard).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
        if (ttt.isRunning() && ttt != null) {
            if (content.equals("1") || content.equals("2") || content.equals("3") || content.equals("4") || content.equals("5") || content.equals("6") || content.equals("7") || content.equals("8") || content.equals("9")) {

                ttt.game(content, channel);
            }

        }
    }


}
