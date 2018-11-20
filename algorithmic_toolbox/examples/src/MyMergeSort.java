import java.util.Arrays;
import java.util.Random;

/**
 * Created by petroverheles on 6/2/16.
 */
public class MyMergeSort {
    //static int[] numbers = {5, 1, 3, 10, 5, 2, 20};

    public static void main(String[] args) {
        int min = 1;
        int max = 1000;
        Random rand = new Random();

        long mergeSortTime = 0;
        long selectionSortTime = 0;
        long iterations = 100;

        for(int i = 0; i < iterations; i++) {
            //int size = rand.nextInt((max - min) + 1) + min;
            int size = 100000;
            int[] numbersForMergeSort = generateInput(size);

            int[] numbersForSelectionSort = coppyArr(numbersForMergeSort);

            //System.out.println(Arrays.toString(numbersForMergeSort));

            long start = System.currentTimeMillis();
            sort(numbersForMergeSort);
            long finish = System.currentTimeMillis();
            mergeSortTime += (finish - start);

            start = System.currentTimeMillis();
            selectionSort(numbersForSelectionSort);
            finish = System.currentTimeMillis();
            selectionSortTime += (finish - start);

            //System.out.println(Arrays.toString(numbersForMergeSort));
            //System.out.println(Arrays.toString(numbersForSelectionSort));

            boolean pass = areSortedArraysEqual(numbersForMergeSort, numbersForSelectionSort);
            //System.out.println(pass);
            if(!pass) {
                break;
            }
        }

        System.out.println();
        System.out.println("merge sort  time: " + (mergeSortTime));
        System.out.println("selection sort  time: " + (selectionSortTime));
        System.out.println("merge sort avarage time: " + (mergeSortTime / iterations));
        System.out.println("selection sort avarage time: " + (selectionSortTime / iterations));
        System.out.println("vidnoshenia: " + (selectionSortTime / (float)mergeSortTime));

    }

    static void sort(int[] arr) {
        if(arr.length == 1) {
            return;
        }

        int h = arr.length / 2;
        int a[] = new int[h];
        int b[] = new int[arr.length - h];

        for(int i = 0; i < h; i++) {
            a[i] = arr[i];
        }

        for(int i = h, j = 0; i < arr.length; i++, j++) {
            b[j] = arr[i];
        }

        sort(a);
        sort(b);

        merge(a, b, arr);
    }

    static void merge(int[] a, int[] b, int[] dest) {
        int i = 0;
        int j = 0;
        int k = 0;
        while(i < a.length && j < b.length) {
            int min;
            if(a[i] < b[j]) {
                min = a[i];
                i++;
            } else {
                min = b[j];
                j++;
            }
            dest[k] = min;
            k++;
        }

        if(i == a.length) {
            for(int n = j; n < b.length; n++) {
                dest[k] = b[n];
                k++;
            }
        } else {
            for(int n = i; n < a.length; n++) {
                dest[k] = a[n];
                k++;
            }
        }
    }

    static void selectionSort(int[] arr) {
        for(int i = 0; i < arr.length - 1; i++) {
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[j] < arr[i]) {
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                }
            }
        }
    }

    static int[] generateInput(int size) {
        int[] arr = new int[size];
        Random random = new Random();
        for(int i = 0; i < size; i++) {
            arr[i] = random.nextInt(100);
        }
        return arr;
    }

    static boolean areSortedArraysEqual(int[] arr1, int[] arr2) {
        if(arr1.length != arr2.length) {
            return false;
        }

        for(int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    static int[] coppyArr(int[] arr) {
        int[] copyArr = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;
    }
}
