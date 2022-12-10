import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static final String CLIENT = "Client";
    public static int COUNT_OF_QUEUE  = 2;
    public static final int QUEUE_CAPACITY = 5;
    public static final ArrayList<BlockingQueue<String>> queues = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < COUNT_OF_QUEUE; i++) {
            queues.add(new LinkedBlockingQueue<>(QUEUE_CAPACITY));
            fillTheQueue(queues.get(i));
        }
        printQueues();



    }
    public static void fillTheQueue(BlockingQueue<String> queue) {
        int numberOfPeople = (int) (5 * Math.random());
        for (int i = 0; i <= numberOfPeople; i++) {
            queue.add(CLIENT);
        }
    }
    public static boolean addClientToQueue(String shopClient){
        Integer targetIndex = null;
        for (int i = 0; i < queues.size(); i++) {
            if (targetIndex != null
                    && queues.get(i).remainingCapacity() > queues.get(targetIndex).remainingCapacity()
                    || targetIndex == null && queues.get(i).remainingCapacity() > 0) {
                targetIndex = i;
            }
        }
        if (targetIndex != null) {
            queues.get(targetIndex).add(shopClient);
            return true;
        }
        System.out.println("Кассы перегружены, зовите Галю");
        System.out.println();
        queues.add(new LinkedBlockingQueue<>(QUEUE_CAPACITY));
        addClientToQueue(shopClient);
        return false;
    }

    public static boolean deleteRandomClient() {
        ArrayList<BlockingQueue<String>> notEmptyQueues = new ArrayList<>();
        for (BlockingQueue<String> queue : queues) {
            if (!queue.isEmpty()) {
                notEmptyQueues.add(queue);
            }
        }
        if (notEmptyQueues.size() == 0) {
            return false;
        }
        int random = (int) (notEmptyQueues.size() * Math.random());
        notEmptyQueues.get(random).remove();
        return true;
    }
    public static void printQueues() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < COUNT_OF_QUEUE; i++) {
            stringBuilder.append(String.format("%-20s", "Очередь №" + (i + 1)));
        }
        System.out.println(stringBuilder);
        for (int i = 0; i < QUEUE_CAPACITY; i++) {
            stringBuilder = new StringBuilder();
            for (BlockingQueue<String> queue : queues) {
                stringBuilder.append(String.format("%-20s",
                        queue.remainingCapacity() < QUEUE_CAPACITY - i ? CLIENT : "<blank>"));
            }
            System.out.println(stringBuilder);
        }
        System.out.println();
    }
}
