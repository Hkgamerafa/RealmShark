package packets.outgoing;

import packets.Packet;
import packets.buffer.PBuffer;

/**
 * Sent when the player is hit.
 */
public class PlayerHitPacket extends Packet {
    /**
     * The id of the bullet which hit the player.
     */
    public int bulletId;
    /**
     * The object id of the enemy that hit the player.
     */
    public int objectId;

    @Override
    public void deserialize(PBuffer buffer) {
        bulletId = buffer.readUnsignedByte();
        objectId = buffer.readInt();
    }
}