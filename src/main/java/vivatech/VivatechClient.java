package vivatech;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.util.Identifier;
import vivatech.block.CoalGeneratorBlock;
import vivatech.block.CrusherBlock;
import vivatech.block.ElectricFurnaceBlock;
import vivatech.block.PressBlock;
import vivatech.controller.CoalGeneratorController;
import vivatech.controller.CrusherController;
import vivatech.controller.ElectricFurnaceController;
import vivatech.controller.PressController;
import vivatech.screen.CoalGeneratorScreen;
import vivatech.screen.CrusherScreen;
import vivatech.screen.ElectricFurnaceScreen;
import vivatech.screen.PressScreen;

public class VivatechClient implements ClientModInitializer {
    public static final Identifier ENERGY_BAR_BG = new Identifier(Vivatech.MODID, "textures/gui/energy_bar_bg.png");
    public static final Identifier ENERGY_BAR = new Identifier(Vivatech.MODID, "textures/gui/energy_bar.png");
    public static final Identifier PROGRESS_BAR_BG = new Identifier(Vivatech.MODID, "textures/gui/progress_bar_bg.png");
    public static final Identifier PROGRESS_BAR = new Identifier(Vivatech.MODID, "textures/gui/progress_bar.png");
    public static final Identifier FIRE_BAR_BG = new Identifier(Vivatech.MODID, "textures/gui/fire_bar_bg.png");
    public static final Identifier FIRE_BAR = new Identifier(Vivatech.MODID, "textures/gui/fire_bar.png");
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(CoalGeneratorBlock.ID, (syncId, identifier, player, buf) ->
                new CoalGeneratorScreen(new CoalGeneratorController(
                        syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
        ScreenProviderRegistry.INSTANCE.registerFactory(CrusherBlock.ID, (syncId, identifier, player, buf) ->
                new CrusherScreen(new CrusherController(
                        syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
        ScreenProviderRegistry.INSTANCE.registerFactory(ElectricFurnaceBlock.ID, (syncId, identifier, player, buf) ->
                new ElectricFurnaceScreen(new ElectricFurnaceController(
                        syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
        ScreenProviderRegistry.INSTANCE.registerFactory(PressBlock.ID, (syncId, identifier, player, buf) ->
                new PressScreen(new PressController(
                        syncId, player.inventory, BlockContext.create(player.world, buf.readBlockPos())), player));
    }
}
