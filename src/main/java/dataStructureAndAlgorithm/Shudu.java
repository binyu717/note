package dataStructureAndAlgorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 回溯法解数独(导入文件待填值用0代替)
 *
 * @author bin.yu
 * @create 2018-12-02 16:27
 **/
public class Shudu {

    public static void main(String[] args) {
        File output = new File(args[1]);
        try {
            output.createNewFile();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        Scanner sc = null;
        PrintStream p = null;
        try {
            try {
                int j;
                sc = new Scanner(new File(args[0]));
                p = new PrintStream(output);
                // 九空格的值
                int[][] a = new int[9][9];
                // 记录每列 一维记录位置，二维代表该位置的值，有值的true
                boolean[][] cols = new boolean[9][9];
                // 记录每行
                boolean[][] rows = new boolean[9][9];
                // 记录每3x3块
                boolean[][] blocks = new boolean[9][9];
                int i = 0;
                // 初始化九宫格，rows、cols、blocks用来校验
                while (i < a.length) {
                    j = 0;
                    while (j < a.length) {
                        a[i][j] = sc.nextInt();
                        if (a[i][j] != 0) {
                            // k
                            int k = i / 3 * 3 + j / 3;
                            int val = a[i][j] - 1;
                            rows[i][val] = true;
                            cols[j][val] = true;
                            blocks[k][val] = true;
                        }
                        ++j;
                    }
                    ++i;
                }
               DFS(a, cols, rows, blocks);
                // 打印完成后的九空格
                i = 0;
                while (i < 9) {
                    j = 0;
                    while (j < 8) {
                        System.out.print(String.valueOf(a[i][j]) + " ");
                        p.print(String.valueOf(a[i][j]) + " ");
                        ++j;
                    }
                    System.out.println(a[i][8]);
                    p.println(a[i][8]);
                    ++i;
                }
                p.flush();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                sc.close();
                p.close();
            }
        }
        finally {
            sc.close();
            p.close();
        }
    }

    private static boolean DFS(int[][] a, boolean[][] cols, boolean[][] rows, boolean[][] blocks) {
        // 标识行
        int i = 0;
        while (i < 9) {
            // 标识列
            int j = 0;
            while (j < 9) {
                if (a[i][j] == 0) {
                    int k = i / 3 * 3 + j / 3;
                    // 每行或每列的
                    int l = 0;
                    while (l < 9) {
                        // 判断每行每列每三宫格是否已存在该值，都不存在进循环赋值，递归下一个计算
                        if (!(cols[j][l] || rows[i][l] || blocks[k][l])) {
                            blocks[k][l] = true;
                            cols[j][l] = true;
                            rows[i][l] = true;
                            a[i][j] = 1 + l;
                            if (DFS(a, cols, rows, blocks)) {
                                return true;
                            }
                            blocks[k][l] = false;
                            cols[j][l] = false;
                            rows[i][l] = false;
                            a[i][j] = 0;
                        }
                        ++l;
                    }
                    return false;
                }
                ++j;
            }
            ++i;
        }
        return true;
    }
}