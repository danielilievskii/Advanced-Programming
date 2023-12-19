package FirstMidtermExercises.Ex27;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Round {

    List<Integer> attacker;
    List<Integer> defender;
    public Round(String input) {
        String[] parts = input.split(";");

        attacker = Arrays.stream(parts[0].split("\\s+")).map(Integer::parseInt).sorted().collect(Collectors.toList());
        defender = Arrays.stream(parts[1].split("\\s+")).map(Integer::parseInt).sorted().collect(Collectors.toList());
    }

    void stats() {
        int counterAtt=0, counterDef=0;
        for(int i=0; i< attacker.size(); i++) {
            if(attacker.get(i)>defender.get(i)) {
                counterAtt++;
            } else counterDef++;
        }

        System.out.printf("%d %d%n", counterAtt, counterDef);
    }
}
