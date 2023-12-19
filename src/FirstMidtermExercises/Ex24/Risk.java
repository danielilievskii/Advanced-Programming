package FirstMidtermExercises.Ex24;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Round {
    List<Integer> attacker;
    List<Integer> defender;

    public Round(String input) {
        String[] parts = input.split(";");

        attacker = Arrays.stream(parts[0].split("\\s+")).map(Integer::parseInt).sorted().collect(Collectors.toList());
        defender = Arrays.stream(parts[1].split("\\s+")).map(Integer::parseInt).sorted().collect(Collectors.toList());
    }
    public boolean ifSuccessAttack() {
//        for(int i=0; i<attacker.size(); i++) {
//            if(attacker.get(i)<=defender.get(i)) {
//                return false;
//            }
//        }
//        return true;

        return IntStream.range(0, attacker.size()).allMatch(i -> attacker.get(i) > defender.get(i));
    }
}

public class Risk {
    public int processAttacksData(InputStream is) {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<Round> rounds = br.lines().map(line -> new Round(line)).collect(Collectors.toList());

        return (int) rounds.stream().filter(Round::ifSuccessAttack).count();

    }
}
