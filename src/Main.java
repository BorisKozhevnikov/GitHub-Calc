import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int a;
        int b;
        int result;
        Scanner s = new Scanner(System.in);


        System.out.println("Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b.\nЧто посчитать?");
        String input = s.nextLine();
        String[] parts = input.split("\\s");


        // Проверка на формат запроса

        if(parts.length != 3){
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) разделенные пробелом");
                return;
            }
        }

        // Инициация Метода проверки Символа
        сheckMathChar(parts[1]);

        // Инициация Метода проверки римский цифр

        int found = checkArray(parts);

        // 0 - Римский цифры,
        // 1 - Не римские цифры,
        // 2 - Ошибка: Разные системы счисления

        if (found == 0){
            // Инициация метода расчета Римский цифр
            String resultRom = romMath(parts);
            System.out.println("Ответ: " + resultRom);
        } else if (found == 1) {
            try {
                a = Integer.parseInt(parts[0]);
                b = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e){
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                return;
            }
            // Инициация метода расчета Арабский цифр
            result = arabicMath (a,b,parts[1]);
            System.out.println("Ответ: " + result);
        } else {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
                return;
            }
        }

    }


    // Метод проверки наличия римский цифр
    private static int checkArray(String[]array){
        String[] romNum = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        boolean a = false;
        boolean b = false;
        int x;
        for (int i=0;i<romNum.length;i++) {
            a = Arrays.asList(array[0]).contains(romNum[i]);
            if (a) {
                break;
            }
        }
        for (int i=0;i<romNum.length;i++){
            b = Arrays.asList(array[2]).contains(romNum[i]);
            if (b) {
                break;
            }
        }
        if (a && b) {
            x = 0;
        }
        else if (!a && !b) {
            x = 1;
        } else {
            x = 3;
        }
        return x;
    }

    // Метод расчета Арабский цифр
    private static int arabicMath(int a, int b, String c){

        int result = 0;

        if (a > 10 || b > 10 || a < 1 || b < 1){
            System.out.println("Цифры должны быть от 1 до 10");
            System.exit(0);
        }
        switch (c) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
        }

        return result;
    }

    // Метод проверки символа
    private static void сheckMathChar(String c){
        String[] mathChar = {"+","-","*","/"};
        boolean b = Arrays.asList(mathChar).contains(c);
        if (!b){
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) разделенные пробелом");
                System.exit(0);
            }
        }
    }
    // Метод расчета Римский цифр
    private static String romMath(String[] parts) {
        ArrayList<String> list = new ArrayList<>();
        try (Scanner scan = new Scanner(new File("RomNum100"))) {
            while (scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл с римскими цифрами отсутствует");
        }
        String[] romNum = list.toArray(new String[0]);

        int aArabic = 0;
        int bArabic = 0;


        for (int i=0;i<romNum.length;i++){
            if (parts[0].equals(romNum[i])) {
                aArabic = i + 1;
                break;
            }
        }
        for (int i=0;i<romNum.length;i++){
            if (parts[2].equals(romNum[i])){
                bArabic = i+1;
                break;
            }
        }

        // Инициация метода расчета Арабский цифр/ Возврат расчетов для преобразования в Римский ответ
        int result = arabicMath(aArabic,bArabic,parts[1]);

        if (result < 0){
            try {
                throw new IOException();
            } catch (IOException d) {
                System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
                System.exit(0);
            }
        } else if (result == 0) {
            System.out.println("nulla (nihil)");
            System.exit(0);
        }
        String resultRom = romNum[result - 1];
        return resultRom;


    }

}