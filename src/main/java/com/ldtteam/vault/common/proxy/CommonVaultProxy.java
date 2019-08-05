package com.ldtteam.vault.common.proxy;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.ldtteam.vault.api.IVaultAPI;
import com.ldtteam.vault.api.VaultAPI;
import com.ldtteam.vault.api.common.proxy.IVaultProxy;
import com.ldtteam.vault.api.permission.VaultPermissionHandler;
import com.ldtteam.vault.common.context.EntityContext;
import com.ldtteam.vault.common.context.RawWorldBlockPosContext;
import com.ldtteam.vault.common.event.EventAnalyzer;
import com.ldtteam.vault.api.event.EventPermissionHandler;
import com.ldtteam.vault.common.gui.VaultServerManagementUI;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.context.PlayerContext;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public class CommonVaultProxy implements IVaultProxy
{

    @Override
    public void setupAPI()
    {
        IVaultAPI.Holder.setApi(new VaultAPI());
    }

    @Override
    public void preInit(@NotNull final FMLPreInitializationEvent event)
    {
        PermissionAPI.setPermissionHandler(VaultPermissionHandler.getInstance());

        EventAnalyzer.getInstance().discoverEvents(event);
    }

    @Override
    public void init(final FMLInitializationEvent event)
    {
        EventAnalyzer.getInstance().registerEvents(event);
        MinecraftForge.EVENT_BUS.register(EventPermissionHandler.getInstance());
        setupEventPermissionHandler();
    }

    @Override
    public void serverLoad(final FMLServerStartingEvent event)
    {
        setupVaultCommand(event);

        //TODO: Replace the commands after the server gets loaded, to intercept the permissions check.
        event.getServer().addScheduledTask(() -> {
            event.getServer().getCommandManager().getCommands().values().forEach(command -> {
                getPermissionDataFromCommand(event.getServer(), "command", command).forEach(dataTriple -> {
                    PermissionAPI.registerNode(dataTriple.getLeft(), dataTriple.getMiddle(), dataTriple.getRight());
                });
            });

            try
            {
                final File vaultFile = event.getServer().getFile("vault.dat");
                if (vaultFile.exists())
                {
                    final NBTTagCompound compound = CompressedStreamTools.read(vaultFile);
                    if (compound != null)
                    {
                        VaultPermissionHandler.getInstance().deserializeNBT(
                          compound
                        );
                    }
                }

                CompressedStreamTools.write(VaultPermissionHandler.getInstance().serializeNBT(), vaultFile);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    private Set<Triple<String, DefaultPermissionLevel, String>> getPermissionDataFromCommand(final MinecraftServer server, final String parentName, final ICommand command)
    {
        final Set<Triple<String, DefaultPermissionLevel, String>> permissionData = Sets.newHashSet();
        permissionData.add(new Triple<String, DefaultPermissionLevel, String>() {
            @Override
            public String getLeft()
            {
                return (parentName + "." + command.getName().replace(" ", "-")).toLowerCase();
            }

            @Override
            public DefaultPermissionLevel getMiddle()
            {
                if (!(command instanceof CommandBase))
                    return DefaultPermissionLevel.NONE;

                final CommandBase commandBase = (CommandBase) command;

                switch (commandBase.getRequiredPermissionLevel())
                {
                    case 3:
                        return DefaultPermissionLevel.OP;
                    case 2:
                        return DefaultPermissionLevel.ALL;
                    default:
                        return DefaultPermissionLevel.NONE;
                }
            }

            @Override
            public String getRight()
            {
                return "Execution rights for the command: " + command.getName() + " with usage: " + I18n.translateToLocal(command.getUsage(server));
            }
        });

        if (command instanceof CommandTreeBase)
        {
            final CommandTreeBase commandTreeBase = (CommandTreeBase) command;

            commandTreeBase.getSubCommands().forEach(subCommand -> {
                permissionData.addAll(
                  this.getPermissionDataFromCommand(server, (parentName + "." + command.getName().replace(" ", "-")).toLowerCase(), subCommand)
                );
            });
        }

        return permissionData;
    }

    private void setupEventPermissionHandler()
    {
        EventPermissionHandler.getInstance()
          .registerEntityPlayerExtractor(
            PlayerEvent::getEntityPlayer,
            PlayerEvent.class,
            ArrowLooseEvent.class,
            AttackEntityEvent.class,
            BonemealEvent.class,
            EntityItemPickupEvent.class,
            FillBucketEvent.class,
            ItemFishedEvent.class,
            PlayerEvent.BreakSpeed.class,
            PlayerInteractEvent.EntityInteract.class,
            PlayerInteractEvent.EntityInteractSpecific.class,
            PlayerInteractEvent.LeftClickBlock.class,
            PlayerInteractEvent.RightClickBlock.class,
            PlayerInteractEvent.RightClickItem.class,
            PlayerPickupXpEvent.class,
            PlayerSetSpawnEvent.class,
            UseHoeEvent.class
            )
          .registerEntityPlayerExtractor(
            PlayerDropsEvent::getEntityPlayer,
            PlayerDropsEvent.class)
          .registerEntityPlayerExtractor(
            ServerChatEvent::getPlayer,
            ServerChatEvent.class
          )
          .registerEntityPlayerExtractor(
            ItemTossEvent::getPlayer,
            ItemTossEvent.class
          )
          .registerEntityPlayerExtractor(
            BabyEntitySpawnEvent::getCausedByPlayer,
            BabyEntitySpawnEvent.class
          )
          .registerEntityPlayerExtractor(
            LivingExperienceDropEvent::getAttackingPlayer,
            LivingExperienceDropEvent.class
          )
          .registerEntityPlayerExtractor(
            MinecartInteractEvent::getPlayer,
            MinecartInteractEvent.class
          )
          .registerEntityPlayerExtractor(
            VillageSiegeEvent::getPlayer,
            VillageSiegeEvent.class
          )
          .registerEntityPlayerExtractor(
            BlockEvent.BreakEvent::getPlayer,
            BlockEvent.BreakEvent.class
          )
          .registerEntityPlayerExtractor(
            BlockEvent.PlaceEvent::getPlayer,
            BlockEvent.PlaceEvent.class,
            BlockEvent.MultiPlaceEvent.class
          )
          .registerEntityPlayerExtractor(
            event -> event.getSender() instanceof EntityPlayer ? (EntityPlayer) event.getSender() : null,
            CommandEvent.class
          )
          .registerContextAndGameProfileExtractor(
            event -> event.getEntity() instanceof  EntityPlayer ? new PlayerContext((EntityPlayer) event.getEntity()) : (event.getEntity() != null ? new EntityContext(event.getEntity()) : null),
            event -> event.getEntity() instanceof  EntityPlayer ? ((EntityPlayer) event.getEntity()).getGameProfile() : null,
            EntityJoinWorldEvent.class,
            EntityMountEvent.class,
            EntityStruckByLightningEvent.class,
            EntityTravelToDimensionEvent.class,
            PlaySoundAtEntityEvent.class,
            ProjectileImpactEvent.class,
            ProjectileImpactEvent.Arrow.class,
            ProjectileImpactEvent.Fireball.class,
            ProjectileImpactEvent.Throwable.class,
            ThrowableImpactEvent.class,
            ItemExpireEvent.class
          )
          .registerEntityPlayerExtractor(
            ItemTossEvent::getPlayer,
            ItemTossEvent.class
          )
          .registerEntityPlayerExtractor(
            AnimalTameEvent::getTamer,
            AnimalTameEvent.class
          )
          .registerEntityPlayerExtractor(
            BabyEntitySpawnEvent::getCausedByPlayer,
            BabyEntitySpawnEvent.class
          )
          .registerContextAndGameProfileExtractor(
            event -> event.getEntity() instanceof  EntityPlayer ? new PlayerContext((EntityPlayer) event.getEntity()) : (event.getEntity() != null ? new EntityContext(event.getEntity()) : null),
            event -> event.getEntity() instanceof  EntityPlayer ? ((EntityPlayer) event.getEntity()).getGameProfile() : null,
            LivingEvent.class,
            EnderTeleportEvent.class,
            LivingAttackEvent.class,
            LivingDamageEvent.class,
            LivingDestroyBlockEvent.class,
            LivingDropsEvent.class,
            LivingEntityUseItemEvent.Start.class,
            LivingEntityUseItemEvent.Stop.class,
            LivingEntityUseItemEvent.Tick.class,
            LivingEvent.LivingUpdateEvent.class,
            LivingFallEvent.class,
            LivingHealEvent.class,
            LivingHurtEvent.class,
            LivingKnockBackEvent.class,
            LivingSpawnEvent.SpecialSpawn.class,
            PotionEvent.PotionRemoveEvent.class
          )
          .registerContextExtractor(
            event -> new RawWorldBlockPosContext(event.getWorld(), event.getPos()),
            BlockEvent.class,
            BlockEvent.EntityMultiPlaceEvent.class,
            BlockEvent.EntityPlaceEvent.class,
            BlockEvent.FarmlandTrampleEvent.class,
            BlockEvent.FluidPlaceBlockEvent.class,
            BlockEvent.NeighborNotifyEvent.class,
            BlockEvent.PortalSpawnEvent.class,
            NoteBlockEvent.Change.class,
            NoteBlockEvent.Play.class
          )
          .registerContextExtractor(
            event -> new RawWorldBlockPosContext(event.getWorld(), new BlockPos(event.getExplosion().getPosition())),
            ExplosionEvent.Start.class
          )
          .registerContextExtractor(
            event -> new RawWorldBlockPosContext(event.getWorld(), event.getPos()),
            WorldEvent.PotentialSpawns.class
          );
    }

    private void setupVaultCommand(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandBase() {
            @Override
            public String getName()
            {
                return "assets/vault";
            }

            @Override
            public String getUsage(final ICommandSender sender)
            {
                return "/assets/vault";
            }

            @Override
            public List<String> getAliases()
            {
                return ImmutableList.of("v", "vl", "vlt", "assets/vault");
            }

            @Override
            public int getRequiredPermissionLevel()
            {
                return 4;
            }

            @Override
            public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException
            {
                if (!(sender instanceof EntityPlayerMP))
                {
                    sender.sendMessage(new TextComponentString("Only a player can execute the Vault command to open the UI"));
                    return;
                }

                VaultServerManagementUI.openUi((EntityPlayerMP) sender);
            }
        });
    }
}
