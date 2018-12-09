package ru.otus.l031.arrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> myArrayList = new MyArrayList<>();
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            myArrayList.add("Hello_" + i + " ");
            arrayList.add("Otus_" + i + " ");
        }

        System.out.print("Элементы в MyArrayList: ");
        for (String s : myArrayList) System.out.print(s);
        System.out.println("\nРазмер MyArrayList: " + myArrayList.size());
        System.out.print("Элементы в ArrayList: ");
        for (String s : arrayList) System.out.print(s);
        System.out.println("\nРазмер ArrayList: " + arrayList.size());

        System.out.println("-------------------------------------------------");
        System.out.println("Добавляем все элементы из MyArrayList в ArrayList");
        Collections.addAll(arrayList, myArrayList.toArray(new String[0]));
        System.out.print("Элементы в ArrayList: ");
        for (String s : arrayList) System.out.print(s);
        System.out.println("\nРазмер ArrayList: " + arrayList.size());

        System.out.println("-------------------------------------------------");
        System.out.println("Сортируем элементы MyArrayList");
        Collections.sort(myArrayList, Comparator.naturalOrder());
        System.out.print("Элементы в MyArrayList: ");
        for (String s : myArrayList) System.out.print(s);

        System.out.println("\n-------------------------------------------------");
        System.out.println("Копируем элементы из MyArrayList в ArrayList");
        Collections.copy(arrayList, myArrayList);
        System.out.print("Элементы в ArrayList: ");
        for (String s : arrayList) System.out.print(s);
        System.out.println("\nРазмер ArrayList: " + arrayList.size());
    }
}
