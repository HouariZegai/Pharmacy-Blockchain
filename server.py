
# Importing the libraries
import json
from flask import Flask, jsonify, request
# Creating a Web App
app = Flask(__name__)

@app.route('/getMaladies', methods = ['GET'])
def getMaladiesBlockchain():
    file = open('C:\\AppPharmacy\\maladies.json')
    blockchain = json.load(file)
    return jsonify(blockchain), 200

@app.route('/getSales', methods = ['GET'])
def getSalesBlockchain():
    file = open('C:\\AppPharmacy\\sales.json')
    blockchain = json.load(file)
    return jsonify(blockchain), 200

@app.route('/addSaleBlock', methods = ['POST'])
def addSaleBlock():
    file = open('C:\\AppPharmacy\\sales.json')
    blockchain = json.load(file)
    blockchain.append(json.loads(request.args.get('block')))
    with open('C:\\AppPharmacy\\sales.json','w') as data:    
        json.dump(blockchain,data,indent=4)
    return jsonify(blockchain),200

@app.route('/addMaladyBlock', methods = ['POST'])
def addMaladyBlock():
    file = open('C:\\AppPharmacy\\maladies.json')
    blockchain = json.load(file)
    blockchain.append(json.loads(request.args.get('block')))
    with open('C:\\AppPharmacy\\maladies.json','w') as data:    
        json.dump(blockchain,data,indent=4)
    return jsonify(blockchain),200
    

app.run(host = '0.0.0.0', port = 5000)
