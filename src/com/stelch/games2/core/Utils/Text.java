package com.stelch.games2.core.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import com.stelch.games2.core.Lang.en;
import net.minecraft.server.v1_14_R1.ChatMessageType;
import net.minecraft.server.v1_14_R1.IChatBaseComponent;
import net.minecraft.server.v1_14_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.Iterator;

public class Text {
        public static enum MessageType {
            TEXT_CHAT,
            TITLE,
            DRAGON_BAR,
            ACTION_BAR;

            private MessageType() {}
        }

        public static enum NotifyType {
            JOIN,
            LEAVE,
            KICK,
            GAME,
            ANNOUNCE,
            PERMISSION,
            ADMIN;

            private NotifyType() {}
        }

        public static boolean sendAll(String msg, MessageType type) {
            Player p;
            for (Iterator localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext(); sendMessage(p, msg, type)) {
                p = (Player) localIterator.next();
            }
            return true;
        }

        public static boolean sendMessage(Player p, String msg, NotifyType type) {
            return sendMessage(p, "&9" + type + "> &7" + msg, MessageType.TEXT_CHAT);
        }

        public static boolean sendMessage(Player p, String msg, MessageType type) {
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            switch (type) {
                case TEXT_CHAT:
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg + "&r"));
                    return true;
                case TITLE:
                    return true;
                case DRAGON_BAR:
                    return true;
                case ACTION_BAR:
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + msg + "\"}"), ChatMessageType.GAME_INFO));
                    return true;
            }
            return false;
        }

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
