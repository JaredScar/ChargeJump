package com.core.wolfbadger.charge.jump;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: MayoDwarf
 * Date: 6/30/14
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main extends JavaPlugin implements Listener {
    private HashMap<String, Double> heldDown;
    public void onEnable() {
        this.heldDown = new HashMap<String, Double>();
        this.getServer().getPluginManager().registerEvents(this, this);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()) {
                    if(players.isSneaking()) {
                        if(heldDown.containsKey(players.getName())) {
                            heldDown.put(players.getName(), heldDown.get(players.getName())+0.2);
                        } else {
                            heldDown.put(players.getName(), 0.2);
                        }
                    }
                }
            }
        }, 0, 20);
    }
    public void onDisable() {}
    @EventHandler
    public void toggleSneak(PlayerToggleSneakEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        if(e.isSneaking()) {
            if(!heldDown.containsKey(p.getName())) {
                heldDown.put(p.getName(), 0.2);
            }
        } else {
            if(heldDown.containsKey(p.getName())) {
                p.setVelocity(p.getVelocity().setY(heldDown.get(p.getName())+p.getVelocity().getY()));
                heldDown.remove(p.getName());
            }
        }
    }
}
