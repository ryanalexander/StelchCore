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

package com.Aspy.VersionSupport.API;

import com.stelch.games2.core.Utils.JavaUtils;
import org.apache.commons.lang.Validate;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.stream.Collectors;

public enum GameVersion {

    MINECRAFT_FUTURE(-1, new OrderId(ClientType.JAVA, 27)),
    MINECRAFT_1_14_3(490, new OrderId(ClientType.JAVA, 26), "1.14.3"),
    MINECRAFT_1_14_2(485, new OrderId(ClientType.JAVA, 25), "1.14.2"),
    MINECRAFT_1_14_1(480, new OrderId(ClientType.JAVA, 24), "1.14.1"),
    MINECRAFT_1_14(477, new OrderId(ClientType.JAVA, 23), "1.14"),
    MINECRAFT_1_13_2(404, new OrderId(ClientType.JAVA, 22), "1.13.2"),
    MINECRAFT_1_13_1(401, new OrderId(ClientType.JAVA, 21), "1.13.1"),
    MINECRAFT_1_13(393, new OrderId(ClientType.JAVA, 20), "1.13"),
    MINECRAFT_1_12_2(340, new OrderId(ClientType.JAVA, 19), "1.12.2"),
    MINECRAFT_1_12_1(338, new OrderId(ClientType.JAVA, 18), "1.12.1"),
    MINECRAFT_1_12(335, new OrderId(ClientType.JAVA, 17), "1.12"),
    MINECRAFT_1_11_1(316, new OrderId(ClientType.JAVA, 16), "1.11.2"),
    MINECRAFT_1_11(315, new OrderId(ClientType.JAVA, 15), "1.11"),
    MINECRAFT_1_10(210, new OrderId(ClientType.JAVA, 14), "1.10"),
    MINECRAFT_1_9_4(110, new OrderId(ClientType.JAVA, 13), "1.9.4"),
    MINECRAFT_1_9_2(109, new OrderId(ClientType.JAVA, 12), "1.9.2"),
    MINECRAFT_1_9_1(108, new OrderId(ClientType.JAVA, 11), "1.9.1"),
    MINECRAFT_1_9(107, new OrderId(ClientType.JAVA, 10), "1.9"),
    MINECRAFT_1_8(47, new OrderId(ClientType.JAVA, 9), "1.8"),
    UNKNOWN(-1, new OrderId(ClientType.UNKNOWN, 0));

    private final int id;
    private final OrderId orderId;
    private final String name;


