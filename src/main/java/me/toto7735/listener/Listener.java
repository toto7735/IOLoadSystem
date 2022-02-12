package me.toto7735.listener;

import me.toto7735.IOLoadSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import me.toto7735.data.Data;
import me.toto7735.data.DataManager;

import java.util.ArrayList;
import java.util.List;

public class Listener implements org.bukkit.event.Listener {

    private static List<Player> createMap = new ArrayList<>();

    public Listener() {
        Bukkit.getPluginManager().registerEvents(this, IOLoadSystem.getInstance());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getClickedBlock() == null || !event.getClickedBlock().getType().toString().contains("SIGN")) return;
        Player player = event.getPlayer();
        if (createMap.contains(player)) {
            createMap.remove(player);
            event.getClickedBlock().setMetadata("infoSign", new FixedMetadataValue(IOLoadSystem.getInstance(), "true"));
            player.sendMessage("표지판을 성공적으로 생성하였습니다.");
        } else if (event.getClickedBlock().hasMetadata("infoSign")) {
            Data data = DataManager.getData(player.getUniqueId());
            player.sendMessage(player.getName() + "님은 가로" + data.getWidth() + " x " + "세로 " + data.getHeight() + " = " + data.getWidth() * data.getHeight() + "입니다.");
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().hasMetadata("infoSign")) event.getBlock().removeMetadata("infoSign", IOLoadSystem.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (DataManager.getData(event.getPlayer().getUniqueId()) == null) DataManager.saveData(new Data(event.getPlayer().getUniqueId(), 100, 100));
    }

    public static void addCreateMap(Player player) {
        createMap.add(player);
    }

}
