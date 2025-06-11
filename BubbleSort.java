// Optimized Bubble sort in Java

import java.util.Arrays;

class BubbleSort {

    // perform the bubble sort
    public static void bubbleSort(int array[]) {
        int n = array.length;

        // loop to access each array element
        for (int i = 0; i < (n - 1); i++) {

            // check if swapping occurs
            boolean swapped = false;

            // loop to compare adjacent elements
            for (int j = 0; j < (n - i - 1); j++) {

                if (array[j] > array[j + 1]) {

                    // swapping occurs if elements
                    // are not in the intended order
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;

                    swapped = true;
                }
            }
            // no swapping means the array is already sorted
            // so no need for further comparison
            if (!swapped)
                break;

        }
    }

    public static void main(String args[]) {

        int[] data = { -2, 45, 0, 11, -9 };

        Main.bubbleSort(data);

        System.out.println("Sorted Array in Ascending Order:");
        System.out.println(Arrays.toString(data));
    }
}
