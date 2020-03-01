package task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* 
Что внутри папки?
*/
public class Solution {
    private static int filesCount;
    private static int foldersCount = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String path = reader.readLine();
        reader.close();
        String fullPath = Paths.get(path).toAbsolutePath().toString();
        if (!Files.isDirectory(Paths.get(path))) {
            System.out.println(fullPath + " - не папка");
            return;
        }
        CountFileVisitor countFileVisitor = new CountFileVisitor();
        Files.walkFileTree(Paths.get(path), countFileVisitor);
        System.out.println("Всего папок - " + foldersCount);
        System.out.println("Всего файлов - " + filesCount);
        System.out.println("Общий размер - " + countFileVisitor.overallSize);
    }

    public static class CountFileVisitor extends SimpleFileVisitor<Path> {
        private long overallSize = 0;
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            overallSize += attrs.size();
            filesCount++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            foldersCount++;
            return FileVisitResult.CONTINUE;
        }
    }

}
