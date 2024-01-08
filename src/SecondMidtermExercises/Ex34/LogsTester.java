package SecondMidtermExercises.Ex34;

import java.util.*;
import java.util.stream.Collectors;

abstract class Log {
    String serviceName;
    String microServiceName;
    String type;
    String message;
    int timestamp;
    public Log(String serviceName, String microServiceName, String message, int timestamp) {
        this.serviceName = serviceName;
        this.microServiceName = microServiceName;
        this.message = message;
        this.timestamp = timestamp;
    }
    public abstract int calcSeverity();
    public String getServiceName() {
        return serviceName;
    }
    public String getMicroServiceName() {
        return microServiceName;
    }
    public int getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}

class InfoLog extends Log {
    public InfoLog(String serviceName, String microServiceName, String message, int timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }
    @Override
    public int calcSeverity() {
        return 0;
    }
    @Override
    public String toString() {
        return String.format("%s|%s [INFO] %s %d T:%d", serviceName, microServiceName, message, timestamp, timestamp);
    }
}

class WarnLog extends Log {
    public WarnLog(String serviceName, String microServiceName, String message, int timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }
    @Override
    public int calcSeverity() {
        return 1 + (getMessage().contains("might cause error")?1:0);
    }
    @Override
    public String toString() {
        return String.format("%s|%s [WARN] %s %d T:%d", serviceName, microServiceName, message, timestamp, timestamp);
    }
}

class ErrorLog extends Log {
    public ErrorLog(String serviceName, String microServiceName, String message, int timestamp) {
        super(serviceName, microServiceName, message, timestamp);
    }
    @Override
    public int calcSeverity() {
        int severity = 3;
        severity+=getMessage().contains("fatal") ? 2 : 0;
        severity+=getMessage().contains("exception") ? 3 : 0;
        return severity;
    }
    @Override
    public String toString() {
        return String.format("%s|%s [ERROR] %s %d T:%d", serviceName, microServiceName, message, timestamp, timestamp);
    }
}

class LogFactory {
    public static Log createLog(String line) {
        String[] logParts = line.split(" ");
        String service = logParts[0];
        String microService = logParts[1];
        String type = logParts[2];
        StringBuilder messageSB = new StringBuilder();
        for(int i=3; i<logParts.length-2; i++) {
            messageSB.append(logParts[i]).append(" ");
        }
        messageSB.append(logParts[logParts.length - 2]);
        String message = messageSB.toString();
        int timestamp = Integer.parseInt(logParts[logParts.length - 1]);

        switch (type) {
            case "INFO":
                return new InfoLog(service, microService, message, timestamp);
            case "WARN":
                return new WarnLog(service, microService, message, timestamp);
            case "ERROR":
                return new ErrorLog(service, microService, message, timestamp);
            default: return null;
        }
    }
}
class LogCollector {

    Map<String, Map<String, ArrayList<Log>>> logsByServices;
    public LogCollector() {
        this.logsByServices = new HashMap<>();
    }

    void addLog (String log) {
        //service_name microservice_name type message timestamp
        //service2 microservice3 ERROR Log message 2. 252
        Log newLog = LogFactory.createLog(log);
        String[] logParts = log.split(" ");
        String service = logParts[0];
        String microService = logParts[1];

        logsByServices.putIfAbsent(service, new HashMap<>());
        logsByServices.get(service).putIfAbsent(microService, new ArrayList<>());
        logsByServices.get(service).get(microService).add(newLog);
    }

    public double averageSeverity(List<Log> list){
        return list.stream()
                .mapToDouble(Log::calcSeverity)
                .average().orElse(0);
    }
    void printServicesBySeverity() {
        logsByServices.entrySet().stream()
                .sorted(Comparator
                        .comparing(entry -> -averageSeverity(entry
                                .getValue()
                                .values()
                                .stream()
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList()))))
                .forEach(entry -> {
                        String service = entry.getKey();
                        int numMicroServices = entry.getValue().size();
                        Map<String, ArrayList<Log>> logsByMicroServices = entry.getValue();

                        int totalLogs = logsByMicroServices.values().stream()
                        .mapToInt(ArrayList::size).sum();

                        double averageSeverity = averageSeverity(logsByMicroServices.values().stream()
                                .flatMap(Collection::stream).collect(Collectors.toList()));

                        double averageNumberOfLogsPerMicroService = logsByMicroServices.values().stream()
                        .mapToDouble(ArrayList::size).average().orElse(0);

                        System.out.println(String.format("Service name: %s Count of microservices: %d Total logs in service: %d Average severity for all logs: %.2f Average number of logs per microservice: %.2f",
                        service, numMicroServices, totalLogs, averageSeverity, averageNumberOfLogsPerMicroService ));
        });
    }
    public Map<Integer, Integer> getSeverityDistribution(String service, String microservice) {
        Map<Integer, Integer> getSeverityDistribution;

        List<Log> neededLogs;
        if(microservice == null) {
            neededLogs = logsByServices.get(service).values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        } else {
            neededLogs = logsByServices.get(service).get(microservice);
        }

        getSeverityDistribution = neededLogs.stream().collect(Collectors.groupingBy(
                Log::calcSeverity,
                Collectors.summingInt(i -> 1)
        ));

        return getSeverityDistribution;
    }

    void displayLogs(String service, String microservice, String order) {
        ArrayList<Log> neededLogs;
        if(microservice == null) {
            neededLogs = (ArrayList<Log>) logsByServices.get(service).values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        } else {
            neededLogs = logsByServices.get(service).get(microservice);
        }

        switch (order) {
            case "NEWEST_FIRST":
                neededLogs.stream().sorted(Comparator.comparing(Log::getTimestamp).reversed()).forEach(System.out::println);
                break;
            case "OLDEST_FIRST":
                neededLogs.stream().sorted(Comparator.comparing(Log::getTimestamp)).forEach(System.out::println);
                break;
            case "MOST_SEVERE_FIRST":
                neededLogs.stream().sorted(Comparator.comparing(Log::calcSeverity).thenComparing(Log::getTimestamp).thenComparing(Log::getMicroServiceName).reversed()).forEach(System.out::println);
                break;
            case "LEAST_SEVERE_FIRST":
                neededLogs.stream().sorted(Comparator.comparing(Log::calcSeverity).thenComparing(Log::getTimestamp).thenComparing(Log::getMicroServiceName)).forEach(System.out::println);
                break;
        }
    }
}

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
            } else if (line.startsWith("displayLogs")){
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                System.out.println(line);

                collector.displayLogs(service, microservice, order);
            }
        }
    }
}
