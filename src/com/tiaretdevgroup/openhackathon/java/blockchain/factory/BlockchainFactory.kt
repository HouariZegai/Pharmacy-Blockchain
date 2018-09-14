package blockchain.factory

import blockchain.blocks.Block
import blockchain.blocks.MaladyBlock
import blockchain.blocks.SaleBlock
import blockchain.chains.BlockChain
import blockchain.chains.MaladyBlockChain
import blockchain.chains.SalesBlockChain
import org.json.JSONArray
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Singleton Class to create Blockchains from file and JSON String
 * and also for saving the Blockchains in files.
 */
object BlockchainFactory {

    private const val MALADIES = "C:\\App\\maladies.json"
    private const val SALES = "C:\\App\\sales.json"

    fun readMaladyBlockChainFromJSONFile(): MaladyBlockChain {
        val blocks = JSONArray(String(Files.readAllBytes(Paths.get(MALADIES))))
        val allBlocks = mutableListOf<MaladyBlock>()

        for (i in 0 until blocks.length()) {
            val block = blocks.getJSONObject(i)
            val idMalady = block.getString(MaladyBlock.ID_MALADY)
            val idPatient = block.getString(MaladyBlock.ID_PATIENT)
            val maladyValue = block.getInt(MaladyBlock.MALADY_VALUE)
            val number = block.getLong(Block.NUMBER)
            val nonce = block.getInt(Block.NONCE)
            val previous = block.getString(Block.PREVIOUS_HASH)
            val timestamp = block.getLong(Block.TIMESTAMP)
            allBlocks.add(MaladyBlock(number, idPatient, idMalady, maladyValue
                    , nonce, timestamp, previous))
        }
        return MaladyBlockChain(allBlocks)
    }

    fun readMaladyBlockChainFromJSONString(path: String): MaladyBlockChain {
        val blocks = JSONArray(path)
        val allBlocks = mutableListOf<MaladyBlock>()

        for (i in 0 until blocks.length()) {
            val block = blocks.getJSONObject(i)
            val idMalady = block.getString(MaladyBlock.ID_MALADY)
            val idPatient = block.getString(MaladyBlock.ID_PATIENT)
            val maladyValue = block.getInt(MaladyBlock.MALADY_VALUE)
            val number = block.getLong(Block.NUMBER)
            val nonce = block.getInt(Block.NONCE)
            val previous = block.getString(Block.PREVIOUS_HASH)
            val timestamp = block.getLong(Block.TIMESTAMP)
            allBlocks.add(MaladyBlock(number, idPatient, idMalady, maladyValue
                    , nonce, timestamp, previous))
        }
        return MaladyBlockChain(allBlocks)
    }

    fun readSalesBlockChainFromJSONFile(): SalesBlockChain {
        val blocks = JSONArray(String(Files.readAllBytes(Paths.get(SALES))))
        val allBlocks = mutableListOf<SaleBlock>()

        for (i in 0 until (blocks.length())) {

            val block = blocks.getJSONObject(i)

            val productId = block.getString(SaleBlock.PRODUCT_ID)
            val idPatient = block.getString(MaladyBlock.ID_PATIENT)
            val pharmacyId = block.getString(SaleBlock.PHARMACY_ID)
            val number = block.getLong(Block.NUMBER)
            val nonce = block.getInt(Block.NONCE)
            val previous = block.getString(Block.PREVIOUS_HASH)
            val timestamp = block.getLong(Block.TIMESTAMP)
            val blo = SaleBlock(number, idPatient, productId, pharmacyId
                    , nonce, timestamp, previous)

            allBlocks.add(blo)
        }
        return SalesBlockChain(allBlocks)
    }

    fun readSalesBlockChainFromJSONString(path: String): SalesBlockChain {
        val blocks = JSONArray(path)
        val allBlocks = mutableListOf<SaleBlock>()

        for (i in 0 until (blocks.length())) {

            val block = blocks.getJSONObject(i)

            val productId = block.getString(SaleBlock.PRODUCT_ID)
            val idPatient = block.getString(MaladyBlock.ID_PATIENT)
            val pharmacyId = block.getString(SaleBlock.PHARMACY_ID)
            val number = block.getLong(Block.NUMBER)
            val nonce = block.getInt(Block.NONCE)
            val previous = block.getString(Block.PREVIOUS_HASH)
            val timestamp = block.getLong(Block.TIMESTAMP)
            val blo = SaleBlock(number, idPatient, productId, pharmacyId
                    , nonce, timestamp, previous)

            allBlocks.add(blo)
        }
        return SalesBlockChain(allBlocks)
    }

    fun saveBlockChainToJSONFile(blockChain: BlockChain<*, *>, path: String) {
        FileWriter(path).use { file ->
            file.write(blockChain.toSaveString())
        }
    }
}