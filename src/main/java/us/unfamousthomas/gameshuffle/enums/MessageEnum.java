package us.unfamousthomas.gameshuffle.enums;

public interface MessageEnum {

    String replace(Lang lang, String target, String replacement);

    String get(Lang lang);

    enum Lang {
        EESTI;
    }
}
