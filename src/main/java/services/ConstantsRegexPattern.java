package services;


public final class ConstantsRegexPattern {
    private static final String PATTERN_LOGIN_REGEXP = "^(.+)@(.+)$";
    private static final String PATTERN_MOBILE_PHONE_REGEXP1 = "^\\+[\\(\\-]?(\\d[\\(\\)\\-]?){11}\\d$";
    private static final String PATTERN_MOBILE_PHONE_REGEXP2 = "^\\(?(\\d[\\-\\(\\)]?){9}\\d$";
    private static final String PATTERN_MOBILE_PHONE_REGEXP3 = "[\\+]?\\d*(\\(\\d{3}\\))?\\d*\\-?\\d*\\-?\\d*\\d$";
    private static final String PATTERN_STATIONARY_PHONE_UKR_REGEXP = "[+][3][8][0][(][3456][0-9][)][0-9]{7}";
    private static final String PATTERN_EMAIL_REGEXP = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    public static String getPatternEmail() {
        return PATTERN_EMAIL_REGEXP;
    }

    public static String getPatternMobilePhone1() {
        return PATTERN_MOBILE_PHONE_REGEXP1;
    }
    public static String getPatternMobilePhone2() {
        return PATTERN_MOBILE_PHONE_REGEXP2;
    }
    public static String getPatternMobilePhone3() {
        return PATTERN_MOBILE_PHONE_REGEXP3;
    }

    //public static String getPatternStationaryPhoneUkr() {
  //      return PATTERN_STATIONARY_PHONE_UKR_REGEXP;
  //  }

    public static String getPatternLogin() {
        return PATTERN_LOGIN_REGEXP;
    }
}


/* Проверка номера телефона
Метод checkTelNumber должен проверять, является ли аргумент telNumber валидным номером телефона.
Критерии валидности:
1) если номер начинается с '+', то он содержит 12 цифр
2) если номер начинается с цифры или открывающей скобки, то он содержит 10 цифр
3) может содержать 0-2 знаков '-', которые не могут идти подряд
4) может содержать 1 пару скобок '(' и ')'  , причем если она есть, то она расположена левее знаков '-'
5) скобки внутри содержат четко 3 цифры
6) номер не содержит букв
7) номер заканчивается на цифру

Примеры:
+380501234567 - true
+38(050)1234567 - true
+38050123-45-67 - true
050123-4567 - true

+38)050(1234567 - false
+38(050)1-23-45-6-7 - false
050ххх4567 - false
050123456 - false
(0)501234567 - false
*/