
package LabaratoryExercises.Lab8.MP3Player_StatePattern;

import java.util.ArrayList;
import java.util.List;

interface IMP3PlayerState {
    void pressPlay();
    void pressStop();
    void pressFWD();
    void pressRWD();
}

abstract class MP3PlayerState implements IMP3PlayerState {
    MP3Player mp3Player;

    public MP3PlayerState(MP3Player mp3Player) {
        this.mp3Player = mp3Player;
    }
}

class PausedMP3PlayerState extends MP3PlayerState {
    public PausedMP3PlayerState(MP3Player mp3Player) {
        super(mp3Player);
    }
    @Override
    public void pressPlay() {
        mp3Player.state = new PlayingMP3PlayerState(mp3Player);
        System.out.printf("Song %d is playing%n", mp3Player.currentSong);

        mp3Player.timesStopped=0; //for specific case while stopping twice.
    }
    @Override
    public void pressStop() {
        mp3Player.timesStopped++;
        if(mp3Player.timesStopped == 1) {
            System.out.println("Songs are stopped");
        } else if(mp3Player.timesStopped>1) {
            System.out.println("Songs are already stopped");
        }
        mp3Player.currentSong = 0;
    }
    @Override
    public void pressFWD() {
        if(mp3Player.currentSong+1 == mp3Player.songs.size()) {
            mp3Player.currentSong=0;
        }else mp3Player.currentSong++;

        System.out.println("Forward...");
    }
    @Override
    public void pressRWD() {
        if(mp3Player.currentSong-1 == -1) {
            mp3Player.currentSong=mp3Player.songs.size() - 1;
        }else mp3Player.currentSong--;

        System.out.println("Reward...");
    }
}

class PlayingMP3PlayerState extends MP3PlayerState {
    public PlayingMP3PlayerState(MP3Player mp3Player) {
        super(mp3Player);
    }
    @Override
    public void pressPlay() {
        System.out.printf("Song is already playing%n");
    }
    @Override
    public void pressStop() {
        mp3Player.state = new PausedMP3PlayerState(mp3Player);
        System.out.printf("Song %d is paused%n", mp3Player.currentSong);
        //mp3Player.currentSong=0;
    }
    @Override
    public void pressFWD() {
        mp3Player.state = new PausedMP3PlayerState(mp3Player);
        if(mp3Player.currentSong+1 == mp3Player.songs.size()) {
            mp3Player.currentSong=0;
        }else mp3Player.currentSong++;

        System.out.println("Forward...");
    }
    @Override
    public void pressRWD() {
        mp3Player.state = new PausedMP3PlayerState(mp3Player);
        if(mp3Player.currentSong-1 == -1) {
            mp3Player.currentSong=mp3Player.songs.size() - 1;
        }else mp3Player.currentSong--;

        System.out.println("Reward...");
    }
}


class Song {
    String title;
    String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title=" + title +
                ", artist=" + artist +
                '}';
    }
}

class MP3Player {
    List<Song> songs;
    int currentSong;
    boolean isCurrentSongPlaying;
    int timesStopped;
    MP3PlayerState state = new PausedMP3PlayerState(this);
    public MP3Player(List<Song> songs) {
        this.songs = songs;
        this.currentSong = 0;
        timesStopped=0;
    }
    public void pressPlay() {
       state.pressPlay();
    }
    public void pressStop() {
        state.pressStop();
    }
    public void pressFWD() {
        state.pressFWD();
    }
    public void pressREW() {
        state.pressRWD();
    }
    public void printCurrentSong() {
        System.out.println(songs.get(currentSong));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MP3Player{");
        sb.append("currentSong = ").append(currentSong);
        sb.append(", songList = ").append(songs);
        sb.append('}');
        return sb.toString();
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}


//Vasiot kod ovde
