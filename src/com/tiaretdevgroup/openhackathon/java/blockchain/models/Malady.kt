package blockchain.models

import blockchain.blocks.MaladyType

data class Malady(val idPatient: String,
                  val idMalady: String,
                  val maladyValue: MaladyType)