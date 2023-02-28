package day6;

import java.time.Month;

public class SwitchExpression {

    public static String getMessage(String lang, String key) {
        return switch (lang) {
            case "en" -> switch (key) {
                case "title" -> "Title";
                case "message" -> "Message";
                default -> throw new IllegalArgumentException("Invalid key: " + key);
            };
            case "fr" -> switch (key) {
                case "title" -> "Titre";
                case "message" -> "Message";
                default -> throw new IllegalArgumentException("Clé invalide : " + key);
            };
            default -> throw new IllegalArgumentException("Invalid language: " + lang);
        };
    }

    public static int getDays(Month month) {

        int days = switch (month) {
            case JANUARY, MARCH, MAY, JULY, AUGUST, OCTOBER, DECEMBER -> {
                System.out.println(month);
                yield 31;
            }
            case FEBRUARY -> {
                System.out.println(month);
                yield 28;
            }
            case APRIL, JUNE, SEPTEMBER, NOVEMBER -> {
                System.out.println(month);
                yield 30;
            }
            default -> throw new IllegalStateException();
        };
        return days;
    }

    public static void main(String[] args) {
        System.out.println(getMessage("en", "title"));
        System.out.println(getMessage("fr", "message"));

        System.out.println(getDays(Month.JANUARY));
    }

}
