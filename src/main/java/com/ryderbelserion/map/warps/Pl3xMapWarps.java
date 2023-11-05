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
package com.ryderbelserion.map.warps;

import java.util.Arrays;
import com.ryderbelserion.map.warps.hook.Hook;
import com.ryderbelserion.map.warps.listener.Pl3xMapListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Pl3xMapWarps extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getServer().getPluginManager().isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        findHooks();

        getServer().getPluginManager().registerEvents(new Pl3xMapListener(this), this);
    }

    @Override
    public void onDisable() {
        Hook.clear();
    }

    public void reload() {
        Hook.clear();
        findHooks();
    }

    public void findHooks() {
        Arrays.stream(Hook.Impl.values()).forEach(impl -> {
            if (getServer().getPluginManager().isPluginEnabled(impl.getPluginName())) {
                getLogger().info("Hooking into " + impl.getPluginName());
                Hook.add(impl);
            }
        });
    }
}