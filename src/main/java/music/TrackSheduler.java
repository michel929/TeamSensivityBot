package music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import main.Main;
import main.Start;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class TrackSheduler extends AudioEventAdapter {
    public final AudioPlayer audioPlayer;
    public final BlockingDeque<AudioTrack> queue;

    public TrackSheduler(AudioPlayer audioPlayer){
        this.audioPlayer = audioPlayer;
        this.queue = new LinkedBlockingDeque<>();
    }

    public void queue(AudioTrack track){
        if(!this.audioPlayer.startTrack(track, true)){
            this.queue.offer(track);
        }
    }

    public void nextTrack(){
        this.audioPlayer.startTrack(this.queue.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext){
            nextTrack();
            if(player.getPlayingTrack() == null){
                final AudioManager audioManager = Main.INSTANCE.getGuild().getAudioManager();
                audioManager.closeAudioConnection();
            }
        }
    }
}
