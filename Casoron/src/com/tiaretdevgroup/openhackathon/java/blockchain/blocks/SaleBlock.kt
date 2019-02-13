package blockchain.blocks

import org.json.JSONObject

/**
 * Simple class to represent the Block of a Sale
 */
class SaleBlock(number: Long,
                val idPatient: String,
                val productId: String,
                val pharmacyId: String,
                nonce: Int,
                timestamp: Long,
                previousHash: String) : Block(number, nonce, timestamp, previousHash) {

    companion object {
        const val PRODUCT_ID = "productId"
        const val PHARMACY_ID = "pharmacyId"
    }

    override fun toStringBlockChain(): String {
        val blockObject = JSONObject()
        blockObject.put(NUMBER, number)
                .put(MaladyBlock.ID_PATIENT, idPatient)
                .put(PRODUCT_ID, productId)
                .put(PHARMACY_ID, pharmacyId)
                .put(NONCE, nonce)
                .put(HASH, hash)
                .put(TIMESTAMP, timestamp)
                .put(PREVIOUS_HASH, previousHash)
        return blockObject.toString()
    }

    override fun toStringBlock(): String {
        val blockObject = JSONObject()
        blockObject.put(NUMBER, number)
                .put(MaladyBlock.ID_PATIENT, idPatient)
                .put(PRODUCT_ID, productId)
                .put(PHARMACY_ID, pharmacyId)
                .put(PREVIOUS_HASH, previousHash)
                .put(NONCE, nonce)
        return blockObject.toString()
    }

    // just for debug
    override fun toString(): String {
        return "MaladyBlock(number=$number, idPatient='$idPatient', productId='$productId', nonce=$nonce, previousHash='$previousHash' , timestamp='$timestamp')"
    }
}