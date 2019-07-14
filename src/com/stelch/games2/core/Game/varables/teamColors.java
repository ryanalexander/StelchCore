/*
 *
 * *
 *  *
 *  * © Stelch Games 2019, distribution is strictly prohibited
 *  *
 *  * Changes to this file must be documented on push.
 *  * Unauthorised changes to this file are prohibited.
 *  *
 *  * @author Ryan Wood
 *  * @since 14/7/2019
 *
 */

package com.stelch.games2.core.Game.varables;

import org.bukkit.Color;

public enum teamColors {
    WHITE("&f",Color.WHITE), PURPLE("&5",Color.PURPLE), RED("&c",Color.RED), BLUE("&b",Color.BLUE), GREEN("&a",Color.GREEN), YELLOW("&e",Color.YELLOW), ORANGE("&6",Color.ORANGE), GREY("&7",Color.GRAY);
    private Color color;
    private String chatColor;
    public String getChatColor() { return chatColor; }
    public Color getColor() {return this.color;}
    teamColors(String chatColor, Color Color){this.color = Color;this.chatColor=chatColor;}
}
