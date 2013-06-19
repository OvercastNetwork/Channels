package com.github.rmsy.channels;

import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Set;

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
     * Gets the channels the player is listening to.
     *
     * @param player The player.
     * @return The channels the player is listening to.
     */
    @Nonnull
    public Set<Channel> getListeningChannels(@Nonnull final Player player);

    /**
     * Sets the channel the player is a member of. Removes the player from their old membership channel.
     *
     * @param player            The player.
     * @param membershipChannel The channel the player is a member of.
     */
    public void setMembershipChannel(@Nonnull final Player player, @Nonnull Channel membershipChannel);

    /**
     * Adds the player as a listener of the specified channel.
     *
     * @param player  The player.
     * @param channel The channel.
     * @return Whether or not the player was added as a listener.
     */
    public boolean addListener(@Nonnull final Player player, @Nonnull final Channel channel);

    /**
     * Removes the player as a listener from the specified channel.
     *
     * @param player  The player.
     * @param channel The channel.
     * @return Whether or not the player was removed as a listener.
     */
    public boolean removeListener(@Nonnull final Player player, @Nonnull final Channel channel);
}
