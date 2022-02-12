package me.toto7735;
import org.bukkit.plugin.java.JavaPlugin;
import me.toto7735.commands.Commands;
import me.toto7735.listener.Listener;

public class IOLoadSystem extends JavaPlugin {

    private static IOLoadSystem instance;
    public static IOLoadSystem getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        new Listener();
        new Commands();
    }

    @Override
    public void onDisable() {

    }

}