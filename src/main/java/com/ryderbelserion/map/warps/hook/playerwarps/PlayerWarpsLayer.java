/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryderbelserion.map.warps.hook.playerwarps;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class PlayerWarpsLayer extends WorldLayer {
    public static final String KEY = "playerwarps_warps";

    private final PlayerWarpsHook playerWarpsHook;

    public PlayerWarpsLayer(@NotNull PlayerWarpsHook playerWarpsHook, @NotNull World world) {
        super(KEY, world, () -> PlayerWarpsConfig.LAYER_LABEL);
        this.playerWarpsHook = playerWarpsHook;

        setShowControls(PlayerWarpsConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(PlayerWarpsConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(PlayerWarpsConfig.LAYER_UPDATE_INTERVAL);
        setPriority(PlayerWarpsConfig.LAYER_PRIORITY);
        setZIndex(PlayerWarpsConfig.LAYER_ZINDEX);
        setCss(PlayerWarpsConfig.LAYER_CSS);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.playerWarpsHook.getWarps(getWorld());
    }
}