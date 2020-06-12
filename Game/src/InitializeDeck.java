import java.util.Random;

public class InitializeDeck {
    private int[] deck = new int[24];
    private int[] slots = new int[24];

    public int[] getDeck() {
        return deck;
    }

    public InitializeDeck() {
        for (int i = 0; i < 24; i++) {
            slots[i] = i;
            deck[i] = 0;
        }
        int i = 0, j = 1, i_cnt = 0;
        while (i < 24) {

            int randomIndex = new Random().nextInt(slots.length);

            int current = slots[randomIndex];
            deck[current] = j;
            slots = remove(slots, index(slots, current));
            i_cnt++;
            if (i_cnt == 4) {
                j++;
                i_cnt = 0;
            }
            i++;
        }
    }

    private int index(int array[], int search) {
        int cnt = 0;
        for (int x : array) {
            if (x == search)
                return cnt;
            cnt++;
        }
        return -1;
    }

    private int[] remove(int array[], int indexToDelete) {
        int new_array[] = new int[array.length - 1];
        int sizeof_new_array = 0;
        for (int i = 0; i < array.length; i++) {
            if (indexToDelete == i) {
                continue;
            }
            new_array[sizeof_new_array] = array[i];
            sizeof_new_array++;
        }
        return new_array;
    }

    public void view(int array[]) {
        System.out.print("[");
        for (int x : array) {
            System.out.print(x + ", ");
        }
        System.out.println("]");
    }

}
