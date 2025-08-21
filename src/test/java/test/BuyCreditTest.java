package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.BuyCreditPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BuyCreditTest {

    private HomePage homePage;
    private BuyCreditPage buyCreditPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setup() {
        homePage = open("http://localhost:8080/", HomePage.class);
    }

    @AfterEach
    void clean() {
        SQLHelper.clear();
    }

    @Test
    public void shouldPurchaseWithApprovedCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldPurchaseWithDeclinedCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitErrorNotification();
        var expected = DataHelper.getSecondCardStatus();
        var actual = SQLHelper.getCreditPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnErrorWithEmptyNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpaceNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSpacesCardInfo();
        buyCreditPage.onlyCardField(cardNumber);
        buyCreditPage.emptyCardField();
    }

    @Test
    public void shouldErrorWithUnknownNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardInvalidCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitErrorNotification();
    }

    @Test
    public void shouldErrorWithUnderLimitNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardUnderLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnValidWithAfterLimitNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardAfterLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitErrorNotification();
    }


    @Test
    public void shouldReturnErrorWithCyrillicNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardWithCyrillicCardInfo();
        buyCreditPage.onlyCardField(cardNumber);
        buyCreditPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithLatinNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getCardWithLatinCardInfo();
        buyCreditPage.onlyCardField(cardNumber);
        buyCreditPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithSpecialSymbolNumberCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSpecialSimbolsCardInfo();
        buyCreditPage.onlyCardField(cardNumber);
        buyCreditPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithEmptyMonth() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }


    @Test
    public void shouldReturnErrorWithSpacesInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var month = DataHelper.getSpacesMonth();
        buyCreditPage.onlyMonthField(month);
        buyCreditPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithZerosInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardWithZerosMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneNumberInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getUnderLimitMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithInvalidDateMonthCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateInvalidMonthDate();
        var year = DataHelper.getGenerateYear(0);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithTooMuchMonth() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getTooMuchMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongCardDate();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var month = DataHelper.getCyrillicMonth();
        buyCreditPage.onlyMonthField(month);
        buyCreditPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithLatinInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var month = DataHelper.getLatinMonth();
        buyCreditPage.onlyMonthField(month);
        buyCreditPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithSpacialSymbolInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var month = DataHelper.getSpecialsSymbolMonth();
        buyCreditPage.onlyMonthField(month);
        buyCreditPage.emptyMonthField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInMonth() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardAfterLimitMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyYearCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpacesInYear() {
        buyCreditPage = homePage.openCreditForm();
        var year = DataHelper.getSpacesYear();
        buyCreditPage.onlyYearField(year);
        buyCreditPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithOneNumberInYear() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardUnderLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }


    @Test
    public void shouldErrorWithInvalidDateYearCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateInvalidYearDate(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitCardExpired();
    }


    @Test
    public void shouldErrorWithTooMuchDateYear() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(6);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongCardDate();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInYear() {
        buyCreditPage = homePage.openCreditForm();
        var year = DataHelper.getCyrillicYearInfo();
        buyCreditPage.onlyYearField(year);
        buyCreditPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithLatinInYear() {
        buyCreditPage = homePage.openCreditForm();
        var year = DataHelper.getLatinYearInfo();
        buyCreditPage.onlyYearField(year);
        buyCreditPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithSpecialsSymbolInYear() {
        buyCreditPage = homePage.openCreditForm();
        var year = DataHelper.getSpecialsSymbolsYearInfo();
        buyCreditPage.onlyYearField(year);
        buyCreditPage.emptyYearField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInYear() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(3);
        var year = DataHelper.getCardAfterLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEmptyCvcCode();
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpacesInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cvc = DataHelper.getSpacesCvcCode();
        buyCreditPage.onlyCVCField(cvc);
        buyCreditPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithUnderLimitNumberInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getUnderLimitCvcCode();
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cvc = DataHelper.getCyrillicCvcCode();
        buyCreditPage.onlyCVCField(cvc);
        buyCreditPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithLatinInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cvc = DataHelper.getLatinCvcCode();
        buyCreditPage.onlyCVCField(cvc);
        buyCreditPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithSpecialSymbolsInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cvc = DataHelper.getSpacesCvcCode();
        buyCreditPage.onlyCVCField(cvc);
        buyCreditPage.emptyCVCField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInCvc() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(3);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCvcAfterLimitCvcCode();
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyOwnerDebitCard() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithSpacesInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getSpacesOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneLetterInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getOneLetterOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getCyrillicOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithNumbersInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getNumbersOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpecialsSymbolsInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getSpecialsSymbolsOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneNameInOwner() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getOneNameOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        buyCreditPage.waitWrongFormat();
    }


    @Test
    public void shouldAddPaymentIDInOrderEntry() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getCreditRequestReEntryId();
        var actual = SQLHelper.getCreditOrderEntryId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDontAddPaymentIDInOrderEntryStatusDeclined() {
        buyCreditPage = homePage.openCreditForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyCreditPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getCreditRequestReEntryId();
        var actual = SQLHelper.getCreditOrderEntryId();
        assertNotEquals(expected, actual);
    }
}