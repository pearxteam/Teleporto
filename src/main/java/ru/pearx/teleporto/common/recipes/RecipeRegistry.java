package ru.pearx.teleporto.common.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.items.ItemRegistry;
import ru.pearx.teleporto.common.items.ItemUpgrade;

import javax.annotation.Nonnull;

/*
 * Created by mrAppleXZ on 25.07.17 7:19.
 */
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
public class RecipeRegistry
{
    @SubscribeEvent
    public static void onRecipeRegistry(RegistryEvent.Register<IRecipe> reg)
    {
        int index = 0;
        registerShaped(index++, new ItemStack(ItemRegistry.ender_circuit),
                "IIQ",
                "GPR",
                "===",
                '=', "obsidian", 'G', "ingotGold", 'P', "enderpearl", 'I', "ingotIron", 'Q', "gemQuartz", 'R', "dustRedstone");
        registerShaped(index++, new ItemStack(ItemRegistry.telecore),
                "LQL",
                "DCD",
                "LQL",
                'D', "gemDiamond", 'C', new ItemStack(ItemRegistry.ender_circuit), 'L', "gemLapis", 'Q', "gemQuartz");
        registerShaped(index++, new ItemStack(ItemRegistry.teleporting_station),
                "ITI",
                "ICI",
                "III",
                'I', "ingotIron", 'T', new ItemStack(ItemRegistry.telecore), 'C', new ItemStack(ItemRegistry.ender_circuit));
        registerShaped(index++, new ItemStack(ItemRegistry.portable_teleport),
                "NNN",
                "CBI",
                "ITI",
                'N', "nuggetIron", 'C', new ItemStack(ItemRegistry.ender_circuit), 'B', new ItemStack(Blocks.STONE_BUTTON), 'I', "ingotIron", 'T', new ItemStack(ItemRegistry.telecore));
        registerShaped(index++, new ItemStack(ItemRegistry.primal_teleport, 1, 1),
                "NNN",
                "IBI",
                "ITI",
                'N', "nuggetIron", 'B', new ItemStack(Blocks.STONE_BUTTON), 'I', "ingotIron", 'T', new ItemStack(ItemRegistry.telecore));
        registerShaped(index++, new ItemStack(ItemRegistry.primal_teleport),
                " N ",
                "IBI",
                "ITI",
                'N', "nuggetIron", 'B', new ItemStack(Blocks.STONE_BUTTON), 'I', "ingotIron", 'T', new ItemStack(ItemRegistry.telecore));
        registerShaped(index++, new ItemStack(ItemRegistry.des_focus),
                "NBN",
                "C N",
                "NNN",
                'N', "nuggetIron", 'B', new ItemStack(Blocks.STONE_BUTTON), 'C', new ItemStack(ItemRegistry.ender_circuit));
        registerShaped(index++, new ItemStack(ItemRegistry.telenergy_meter),
                " G",
                " G",
                "CL",
                'G', new ItemStack(Blocks.GLASS_PANE), 'L', "gemLapis", 'C', new ItemStack(ItemRegistry.ender_circuit));
        registerShaped(index++, new ItemStack(ItemRegistry.upgrade, 1, ItemUpgrade.getUpgradeMeta("max_capacity")),
                "NNN",
                "QLQ",
                "NNN",
                'N', "nuggetIron", 'Q', "gemQuartz", 'L', "gemLapis");
        registerShaped(index++, new ItemStack(ItemRegistry.upgrade, 1, ItemUpgrade.getUpgradeMeta("per_second")),
                "NNN",
                "QDQ",
                "NNN",
                'N', "nuggetIron", 'Q', "gemQuartz", 'D', "gemDiamond");
        registerShapeless(index++, new ItemStack(ItemRegistry.upgrade, 8, ItemUpgrade.getUpgradeMeta("max_capacity_d")), Ingredient.fromStacks(new ItemStack(ItemRegistry.upgrade, 1, ItemUpgrade.getUpgradeMeta("max_capacity"))));
        registerShapeless(index++, new ItemStack(ItemRegistry.upgrade, 8, ItemUpgrade.getUpgradeMeta("per_second_d")), Ingredient.fromStacks(new ItemStack(ItemRegistry.upgrade, 1, ItemUpgrade.getUpgradeMeta("per_second"))));
    }

    private static void registerShaped(int index, @Nonnull ItemStack output, Object... params)
    {
        GameRegistry.addShapedRecipe(new ResourceLocation(Teleporto.MODID, Integer.toString(index)), null, output, params);
    }

    private static void registerShapeless(int index, @Nonnull ItemStack output, Ingredient... params)
    {
        GameRegistry.addShapelessRecipe(new ResourceLocation(Teleporto.MODID, Integer.toString(index)), null, output, params);
    }
}
