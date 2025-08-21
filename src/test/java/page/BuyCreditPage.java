package page;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BuyCreditPage {

    private final SelenideElement PayCardField = $(byText("Оплата по карте"));
    private final SelenideElement cardNumberForm = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthForm = $("[placeholder='08']");
    private final SelenideElement yearForm = $("[placeholder='22']");
    private final SelenideElement ownerForm = $$("[class='input__control']").get(3);
    private final SelenideElement cvcForm = $("[placeholder='999']");
    private final SelenideElement buttonForm = $(Selectors.byText("Продолжить"));
    private final SelenideElement successfulNotification = $(Selectors.withText("Операция одобрена Банком."));
    private final SelenideElement errorNotification = $(Selectors.withText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement wrongFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement wrongCardDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpired = $(byText("Истёк срок действия карты"));

    public void filledForm(DataHelper.CardInfo cardInfo, DataHelper.MonthInfo monthInfo, DataHelper.YearInfo yearInfo, DataHelper.OwnerInfo ownerInfo, DataHelper.CvcInfo cvcInfo) {
        cardNumberForm.setValue(cardInfo.getCardNumber());
        monthForm.setValue(monthInfo.getMonth());
        yearForm.setValue(yearInfo.getYear());
        ownerForm.setValue(ownerInfo.getOwner());
        cvcForm.setValue(cvcInfo.getCvc());
        buttonForm.click();
    }

    public void waitSuccessfulNotification() {
        successfulNotification.should(visible, Duration.ofSeconds(15));
    }

    public void waitErrorNotification() {
        errorNotification.should(visible, Duration.ofSeconds(15));
    }

    public void waitEmptyField() {
        emptyField.should(visible);
    }

    public void waitWrongFormat() {
        wrongFormat.should(visible);
    }

    public void waitWrongCardDate() {
        wrongCardDate.should(visible);
    }

    public void waitCardExpired() {
        cardExpired.should(visible);
    }

    public void onlyCardField(DataHelper.CardInfo cardInfo) {
        cardNumberForm.setValue(cardInfo.getCardNumber());
    }

    public void emptyCardField() {
        cardNumberForm.should(empty);
    }

    public void onlyMonthField(DataHelper.MonthInfo monthInfo) {
        monthForm.setValue(monthInfo.getMonth());
    }

    public void emptyMonthField() {
        monthForm.should(empty);
    }

    public void onlyYearField(DataHelper.YearInfo yearInfo) {
        yearForm.setValue(yearInfo.getYear());
    }

    public void emptyYearField() {
        yearForm.should(empty);
    }

    public void onlyCVCField(DataHelper.CvcInfo cvcInfo) {
        cvcForm.setValue(cvcInfo.getCvc());
    }

    public void emptyCVCField() {
        cvcForm.should(empty);
    }
}