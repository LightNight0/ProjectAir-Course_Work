package localization;

public class ThemeController {
    private static String DarkTheme = "Themes/DarkTheme.css";
    private static String LightTheme = "Themes/LightTheme.css";
    private static boolean check = false;

    public static void changeTheme(){
        check = !check;
    }

    public static String getTheme(){
        return check ? LightTheme : DarkTheme;
    }
}