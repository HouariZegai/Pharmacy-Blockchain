package blockchain.factory

import blockchain.chains.MaladyBlockChain
import blockchain.chains.SalesBlockChain
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object BlockchainPeersFactory {

    fun getSaleBlockchain(ipAddress: String): SalesBlockChain {
        val url = URL("$ipAddress/getSales")
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val inp = connection.inputStream
        val scanner = Scanner(inp)
        scanner.useDelimiter("\\A")
        val json = scanner.next()
        return BlockchainFactory.readSalesBlockChainFromJSONString(json)
    }

    fun getMaladiesBlockchain(ipAddress: String): MaladyBlockChain {
        val url = URL("$ipAddress/getMaladies")
        val connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val inp = connection.inputStream
        val scanner = Scanner(inp)
        scanner.useDelimiter("\\A")
        val json = scanner.next()
        return BlockchainFactory.readMaladyBlockChainFromJSONString(json)
    }
}