import java.util.*;

public class Main{

    // Build LPS array
    public static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }

    // KMP search counting comparisons
    public static Result kmpSearch(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        int comparisons = 0;

        while (i < text.length()) {
            comparisons++;  // counting this character comparison
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }

            if (j == pattern.length()) {
                matches.add(i - j);
                j = lps[j - 1];
            }
        }
        return new Result(matches, comparisons);
    }

    // Result holder class
    public static class Result {
        List<Integer> matches;
        int comparisons;

        public Result(List<Integer> matches, int comparisons) {
            this.matches = matches;
            this.comparisons = comparisons;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

        System.out.print("Enter the pattern: ");
        String pattern = scanner.nextLine();

        long startTime = System.nanoTime();

        Result result = kmpSearch(text, pattern);

        long endTime = System.nanoTime();
        double timeInSeconds = (endTime - startTime) / 1_000_000_000.0;

        if (result.matches.isEmpty()) {
            System.out.println("Pattern not found in the text.");
        } else {
            System.out.println("Pattern found at positions: " + result.matches);
        }

        System.out.printf("Search Time: %.3f seconds\n", timeInSeconds);

        System.out.println("Time complexity O(n + m) = O(" + text.length() + " + " + pattern.length() + ")  = O(" + result.comparisons + ")");

        scanner.close();
    }
}
