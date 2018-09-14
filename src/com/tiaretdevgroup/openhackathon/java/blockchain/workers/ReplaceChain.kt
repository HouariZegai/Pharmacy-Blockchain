package blockchain.workers

import blockchain.chains.BlockChain
import blockchain.factory.BlockchainFactory
import utils.NetworkUtils

/**
 * Create a background thread that replace the Blockchain
 * by the newest one in the network of peers.
 */
class ReplaceChain(private val type: ChainType) : Thread() {

    override fun run() {
        val chain = when (type) {
            ChainType.SALE -> {
                BlockchainFactory.readSalesBlockChainFromJSONFile()
            }
            ChainType.MALADY -> {
                BlockchainFactory.readMaladyBlockChainFromJSONFile()
            }
        }

        val nodes = NetworkUtils.getAllNodes()
        chain.replaceChain(nodes)
    }
}