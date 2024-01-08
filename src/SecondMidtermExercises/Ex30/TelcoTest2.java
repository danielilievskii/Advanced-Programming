package SecondMidtermExercises.Ex30;

//package mk.ukim.finki.midterm;


import java.util.*;
import java.util.stream.Collectors;

interface ICallState {
    void answer(long timestamp);
    void end(long timestamp);
    void hold(long timestamp);
    void resume(long timestamp);
}

abstract class CallState implements ICallState {
    Call call;
    public CallState(Call call) {
        this.call = call;
    }
}
class CallStartedState extends CallState{
    public CallStartedState(Call call) {
        super(call);
    }
    @Override
    public void answer(long timestamp) {
        call.callStarted = timestamp;
        call.state = new InProgressCallState(call);
    }
    @Override
    public void end(long timestamp) {
        call.callStarted=timestamp;
        call.callEnded = timestamp;
        call.isMissedCall = true;
        call.state = new TerminatedCallState(call);
    }
    @Override
    public void hold(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void resume(long timestamp) {
        throw new RuntimeException();
    }
}

class InProgressCallState extends CallState{
    public InProgressCallState(Call call) {
        super(call);
    }
    @Override
    public void answer(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void end(long timestamp) {
        call.callEnded = timestamp;
        call.state = new TerminatedCallState(call);
    }
    @Override
    public void hold(long timestamp) {
        call.holdStartedAt = timestamp;
        call.state = new PausedCallState(call);
    }
    @Override
    public void resume(long timestamp) {
        throw new RuntimeException();
    }
}
class PausedCallState extends CallState{
    public PausedCallState(Call call) {
        super(call);
    }
    @Override
    public void answer(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void end(long timestamp) {
        call.callEnded = timestamp;
        call.totalTimeHold += call.callEnded - call.holdStartedAt;
        call.holdStartedAt = 0;
        call.state = new TerminatedCallState(call);
    }
    @Override
    public void hold(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void resume(long timestamp) {
        call.totalTimeHold += timestamp - call.holdStartedAt;
        call.state = new InProgressCallState(call);
    }
}

class TerminatedCallState extends CallState{
    public TerminatedCallState(Call call) {
        super(call);
    }
    @Override
    public void answer(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void end(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void hold(long timestamp) {
        throw new RuntimeException();
    }
    @Override
    public void resume(long timestamp) {
        throw new RuntimeException();
    }
}

class Call {
    String uuid;
    String dialer;
    String receiver;
    long timestampCalled;
    long callStarted;
    long callEnded;
    long holdStartedAt;
    long totalTimeHold;
    boolean isMissedCall = false;

    CallState state = new CallStartedState(this);
    public Call(String uuid, String dialer, String receiver, long timestamp) {
        this.uuid = uuid;
        this.dialer = dialer;
        this.receiver = receiver;
        this.timestampCalled = timestamp;

        this.callStarted=0;
        this.callEnded=0;
        this.holdStartedAt=0;
        totalTimeHold = 0;
    }
   
    public void answer(long timestamp) {
        state.answer(timestamp);
    }   
    public void end(long timestamp) {
        state.end(timestamp);
    }   
    public void hold(long timestamp) {
        state.hold(timestamp);
    }   
    public void resume(long timestamp) {
        state.resume(timestamp);
    }
    public String duration() {
        return DurationConverter.convert(callEnded-callStarted-totalTimeHold);
    }

    public long durationLong() {
        return callEnded-callStarted-totalTimeHold;
    }

    @Override
    public String toString() {
        return String.format("%s <-> %s : %s", dialer, receiver, duration());
    }

    public long getCallStarted() {
        return callStarted;
    }

}

class DurationConverter {
    public static String convert(long duration) {
        long minutes = duration / 60;
        duration %= 60;
        return String.format("%02d:%02d", minutes, duration);
    }
}

class TelcoApp {
    Map<String, List<Call>> callsByPhone;
    Map<String, Call> callsByUUID;

    public TelcoApp() {
        this.callsByPhone = new HashMap<>();
        this.callsByUUID = new HashMap<>();
    }

    void addCall (String uuid, String dialer, String receiver, long timestamp) {
            Call newCall = new Call(uuid, dialer, receiver, timestamp);

            callsByPhone.putIfAbsent(dialer, new ArrayList<>());
            callsByPhone.putIfAbsent(receiver, new ArrayList<>());

            callsByPhone.get(dialer).add(newCall);
            callsByPhone.get(receiver).add(newCall);

            callsByUUID.put(uuid, newCall);
    }

    void updateCall (String uuid, long timestamp, String action) {
        if(action.equals("ANSWER")) {
            callsByUUID.get(uuid).answer(timestamp);
        } else if (action.equals("END")) {
            callsByUUID.get(uuid).end(timestamp);
        } else if (action.equals("HOLD")) {
            callsByUUID.get(uuid).hold(timestamp);
        } else {
            callsByUUID.get(uuid).resume(timestamp);
        }
    }
    void printListOfCalls (List<Call> callsSearched, String phoneNumber) {
        callsSearched.forEach(call -> {
            if(call.dialer.equals(phoneNumber)) {
                System.out.println(String.format("D %s %d %s %s",call.receiver, call.callStarted, call.isMissedCall ? "MISSED CALL" : call.callEnded, call.duration()));
            } else {
                System.out.println(String.format("R %s %d %s %s",call.dialer, call.callStarted, call.isMissedCall ? "MISSED CALL" : call.callEnded, call.duration()));
            }
        });
    }
    void printChronologicalReport(String phoneNumber) {
        List<Call> callsSearched = callsByPhone.get(phoneNumber);
        callsSearched.sort(Comparator.comparing(Call::getCallStarted));
        printListOfCalls(callsSearched, phoneNumber);
    }
    void printReportByDuration(String phoneNumber) {
        List<Call> callsSearched = callsByPhone.get(phoneNumber);
        callsSearched.sort(Comparator.comparing(Call::duration).thenComparing(Call::getCallStarted).reversed());
        printListOfCalls(callsSearched, phoneNumber);
    }
    void printCallsDuration() {
        TreeMap<String, Long> result = callsByUUID.values().stream().collect(Collectors.groupingBy(
                call -> String.format("%s <-> %s", call.dialer, call.receiver),
                TreeMap::new,
                Collectors.summingLong(Call::durationLong)
        ));

        result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s : %s", entry.getKey(), DurationConverter.convert(entry.getValue()))));
    }
}


public class TelcoTest2 {
    public static void main(String[] args) {
        TelcoApp app = new TelcoApp();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            String command = parts[0];

            if (command.equals("addCall")) {
                String uuid = parts[1];
                String dialer = parts[2];
                String receiver = parts[3];
                long timestamp = Long.parseLong(parts[4]);
                app.addCall(uuid, dialer, receiver, timestamp);
            } else if (command.equals("updateCall")) {
                String uuid = parts[1];
                long timestamp = Long.parseLong(parts[2]);
                String action = parts[3];
                app.updateCall(uuid, timestamp, action);
            } else if (command.equals("printChronologicalReport")) {
                String phoneNumber = parts[1];
                app.printChronologicalReport(phoneNumber);
            } else if (command.equals("printReportByDuration")) {
                String phoneNumber = parts[1];
                app.printReportByDuration(phoneNumber);
            } else {
                app.printCallsDuration();
            }
        }

    }
}
