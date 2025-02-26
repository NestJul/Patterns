package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        Random rand = new Random();
        LocalDate date = LocalDate.now().plusDays(shift + rand.nextInt(3));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return date.format(formatter);
    }

    public static String generateCity(String locale) {
        Random random = new Random();
        String[] city = {
                "Абакан",
                "Анадырь",
                "Архангельск",
                "Астрахань",
                "Барнаул",
                "Белгород",
                "Биробиджан",
                "Благовещенск",
                "Великий Новгород",
                "Владивосток",
                "Владикавказ",
                "Волгоград",
                "Воронеж",
                "Горно-Алтайск",
                "Екатеринбург",
                "Иваново",
                "Ижевск",
                "Йошкар-Ола",
                "Казань",
                "Калининград",
                "Киров",
                "Кострома",
                "Краснодар",
                "Красноярск",
                "Курган",
                "Магадан",
                "Магас",
                "Майкоп",
                "Махачкала",
                "Москва",
                "Мурманск",
                "Нарьян-Мар",
                "Нижний Новгород",
                "Новосибирск",
                "Орёл",
                "Оренбург",
                "Петропавловск-Камчатский",
                "Ростов-на-Дону",
                "Рязань",
                "Санкт-Петербург",
                "Саранск",
                "Самара",
                "Чебоксары",
                "Ханты-Мансийск",
                "Улан-Удэ",
                "Южно-Сахалинск"
        };

        return city[random.nextInt(city.length)];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));

        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));

        return faker.numerify("+79#########");
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale)
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
