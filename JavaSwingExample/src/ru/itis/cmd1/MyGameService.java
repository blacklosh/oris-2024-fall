package ru.itis.cmd1;

import javax.swing.*;

import java.util.Arrays;
import java.util.Random;

import static ru.itis.cmd1.Main.*;

public class MyGameService {

    private int stepsCount = 0;

    private final Random random = new Random();

    private static boolean isActive = true;

    private static int skipIterations = 7;

    private static int needGlider = 0;

    public static void needNewGlider() {
        needGlider++;

        System.out.println("Получил увеличение глайдеров до " + needGlider);
    }

    public static void setSkipIterations(int s) {
        skipIterations = s;
    }

    public static void setActive(boolean active) {
        isActive = active;
    }

    public MyGameService(int delay) {
        Timer timer = new Timer(delay, e -> worldReload());
        timer.start();
    }

    private void worldReload() {
        if(!isActive) return;
        stepsCount++;
        if(stepsCount < skipIterations) return;
        stepsCount = 0;
        int[][] copy = new int[W_COUNT][H_COUNT];
        copyArray(copy, MAP);

        for(int x = 0; x < W_COUNT; x++) {
            for(int y = 0; y < H_COUNT; y++) {
                int n = countNeighbours(x, y);
                if(MAP[x][y] == 1) {
                    // текущая клетка жива
                    if(n == 2 || n == 3) {
                        // клетка остаётся живой
                        copy[x][y] = 1;
                    } else {
                        // клетка умирает
                        copy[x][y] = 0;
                    }
                } else {
                    // текущая клетка мертва
                    if(n == 3) {
                        // клетка оживает
                        copy[x][y] = 1;
                    } else {
                        // клетка остаётся мёртвой
                        copy[x][y] = 0;
                    }
                }
            }
        }

        if(needGlider > 0) {
            needGlider--;
            System.out.println("Создаю глайдер... Их в очереди теперь " + needGlider);
            summonGlider(copy);
        }

        copyArray(MAP, copy);
    }

    private void summonGlider(int[][] copy) {

        int r = random.nextInt(4);
        int x = random.nextInt(W_COUNT - 10) + 5;
        int y = random.nextInt(H_COUNT - 10) + 5;

        if(r == 0) {
            copy[x][y] = 1;
            copy[x][y+1] = 1;
            copy[x][y+2] = 1;
            copy[x+1][y+2] = 1;
            copy[x+2][y+1] = 1;
        } else if(r == 1) {
            copy[x][y] = 1;
            copy[x][y+1] = 1;
            copy[x][y+2] = 1;
            copy[x-1][y+2] = 1;
            copy[x-2][y+1] = 1;
        } else if(r == 2) {
            copy[x][y] = 1;
            copy[x+1][y] = 1;
            copy[x+2][y] = 1;
            copy[x+1][y+2] = 1;
            copy[x+2][y+1] = 1;
        } else {
            copy[x][y] = 1;
            copy[x+1][y] = 1;
            copy[x+2][y] = 1;
            copy[x+1][y-2] = 1;
            copy[x+2][y-1] = 1;
        }
    }

    private int countNeighbours(int x, int y) {
        int result = 0;
        if(x > 0) {
            result += MAP[x-1][y];
            if(y > 0) {
                result += MAP[x-1][y-1];
            }
            if(y < H_COUNT-1) {
                result += MAP[x-1][y+1];
            }
        }
        if(x < W_COUNT-1) {
            result += MAP[x+1][y];
            if(y > 0) {
                result += MAP[x+1][y-1];
            }
            if(y < H_COUNT-1) {
                result += MAP[x+1][y+1];
            }
        }
        if(y > 0) {
            result += MAP[x][y-1];
        }
        if(y < H_COUNT-1) {
            result += MAP[x][y+1];
        }
        return result;
    }

    private void copyArray(int[][] target, int[][] source) {
        for(int i = 0; i < target.length; i++) {
            target[i] = Arrays.copyOf(source[i], target.length);
        }
    }

}