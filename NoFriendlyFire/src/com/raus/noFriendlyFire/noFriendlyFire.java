

package com.raus.noFriendlyFire;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class noFriendlyFire extends JavaPlugin {
    static String root_dir = "plugins" + File.separator + "FriendlyFire";
    static String messages_file = root_dir + File.separator + "messages.yml";


    //TODO: HashSet
    static HashMap<UUID, Boolean> friendlyFire = new HashMap<>();
    static Logger log;
    private  String[] messages;

    public static boolean isNoFF(UUID user){
        return friendlyFire.getOrDefault(user, true);
    }

    public static void setNoFF(UUID user, boolean state){
        friendlyFire.put(user,state);
    }

    public void onEnable() {
        log = getLogger();
        loadMessages();
        PluginCommand command = this.getCommand("noff");
        if (command!=null){
            command.setExecutor(new ToggleCommand(this));
            command.setTabCompleter(new TabHelper(this));
        }
        else Error("Unable to register Executor and TabCompleter for \"noff\" command!");

        this.getServer().getPluginManager().registerEvents(new FriendlyFireListener(this), this);
    }

    protected static synchronized void Log(String entry)
    {
        if (log==null) return;
        log.info(entry);
    }
    protected static synchronized void Error(String entry)
    {
        if (log==null) return;
        log.severe(entry);
    }

    private void loadMessages()
    {
        Messages[] messageIDs = Messages.values();
        this.messages = new String[Messages.values().length];

        HashMap<String, CustomizableMessage> defaults = new HashMap<>();
        this.addDefault(defaults, Messages.PROTECTION_DISABLED, "$7 You have $a$nenabled$7 FriendlyFire.", null);
        this.addDefault(defaults, Messages.PROTECTION_ENABLED, "$7 You have $c$ndisabled$7 FriendlyFire.", null);
        this.addDefault(defaults, Messages.PLAYER_ONLY_COMMAND, "[NoFF] This command cannot be run from the console.", null);
        this.addDefault(defaults, Messages.NO_PERMS,  "[NoFF] You do not have permission to run this command.", null);
        this.addDefault(defaults, Messages.VERSION_INFO,  "[NoFF] Version: {0}", "Args: 0-version");


        //load the config file
        FileConfiguration msg_config = YamlConfiguration.loadConfiguration(new File(messages_file));

        //for each message ID
        for (Messages messageID : messageIDs)
        {
            //get default for this message
            CustomizableMessage messageData = defaults.get(messageID.name());

            //if default is missing, log an error and use some fake data for now so that the plugin can run
            if (messageData == null)
            {
                Log("Missing message for " + messageID.name() + ".  Please contact the developer.");
                messageData = new CustomizableMessage(messageID, "Missing message!  ID: " + messageID.name() + ".  Please contact a server admin.", null);
            }

            //read the message from the file, use default if necessary
            this.messages[messageID.ordinal()] = msg_config.getString("Messages." + messageID.name() + ".Text", messageData.text);
            msg_config.set("Messages." + messageID.name() + ".Text", this.messages[messageID.ordinal()]);

            //support color codes
            this.messages[messageID.ordinal()] = this.messages[messageID.ordinal()].replace('$', (char) 0x00A7);


            if (messageData.notes != null)
            {
                messageData.notes = msg_config.getString("Messages." + messageID.name() + ".Notes", messageData.notes);
                msg_config.set("Messages." + messageID.name() + ".Notes", messageData.notes);
            }
        }

        //save any changes
        try
        {
            msg_config.options().header("Use a YAML editor like NotepadPlusPlus to edit this file.  \nAfter editing, back up your changes before reloading the server in case you made a syntax error.  \nUse dollar signs ($) for formatting codes, which are documented here: http://minecraft.gamepedia.com/Formatting_codes");
            msg_config.save(messages_file);
        }
        catch (IOException exception)
        {
            Log("Unable to write to the configuration file at \"" + messages_file + "\"");
        }

        defaults.clear();
        System.gc();
    }

    private void addDefault(HashMap<String, CustomizableMessage> defaults,
                            Messages id, String text, String note)
    {
        CustomizableMessage message = new CustomizableMessage(id, text, note);
        defaults.put(id.name(), message);
    }

    protected String getMessage(Messages messageID, String... args)
    {
        String message = messages[messageID.ordinal()];

        for (int i = 0; i < args.length; i++)
        {
            String param = args[i];
            //sendMessage(null, TextMode.Info,message);
            if (param==null) param = "null";
            message = message.replace("{" + i + "}", param);
        }

        return message;
    }

    public void onDisable() {

    }

}
