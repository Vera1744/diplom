package page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class HomePage {

    SelenideElement debitForm = $(byText("Купить"));
    SelenideElement creditForm = $(byText("Купить в кредит"));


    public BuyPage openDebitForm() {
        debitForm.click();
        return new BuyPage();
    }

    public BuyCreditPage openCreditForm() {
        creditForm.click();
        return new BuyCreditPage();
    }
}