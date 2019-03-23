function createContract(){
    return web3.eth.contract(abi);
}

function deployConract(_name_type){
    let contract = createContract();
    contract.new(
        _name_type,
        {
            from: web3.eth.accounts[0],
            data: byteCode,
            gas: '4700000'
        }, function (e, contract){
            console.log(e, contract);
            if (typeof contract.address !== 'undefined') {
                console.log('Contract mined! address: ' + contract.address + ' transactionHash: ' + contract.transactionHash);
                productAddreses[step] = contract.address;
                step++;
            }
        })
}

function openContract(){
    let contract = createContract();
    let index = productNames.indexOf(selectedValue);
    let address = productAddreses[index];
    let contractInstance = contract.at(address);

    return contractInstance;
}


function changeFunc() {
    var selectBox = document.getElementById("selectProduct");
    selectedValue = selectBox.options[selectBox.selectedIndex].value;
    /*return selectedValue;*/
}

function getStates(contract) {
    console.log(contract);
    let index = productNames.indexOf(selectedValue);
    let address = productAddreses[index];
    contract.State.call(address,{ from: window.web3.eth.accounts[0] },function (e,r) {
        console.log(r);
        console.log(r[1]);
        let index1 = r[1].indexOf(':')
        let index2= r[1].indexOf(':',index1+1)
        let container = r[1].substr(index1+1,index2-index1-1);
        let navi = r[1].substr(index2+1,6);
        console.log(container);
        console.log(navi);
        getNavi(container, navi);
    })
}