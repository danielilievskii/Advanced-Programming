//THERE IS ONE LITTLE MISTAKE
//package LabaratoryExercises.Lab8.MP3Player;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class Song {
//    String title;
//    String artist;
//
//    public Song(String title, String artist) {
//        this.title = title;
//        this.artist = artist;
//    }
//
//    @Override
//    public String toString() {
//        return "Song{" +
//                "title=" + title +
//                ", artist=" + artist +
//                '}';
//    }
//}
//
//class MP3Player {
//    List<Song> songs;
//    int currentSong;
//    boolean isCurrentSongPlaying;
//
//    public MP3Player(List<Song> songs) {
//        this.songs = songs;
//        this.currentSong = 0;
//        this.isCurrentSongPlaying = false;
//    }
//
//    public void pressPlay() {
//        if (isCurrentSongPlaying) {
//            System.out.println("Song is already playing");
//        } else {
//            System.out.printf("Song %d is playing%n", currentSong);
//            isCurrentSongPlaying = true;
//        }
//    }
//
//    public void printCurrentSong() {
//        System.out.println(songs.get(currentSong));
//    }
//
//    public void pressStop() {
//        if (isCurrentSongPlaying) {
//            System.out.printf("Song %d is paused%n", currentSong);
//            isCurrentSongPlaying = false;
//        } else if(!isCurrentSongPlaying && currentSong==0){
//            System.out.println("Songs are already stopped");
//        } else {
//            System.out.println("Songs are stopped");
//            currentSong=0;
//        }
//    }
//
//    public void pressFWD() {
//        if (currentSong + 1 == songs.size()) {
//            currentSong = 0;
//        } else currentSong++;
//
//        if (isCurrentSongPlaying) {
//            isCurrentSongPlaying = false;
//        }
//
//        System.out.println("Forward...");
//    }
//
//    public void pressREW() {
//        if (currentSong - 1 == -1) {
//            currentSong = songs.size() - 1;
//        } else currentSong--;
//
//        if (isCurrentSongPlaying) {
//            isCurrentSongPlaying = false;
//        }
//
//        System.out.println("Reward...");
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("MP3Player{");
//        sb.append("currentSong = ").append(currentSong);
//        sb.append(", songList = ").append(songs);
//        sb.append('}');
//        return sb.toString();
//    }
//}
//
//public class PatternTest {
//    public static void main(String args[]) {
//        List<Song> listSongs = new ArrayList<Song>();
//        listSongs.add(new Song("first-title", "first-artist"));
//        listSongs.add(new Song("second-title", "second-artist"));
//        listSongs.add(new Song("third-title", "third-artist"));
//        listSongs.add(new Song("fourth-title", "fourth-artist"));
//        listSongs.add(new Song("fifth-title", "fifth-artist"));
//        MP3Player player = new MP3Player(listSongs);
//
//
//        System.out.println(player.toString());
//        System.out.println("First test");
//
//
//        player.pressPlay();
//        player.printCurrentSong();
//        player.pressPlay();
//        player.printCurrentSong();
//
//        player.pressPlay();
//        player.printCurrentSong();
//        player.pressStop();
//        player.printCurrentSong();
//
//        player.pressPlay();
//        player.printCurrentSong();
//        player.pressFWD();
//        player.printCurrentSong();
//
//        player.pressPlay();
//        player.printCurrentSong();
//        player.pressREW();
//        player.printCurrentSong();
//
//
//        System.out.println(player.toString());
//        System.out.println("Second test");
//
//
//        player.pressStop();
//        player.printCurrentSong();
//        player.pressStop();
//        player.printCurrentSong();
//
//        player.pressStop();
//        player.printCurrentSong();
//        player.pressPlay();
//        player.printCurrentSong();
//
//        player.pressStop();
//        player.printCurrentSong();
//        player.pressFWD();
//        player.printCurrentSong();
//
//        player.pressStop();
//        player.printCurrentSong();
//        player.pressREW();
//        player.printCurrentSong();
//
//
//        System.out.println(player.toString());
//        System.out.println("Third test");
//
//
//        player.pressFWD();
//        player.printCurrentSong();
//        player.pressFWD();
//        player.printCurrentSong();
//
//        player.pressFWD();
//        player.printCurrentSong();
//        player.pressPlay();
//        player.printCurrentSong();
//
//        player.pressFWD();
//        player.printCurrentSong();
//        player.pressStop();
//        player.printCurrentSong();
//
//        player.pressFWD();
//        player.printCurrentSong();
//        player.pressREW();
//        player.printCurrentSong();
//
//
//        System.out.println(player.toString());
//    }
//}
//
//
////Vasiot kod ovde
