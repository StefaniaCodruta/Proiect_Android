package com.example.tarisiorase.models;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
