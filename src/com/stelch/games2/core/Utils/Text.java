package com.stelch.games2.core.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import com.stelch.games2.core.Lang.en;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class Text {

    public static BaseComponent[] build(String str) { return (new ComponentBuilder("").appendLegacy(str.replaceAll("&","§")).create()); }

    public static BaseComponent[] build(String text, String hover) { return (new ComponentBuilder("").appendLegacy(text.replaceAll("&","§")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hover)).create())).create()); }

    public static BaseComponent[] build(String text, String hover, String command, ClickEvent.Action action) { return (new ComponentBuilder("").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hover)).create())).event(new ClickEvent(action, command)).appendLegacy(text.replaceAll("&","§"))).create(); }

    public static String format(String str) { return (str).replaceAll("&","§"); }
    public static String format(en str) { return (str.get()).replaceAll("&","§"); }

    public static boolean isNumeric(String str) { return str.matches("-?\\d+(\\.\\d+)?"); }

    public static void sendTitle(Player player, String text, String subtitle, int fadeInTime, int showTime, int fadeOutTime)
    {
        try
        {
            Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + text + "\"}");
            Object schatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");

            Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Constructor<?> stitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);
            Object packet = titleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);
            Object spacket = stitleConstructor.newInstance(getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);

            Object handle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, spacket);

        }

        catch (Exception ex)
        {
            //Do something
        }
    }
    private static Class<?> getNMSClass(String name)
    {
        try
        {
            return Class.forName("net.minecraft.server" + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        }
        catch(ClassNotFoundException ex)
        {
            //Do something
        }
        return null;
    }
}
