package blockchain.chains

import blockchain.blocks.Block
import blockchain.blocks.MaladyBlock
import blockchain.blocks.MaladyType
import blockchain.factory.BlockchainFactory
import blockchain.factory.BlockchainPeersFactory
import blockchain.models.Malady
import org.json.JSONObject
import utils.HashUtils
import java.util.*

/**
 * @definition: Class that represent the Malady BlockChain
 * and it's corresponding functions.
 */
class MaladyBlockChain(data: MutableList<MaladyBlock> = mutableListOf())
    : BlockChain<MaladyBlock, Malady>(data) {

    init {
        if (blockChain.size == 0)
            blockChain.add(MaladyBlock(1, "First One",
                    "First Malady", 1, 1, Date().time, "0000"))
    }

    override fun mineBlock(model: Malady): MaladyBlock {
        var nonce = 1
        var check = false
        val jsonObject = JSONObject()

        jsonObject.put(Block.NUMBER, lastBLock.number + 1)
        jsonObject.put(MaladyBlock.ID_PATIENT, model.idPatient)
        jsonObject.put(MaladyBlock.ID_MALADY, model.idMalady)
        jsonObject.put(MaladyBlock.MALADY_VALUE, model.maladyValue.value)
        jsonObject.put(Block.PREVIOUS_HASH, lastBLock.hash)

        while (!check) {

            val hash = HashUtils.hash(JSONObject(jsonObject.toString()).put(Block.NONCE, nonce).toString())
            // the proof of work
            if (hash.startsWith(PROOF))
                check = true
            else
                nonce++
        }

        val newBlock = MaladyBlock(lastBLock.number + 1,
                model.idPatient,
                model.idMalady,
                model.maladyValue.value,
                nonce, Date().time, lastBLock.hash)
        blockChain.add(newBlock)
        return newBlock
    }

    fun isPatientAMalady(idPatient: String): Boolean {
        for (i in blockChain.size - 1 downTo 0) {
            val block = blockChain[i]
            println("Block is ${block.idPatient}")
            println("Patiant Text is ${idPatient}")
            if (block.idPatient.equals(idPatient))
                return block.maladyValue == MaladyType.SICK.value
        }
        return false
    }

    fun replaceChain(nodes: Array<String>) : Boolean {

        var longestChain: BlockChain<*, *>? = null
        var maxLength = blockChain.size

        for (node in nodes){
            val blockPeer = BlockchainPeersFactory.getMaladiesBlockchain(node)
            val len = blockPeer.blockChain.size

            if (len > maxLength && blockPeer.isValid()) {
                maxLength = len
                longestChain = blockPeer
            }
        }
        if (longestChain != null){
            @Suppress("UNCHECKED_CAST")
            blockChain = longestChain.blockChain as MutableList<MaladyBlock>
            BlockchainFactory.saveBlockChainToJSONFile(this,"maladies.json")
            return true
        }
        return false
    }
}