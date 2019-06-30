package com.ldtteam.vault.common.gui;

import com.ldtteam.blockout.binding.dependency.DependencyObjectHelper;
import com.ldtteam.blockout.connector.core.IGuiController;
import com.ldtteam.blockout.connector.core.IGuiKey;
import com.ldtteam.blockout.element.advanced.list.constructiondatabuilder.ListConstructionDataBuilder;
import com.ldtteam.blockout.element.simple.Button;
import com.ldtteam.jvoxelizer.launcher.forge_1_12.entity.living.player.PlayerEntity;
import com.ldtteam.jvoxelizer.util.identifier.IIdentifier;
import com.ldtteam.vault.api.constants.ModConstants;
import com.ldtteam.vault.api.constants.ModTranslationConstants;
import com.ldtteam.vault.api.permission.VaultPermissionHandler;
import com.ldtteam.vault.api.region.VaultWorldRegion;
import com.ldtteam.vault.api.utils.LogUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;


@SuppressWarnings("unchecked")
public class VaultServerManagementUI
{

    private static final Logger logger = LogUtils.constructLoggerForClass(VaultServerManagementUI.class);

    public static void openUi(EntityPlayerMP playerMP)
    {
        IGuiController.getInstance().openUI(
          PlayerEntity.fromForge(playerMP),
          guiKeyBuilder -> guiKeyBuilder
                             .forEntity(PlayerEntity.fromForge(playerMP))
                             .ofFile(IIdentifier.create(ModConstants.CONST_MOD_ID, "guis/management/server/entrypoint.json"))
                             .usingData(dataBuilder -> dataBuilder
                                                         .withControl("dimensionList", ListConstructionDataBuilder.class, listDataBuilder -> listDataBuilder
                                                                                                                                               .withDependentDataContext(
                                                                                                                                                 DependencyObjectHelper.createFromValue(
                                                                                                                                                   VaultPermissionHandler.getInstance()
                                                                                                                                                     .getWorldRegionMap()
                                                                                                                                                     .entrySet()
                                                                                                                                                     .stream()
                                                                                                                                                     .map(e -> new DimensionDataWrapper(
                                                                                                                                                       e.getKey(),
                                                                                                                                                       e.getValue()))
                                                                                                                                                     .collect(
                                                                                                                                                       Collectors.toList()
                                                                                                                                                     )
                                                                                                                                                 ))
                                                           .withTemplateConstructionData(entryConstructionData -> entryConstructionData
                                                            .withControl("editButton", Button.ButtonConstructionDataBuilder.class, editButtonBuilder -> editButtonBuilder
                                                                .withClickedEventHandler((source, args) -> {
                                                                    if (!args.isStart())
                                                                        return;

                                                                    final Object buttonDataContext = source.getDataContext();
                                                                    if (!(buttonDataContext instanceof DimensionDataWrapper))
                                                                    {
                                                                        logger.error("Could not open edit UI for vault world management. Datacontext is not a DimensionDataWrapper.");
                                                                        return;
                                                                    }

                                                                    final DimensionDataWrapper buttonData = (DimensionDataWrapper) buttonDataContext;
                                                                    final IGuiKey currentUi = IGuiController.getInstance().getOpenUI(PlayerEntity.fromForge(playerMP));

                                                                    VaultWorldManagementUI.openEditUi(playerMP, buttonData.id, currentUi);
                                                                }))
                                                           .withControl("deleteButton", Button.ButtonConstructionDataBuilder.class, deleteButtonBuilder -> deleteButtonBuilder
                                                                .withClickedEventHandler(((source, args) -> {
                                                                    if (!args.isStart())
                                                                        return;

                                                                    final Object buttonDataContext = source.getDataContext();
                                                                    if (!(buttonDataContext instanceof DimensionDataWrapper))
                                                                    {
                                                                        logger.error("Could not open delete dialog for vault world management. Datacontext is not a DimensionDataWrapper.");
                                                                        return;
                                                                    }

                                                                    final DimensionDataWrapper buttonData = (DimensionDataWrapper) buttonDataContext;
                                                                    final IGuiKey currentUi = IGuiController.getInstance().getOpenUI(PlayerEntity.fromForge(playerMP));
                                                                    DialogUi.openConfirmDialog(
                                                                      playerMP,
                                                                      ModTranslationConstants.TK_DELETION_CONFIRM,
                                                                      () -> {
                                                                          VaultPermissionHandler.getInstance().getWorldRegionMap().remove(buttonData.id);

                                                                          DialogUi.openTextDialog(
                                                                            playerMP,
                                                                            ModTranslationConstants.TK_DELETION_SUCCESS,
                                                                            () -> IGuiController.getInstance().openUI(PlayerEntity.fromForge(playerMP), currentUi)
                                                                          );
                                                                      },
                                                                      () -> IGuiController.getInstance().openUI(PlayerEntity.fromForge(playerMP), currentUi)
                                                                    );
                                                                }))))
                                                         )
                               .withControl("addNewDimension", Button.ButtonConstructionDataBuilder.class, addNewDimensionBuilder -> addNewDimensionBuilder
                                    .withClickedEventHandler(((source, args) -> {
                                        final IGuiKey currentUi = IGuiController.getInstance().getOpenUI(PlayerEntity.fromForge(playerMP));
                                        VaultWorldManagementUI.openNewUi(playerMP, currentUi);
                                    }))
                               )
                               .withControl("editGroups", Button.ButtonConstructionDataBuilder.class, editGroupsBuilder -> editGroupsBuilder
                                .withClickedEventHandler(((source, args) -> {
                                    final IGuiKey currentUi = IGuiController.getInstance().getOpenUI(PlayerEntity.fromForge(playerMP));
                                    VaultGroupManagementUI.openServerGroupManagementUi(playerMP, VaultPermissionHandler.getInstance().getServerWide(), currentUi);
                                })))

                             )
                             .withDefaultItemHandlerManager()
        );
    }

    private static class DimensionDataWrapper
    {
        private final int              id;
        private final VaultWorldRegion data;

        private DimensionDataWrapper(final int id, final VaultWorldRegion data)
        {
            this.id = id;
            this.data = data;
        }

        public int getId()
        {
            return id;
        }

        public VaultWorldRegion getData()
        {
            return data;
        }
    }
}
