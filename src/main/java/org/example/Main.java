package org.example;

import java.util.*;

public class Main {
  public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

  public static void main(String[] args) throws InterruptedException {
    int numThreads = 1000;
    int length = 100;
    String letters = "RLRFR";

    Thread[] threads = new Thread[numThreads];
    for (int i = 0; i < numThreads; i++) {
      threads[i] = new Thread(() -> {
        String route = generateRoute(letters, length);
        int countR = countR(route);
        updateMap(countR);
        System.out.println("Повторений: " + route + ", R повторений: " + countR);
      });
      threads[i].start();
    }
    for (Thread thread : threads) {
      thread.join();
    }
    printMap();

  }

  public static String generateRoute(String letters, int length) {
    Random random = new Random();
    StringBuilder route = new StringBuilder();
    for (int i = 0; i < length; i++) {
      route.append(letters.charAt(random.nextInt(letters.length())));
    }
    return route.toString();
  }

  public static int countR(String route) {
    int count = 0;
    for (int i = 0; i < route.length(); i++) {
      if (route.charAt(i) == 'R') {
        count++;
      }
    }
    return count;
  }

  public static synchronized void updateMap(int countR) {
    if (sizeToFreq.containsKey(countR)) {
      sizeToFreq.put(countR, sizeToFreq.get(countR) + 1);
    } else {
      sizeToFreq.put(countR, 1);
    }
  }

  public static void printMap() {
    int maxFreq = 0;
    List<Integer> sizes = new ArrayList<>();
    for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
      int size = entry.getKey();
      int freq = entry.getValue();
      if (freq > maxFreq) {
        maxFreq = freq;
        sizes.clear();
        sizes.add(size);
      } else if (freq == maxFreq) {
        sizes.add(size);
      }
    }

    System.out.println("Самое частое количество R повторений: " + maxFreq + " (встретилось " + sizes.size() + " раз)");
    System.out.println("Другие размеры:");
    for (int size : sizes) {
      System.out.println("- " + size + " (" + sizeToFreq.get(size) + " раз)");
    }
    for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
      int size = entry.getKey();
      if (!sizes.contains(size)) {
        System.out.println("- " + size + " (" + entry.getValue() + " раз)");
      }
    }
  }


}