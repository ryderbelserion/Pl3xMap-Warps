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
package net.pl3x.map.warps.hook.essentials;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class EssentialsLayer extends WorldLayer {
    public static final String KEY = "essentials_warps";

    private final EssentialsHook essentialsHook;

    public EssentialsLayer(@NotNull EssentialsHook essentialsHook, @NotNull World world) {
        super(KEY, world, () -> EssentialsConfig.LAYER_LABEL);
        this.essentialsHook = essentialsHook;

        setShowControls(EssentialsConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(EssentialsConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(EssentialsConfig.LAYER_UPDATE_INTERVAL);
        setPriority(EssentialsConfig.LAYER_PRIORITY);
        setZIndex(EssentialsConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.essentialsHook.getWarps(getWorld());
    }
}
