package blockchain.workers

import blockchain.blocks.Block
import blockchain.factory.BlockchainFactory
import blockchain.factory.BlockchainPeersFactory
import utils.NetworkUtils

/**
 * Worker Thread that notify the peers about a new block
 * that has been added to the chain.
 * @param block : the block that has been added to the chain.
 */
class NotifyPeers(val block : Block) : Thread() {

    override fun run() {
        val nodes = NetworkUtils.getAllNodes()
        for (node in nodes){
            BlockchainPeersFactory.addBlockToPeer(node, block)
        }
    }
}