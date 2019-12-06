package localization;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;

public class LangController {
    private static Locale locale = new Locale("ru", "RU");

    public static void changeLocale(){
        if(locale.getLanguage().equals("ru"))
            locale = new Locale("en", "EN");
        else
            locale = new Locale("ru", "RU");
    }

    public static Locale getLocale(){
        return locale;
    }
}