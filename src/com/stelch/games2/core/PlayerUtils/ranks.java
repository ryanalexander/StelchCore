package com.stelch.games2.core.PlayerUtils;


public enum ranks {
    MEMBER(0,"&e"),
    SUPER(1,"&a"),
    MEGA(2,"&b"),
    ULTRA(3,"&d"),
    BUILDER(8,"&3"),
    ADMIN(10,"&c"),
    OWNER(11,"&9");
    private int level;
    private String rank;
    public int getLevel() {return this.level;}
    public String getColor() {return this.rank;}
    private ranks(int level,String rank){this.rank = rank.toUpperCase();this.level=level;}
}