package FirstMidtermExercises.Ex4;

class DummyLog implements ILog, Comparable<DummyLog> {
    String type;
    String message;
    long timestamp;

    public DummyLog(String type, String message, long timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(DummyLog o) {
        return Long.compare(this.getTimestamp(), o.getTimestamp());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String newMessage) {
        this.message = message;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public static DummyLog createLog(String line) {
        String[] parts = line.split("\\s+");
        return new DummyLog(parts[0], parts[1], Long.parseLong(parts[2]));
    }

    @Override
    public String toString() {
        return "DummyLog{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
