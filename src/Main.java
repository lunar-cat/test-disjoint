import java.util.*;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) {
//        List<Integer> items = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
//        List<Integer[]> groups = Arrays.stream(new Integer[][]{{1, 2, 3}, {1, 2, 3, 4}, {5}, {5, 6}, {7, 8}, {6, 7}, {9}})
//        List<Integer[]> groups = Arrays.stream(new Integer[][]{{1, 2, 3}, {1, 2, 3, 4}, {4, 5, 6}, {5, 6, 7}, {7, 8}, {9}})
        List<Integer> items = Arrays.asList(1, 2, 3);
        List<Integer[]> groups = Arrays.stream(new Integer[][]{{1, 2, 3}, {1, 2, 3, 4}})
                .sorted((g1, g2) -> {
                    if (g2.length - g1.length != 0) {
                        return g2.length - g1.length;
                    }
                    return g1[0] - g2[0]; // TODO: asumiendo que el grupo si viene sorted
                }).collect(Collectors.toList());
        groups.forEach(group -> System.out.println(Arrays.asList(group)));
        for (int i = 0; i < groups.size(); i++) {
            Counter packageCounter = new Counter(items, new ArrayList<>(groups.subList(i, groups.size())));
            Counter counter = recursiveCall(packageCounter, 0);
            if (counter.itemsReminder.isEmpty()) {
                System.out.println("Hubo match!");
                counter.groupsSelected.values().forEach(group -> System.out.println(Arrays.asList(group)));
                break;
            } else {
                System.out.println("no hubo match :(");
            }
        }
    }

    private static Counter recursiveCall(Counter counter, Integer currentGroupIndex) {
        if (currentGroupIndex >= counter.groupsReminder.size()) {
            return counter;
        }
        Integer[] currentGroup = counter.groupsReminder.get(currentGroupIndex);
        if (counter.itemsReminder.containsAll(Arrays.asList(currentGroup))) {
            Arrays.asList(currentGroup).forEach(counter.itemsReminder::remove);
            counter.groupsSelected.put(currentGroupIndex, currentGroup);
        }
        if (counter.itemsReminder.isEmpty()) {
            return counter;
        } else {
            return recursiveCall(counter, currentGroupIndex + 1);
        }
    }
}

class Counter {
    HashSet<Integer> itemsReminder;
    List<Integer[]> groupsReminder;
    HashMap<Integer, Integer[]> groupsSelected;

    public Counter(List<Integer> itemsReminder, List<Integer[]> groupsReminder) {
        this.itemsReminder = new HashSet<>(itemsReminder);
        this.groupsReminder = groupsReminder;
        this.groupsSelected = new HashMap<>();
    }
}