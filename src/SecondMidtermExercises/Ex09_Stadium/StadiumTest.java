package SecondMidtermExercises.Ex09_Stadium;

import java.util.*;

class SeatNotAllowedException extends Exception {
    public SeatNotAllowedException() {
        super();
    }
}

class SeatTakenException extends Exception {
    public SeatTakenException() {
        super();
    }
}

class Sector {
    String code;
    int capacity;

    Map<Integer, Integer> seats;

    public Sector(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        seats = new HashMap<>();
    }

    public boolean isSeatTaken(int seat) {
        return seats.containsKey(seat);
    }

    public void takeSeat(int seat, int type) throws SeatNotAllowedException {
        if(type==1) {
            if(seats.containsValue(2))
                throw new SeatNotAllowedException();
        }
        if(type==2) {
            if(seats.containsValue(1))
                throw new SeatNotAllowedException();
        }
        seats.put(seat, type);
    }

    public int freeSeats() {
        return capacity - seats.size();
    }

    @Override
    public String toString() {
        return String.format("%s\t%d/%d\t%.1f%%", code, freeSeats(),capacity, (capacity-freeSeats())*100.0/capacity);
    }

    public String getCode() {
        return code;
    }
}

class Stadium {
    String name;
    HashMap<String, Sector> sectors;

    public Stadium(String name) {
        this.name = name;
        this.sectors = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes)  {
        for (int i=0; i<sectorNames.length; i++) {
            Sector sector = new Sector(sectorNames[i], sizes[i]);
            sectors.put(sectorNames[i], sector);
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatNotAllowedException, SeatTakenException {
        Sector sector = sectors.get(sectorName);
        if(sector.isSeatTaken(seat)) {
            throw new SeatTakenException();
        }
        sector.takeSeat(seat, type);
    }

    public void showSectors() {
        sectors.values().stream()
                .sorted(Comparator.comparing(Sector::freeSeats).reversed().thenComparing(Sector::getCode))
                .forEach(System.out::println);
    }
}

public class StadiumTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
