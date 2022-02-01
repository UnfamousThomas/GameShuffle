package us.unfamousthomas.gameshuffle.utils;

public interface Callback<D> {


    void call(D data);

    default void fail(String msg) {
    }
}