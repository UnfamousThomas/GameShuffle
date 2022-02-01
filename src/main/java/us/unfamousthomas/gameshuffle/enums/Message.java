package us.unfamousthomas.gameshuffle.enums;

public enum Message implements MessageEnum {

    INCORRECT_USAGE(
            "&eVale kasutus selle käsu jaoks, vaata /help'i."
    ),
    COMMAND_NOT_FOUND(
            "&eEi suutnud leida sellist käsku, vaata /help'i."
    ),
    NO_PERMS(
            "&eSul ei ole piisavalt õigusi et seda käsku kasutada, vajalik auaste selle jaoks on {required}."
    ),
    HELP(
            "&eKüsi abi meie meeskonnalt!"
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

    @Override
    public String get(Lang lang) {
        return est;
    }
}
