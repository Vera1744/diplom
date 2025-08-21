package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.SQLHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.BuyPage;
import page.HomePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class BuyTest {

    private HomePage homePage;
    private BuyPage buyPage;

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
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitSuccessfulNotification();
        var expected = DataHelper.getFirstCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldPurchaseWithDeclinedCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitErrorNotification();
        var expected = DataHelper.getSecondCardStatus();
        var actual = SQLHelper.getDebitPaymentStatus();
        assertEquals(expected, actual);
    }


    @Test
    public void shouldReturnErrorWithEmptyNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getEmptyCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpaceNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSpacesCardInfo();
        buyPage.onlyCardField(cardNumber);
        buyPage.emptyCardField();
    }

    @Test
    public void shouldErrorWithUnknownNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardInvalidCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitErrorNotification();
    }

    @Test
    public void shouldErrorWithUnderLimitNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardUnderLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnValidWithAfterLimitNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardAfterLimitCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitErrorNotification();
    }


    @Test
    public void shouldReturnErrorWithCyrillicNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardWithCyrillicCardInfo();
        buyPage.onlyCardField(cardNumber);
        buyPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithLatinNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getCardWithLatinCardInfo();
        buyPage.onlyCardField(cardNumber);
        buyPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithSpecialSymbolNumberCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSpecialSimbolsCardInfo();
        buyPage.onlyCardField(cardNumber);
        buyPage.emptyCardField();
    }


    @Test
    public void shouldReturnErrorWithEmptyMonth() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getEmptyMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }


    @Test
    public void shouldReturnErrorWithSpacesInMonth() {
        buyPage = homePage.openDebitForm();
        var month = DataHelper.getSpacesMonth();
        buyPage.onlyMonthField(month);
        buyPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithZerosInMonth() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardWithZerosMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneNumberInMonth() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getUnderLimitMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldErrorWithInvalidDateMonthCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateInvalidMonthDate();
        var year = DataHelper.getGenerateYear(0);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongCardDate();
    }

    @Test
    public void shouldErrorWithTooMuchMonth() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getTooMuchMonth();
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongCardDate();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInMonth() {
        buyPage = homePage.openDebitForm();
        var month = DataHelper.getCyrillicMonth();
        buyPage.onlyMonthField(month);
        buyPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithLatinInMonth() {
        buyPage = homePage.openDebitForm();
        var month = DataHelper.getLatinMonth();
        buyPage.onlyMonthField(month);
        buyPage.emptyMonthField();
    }

    @Test
    public void shouldReturnErrorWithSpacialSymbolInMonth() {
        buyPage = homePage.openDebitForm();
        var month = DataHelper.getSpecialsSymbolMonth();
        buyPage.onlyMonthField(month);
        buyPage.emptyMonthField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInMonth() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getCardAfterLimitMonth();
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyYearCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getEmptyYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpacesInYear() {
        buyPage = homePage.openDebitForm();
        var year = DataHelper.getSpacesYear();
        buyPage.onlyYearField(year);
        buyPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithOneNumberInYear() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getCardUnderLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }


    @Test
    public void shouldErrorWithInvalidDateYearCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateInvalidYearDate(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitCardExpired();
    }


    @Test
    public void shouldErrorWithTooMuchDateYear() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(6);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongCardDate();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInYear() {
        buyPage = homePage.openDebitForm();
        var year = DataHelper.getCyrillicYearInfo();
        buyPage.onlyYearField(year);
        buyPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithLatinInYear() {
        buyPage = homePage.openDebitForm();
        var year = DataHelper.getLatinYearInfo();
        buyPage.onlyYearField(year);
        buyPage.emptyYearField();
    }

    @Test
    public void shouldReturnErrorWithSpecialsSymbolInYear() {
        buyPage = homePage.openDebitForm();
        var year = DataHelper.getSpecialsSymbolsYearInfo();
        buyPage.onlyYearField(year);
        buyPage.emptyYearField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInYear() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(3);
        var year = DataHelper.getCardAfterLimitYear();
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyCvc() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getEmptyCvcCode();
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpacesInCvc() {
        buyPage = homePage.openDebitForm();
        var cvc = DataHelper.getSpacesCvcCode();
        buyPage.onlyCVCField(cvc);
        buyPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithUnderLimitNumberInCvc() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getUnderLimitCvcCode();
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicInCvc() {
        buyPage = homePage.openDebitForm();
        var cvc = DataHelper.getCyrillicCvcCode();
        buyPage.onlyCVCField(cvc);
        buyPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithLatinInCvc() {
        buyPage = homePage.openDebitForm();
        var cvc = DataHelper.getLatinCvcCode();
        buyPage.onlyCVCField(cvc);
        buyPage.emptyCVCField();
    }

    @Test
    public void shouldReturnErrorWithSpecialSymbolsInCvc() {
        buyPage = homePage.openDebitForm();
        var cvc = DataHelper.getSpecialSymbolsCvcCode();
        buyPage.onlyCVCField(cvc);
        buyPage.emptyCVCField();
    }

    @Test
    public void shouldReturnValidValueWithAfterLimitNumberInCvc() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(3);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getCvcAfterLimitCvcCode();
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitSuccessfulNotification();
    }

    @Test
    public void shouldReturnErrorWithEmptyOwnerDebitCard() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getEmptyOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitEmptyField();
    }

    @Test
    public void shouldReturnErrorWithSpacesInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getSpacesOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneLetterInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(5);
        var owner = DataHelper.getOneLetterOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithCyrillicSymbolInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getCyrillicOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithNumbersInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getNumbersOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithSpecialsSymbolsInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getSpecialsSymbolsOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldReturnErrorWithOneNameInOwner() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getOneNameOwner();
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        buyPage.waitWrongFormat();
    }

    @Test
    public void shouldAddPaymentIDInOrderEntry() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getFirstCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getDebitPaymentID();
        var actual = SQLHelper.getDebitOrderEntryId();
        assertEquals(expected, actual);
    }

    @Test
    public void shouldDontAddPaymentIDInOrderEntryStatusDeclined() {
        buyPage = homePage.openDebitForm();
        var cardNumber = DataHelper.getSecondCardInfo();
        var month = DataHelper.getGenerateMonth(1);
        var year = DataHelper.getGenerateYear(1);
        var owner = DataHelper.getGenerateOwner("EN");
        var cvc = DataHelper.getGenerateCvcCode(3);
        buyPage.filledForm(cardNumber, month, year, owner, cvc);
        var expected = SQLHelper.getDebitPaymentID();
        var actual = SQLHelper.getDebitOrderEntryId();
        assertNotEquals(expected, actual);
    }
}