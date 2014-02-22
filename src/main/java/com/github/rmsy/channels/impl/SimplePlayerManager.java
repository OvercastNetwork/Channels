package com.github.rmsy.channels.impl;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.PlayerManager;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/** Simple implementation of {@link PlayerManager}. */
public class SimplePlayerManager implements PlayerManager {

    /** Player names mapped to their membership channel */
    private final Map<String, Channel> playerMembershipMap;

    /** Creates a new SimplePlayerManager. */
    public SimplePlayerManager() {
        this.playerMembershipMap = new HashMap<>();
    }

    /**
     * Gets the channel the player is a member of.
     *
     * @param player The player.
     * @return The channel the player is a member of.
     */
    @Override
    public Channel getMembershipChannel(final Player player) {
        return this.playerMembershipMap.get(Preconditions.checkNotNull(player, "player").getName());
    }

    /**
     * Sets the channel the player is a member of. Removes the player from their old membership channel.
     *
     * @param player            The player.
     * @param membershipChannel The channel the player is a member of.
     */
    @Override
    public void setMembershipChannel(Player player, Channel membershipChannel) {
        SimpleChannel oldChannel = (SimpleChannel) this.playerMembershipMap.get(Preconditions.checkNotNull(player, "player"));
        if (oldChannel != null) {
            oldChannel.removeMember(player);
        }
        this.playerMembershipMap.put(player.getName(), Preconditions.checkNotNull(membershipChannel, "channel"));
        ((SimpleChannel) membershipChannel).addMember(player);
    }

    /**
     * Removes the specified {@link org.bukkit.entity.Player} from the store.
     *
     * @param player The {@link org.bukkit.entity.Player} to be removed.
     */
    @Override
    public void removePlayer(Player player) {
        this.playerMembershipMap.remove(Preconditions.checkNotNull(player, "Player").getName());
    }
}
