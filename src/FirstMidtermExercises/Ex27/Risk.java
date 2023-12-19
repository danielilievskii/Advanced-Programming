package FirstMidtermExercises.Ex27;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Risk {
    public void processAttacksData (InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        List<Round> rounds = br.lines().map(line -> new Round(line)).collect(Collectors.toList());
        rounds.stream().forEach(round -> round.stats());
    }
}
