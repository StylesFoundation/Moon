package com.moon;

import com.moon.account.Account;
import com.moon.account.AccountManager;
import com.moon.command.CommandManager;
import com.moon.command.impl.*;
import com.moon.config.ConfigManager;
import com.moon.connection.Gateway;
import com.moon.events.Event;
import com.moon.events.EventBus;
import com.moon.handlers.*;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.ModuleManager;
import com.moon.module.bind.BindManager;
import com.moon.module.bind.element.BindAction;
import com.moon.module.bind.element.BindType;
import com.moon.module.impl.movement.*;
import com.moon.module.impl.combat.*;
import com.moon.module.impl.exploit.*;
import com.moon.module.impl.visual.*;
import com.moon.module.impl.player.*;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.DraggableHudElement;
import com.moon.notification.NotificationManager;
import com.moon.render.font.bitmap.BitFontManager;
import com.moon.render.font.manager.FontManager;
import com.moon.render.profiler.RenderProfiler;
import com.moon.scripting.Script;
import com.moon.scripting.ScriptManager;
import com.moon.storage.AccountStorage;
import com.moon.storage.FriendStorage;
import com.moon.storage.ModuleStorage;
import com.moon.storage.TargetStorage;
import com.moon.ui.account.AccountManagerScreen;
import com.moon.util.discord.DiscordRPC;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.moon.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class Moon {
   private static Moon INSTANCE;
   private static long startTime;
   private final TaggedLogger logger = Logger.tag("Moon Client");
   private EventBus<Event> eventbus;
   private ModuleManager moduleManager;
   private ModuleStorage moduleStorage;
   private CommandManager commandManager;
   private BindManager bindManager;
   private BitFontManager bitFontManager;
   private FontManager fontManager;
   private WorldRenderHandler worldRenderScheduler;
   private ConfigManager configManager;
   private FriendStorage friendStorage;
   private TargetStorage targetStorage;
   private RenderProfiler profiler;
   private File rootDirectory;
   private ExecutorService executor;
   private SpeedHandler speedHandler;
   private AccountManager accountManager;
   private AccountStorage accountStorage;
   private ScriptManager scriptManager;
   private DiscordRPC discordRpc;
   private AccountManagerScreen accountScreen;
   private NotificationManager notificationManager;
   private Gateway gateway;
   private ServerSyncHandler serverSyncHandler;
   private String username;
   private Account currentAccount;
   private EntityTrackHandler entityTracker;

   public Moon() {
      INSTANCE = this;
      startTime = System.currentTimeMillis();
   }

   public void init() {
      this.logger.info("Starting up Moon client");
      this.rootDirectory = new File("Moon 4.0");
      if (!this.rootDirectory.exists()) {
         this.rootDirectory.mkdirs();
      }

      ViaMCP.getInstance().start(new File(this.rootDirectory, "protocol"));

      this.setEventBus(new EventBus<>())
              .setBitFontManager(new BitFontManager())
              .setFontManager(new FontManager())
              .setModuleManager(new ModuleManager())
              .setModuleStorage(new ModuleStorage(this.rootDirectory.toPath()))
              .setCommandManager(new CommandManager())
              .setBindManager(new BindManager())
              .setWorldRenderScheduler(new WorldRenderHandler())
              .setConfigManager(new ConfigManager())
              .setFriendStorage(new FriendStorage())
              .setTargetStorage(new TargetStorage())
              .setProfiler(new RenderProfiler())
              .setRootDirectory(this.rootDirectory)
              .setExecutor(Executors.newFixedThreadPool(5))
              .setSpeedHandler(new SpeedHandler())
              .setAccountManager(new AccountManager())
              .setAccountStorage(new AccountStorage(this.rootDirectory.toPath()))
              .setScriptManager(new ScriptManager())
              .setNotificationManager(new NotificationManager())
              .setServerSyncHandler(new ServerSyncHandler())
              .setUsername("b")
              .setAccountScreen(new AccountManagerScreen());

      this.eventbus.subscribe(new ScaleHandler());

      this.moduleManager.registerModule(new Sprint("Sprint", "Sprint", "Description", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new HUD("HUD", "HUD", "Draws a heads up display", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new ClickGUI("ClickGUI", "Click Gui", "Displays an interface where you can change the modules settings", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Camera("Camera", "Camera", "Applies multiple changes to your game visually and performance wise", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Effects("Effects", "Effects", "Renders different post processing effects", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new ESP("ESP", "ESP", "Draws an indicator to selected entity types", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new KillAura("KillAura", "Kill Aura", "Attacks entities near you", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new Backtrack("Backtrack", "Backtrack", "Abuses minecraft networking to give you a reach advantage", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new Blink("Blink", "Blink", "Abuses minecraft networking to give you a positioning advantage", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new Tickbase("Tickbase", "Tickbase", "Abuses Timer balance checks to give you a reach advantage", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new InventoryManager("InventoryManager", "Inventory Manager", "Manages your inventory, keeping in clean and organized", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new ChestStealer("ChestStealer", "Chest Stealer", "Steals all important chest content for you", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new Flight("Flight", "Flight", "Lets you fly", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new Speed("Speed", "Speed", "Make you walk faster", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new Velocity("Velocity", "Velocity", "Prevents you from receiving Knockback", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new Disabler("Disabler", "Disabler", "Used to disable parts of servers anti-cheats", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new ScaffoldWalk("ScaffoldWalk", "Scaffold Walk", "Automatically places blocks below you", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new NoRotate("NoRotate", "No Rotate", "Prevents rotation sets on setback", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new PingSpoof("PingSpoof", "Ping Spoof", "Spoofs your ping by delaying packets", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new PacketSniffer("PacketSniffer", "Packet Sniffer", "Allow you to print out packets sent by the server", ModuleCategory.EXPLOIT));
      this.moduleManager.registerModule(new CustomMinecraft("CustomMinecraft", "Custom Minecraft", "Customizes the minecraft hud", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new NoSlowDown("NoSlowDown", "No Slowdown", "Disables slowdown from items and blocks", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new GuiMove("GuiMove", "Gui Move", "Allows you to move in guis and inventories", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new NoFall("NoFall", "No Fall", "Reduces or prevents fall damage", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new MiddleClickFriends("MiddleClickFriends", "Middle Click Friends", "Allows you to add friends by clicking the middle mouse button", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new FastPlace("FastPlace", "Fast Place", "Places blocks faster", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new AutoClicker("AutoClicker", "Auto Clicker", "Automatically clicks for you", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new ThePit("ThePit", "The Pit", "Automatically use pit-items for you", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new Animations("Animations", "Animations", "Changes the minecraft item animation", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new TimeChanger("TimeChanger", "Time Changer", "Changes your local world time", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new AutoTool("AutoTool", "Auto Tool", "Automatically switch tools for you", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new AntiBot("AntiBot", "Anti Bot", "Automatically detect and ignore bots", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new RichPresence("RichPresence", "Rich Presence", "Displays a rich presence in discord", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new TargetHUD("TargetHud", "Target HUD", "Displays a target hud for killaura targets", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Reach("Reach", "Reach", "Modifies the client attacking reach", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new HitBox("HitBox", "Hit Box", "Modifies the client hit-box expansion", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new Timer("Timer", "Timer", "Speeds up your game", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new TargetStrafe("TargetStrafe", "Target Strafe", "Strafes around the current kill aura target", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new AntiVoid("AntiVoid", "Anti Void", "Prevents you from falling into the void", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new Pointers("Pointers", "Pointers", "Draws out of fov pointers towards entities", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new LongJump("LongJump", "Long Jump", "Lets you jump further", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new Spider("Spider", "Spider", "Lets you climb walls", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new Criticals("Criticals", "Criticals", "Allows you to do critical hits on ground", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new AntiResourceExploit("AntiResourceExploit", "Anti Resource Exploit", "Prevents the resource exploit fix from working", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new SessionInformation("SessionInformation", "Session Information", "Displays information about your current session", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new WTap("WTap", "WTap", "Automatically sprints and un-sprints to give your target more knock-back", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new FullBright("FullBright", "Full Bright", "Adds a night vision effect to your player", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Teams("Teams", "Teams", "Excludes team-mates from the KillAura", ModuleCategory.COMBAT));
      this.moduleManager.registerModule(new Capes("Capes", "Capes", "Allows you to have an own cape and one on other users", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new NoJumpDelay("NoJumpDelay", "No Jump Delay", "Removes the jump delay when holding space", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new SafeWalk("SafeWalk", "Safe Walk", "Makes sure you don't fall off while walking", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new AutoRespawn("AutoRespawn", "Auto Respawn", "Respawns you automatically", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new AntiBlind("AntiBlind", "Anti Blind", "Removes any blindness-effects", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new Nametags("Nametags", "Nametags", "Renders information about an entity over the entity", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Glint("Glint", "Glint", "Allows you to customize the glint colors", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new ModelModifier("ModelModifier", "Model Modifier", "Allows you to customize your player model and others", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Trajectories("Trajectories", "Trajectories", "Draws a path to where your projectile will go at", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new SpeedMine("SpeedMine", "Speed Mine", "Allows you to mine blocks faster", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new AntiFireball("AntiFireball", "Anti Fireball", "Automatically attacks nearby fireballs", ModuleCategory.PLAYER));
      this.moduleManager.registerModule(new StorageESP("StorageEsp", "Storage ESP", "Displays storage blocks through walls", ModuleCategory.VISUAL));
      this.moduleManager.registerModule(new Step("Step", "Step", "Allows you to move up blocks faster", ModuleCategory.MOVEMENT));
      this.moduleManager.registerModule(new BedBreaker("BedBreaker", "Bed Breaker", "Breaks beds for you. Only for Watchdog and other bad anticheats right now!", ModuleCategory.PLAYER));

      this.bindManager.getBinds().addAll(this.moduleManager.getRegistry().getModules());
      for (Module module : this.moduleManager.getRegistry().getModules()) {
         module.resolveSettings();
      }

      HUD hudModule = this.moduleManager.getModule(HUD.class);
      if (hudModule != null) {
         for (Module module : this.moduleManager.getRegistry().getModules()) {
            if (module.getSetting("individual-module-color") == null) {
               com.moon.module.setting.impl.ColorSetting colorSetting = com.moon.module.setting.impl.ColorSetting.builder("individual-module-color")
                  .defaults("Arraylist Color", -1)
                  .visibleWhen(new com.moon.module.impl.visual.hud.base.IndividualColorVisibilitySupplier(hudModule))
                  .onChange(new com.moon.module.impl.visual.hud.modulelist.ModuleListElementRemoveCallback(hudModule, hudModule.getModuleListElement()))
                  .build();
               module.getSettings().add(colorSetting);
            }
         }
      }

      this.commandManager.registerCommand(new BindCommand("bind", "Used to bind modules to a key on your keyboard or mouse.", new String[]{"b"}));
      this.commandManager.registerCommand(new ToggleCommand("toggle", "Used to toggle a given module", new String[]{"t"}));
      this.commandManager.registerCommand(new ConfigCommand("config", "Used to create and load configs.", new String[]{"c", "conf"}));
      this.commandManager.registerCommand(new FriendCommand("friend", "Used to add and remove friends.", new String[]{"f"}));
      this.commandManager.registerCommand(new TargetCommand("target", "Used to add and remove targets.", new String[0]));
      this.commandManager.registerCommand(new HideCommand("hide", "Used to hide and un-hide modules", new String[0]));
      this.commandManager.registerCommand(new VclipCommand("vclip", "Vertically teleports you", new String[0]));
      this.commandManager.registerCommand(new HclipCommand("hclip", "Horizontally teleports you", new String[0]));
      this.commandManager.registerCommand(new ScriptCommand("script", "Used to load and enable scripts.", new String[]{"s"}));
      this.commandManager.registerCommand(new ChatCommand("chat", "Used to chat inside the moon user chat", new String[0]));
      this.commandManager.registerCommand(new UsersCommand("users", "Displays all users online in the client", new String[0]));
      this.commandManager.registerCommand(new IgnCommand("ign", "Copies the current ign to your clipboard", new String[0]));

      this.eventbus.subscribe(new CommandHandler(this.commandManager.getCommands()));
      this.eventbus.subscribe(new CommandSuggestionHandler(this.commandManager.getCommands()));
      this.eventbus.subscribe(new EntityCullingHandler());
      this.setEntityTracker(new EntityTrackHandler(this.moduleManager.getModule(ESP.class), this.moduleManager.getModule(StorageESP.class)));
      this.eventbus.subscribe(this.entityTracker);
      this.eventbus.subscribe(new HudDragHandler(((HUD)this.moduleManager.getModule(HUD.class)).draggableElements, this.moduleManager.getModule(HUD.class)));
      this.eventbus.subscribe(new KeybindHandler(this.bindManager.getBinds()));
      this.eventbus.subscribe(new NotificationHandler());
      this.eventbus.subscribe(this.speedHandler);
      this.eventbus.subscribe(new TimeSyncHandler());
      this.eventbus.subscribe(this.worldRenderScheduler);


      ClickGUI clickGUI = this.moduleManager.getModule(ClickGUI.class);
      if (clickGUI != null) {
         clickGUI.setKey(54);
         clickGUI.setBindAction(BindAction.PRESS);
         clickGUI.setBindType(BindType.KEYBOARD);
      }

      HUD hud = this.moduleManager.getModule(HUD.class);
      if(hud != null) {
         File hudDir = new File(this.rootDirectory, "hud");
         if (!hud.isEnabled()) {
            hud.setEnabled(true);
         }
         for (DraggableHudElement el : hud.draggableElements) {
            el.loadPosition(hudDir);
         }
      }

      this.configManager.init(this.rootDirectory);
      this.friendStorage.load(this.rootDirectory);
      this.targetStorage.load(this.rootDirectory);
      this.accountStorage.load();
      this.moduleStorage.load(this.moduleManager.getRegistry());
      this.scriptManager.init(this.rootDirectory, true);

      for (Script script : this.scriptManager.getScripts().values()) {
         scriptManager.reloadSource(script);
         scriptManager.getScriptLoader().load(script);
      }

   }

   public void login(String uid) throws InterruptedException {
      try {
         System.setProperty("moon.input-uid", uid);
         if (uid != null && !uid.isEmpty()) {
            this.setUsername(uid);
         }
         Minecraft.getMinecraft().addScheduledTask(() -> {
            Display.setTitle("Moon Client 4.0.0 ("+"256ae016d1ab5f21124f38e269e06bb26cfdc63d".substring(0, 7)+") - LWJGL 3.3.1");
            Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
         });
      } catch (Throwable throwable) {
         throw new RuntimeException(throwable);
      }
   }

   public void shutdown() {
      this.runShutdownStep("modules", () -> this.moduleStorage.save(this.moduleManager.getRegistry().getModuleNameMap().values()));
      this.runShutdownStep("accounts", () -> this.accountStorage.save(this.accountManager.getAccounts()));
      this.runShutdownStep("friends", () -> this.friendStorage.save(this.rootDirectory));
      this.runShutdownStep("targets", () -> this.targetStorage.save(this.rootDirectory));
      this.runShutdownStep("scripts", () -> this.scriptManager.saveRegistry());
      this.runShutdownStep("hud", () -> {
         File hudDir = new File(this.getRootDirectory(), "hud");
         if (!hudDir.exists()) {
            hudDir.mkdirs();
         }

         for (DraggableHudElement element : ((HUD)this.moduleManager.getModule(HUD.class)).draggableElements) {
            element.savePosition(hudDir);
         }
      });
   }

   private void runShutdownStep(String name, Runnable step) {
      try {
         step.run();
      } catch (Throwable throwable) {
         this.logger.error("Failed to save " + name + " during shutdown", new Object[]{throwable});
      }
   }

   public TaggedLogger getLogger() {
      return this.logger;
   }

   public EventBus<Event> getEventBus() {
      return this.eventbus;
   }

   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   public ModuleStorage getModuleStorage() {
      return this.moduleStorage;
   }

   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   public BindManager getBindManager() {
      return this.bindManager;
   }

   public BitFontManager getBitFontManager() {
      return this.bitFontManager;
   }

   public FontManager getFontManager() {
      return this.fontManager;
   }

   public WorldRenderHandler getWorldRenderScheduler() {
      return this.worldRenderScheduler;
   }

   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   public FriendStorage getFriendStorage() {
      return this.friendStorage;
   }

   public TargetStorage getTargetStorage() {
      return this.targetStorage;
   }

   public RenderProfiler getProfiler() {
      return this.profiler;
   }

   public File getRootDirectory() {
      return this.rootDirectory;
   }

   public ExecutorService getExecutor() {
      return this.executor;
   }

   public SpeedHandler getSpeedHandler() {
      return this.speedHandler;
   }

   public AccountManager getAccountManager() {
      return this.accountManager;
   }

   public AccountStorage getAccountStorage() {
      return this.accountStorage;
   }

   public ScriptManager getScriptManager() {
      return this.scriptManager;
   }

   public DiscordRPC getDiscordRpc() {
      return this.discordRpc;
   }

   public AccountManagerScreen getAccountScreen() {
      return this.accountScreen;
   }

   public NotificationManager getNotificationManager() {
      return this.notificationManager;
   }

   public ServerSyncHandler getServerSyncHandler() {
      return this.serverSyncHandler;
   }

   public String getUsername() {
      return this.username;
   }

   public Account getCurrentAccount() {
      return this.currentAccount;
   }

   public EntityTrackHandler getEntityTracker() {
      return this.entityTracker;
   }

   public Moon setEventBus(EventBus<Event> eventBus) {
      this.eventbus = eventBus;
      return this;
   }

   public Moon setModuleManager(ModuleManager moduleManager) {
      this.moduleManager = moduleManager;
      return this;
   }

   public Moon setModuleStorage(ModuleStorage moduleStorage) {
      this.moduleStorage = moduleStorage;
      return this;
   }

   public Moon setCommandManager(CommandManager commandManager) {
      this.commandManager = commandManager;
      return this;
   }

   public Moon setBindManager(BindManager bindManager) {
      this.bindManager = bindManager;
      return this;
   }

   public Moon setBitFontManager(BitFontManager bitFontManager) {
      this.bitFontManager = bitFontManager;
      return this;
   }

   public Moon setFontManager(FontManager fontManager) {
      this.fontManager = fontManager;
      return this;
   }

   public Moon setWorldRenderScheduler(WorldRenderHandler worldRenderHandler) {
      this.worldRenderScheduler = worldRenderHandler;
      return this;
   }

   public Moon setConfigManager(ConfigManager configManager) {
      this.configManager = configManager;
      return this;
   }

   public Moon setFriendStorage(FriendStorage friendStorage) {
      this.friendStorage = friendStorage;
      return this;
   }

   public Moon setTargetStorage(TargetStorage targetStorage) {
      this.targetStorage = targetStorage;
      return this;
   }

   public Moon setProfiler(RenderProfiler renderProfiler) {
      this.profiler = renderProfiler;
      return this;
   }

   public Moon setRootDirectory(File file) {
      this.rootDirectory = file;
      return this;
   }

   public Moon setExecutor(ExecutorService executorService) {
      this.executor = executorService;
      return this;
   }

   public Moon setSpeedHandler(SpeedHandler speedHandler) {
      this.speedHandler = speedHandler;
      return this;
   }

   public Moon setAccountManager(AccountManager accountManager) {
      this.accountManager = accountManager;
      return this;
   }

   public Moon setAccountStorage(AccountStorage accountStorage) {
      this.accountStorage = accountStorage;
      return this;
   }

   public Moon setScriptManager(ScriptManager scriptManager) {
      this.scriptManager = scriptManager;
      return this;
   }

   public Moon setDiscordRpc(DiscordRPC discordRpc) {
      this.discordRpc = discordRpc;
      return this;
   }

   public Moon setAccountScreen(AccountManagerScreen accountScreen) {
      this.accountScreen = accountScreen;
      return this;
   }

   public Moon setNotificationManager(NotificationManager notificationManager) {
      this.notificationManager = notificationManager;
      return this;
   }

   public Moon setGateway(Gateway gateway) {
      this.gateway = gateway;
      return this;
   }

   public Moon setServerSyncHandler(ServerSyncHandler serverSyncHandler) {
      this.serverSyncHandler = serverSyncHandler;
      return this;
   }

   public Moon setUsername(String username) {
      this.username = username;
      return this;
   }

   public Moon setCurrentAccount(Account currentAccount) {
      this.currentAccount = currentAccount;
      return this;
   }

   public Moon setEntityTracker(EntityTrackHandler entityTracker) {
      this.entityTracker = entityTracker;
      return this;
   }

   public static Moon getInstance() {
      return INSTANCE;
   }

   public static long getStartTime() {
      return startTime;
   }
}
