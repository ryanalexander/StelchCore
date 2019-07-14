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

package com.stelch.games2.core.PlayerUtils;


public enum ranks {
    MEMBER(0,"&e"),
    SUPER(1,"&a"),
    MEGA(2,"&b"),
    ULTRA(3,"&d"),
    BUILDER(8,"&3"),
    ADMIN(10,"&c"),
    DEV(11,"&6"),
    OWNER(12,"&9");
    private int level;
    private String rank;
    public int getLevel() {return this.level;}
    public String getColor() {return this.rank;}
    private ranks(int level,String rank){this.rank = rank.toUpperCase();this.level=level;}
}