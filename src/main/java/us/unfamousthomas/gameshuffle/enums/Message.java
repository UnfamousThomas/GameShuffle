package us.unfamousthomas.gameshuffle.enums;

public enum Message implements MessageEnum {

    INCORRECT_USAGE(
            "&eVale kasutus selle käsu jaoks, vaata /help'i."
    ),
    NOBODY_WON(
            "&cMitte keegi ei saanud ühtegi punkti... Mitte keegi ei võitnud... või siis kõik võitsid?"
    ),
    WON_SINGLE(
            "&eMängija %user% võitis mängu %points% punktiga!"
    ),
    SKIPPED_LEVEL(
            "&cJätsite ühe toa vahele ja ei saa järgmised 7 sekundit liikuda."
    ),
    WON_MULTIPLE(
            "&eMitu mängijat võitsid kuna nad jäid viiki. Nende punktid olid %points%. Mängijad kes võitsid olid: %users%!"
    ),
    COMMAND_NOT_FOUND(
            "&eEi suutnud leida sellist käsku, vaata /help'i."
    ),
    NO_PERMS(
            "&eSul ei ole piisavalt õigusi et seda käsku kasutada, vajalik auaste selle jaoks on {required}."
    ),
    HELP(
            "&eKüsi abi meie meeskonnalt!"
    ),
    SUCCESS(
            "&eKäsklus edukalt täidetud!"
    ),
    INVALID_ARGUMENTS(
            "&eKäsklust kasutati õigesti aga mõni argument on vales formaadis."
    ),
    PLAYER_OFFLINE(
            "&eSelle kasutajanimega mängija ei ole hetkel serveriga ühendatud."
    ),
    ERROR(
            "&4Midagi läks valesti käskluse täitmisel!"
    ),
    CANT_BREAK_PLACE(
            "&cSa ei saa asju lõhkuda ja maha panna!"
    );

    private String est;

    Message(String est) {
        this.est = est;
    }

    @Override
    public String replace(Lang lang, String target, String replacement) {
        String original = get(lang);

        return original.replace(target, replacement);
    }

    public String replace(String target, String replacement) {
        String original = get(Lang.EESTI);

        return original.replace(target, replacement);
    }

    @Override
    public String get(Lang lang) {
        return est;
    }

}
