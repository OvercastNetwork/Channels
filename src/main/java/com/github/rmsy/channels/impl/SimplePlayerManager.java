package com.github.rmsy.channels.impl;

import com.github.rmsy.channels.Channel;
import com.github.rmsy.channels.PlayerManager;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of {@link PlayerManager}.
 */
public class SimplePlayerManager implements PlayerManager {

    /**
     * Players mapped to their membership channels.
     */
    private final Map<Player, Channel> playerMembershipMap;

    /**
     * Creates a new SimplePlayerManager.
     */
    public SimplePlayerManager() {
        this.playerMembershipMap = new HashMap<Player, Channel>();
    }

    /**
     * Gets the channel the player is a member of.
     *
     * @param player The player.
     * @return The channel the player is a member of.
     */
    @Override
    public Channel getMembershipChannel(@Nonnull final Player player) {
        return this.playerMembershipMap.get(Preconditions.checkNotNull(player, "player"));
    }

    /**
     * Sets the channel the player is a member of. Removes the player from their old membership channel.
     *
     * @param player            The player.
     * @param membershipChannel The channel the player is a member of.
     */
    @Override
    public void setMembershipChannel(@Nonnull Player player, @Nonnull Channel membershipChannel) {
        this.playerMembershipMap.put(Preconditions.checkNotNull(player, "player"), Preconditions.checkNotNull(membershipChannel, "channel"));
        ((SimpleChannel) membershipChannel).addMember(player);
        SimpleChannel oldChannel = (SimpleChannel) this.playerMembershipMap.get(player);
        if (oldChannel != null) {
            oldChannel.removeMember(player);
        }
    }
}
