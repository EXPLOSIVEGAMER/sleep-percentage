package at.woodexplsoive.sleep_percentage.util;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public final class Text {

    public static ChatComponentText literal(String msg) {
        return new ChatComponentText(msg);
    }

    public static ChatComponentTranslation translatable(String key, Object... args) {
        return new ChatComponentTranslation(key, args);
    }
}
