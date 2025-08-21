package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }

    private static final Faker faker = new Faker();

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("4444444444444441");
    }


    public static String getFirstCardStatus() {
        return new String("APPROVED");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("4444444444444442");
    }

    public static String getSecondCardStatus() {
        return new String("DECLINED");
    }

    public static CardInfo getEmptyCardInfo() {
        return new CardInfo("");
    }

    public static CardInfo getSpacesCardInfo() {
        return new CardInfo("                ");
    }

    public static CardInfo getCardInvalidCardInfo() {
        return new CardInfo("1111111111111111");
    }

    public static CardInfo getCardUnderLimitCardInfo() {
        return new CardInfo("444444444444444");
    }

    public static CardInfo getCardAfterLimitCardInfo() {
        return new CardInfo("44444444444444444");
    }

    public static CardInfo getCardWithCyrillicCardInfo() {
        return new CardInfo("йцукенгшщзхъфыва");
    }

    public static CardInfo getCardWithLatinCardInfo() {
        return new CardInfo("qwertyuiopasdfgh");
    }

    public static CardInfo getSpecialSimbolsCardInfo() {
        return new CardInfo("!@%^*($#^&@#(&%$");
    }

    public static MonthInfo getGenerateMonth(int shift) {
        var month = LocalDate.now().plusMonths(shift).format(DateTimeFormatter.ofPattern("MM"));
        return new MonthInfo(month);
    }


    public static MonthInfo getEmptyMonth() {
        return new MonthInfo("");
    }

    public static MonthInfo getSpacesMonth() {
        return new MonthInfo("  ");
    }

    public static MonthInfo getCardWithZerosMonth() {
        return new MonthInfo("00");
    }

    public static MonthInfo getUnderLimitMonth() {
        return new MonthInfo("1");
    }

    public static MonthInfo getGenerateInvalidMonthDate() {
        var month = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
        return new MonthInfo(month);
    }

    public static MonthInfo getTooMuchMonth() {
        return new MonthInfo("13");
    }

    public static MonthInfo getCyrillicMonth() {
        return new MonthInfo("фы");
    }

    public static MonthInfo getLatinMonth() {
        return new MonthInfo("as");
    }

    public static MonthInfo getSpecialsSymbolMonth() {
        return new MonthInfo("&#");
    }

    public static MonthInfo getCardAfterLimitMonth() {
        return new MonthInfo("111");
    }

    public static YearInfo getGenerateYear(int shift) {
        var year = LocalDate.now().plusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        return new YearInfo(year);
    }

    public static YearInfo getEmptyYear() {
        return new YearInfo("");
    }

    public static YearInfo getSpacesYear() {
        return new YearInfo("  ");
    }

    public static YearInfo getCardUnderLimitYear() {
        return new YearInfo("2");
    }

    public static YearInfo getGenerateInvalidYearDate(int shift) {
        var year = LocalDate.now().minusYears(shift).format(DateTimeFormatter.ofPattern("yy"));
        return new YearInfo(year);
    }


    public static YearInfo getCyrillicYearInfo() {
        return new YearInfo("цы");
    }

    public static YearInfo getLatinYearInfo() {
        return new YearInfo("ep");
    }

    public static YearInfo getSpecialsSymbolsYearInfo() {
        return new YearInfo("%#");
    }


    public static YearInfo getCardAfterLimitYear() {
        return new YearInfo("255");
    }


    public static CvcInfo getGenerateCvcCode(int digits) {
        Faker faker = new Faker();
        var cvc = faker.number().digits(digits);
        return new CvcInfo(cvc);
    }

    public static CvcInfo getEmptyCvcCode() {
        return new CvcInfo("");
    }

    public static CvcInfo getSpacesCvcCode() {
        return new CvcInfo("   ");
    }

    public static CvcInfo getUnderLimitCvcCode() {
        return new CvcInfo("00");
    }

    public static CvcInfo getCyrillicCvcCode() {
        return new CvcInfo("ячф");
    }

    public static CvcInfo getLatinCvcCode() {
        return new CvcInfo("qwe");
    }

    public static CvcInfo getSpecialSymbolsCvcCode() {
        return new CvcInfo("&^%");
    }

    public static CvcInfo getCvcAfterLimitCvcCode() {
        return new CvcInfo("0000");
    }


    public static OwnerInfo getGenerateOwner(String locale) {
        Faker faker = new Faker(new Locale(locale));
        var fakerOwner = faker.name().lastName() + " " + faker.name().firstName();
        return new OwnerInfo(fakerOwner);
    }

    public static OwnerInfo getEmptyOwner() {
        return new OwnerInfo("");
    }

    public static OwnerInfo getSpacesOwner() {
        return new OwnerInfo("      ");
    }

    public static OwnerInfo getOneLetterOwner() {
        return new OwnerInfo("S");
    }

    public static OwnerInfo getCyrillicOwner() {
        return new OwnerInfo("чхве сок");
    }

    public static OwnerInfo getNumbersOwner() {
        return new OwnerInfo("123 123");
    }

    public static OwnerInfo getSpecialsSymbolsOwner() {
        return new OwnerInfo("#$%^*#$^&$");
    }

    public static OwnerInfo getOneNameOwner() {
        return new OwnerInfo("Alosha");
    }


    @Value
    public static class CardInfo {
        String cardNumber;
    }

    @Value
    public static class MonthInfo {
        String month;
    }

    @Value
    public static class YearInfo {
        String year;
    }

    @Value
    public static class OwnerInfo {
        String owner;
    }

    @Value
    public static class CvcInfo {
        String cvc;
    }
}