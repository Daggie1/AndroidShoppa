package tech.muva.academy.android_shoppa.object_box;

import android.content.Context;

import io.objectbox.BoxStore;

import tech.muva.academy.android_shoppa.models.MyObjectBox;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() { return boxStore; }
}