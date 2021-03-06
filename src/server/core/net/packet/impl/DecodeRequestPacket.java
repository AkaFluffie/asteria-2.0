package server.core.net.packet.impl;

import server.core.net.buffer.PacketBuffer.ByteOrder;
import server.core.net.buffer.PacketBuffer.ReadBuffer;
import server.core.net.packet.PacketDecoder;
import server.core.net.packet.PacketOpcodeHeader;
import server.world.World;
import server.world.entity.player.Player;
import server.world.entity.player.skill.SkillEvent;

/**
 * Sent when the player sends another player some sort of request using the
 * <code>sendMessage</code> method.
 * 
 * @author lare96
 */
@PacketOpcodeHeader( { 139 })
public class DecodeRequestPacket extends PacketDecoder {

    @Override
    public void decode(Player player, ReadBuffer in) {
        int requestId = in.readShort(true, ByteOrder.LITTLE);
        Player request = World.getPlayers().get(requestId);

        if (request == null) {
            return;
        }

        SkillEvent.fireSkillEvents(player);

        switch (player.getSession().getPacketOpcode()) {
            case 139:
                player.getTradeSession().request(request);
                break;

        }
    }
}
