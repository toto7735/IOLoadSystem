package me.toto7735.commands;

import me.toto7735.IOLoadSystem;
import me.toto7735.data.Data;
import me.toto7735.data.DataManager;
import me.toto7735.listener.Listener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    public Commands() {
        IOLoadSystem.getInstance().getCommand("sign").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!command.getName().equalsIgnoreCase("sign")) return false;
        if (commandSender instanceof ConsoleCommandSender) {
            if (strings.length == 0) return false;
            if (strings[0].equalsIgnoreCase("info")) {
                if (strings.length == 1) for (Data allDat : DataManager.getAllDats())
                    System.out.println(Bukkit.getOfflinePlayer(allDat.getUuid()).getName() + "님은 가로" + allDat.getWidth() + " x " + "세로 " + allDat.getHeight() + " = " + allDat.getWidth() * allDat.getHeight() + "입니다.");
                else if (DataManager.getAllNames().contains(strings[1])) {
                    Data data = DataManager.getData(Bukkit.getOfflinePlayer(strings[1]).getUniqueId());
                    System.out.println(Bukkit.getOfflinePlayer(data.getUuid()).getName() + "님은 가로" + data.getWidth() + " x " + "세로 " + data.getHeight() + " = " + data.getWidth() * data.getHeight() + "입니다.");
                }
            } else if (strings[0].equalsIgnoreCase("export")) {
                try {
                    DataManager.export();
                    System.out.println("출력하였습니다.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        } else if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;
        if (strings[0].equalsIgnoreCase("create")) {
            if (!player.isOp()) {
                player.sendMessage("알 수 없는 명령어입니다.");
                return false;
            }
            Listener.addCreateMap(player);
            player.sendMessage("표지판을 우클릭하여 연동하십시오.");
        } else if (strings[0].equalsIgnoreCase("reload")) {
            if (!player.isOp()) {
                player.sendMessage("알 수 없는 명령어입니다.");
                return false;
            }
            IOLoadSystem.getInstance().reloadConfig();
            player.sendMessage("[V] 유저 데이터 연동 완료");
        } else if (strings[0].equalsIgnoreCase("info")) {
            if (!player.isOp()) {
                Data data = DataManager.getData(player.getUniqueId());
                player.sendMessage(player.getName() + "님은 가로" + data.getWidth() + " x " + "세로 " + data.getHeight() + " = " + data.getWidth() * data.getHeight() + "입니다.");
            } else {
                if (strings.length == 1) for (Data allDat : DataManager.getAllDats()) player.sendMessage(Bukkit.getOfflinePlayer(allDat.getUuid()).getName() + "님은 가로" + allDat.getWidth() + " x " + "세로 " + allDat.getHeight() + " = " + allDat.getWidth() * allDat.getHeight() + "입니다.");
                else if (DataManager.getAllNames().contains(strings[1])) {
                    Data data = DataManager.getData(Bukkit.getOfflinePlayer(strings[1]).getUniqueId());
                    player.sendMessage(Bukkit.getOfflinePlayer(data.getUuid()).getName() + "님은 가로" + data.getWidth() + " x " + "세로 " + data.getHeight() + " = " + data.getWidth() * data.getHeight() + "입니다.");
                }
            }
        }
        return false;
    }
}
