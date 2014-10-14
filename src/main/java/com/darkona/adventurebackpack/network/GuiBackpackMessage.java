package com.darkona.adventurebackpack.network;

import com.darkona.adventurebackpack.AdventureBackpack;
import com.darkona.adventurebackpack.util.LogHelper;
import com.darkona.adventurebackpack.util.Utils;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

/**
 * Created by Darkona on 12/10/2014.
 */
public class GuiBackpackMessage implements IMessage {

    private byte from;
    private byte type;

    public GuiBackpackMessage() {
    }

    public GuiBackpackMessage(byte type, byte from) {
        this.type = type;
        this.from = from;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = buf.readByte();
        this.from = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(type);
        buf.writeByte(from);
    }

    public static class GuiBackpackMessageServerHandler implements IMessageHandler<GuiBackpackMessage, IMessage> {

        @Override
        public IMessage onMessage(GuiBackpackMessage message, MessageContext ctx) {

            if (message.from == GuiMessageConstants.FROM_KEYBIND) {
                if (message.type == GuiMessageConstants.NORMAL_GUI) {
                    LogHelper.info("Received message from keybind for normal gui");
                    EntityPlayerMP player = ctx.getServerHandler().playerEntity;
                    int playerX = (int) player.posX;
                    int playerY = (int) player.posY;
                    int playerZ = (int) player.posZ;
                    World world = player.worldObj;
                    if (Utils.isWearingBackpack(player)) {
                        FMLNetworkHandler.openGui(player, AdventureBackpack.instance, 1, world, playerX, playerY, playerZ);
                    }
                }
            }
            return null;
        }
    }

    public static class GuiBackpackMessageClientHandler implements IMessageHandler<GuiBackpackMessage, IMessage> {

        @Override
        public IMessage onMessage(GuiBackpackMessage message, MessageContext ctx) {
            return null;
        }
    }
}