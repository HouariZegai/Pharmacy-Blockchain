package blockchain.factory

import blockchain.blocks.Block
import blockchain.blocks.MaladyBlock
import com.tiaretdevgroup.openhackathon.java.blockchain.chains.MaladyBlockChain
import com.tiaretdevgroup.openhackathon.java.blockchain.chains.SalesBlockChain
import com.mashape.unirest.http.Unirest

/**
 * Singleton class to interact with other peers.
 */

object BlockchainPeersFactory {

    fun getSaleBlockchain(ipAddress: String): SalesBlockChain {
        val data = Unirest.get("http://$ipAddress:5000/getSales").asJson()
        return BlockchainFactory.readSalesBlockChainFromJSONString(data.body.toString())
    }

    fun getMaladiesBlockchain(ipAddress: String): MaladyBlockChain {
        val data = Unirest.get("http://$ipAddress:5000/getMaladies").asJson()
        return BlockchainFactory.readMaladyBlockChainFromJSONString(data.body.toString())
    }

    fun addBlockToPeer(ipAddress: String, block: Block) {
        Unirest.post("http://$ipAddress:5000/${if (block is MaladyBlock) "addMaladyBlock" else "addSaleBlock"}").queryString("block",block.toStringBlockChain()).asString()
    }
}