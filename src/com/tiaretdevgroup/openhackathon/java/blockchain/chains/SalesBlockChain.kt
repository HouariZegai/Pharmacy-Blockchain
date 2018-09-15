package com.tiaretdevgroup.openhackathon.java.blockchain.chains

import blockchain.blocks.Block
import blockchain.blocks.MaladyBlock
import blockchain.blocks.SaleBlock
import blockchain.chains.BlockChain
import blockchain.factory.BlockchainFactory
import blockchain.factory.BlockchainPeersFactory
import blockchain.models.Sale
import blockchain.workers.NotifyPeers
import com.tiaretdevgroup.openhackathon.java.utils.Constants
import org.json.JSONObject
import utils.HashUtils
import java.net.InetAddress
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


/**
 * Class that represent the Sales BlockChain
 * and it's corresponding functions.
 */
class SalesBlockChain(data: MutableList<SaleBlock> = mutableListOf())
    : BlockChain<SaleBlock, Sale>(data) {

    init {
        // genesis block
        if (blockChain.size == 0)
            blockChain.add(SaleBlock(1, "First One",
                    "First Product", "12", 1, Date().time, "0000"))
    }

    fun addBlock(idPatient: String, idProduct: String, idPharmacy: String) {
        val block = this.mineBlock(blockchain.models.Sale(idPatient, idProduct, idPharmacy))
        NotifyPeers(block).start()
    }

    /**
     * this function is responsible to find value for the nonce
     * to make us able to add block to chain.
     */
    override fun mineBlock(model: Sale): SaleBlock {
        var nonce = 1
        var check = false
        val jsonObject = JSONObject()

        jsonObject.put(Block.NUMBER, lastBLock.number + 1)
        jsonObject.put(MaladyBlock.ID_PATIENT, model.idPatient)
        jsonObject.put(SaleBlock.PRODUCT_ID, model.productId)
        jsonObject.put(SaleBlock.PHARMACY_ID, model.pharmacyId)
        jsonObject.put(Block.PREVIOUS_HASH, lastBLock.hash)

        while (!check) {
            val hash = HashUtils.hash(JSONObject(jsonObject.toString()).put(Block.NONCE, nonce).toString())
            // the proof of work
            if (hash.startsWith(PROOF))
                check = true
            else
                nonce++
        }

        val newBlock = SaleBlock(lastBLock.number + 1,
                model.idPatient,
                model.productId,
                model.pharmacyId,
                nonce, Date().time, lastBLock.hash)
        //blockChain.add(newBlock)

        return newBlock
    }

    fun lastSales(idPatient: String, idProduct: String): Date? {
        for (i in blockChain.size - 1 downTo 0) {
            val block = blockChain[i]
            if (block.idPatient == idPatient && block.productId == idProduct)
                return Date(block.timestamp)
        }
        return null
    }

    override fun replaceChain(nodes: Array<String>): Boolean {
        val address = InetAddress.getLocalHost()
        var longestChain: BlockChain<*, *>? = null
        var maxLength = blockChain.size

        for (node in nodes) {
            if (address.hostAddress == node) continue
            val blockPeer = BlockchainPeersFactory.getSaleBlockchain(node)
            val len = blockPeer.blockChain.size

            if (len > maxLength && blockPeer.isValid()) {
                maxLength = len
                longestChain = blockPeer
            }
        }
        if (longestChain != null) {
            @Suppress("UNCHECKED_CAST")
            blockChain = longestChain.blockChain as MutableList<SaleBlock>
            BlockchainFactory.saveBlockChainToJSONFile(this, "sales.json")
            return true
        }
        return false
    }


    fun getSales(): List<Sale> {
        val json = JSONObject(String(Files.readAllBytes(Paths.get(Constants.FILE_TOKEN))))
        val idPharmacy = json.getInt("id")
        val data = mutableListOf<Sale>()
        for (block in blockChain) {
            if (idPharmacy.toString() == block.pharmacyId) {

                val patient = block.idPatient
                val product = block.productId
                data.add(Sale(patient, product, block.pharmacyId))
            }
        }
        return data
    }
}