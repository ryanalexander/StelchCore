package com.stelch.games2.core.Utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import com.stelch.games2.core.Lang.en;
import org.bukkit.Bukkit;

public class Text {


    public static BaseComponent[] build(String str) { return (new ComponentBuilder("").appendLegacy(str.replaceAll("&","§")).create()); }

    public static BaseComponent[] build(String text, String hover) { return (new ComponentBuilder("").appendLegacy(text.replaceAll("&","§")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hover)).create())).create()); }

    public static BaseComponent[] build(String text, String hover, String command, ClickEvent.Action action) { return (new ComponentBuilder("").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hover)).create())).event(new ClickEvent(action, command)).appendLegacy(text.replaceAll("&","§"))).create(); }

    public static String format(String str) { return (str).replaceAll("&","§"); }
    public static String format(en str) { return (str.get()).replaceAll("&","§"); }
}
