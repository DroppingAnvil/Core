package com.seamlessmc.v1.core;

import us.anvildevelopment.v1.util1.database.SQLConnector;

public class Configuration {
    //TODO Integrate with configure
    private static SQLConnector configurationsConnector;
    public static String defaultEco = "";
    public static String commonPrefix = "&8";
    public static String uncommonPrefix = "&7";
    public static String rarePrefix = "&d";
    public static String epicPrefix = "&5&l";
    public static String legendaryPrefix = "&6&l&o";
    public static String defaultTag = "None";
    public static Integer consumers = 0;
    public static String sql_username = "SeamlessBeta";
    public static String sql_password = "25565";
    public static String sql_address = "mysqlx://localhost:33060";
    public static String sql_schema = "Beta";


    public static void save() {

    }
}
