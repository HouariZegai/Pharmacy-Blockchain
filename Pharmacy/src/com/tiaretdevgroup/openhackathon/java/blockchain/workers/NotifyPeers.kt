package blockchain.workers

import blockchain.blocks.Block
import blockchain.factory.BlockchainPeersFactory
import utils.NetworkUtils
import java.net.InetAddress

/**
 * Worker Thread that notify the peers about a new block
 * that has been added to the chain.
 * @param block : the block that has been added to the chain.
 */
class NotifyPeers(val block: Block) : Thread() {

    override fun run() {
        val nodes = NetworkUtils.getAllNodes()
        for (node in nodes) {
            val address = InetAddress.getLocalHost()
            if (address.hostAddress == node) continue
            BlockchainPeersFactory.addBlockToPeer(node, block)
        }
    }
}