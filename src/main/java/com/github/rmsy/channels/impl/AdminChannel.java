package com.github.rmsy.channels.impl;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class AdminChannel extends SimpleChannel {

    /**
     * Creates a new SimpleChannel.
     *
     * @param format     The format to be applied to messages.
     * @param node       The permission node that will be broadcast from this channel to.
     * @see com.github.rmsy.channels.impl.SimpleChannel for detailed formatting information.
     */
    public AdminChannel(String format, String node) {
        super(format, new Permission(node, PermissionDefault.OP));
    }
}
