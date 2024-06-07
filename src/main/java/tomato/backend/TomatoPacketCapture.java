package tomato.backend;

import packets.Packet;
import packets.data.QuestData;
import packets.incoming.*;
import packets.outgoing.EnemyHitPacket;
import packets.outgoing.HelloPacket;
import packets.outgoing.PlayerShootPacket;
import tomato.backend.data.TomatoData;
import tomato.gui.dps.DpsGUI;
import tomato.gui.TomatoGUI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Main packet handling class for incoming packets.
 */
public class TomatoPacketCapture implements Controller {

    private TomatoData data;

    public TomatoPacketCapture(TomatoData data) {
        this.data = data;
    }

    /**
     * @param packet incoming packets to be processed.
     */
    public void packetCapture(Packet packet) {
        if (packet instanceof NewTickPacket) {
            NewTickPacket p = (NewTickPacket) packet;
            data.updateNewTick(p);
            DpsGUI.updateNewTickPacket(data);
            data.logPacket(packet);
        } else if (packet instanceof UpdatePacket) {
            UpdatePacket p = (UpdatePacket) packet;
            data.update(p);
            data.logPacket(packet);
        } else if (packet instanceof PlayerShootPacket) {
            PlayerShootPacket p = (PlayerShootPacket) packet;
            data.playerShoot(p);
            data.logPacket(packet);
        } else if (packet instanceof ServerPlayerShootPacket) {
            ServerPlayerShootPacket p = (ServerPlayerShootPacket) packet;
            data.serverPlayerShoot(p);
            data.logPacket(packet);
        } else if (packet instanceof EnemyHitPacket) {
            EnemyHitPacket p = (EnemyHitPacket) packet;
            data.enemtyHit(p);
            data.logPacket(packet);
        } else if (packet instanceof DamagePacket) {
            DamagePacket p = (DamagePacket) packet;
            data.damage(p);
            data.logPacket(packet);
        } else if (packet instanceof TextPacket) {
            TextPacket p = (TextPacket) packet;
            data.text(p);
            data.logPacket(packet);
        } else if (packet instanceof StasisPacket) {
            StasisPacket p = (StasisPacket) packet;
            StasisCheck.stasis(p, data);
        } else if (packet instanceof MapInfoPacket) {
            MapInfoPacket p = (MapInfoPacket) packet;
            data.setNewRealm(p);
            data.logPacket(packet);
        } else if (packet instanceof CreateSuccessPacket) {
            CreateSuccessPacket p = (CreateSuccessPacket) packet;
            data.setUserId(p.objectId, p.charId, p.str);
            data.logPacket(packet);
            data.webRequest();
        } else if (packet instanceof ExaltationUpdatePacket) {
            ExaltationUpdatePacket p = (ExaltationUpdatePacket) packet;
            data.exaltUpdate(p);
        } else if (packet instanceof NotificationPacket) {
            NotificationPacket p = (NotificationPacket) packet;
            data.notification(p);
        } else if (packet instanceof VaultContentPacket) {
            VaultContentPacket p = (VaultContentPacket) packet;
            data.vaultPacketUpdate(p);
        } else if (packet instanceof HelloPacket) {
            HelloPacket p = (HelloPacket) packet;
            data.updateToken(p.accessToken);
        } else if (packet instanceof QuestFetchResponsePacket) {
            QuestFetchResponsePacket p = (QuestFetchResponsePacket) packet;
            Stream<QuestData> list = Arrays.stream(p.quests).sorted(Comparator.comparing(questData -> questData.category));
            TomatoGUI.updateQuests(list.toArray(QuestData[]::new));
        }
    }

    @Override
    public void dispose() {

    }
}