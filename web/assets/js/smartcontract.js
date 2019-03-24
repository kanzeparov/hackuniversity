function createContract(){
    return web3.eth.contract(abi);
}

function deployConract(ticketName, ticketPrice, ticketOwner, ticketPersonInfo, ticketPlace, ticketId, ticketDate, ticketAll, pin){
    let contract = createContract();

    contract.new(
        ticketName, ticketPrice, ticketOwner, ticketPersonInfo, ticketPlace, ticketId, ticketDate, ticketAll,
        {
            from: web3.eth.accounts[0],
            data: byteCode,
            gas: '4700000'
        }, function (e, contract){
            console.log(e, contract);
            if (typeof contract.address !== 'undefined') {
                console.log('Contract mined! address: ' + contract.address + ' transactionHash: ' + contract.transactionHash);
                var contract_address = contract.address;
		        console.log('Address on JS before: ' + contract.address);
			    var owner_address = ticketOwner;
			    var ticket_id = ticketId;
			    jQuery.ajax({
	              type: "POST",
	              url: "load_contract.php",
	              data: {contract_address : contract_address, owner_address : owner_address, ticket_id : ticket_id, pin : pin},
	              success: function(responce) {
	                alert("Ваш билет хранится на SaveTickets в закрытом виде. Вы можете открыть билет и воспользоваться им или делегировать право пойти на мероприятие другому пользователю системы!")
	              }
	            });					
            }
		  
        })
}

function openContract(address){
    let contract = createContract();
    let contractInstance = contract.at(address);

    return contractInstance;
}


function changeFunc() {
    var selectBox = document.getElementById("selectProduct");
    selectedValue = selectBox.options[selectBox.selectedIndex].value;
    /*return selectedValue;*/
}

function getStates(contract, address) {
	console.log(address); 
    console.log(contract);
	step = 1; 
	console.log(step++); 
	contract.Is_active({ from: window.web3.eth.accounts[0] },function (e,r) { 
	  console.log(step++); 
	  console.log(r);
	  console.log(address+'---open'); 
	  if (r) {
		document.getElementById(address+'---open').style.display = 'block';
        document.getElementById(address+'---close').style.display = 'none';
	  } else {
		document.getElementById(address+'---open').style.display = 'none';
        document.getElementById(address+'---close').style.display = 'block';

	  }	
	})
	console.log(step++); 
	
}


function open_ticket(contract, address) {
    console.log(contract);
	contract.Open({ from: window.web3.eth.accounts[0] },function (e,r) { 
	  console.log(r);
	  
	})
}

function delegate (contract, address) {
	let addr_to = '0xf6f45356002Eee48a0333c480da441dAdFcE1373';
	let person = "Ruslan";
    console.log(contract);
	if (web3.eth.accounts[0]) {
    console.log(web3.eth.accounts[0]);
    } else {
    setTimeout(loop, 100);
    }
	contract.Delegate(addr_to, person, { from: window.web3.eth.accounts[0] }, function (e,r) { 
	 console.log(r);
	 jQuery.ajax({
	  type: "POST",
	  url: "delegate.php",
	  data: {contract_address : address, owner_address : addr_to},
	  success: function(responce) {
	  alert("Билет делегирован!")
	}
	});
	 
	 
    })
	
	
    //contract.State.call({ from: window.web3.eth.accounts[0] },function (e,r) {
        //console.log(r);
        //console.log(r[1]);
        //let index1 = r[1].indexOf(':')
        //let index2= r[1].indexOf(':',index1+1)
        //let container = r[1].substr(index1+1,index2-index1-1);
        //let navi = r[1].substr(index2+1,6);
        //console.log(container);
        //console.log(navi);
        //getNavi(container, navi);
    //})
}