    GameVersion(int id, OrderId orderid){
        this(id,orderid,null);
    }
    GameVersion(int id, OrderId orderId, String name) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
    }

    private static final GameVersion[] allSupported = Arrays.stream(GameVersion.values())
            .filter(GameVersion::isSupported)
            .collect(Collectors.toList())
            .toArray(new GameVersion[0]);

    private static final EnumMap<ClientType, GameVersion[]> byOrderId = new EnumMap<>(ClientType.class);
    static {
        for (ClientType type : ClientType.values()) {
            if (type != ClientType.UNKNOWN) {
                byOrderId.put(type,
                        Arrays.stream(GameVersion.values())
                                .filter(version -> version.getClientType() == type)
                                .sorted((o1, o2) -> o1.orderId.compareTo(o2.orderId))
                                .toArray(size -> new GameVersion[size])
                );
            }
        }
    }

    /**
     * Return ClientType (Eg. JAVA, BEDROCK, UNKNOWN)
     * @return {@link ClientType} of this connection
     */
    public ClientType getClientType() { return this.orderId.type; }

    /**
     * Return the socket id of this version
     * @return socket id of this version
     */
    public int getId() {
        return id;
    }

    /**
     * Return a Human Readable version identifier
     * @return Friendly Version Identifier
     */
    public String getName() {
        return name;
    }

    /**
     * Return if the server can/will support the users version.
     * @return is the protocol version compatible
     */
    public boolean isSupported() {
        return name!=null;
    }

    /**
     * Returns if the game version used by this protocol released after the game version used by another protocol version
     * @param another another protocol version
     * @return true if game version is released after the game version used by another protocol version
     * @throws IllegalArgumentException if protocol versions use different protocol types
     */
    public boolean isAfter(GameVersion another) {
        return orderId.compareTo(another.orderId) > 0;
    }

    /**
     * Returns if the game version used by this protocol released after (or is the same) the game version used by another protocol version
     * @param another another protocol version
     * @return true if game version is released after (or is the same) the game version used by another protocol version
     * @throws IllegalArgumentException if protocol versions use different protocol types
     */
    public boolean isAfterOrEq(GameVersion another) {
        return orderId.compareTo(another.orderId) >= 0;
    }

    /**
     * Returns if the game version used by this protocol released before the game version used by another protocol version
     * @param another another protocol version
     * @return true if game version is released before the game version used by another protocol version
     * @throws IllegalArgumentException if protocol versions use different protocol types
     */
    public boolean isBefore(GameVersion another) {
        return orderId.compareTo(another.orderId) < 0;
    }

    /**
     * Returns if the game version used by this protocol released before (or is the same) the game version used by another protocol version
     * @param another another protocol version
     * @return true if game version is released before (or is the same) the game version used by another protocol version
     * @throws IllegalArgumentException if protocol versions use different protocol types
     */
    public boolean isBeforeOrEq(GameVersion another) {
        return orderId.compareTo(another.orderId) <= 0;
    }

    /**
     * Returns if the game version used by this protocol released in between (or is the same) of other game versions used by others protocol versions
     * @param one one protocol version
     * @param another another protocol version
     * @return true if the game version used by this protocol released in between (or is the same) of other game versions used by others protocol versions
     * @throws IllegalArgumentException if protocol versions use different protocol types
     */
    public boolean isBetween(GameVersion one, GameVersion another) {
        return (isAfterOrEq(one) && isBeforeOrEq(another)) || (isBeforeOrEq(one) && isAfterOrEq(another));
    }

    /**
     * Returns protocol version that is used by the game version released after game version used by this protocol <br>
     * Returns null if next game version doesn't exist
     * @return protocol version that is used by the game version released after game version used by this protocol
     * @throws IllegalArgumentException if protocol type is {@link ClientType#UNKNOWN}
     */
    public GameVersion next() {
        Validate.isTrue(getClientType() != ClientType.UNKNOWN, "Can't get next version for unknown protocol type");
        return JavaUtils.getFromArrayOrNull(byOrderId.get(getClientType()), orderId.id + 1);
    }

    /**
     * Returns protocol version that is used by the game version released before game version used by this protocol <br>
     * Returns null if previous game version doesn't exist
     * @return protocol version that is used by the game version released before game version used by this protocol
     * @throws IllegalArgumentException if protocol type is {@link ClientType#UNKNOWN}
     */
    public GameVersion previous() {
        Validate.isTrue(getClientType() != ClientType.UNKNOWN, "Can't get next version for unknown protocol type");
        return JavaUtils.getFromArrayOrNull(byOrderId.get(getClientType()), orderId.id - 1);
    }

    /**
     * Returns all protocol versions that are between specified ones (inclusive) <br>
     * Throws {@link IllegalArgumentException} if protocol versions types are not the same or one of the types is {@link ClientType#UNKNOWN}
     * @param one one protocol version
     * @param another one protocol version
     * @return all protocol versions that are between specified ones (inclusive)
     * @throws IllegalArgumentException if protocol types are different, or one of the protocol types is {@link ClientType#UNKNOWN}
     */
    public static GameVersion[] getAllBetween(GameVersion one, GameVersion another) {
        ClientType type = one.getClientType();
        Validate.isTrue(type == another.getClientType(), "Can't get versions between different protocol types");
        Validate.isTrue(type != ClientType.UNKNOWN, "Can't get versions for unknown protocol type");
        GameVersion[] versions = byOrderId.get(type);
        int startId = Math.min(one.orderId.id, another.orderId.id);
        int endId = Math.max(one.orderId.id, another.orderId.id);
        GameVersion[] between = new GameVersion[(endId - startId) + 1];
        for (int i = startId; i <= endId; i++) {
            between[i - startId] = versions[i];
        }
        return between;
    }

    /**
     * Returns all protocol versions that are after specified one (inclusive) <br>
     * @param version protocol version
     * @return all protocol versions that are after specified one (including this one)
     * @throws IllegalArgumentException if {@link GameVersion#getAllBetween(GameVersion, GameVersion)} throws one
     */
    public static GameVersion[] getAllAfterI(GameVersion version) {
        return getAllBetween(version, getLatest(version.getClientType()));
    }

    /**
     * Returns all protocol versions that are after specified one (exclusive)
     * @param version protocol version
     * @return all protocol versions that are after specified one  (exclusive) or empty array if no protocol versions exist after this one
     * @throws IllegalArgumentException if {@link GameVersion#getAllBetween(GameVersion, GameVersion)} throws one
     */
    public static GameVersion[] getAllAfterE(GameVersion version) {
        GameVersion next = version.next();
        if ((next == null) || !next.isSupported()) {
            return new GameVersion[0];
        } else {
            return getAllAfterI(next);
        }
    }

    /**
     * Returns all protocol versions that are before specified one (inclusive)
     * @param version protocol version
     * @return all protocol versions that are before specified one (including this one)
     * @throws IllegalArgumentException if {@link GameVersion#getAllBetween(GameVersion, GameVersion)} throws one
     */
    public static GameVersion[] getAllBeforeI(GameVersion version) {
        return getAllBetween(getOldest(version.getClientType()), version);
    }

    /**
     * Returns all protocol versions that are before specified one (exclusive)
     * @param version protocol version
     * @return all protocol versions that are before specified one  (exclusive) or empty array if no protocol versions exist after this one
     * @throws IllegalArgumentException if {@link GameVersion#getAllBetween(GameVersion, GameVersion)} throws one
     */
    public static GameVersion[] getAllBeforeE(GameVersion version) {
        GameVersion prev = version.previous();
        if ((prev == null) || !prev.isSupported()) {
            return new GameVersion[0];
        } else {
            return getAllBeforeI(prev);
        }
    }

    /**
     * Returns latest supported protocol version for specified protocol type
     * @param type protocol type
     * @return latest supported protocol version for specified protocol type
     * @throws IllegalArgumentException if protocol type has no supported protocol versions
     */
    public static GameVersion getLatest(ClientType type) {
        switch (type) {
            case JAVA: {
                return GameVersion.MINECRAFT_1_14_3;
            }
            default: {
                throw new IllegalArgumentException(String.format("No supported versions for protocol type {0}", type));
            }
        }
    }

    /**
     * Returns oldest supported protocol version for specified protocol type
     * @param type protocol type
     * @return oldest supported protocol version for specified protocol type
     * @throws IllegalArgumentException if protocol type has no supported protocol versions
     */
    public static GameVersion getOldest(ClientType type) {
        switch (type) {
            case JAVA: {
                return MINECRAFT_1_8;
            }
            default: {
                throw new IllegalArgumentException(String.format("No supported versions for protocol type {0}", type));
            }
        }
    }

    /**
     * Returns all supported protocol versions
     * @return all supported protocol versions
     */
    public static GameVersion[] getAllSupported() {
        return allSupported.clone();
    }

    private static class OrderId implements Comparable<OrderId> {

        private final ClientType type;
        private final int id;

        public OrderId(ClientType type, int id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public int compareTo(OrderId o) {
            Validate.isTrue(this.type != ClientType.UNKNOWN, "Can't compare unknown protocol type");
            Validate.isTrue(o.type != ClientType.UNKNOWN, "Can't compare with unknown protocol type");
            Validate.isTrue(this.type == o.type, "Cant compare order from different types");
            return Integer.compare(id, o.id);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!getClass().equals(obj.getClass())) {
                return false;
            }
            return id == ((OrderId) obj).id;
        }

        @Override
        public int hashCode() {
            return id;
        }

        public int getId() {
            return id;
        }
    }
}
