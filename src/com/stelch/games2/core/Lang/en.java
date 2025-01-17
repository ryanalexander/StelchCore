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

package com.stelch.games2.core.Lang;

public enum en {
    PLAYER_JOIN("&aJoin> &7Welcome &e%s&7!"),
    GAME_PLAYER_JOIN("&aJoin> &7Welcome &9%s&7, ( &9%s &7/ &9%s &7) for game to start"),
    GAME_BEGIN_IN("&7Starting in &e%s&7 seconds"),

    PERM_NO_PERMISSION("&cPerms> &7You are not permitted to execute that command."),

    PLAYER_OFFLINE("&cError> &7The player &e%s&7 is not currently online."),

    COMMAND_NON_PLAYER("&cError> &7That command may only be executed by in-game players.");

    private String message;
    public String get() {return this.message;}
    en(String message){this.message = message;}
}