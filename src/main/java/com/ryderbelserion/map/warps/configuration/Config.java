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
package com.ryderbelserion.map.warps.configuration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import libs.org.simpleyaml.configuration.ConfigurationSection;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Tooltip;
import com.ryderbelserion.map.warps.Pl3xMapWarps;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Config extends AbstractConfig {

    public static void registerIcon(String image) {
        Pl3xMapWarps plugin = Pl3xMapWarps.getPlugin(Pl3xMapWarps.class);
        String filename = String.format("icons%s%s.png", File.separator, image);
        File file = new File(plugin.getDataFolder(), filename);

        if (!file.exists()) {
            plugin.saveResource(filename, false);
        }

        try {
            String key = String.format("pl3xmap_warps_%s", image);
            Pl3xMap.api().getIconRegistry().register(new IconImage(key, ImageIO.read(file), "png"));
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to register icon (" + image + ") " + filename);
            e.printStackTrace();
        }
    }

    @Override
    protected @Nullable Object get(@NotNull String path) {
        Object value = getConfig().get(path);

        if (value == null) {
            return null;
        }

        //noinspection EnhancedSwitchMigration
        switch (path) {
            case "marker.icon.size":
            case "marker.icon.anchor":
            case "marker.icon.shadow-size":
            case "marker.icon.shadow-anchor":
                if (value instanceof ConfigurationSection section) {
                    return Vector.of(section.getDouble("x"), section.getDouble("z"));
                } else if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Double> vector = (Map<String, Double>) value;
                    return Vector.of(vector.get("x"), vector.get("z"));
                }
                break;
            case "marker.tooltip.offset":
            case "marker.popup.offset":
            case "marker.popup.auto-pan.padding.all":
            case "marker.popup.auto-pan.padding.top-left":
            case "marker.popup.auto-pan.padding.bottom-right":
                if (value instanceof ConfigurationSection section) {
                    return Point.of(section.getInt("x"), section.getInt("z"));
                } else if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Integer> point = (Map<String, Integer>) value;
                    return Point.of(point.get("x"), point.get("z"));
                }

                break;
            case "marker.tooltip.direction":
                return Tooltip.Direction.valueOf(String.valueOf(value).toUpperCase(Locale.ROOT));
        }

        return super.get(path);
    }

    @Override
    protected void set(@NotNull String path, @Nullable Object value) {
        if (value instanceof Point point) {
            value = Map.of("x", point.x(), "z", point.z());
        } else if (value instanceof Vector vector) {
            value = Map.of("x", vector.x(), "z", vector.z());
        } else if (value instanceof Tooltip.Direction direction) {
            value = direction.name();
        }

        super.set(path, value);
    }
}