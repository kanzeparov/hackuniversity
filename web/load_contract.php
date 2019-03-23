<?php 
  include_once 'db_conn.php';
  $pin = $_POST['pin'];
  $contract_address = $_POST['contract_address'];
  $owner_address = $_POST['owner_address'];
  $ticket_id = $_POST['ticket_id'];
  
  $database = new Database();
  $db = $database->getConnection();
  
  $query = "insert into access (eth_address, smartcontract, pin, ticket_id) 
                 values ('$owner_address', '$contract_address', '$pin', '$ticket_id')";
  $stmt = $db->prepare($query);
  
  if($stmt->execute()){
        return true;
    }
    
?>