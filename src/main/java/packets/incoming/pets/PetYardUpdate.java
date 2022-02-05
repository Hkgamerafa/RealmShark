package packets.incoming.pets;


import packets.Packet;
import packets.buffer.PBuffer;
import packets.buffer.data.enums.PetYardType;

/**
 * Received when the pet yard is updated to a new type of yard
 */
public class PetYardUpdate extends Packet {
    /**
     * The type of the new yard
     */
    public PetYardType yardType;

    @Override
    public void deserialize(PBuffer buffer) {
        yardType = PetYardType.byOrdinal(buffer.readInt());
    }
}