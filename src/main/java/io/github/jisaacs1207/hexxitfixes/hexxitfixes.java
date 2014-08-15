package io.github.jisaacs1207.hexxitfixes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.massivecraft.factions.P;

import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
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
	
	

	
	
	
	
	
	// Player Interactions   ========================================================================================================================

	
	
	
	
	
	
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event){	
		Action pAction = event.getAction();

		
// If player right clicks	
		if (pAction == Action.RIGHT_CLICK_AIR || pAction == Action.RIGHT_CLICK_BLOCK ) {
			Player player = event.getPlayer();

// Hookshot Items
			int hookshot1 = 6017;
			int hookshot2 = 7632;
			int manualhook = 7634;
			int longshot = 7633;
			int spiderhook = 7642;

// Checks item in hands
			int inhand = player.getInventory().getItemInHand().getTypeId();

// Passthrough is a fake boolean to check if the event has been cancelled yet. 0=no 1=yes
			int passthrough=0;
			
// Hookshot fix ===================================================================================================================================
			if((inhand==hookshot1)||(inhand==hookshot2)||(inhand==manualhook)||(inhand==spiderhook)||(inhand==longshot)){
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
						if(!getFactions().isPlayerAllowedToBuildHere(player, player.getTargetBlock(null, 20).getLocation())){
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
//==================================================================================================================================================
		    
			
			
			
			
// Soj Staff Fix ===================================================================================================================================
			int sojstaff = 6428;
		    if(inhand==sojstaff){
		    	if(this.getConfig().getBoolean("hasWorldGuard")){
					if(!getWorldGuard().canBuild(player, player.getTargetBlock(null, 20))){
						player.sendMessage(ChatColor.YELLOW + "Your staff has " + ChatColor.RED + "malfunctioned"+ChatColor.YELLOW+"!");
						player.getWorld().playEffect(player.getLocation(), Effect.CLICK1, 1);
						event.setCancelled(true);
						passthrough=1;
						
					}
				}
				if(passthrough==0){
					if(this.getConfig().getBoolean("hasFactions")){
						if(!getFactions().isPlayerAllowedToBuildHere(player, player.getTargetBlock(null, 20).getLocation())){
							player.sendMessage(ChatColor.YELLOW + "Your staff has " + ChatColor.RED + "malfunctioned"+ChatColor.YELLOW+"!");
							player.getWorld().playEffect(player.getLocation(), Effect.CLICK1, 1);
							event.setCancelled(true);
							passthrough=1;
						}
					}
				}
		    }
// ==================================================================================================================================================
		    int frostbow = 6110;
		    if(inhand==frostbow){
		    	ItemStack old = new ItemStack(event.getPlayer().getItemInHand().getTypeId(), event.getPlayer().getItemInHand().getAmount() - 1);
		        event.getPlayer().setItemInHand(old);
		    }
		    
		    
// Frostbow Fix =====================================================================================================================================
//==================================================================================================================================================
		}
		
			
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerItemHeld(PlayerItemHeldEvent  event) {
		int newSlot = event.getNewSlot();
		if(event.getPlayer().getInventory().getItem(newSlot).getTypeId()==6110){
			ItemStack newbow = new ItemStack(261, 1, (short)0);
			setName(newbow, "Frost Bow");
			event.getPlayer().getInventory().setItem(newSlot, newbow);
		}
		if(event.getPlayer().getInventory().getItemInHand().getTypeId()==6110){
			int slot = event.getPreviousSlot();
			ItemStack newbow = new ItemStack(261, 1, (short)0);
			setName(newbow, "Frost Bow");
			event.getPlayer().getInventory().setItem(slot, newbow);
			
		}
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerFireBow(EntityShootBowEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player)event.getEntity();
			ItemStack bowStack = player.getItemInHand();
			ItemMeta bowMeta = bowStack.getItemMeta();
			String bowName = bowMeta.getDisplayName();
			if(bowName.equalsIgnoreCase("Frost Bow")){
				event.setProjectile(player.launchProjectile(org.bukkit.entity.Snowball.class));
				
			}        
	    }	
	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void projectileHit(EntityDamageByEntityEvent event) {
		String theDamager = String.valueOf(event.getDamager());
		if(theDamager.equalsIgnoreCase("CraftSnowball")){
			LivingEntity victim = (LivingEntity)event.getEntity();
			victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 3));
			event.setDamage(1);
		}
	}
	
	
	
	
	
	
	
	
	
	
// Land Protections Calls =================================================================================================================================	
	
	
	
	
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
	
	
	
	// Finds name of player target ==============================================================================================================
	
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
	public ItemStack setName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
	
}

