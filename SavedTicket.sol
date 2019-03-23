pragma solidity ^0.4.0;
contract Ticket {
    address owner;
    
    bool ticket_status; //true - open; false - close
    uint256 price; //first price 
    string place;
    string person_info;
    string ticket_id;
    uint time_active;
    string name;
    
    function Ticket (string _name, uint256 _price, address _user, string _person_info, string _place, string _ticket_id) public {
        
        ticket_status=true;
        owner = _user;
        price = _price;
        person_info = _person_info;
        place = _place;
        ticket_id = _ticket_id;
        name = _name;
    }    
    
    function Delegate (address _user, string _person_info) public {
        require(ticket_status=true);
        require(msg.sender==owner);
    
        owner = _user;
        person_info = _person_info;
    }
    
    function Open () public {
        require(ticket_status=true);
        require(msg.sender==owner);
        
        ticket_status = false;
        time_active = now;
    }    
    
    function Is_active() public view returns (bool) {  //true - open; false - close
        return ticket_status;
    }
    
    function When_active() public view returns (uint) {
        return time_active;
    }
    
    function Who_owner() public view returns (address) {
        return owner;
    }
    
    function Person_info() public view returns (string) {
        return person_info;
    }

    function Price() public view returns (uint256) {
        return price;
    }
    
    function Place() public view returns (string) {
        return place;
    }
    
    function Ticket_ID() public view returns (string) {
        return ticket_id;
    }
    
    function Name() public view returns (string) {
        return name;
    }
}
