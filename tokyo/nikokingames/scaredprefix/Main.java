package tokyo.nikokingames.scaredprefix;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	  public final Logger logger = Logger.getLogger("Minecraft");
	  public static Main plugin;
	  
	  public void onDisable()
	  {
	    PluginDescriptionFile pdfFile = getDescription();
	    this.logger.info(pdfFile.getName() + " The plugin has disabled.");
	    saveConfig();
	  }
	  
	  public void onEnable()
	  {
	    getServer().getPluginManager().registerEvents(this, this);
	    
	    saveDefaultConfig();
	    getConfig();
	    PluginDescriptionFile pdfFile = getDescription();
	    this.logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion() + " The plugin has enabled.");
	    
	    getServer().getPluginManager().registerEvents(new Listener()
	    {
	      @EventHandler
	      public void onPlayerLogin(PlayerLoginEvent event)
	      {
	        if (event.getPlayer().hasPermission("scared.prefix"))
	        {
	          if (Main.this.getConfig().get(event.getPlayer().getName()) == null) {
	            event.getPlayer().setDisplayName(Main.this.getName());
	          }
	          event.getPlayer().setDisplayName("[" + ChatColor.DARK_RED + Main.this.getConfig().get(event.getPlayer().getName()) + ChatColor.WHITE + "]" + event.getPlayer().getName());
	        }
	      }
	    }, this);
	  }
	  
	  public boolean onCommand(CommandSender sender, Command mcd, String commandLabel, String[] args) {
	    if ((commandLabel.equalsIgnoreCase("setprefix")) && (args.length == 2))
	    {
	      if ((sender instanceof Player))
	      {
	        Player target = Bukkit.getServer().getPlayer(args[0]);
	        if (target == null)
	        {
	          Player player = (Player)sender;
	          player.sendMessage(ChatColor.RED + "The player not found.");
	          return true;
	        }
	        if (target.hasPermission("scared.prefix"))
	        {
	          getConfig().set(sender.getName(), args[1]);
	          getConfig().set("nick" + target.getName(), target.getDisplayName());
	          target.setDisplayName(target.getName());
	          target.setDisplayName("[" + ChatColor.DARK_RED + getConfig().get(sender.getName()) + ChatColor.WHITE + "]" + target.getDisplayName());
	          saveConfig();
	          Player player = (Player)sender;
	          target.sendMessage("Prefix set: " + target.getDisplayName());
	          player.sendMessage("The prefix " + target.getName() + " has changed.");
	          return true;
	        }
	        Player player = (Player)sender;
	        player.sendMessage("The player doesn't have permission to set prefix.");
	        return true;
	      }
	      Player target = Bukkit.getServer().getPlayer(args[0]);
	      if (target == null) {
	        sender.sendMessage(ChatColor.RED + "The player not found.");
	      }
	      if (args[1].length() < 1) {
	        return false;
	      }
	      if (target.hasPermission("scared.prefix"))
	      {
	        getConfig().set(sender.getName(), args[1]);
	        target.setDisplayName("[" + ChatColor.DARK_RED + getConfig().get(sender.getName()) + ChatColor.WHITE + "]" + target.getDisplayName());
	        saveConfig();
	        target.sendMessage("Your prefix has changed by server: " + target.getDisplayName());
	        sender.sendMessage("The prefix " + target.getName() + " has changed.");
	        return true;
	      }
	      sender.sendMessage("The player doesn't have permission to set prefix.");
	      return true;
	    }
	    if ((commandLabel.equalsIgnoreCase("unprefix")) && (args.length == 1))
	    {
	      if ((sender instanceof Player))
	      {
	        Player target = Bukkit.getServer().getPlayer(args[0]);
	        if (target == null)
	        {
	          Player player = (Player)sender;
	          player.sendMessage(ChatColor.RED + "The player not found.");
	          return true;
	        }
	        Player player = (Player)sender;
	        getConfig().set(sender.getName(), null);
	        target.setDisplayName(target.getName());
	        target.sendMessage("Your prefix has deleted by: " + player.getDisplayName());
	        saveConfig();
	        player.sendMessage("The prefix has deleted: " + target.getDisplayName() + ".");
	        return true;
	      }
	      Player target = Bukkit.getServer().getPlayer(args[0]);
	      if (target == null)
	      {
	        sender.sendMessage(ChatColor.RED + "The player not found.");
	        return true;
	      }
	      getConfig().set(sender.getName(), "");
	      target.setDisplayName(target.getName());
	      target.sendMessage("Your prefix has changed by server.");
	      saveConfig();
	      sender.sendMessage("The prefix has deleted: " + target.getDisplayName() + ".");
	      return true;
	    }
	    return false;
	  }
	}