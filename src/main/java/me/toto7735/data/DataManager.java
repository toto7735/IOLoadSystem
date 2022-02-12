package me.toto7735.data;

import org.bukkit.Bukkit;
import me.toto7735.IOLoadSystem;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {

    /* 데이터를 가져옵니다 */

    public static Data getData(UUID uuid) {
        if (IOLoadSystem.getInstance().getConfig().get(uuid.toString()) == null) return null;
        return new Data(uuid, IOLoadSystem.getInstance().getConfig().getInt(uuid.toString() + ".width"), IOLoadSystem.getInstance().getConfig().getInt(uuid.toString() + ".height"));
    }

    /* 데이터를 저장합니다 */

    public static void saveData(Data data) {
        IOLoadSystem.getInstance().getConfig().set(data.getUuid().toString() + ".name", Bukkit.getOfflinePlayer(data.getUuid()).getName());
        IOLoadSystem.getInstance().getConfig().set(data.getUuid().toString() + ".width", data.getWidth());
        IOLoadSystem.getInstance().getConfig().set(data.getUuid().toString() + ".height", data.getHeight());
        IOLoadSystem.getInstance().saveConfig(); // 이걸 해주지 않으면 저장이 되지 않습니다.
    }

    /* 모든 데이터를 가져옵니다 */

    public static List<Data> getAllDats() {
        List<Data> dataMap = new ArrayList<>();
        for (String key : IOLoadSystem.getInstance().getConfig().getKeys(false)) dataMap.add(getData(UUID.fromString(key)));
        return dataMap;
    }

    /* 모든 데이터의 플레이어 이름들을 가져옵니다 */

    public static List<String> getAllNames() {
        List<String> nameMap = new ArrayList<>();
        for (String key : IOLoadSystem.getInstance().getConfig().getKeys(false)) nameMap.add(Bukkit.getOfflinePlayer(UUID.fromString(key)).getName());
        return nameMap;
    }

    public static void export() throws Exception {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        if (!new File(IOLoadSystem.getInstance().getDataFolder(), "userdata").exists()) new File(IOLoadSystem.getInstance().getDataFolder(), "userdata").mkdir(); // 중요 이것을 하지 않으면 오류납니다
        File file = new File(IOLoadSystem.getInstance().getDataFolder(), "/userdata/" + date.getYear() + "_" + date.getDayOfMonth() + "_" + date.getDayOfMonth() + "_" + time.getHour() + "" + time.getMinute() + "_" + time.getSecond() + ".txt");
        if (!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(file, true);
        for (Data allDat : DataManager.getAllDats()) fw.write(Bukkit.getOfflinePlayer(allDat.getUuid()).getName() + "님은 가로" + allDat.getWidth() + " x " + "세로 " + allDat.getHeight() + " = " + allDat.getWidth() * allDat.getHeight() + "입니다.\n");
        fw.flush();
        fw.close();
    }

}
