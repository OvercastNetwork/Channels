package com.github.rmsy.channels;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Interface to represent a player manager.
 */
public interface PlayerManager {
    /**
     * Gets the channel the player is a member of.
     *
     * @param player The player.
     * @return The channel the player is a member of.
     */
    public Channel getMembershipChannel(@Nonnull final Player player);

    /**
     * Sets the channel the player is a member of. Removes the player from their old membership channel.
     *
     * @param player            The player.
     * @param membershipChannel The channel the player is a member of.
     */
    public void setMembershipChannel(@Nonnull final Player player, @Nonnull Channel membershipChannel);
}
