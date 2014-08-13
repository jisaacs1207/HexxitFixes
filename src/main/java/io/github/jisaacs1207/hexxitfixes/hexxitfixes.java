package io.github.jisaacs1207.hexxitfixes;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.massivecraft.factions.P;











import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;



public final class hexxitfixes extends JavaPlugin implements Listener{
	@Override
	public void onEnable() {
		getLogger().info("Hexxit Fixes is now fixing Hexxit. How redundant!");
		getServer().getPluginManager().registerEvents(this, this);
		if(this.getConfig().getBoolean("hasWorldGuard")){
			getWorldGuard();
			getLogger().info("WorldGuard hooked.");
		}	
		if(this.getConfig().getBoolean("hasFactions")){
			getFactions();
			getLogger().info("Factions hooked.");
		}
		saveDefaultConfig();
	}
 
	@Override
	public void onDisable() {
		getLogger().info("Hexxit Fixes is no longer fixing Hexxit.");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){	
		Action pAction = event.getAction();
		if (pAction == Action.RIGHT_CLICK_AIR || pAction == Action.RIGHT_CLICK_BLOCK ) {
			Player player = event.getPlayer();
			int hookshot1 = 6017;
			int hookshot2 = 7632;
			int manualhook = 7634;
			int spiderhook = 7642;
			int inhand = player.getInventory().getItemInHand().getTypeId();
			int passthrough=0;
			if((inhand==hookshot1)||(inhand==hookshot2)||(inhand==manualhook)||(inhand==spiderhook)){
				boolean hookf2 = getFactions().isPlayerAllowedToBuildHere(player, player.getTargetBlock(null, 20).getLocation());
				if(this.getConfig().getBoolean("hasWorldGuard")){
					if(!getWorldGuard().canBuild(player, player.getTargetBlock(null, 20))){
						player.sendMessage(ChatColor.YELLOW + "Your hook has " + ChatColor.RED + "malfunctioned"+ChatColor.YELLOW+"!");
						player.getWorld().playEffect(player.getLocation(), Effect.CLICK1, 1);
						event.setCancelled(true);
						passthrough=1;
						
					}
				}
				if(passthrough==0){
					if(this.getConfig().getBoolean("hasFactions")){
						if(!hookf2){
							player.sendMessage(ChatColor.YELLOW + "Your hook has " + ChatColor.RED + "malfunctioned"+ChatColor.YELLOW+"!");
							player.getWorld().playEffect(player.getLocation(), Effect.CLICK1, 1);
							event.setCancelled(true);
							passthrough=1;
						}
					}
				}
				if(passthrough==0){
					if(player.canSee(this.getTarget(player))){
						player.sendMessage(ChatColor.YELLOW + "Your hook has " + ChatColor.RED + "malfunctioned"+ChatColor.YELLOW+"!");
						player.getWorld().playEffect(player.getLocation(), Effect.CLICK1, 1);
						event.setCancelled(true);
					}
				}
				
			}
		}
		
			
	}
		
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        //May disable Plugin
	        return null; // Maybe you want throw an exception instead
	    }
	    
	    return (WorldGuardPlugin) plugin;
	}
	
	private P getFactions() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("Factions");

	    // Factions may not be loaded
	    if (plugin == null || !(plugin instanceof P)) {
	        //May disable Plugin
	        return null; // Maybe you want throw an exception instead
	    }
	    
	    return (P) plugin;
	}
	public Player getTarget(Player player) {
        List<Entity> nearbyE = player.getNearbyEntities(20, 20, 20);
        ArrayList<Player> nearPlayers = new ArrayList<Player>();
        for (Entity e : nearbyE) {
            if (e instanceof Player) {
                nearPlayers.add((Player) e);
            }
        }
        Player target = null;
        BlockIterator bItr = new BlockIterator(player, 20);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        while (bItr.hasNext()) {
 
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            for (Player e : nearPlayers) {
                loc = e.getLocation();
                ex = loc.getX();
                ey = loc.getY();
                ez = loc.getZ();
                if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                    target = e;
                    break;
 
                }
            }
 
        }
        return target;
 
    }
}

