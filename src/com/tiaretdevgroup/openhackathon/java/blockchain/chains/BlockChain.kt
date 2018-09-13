package blockchain.chains

import blockchain.blocks.Block
import blockchain.factory.BlockchainPeersFactory
import org.json.JSONArray
import org.json.JSONObject

abstract class BlockChain<T, in B>(data: MutableList<T> = mutableListOf<T>()) {

    var blockChain = data

    companion object {
        const val PROOF = "00"
    }

    val lastBLock: T
        get() = blockChain[blockChain.lastIndex]

    abstract fun mineBlock(model: B): T

    override fun toString(): String {
        val blockChainObject = JSONObject()
        val blocksArray = JSONArray()

        for (block in blockChain) {
            blocksArray.put(JSONObject((block as Block).toStringBlockChain()))
        }

        blockChainObject.put("blocks", blocksArray)
        blockChainObject.put("length", blockChain.size)
        blockChainObject.put("isValid", this.isValid())
        return blockChainObject.toString()
    }

    fun toSaveString(): String {

        val blocksArray = JSONArray()

        for (block in blockChain) {
            blocksArray.put(JSONObject((block as Block).toStringBlockChain()))
        }

        return blocksArray.toString()
    }

    fun isValid(): Boolean {
        var previous = blockChain[0] as Block
        var currentIndex = 1
        while (currentIndex < blockChain.size) {
            with(blockChain[currentIndex] as Block) {
                if (this.previousHash != previous.hash) return false
                if (!this.hash.startsWith(PROOF)) return false
                previous = this
                currentIndex++
            }
        }
        return true
    }


}