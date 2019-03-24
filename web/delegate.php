<?php 
  include_once 'db_conn.php';
  
  $contract_address = $_POST['contract_address'];
  $owner_address = $_POST['owner_address'];

  
  $database = new Database();
  $db = $database->getConnection();
  
  //$query = "select pin from access where eth_address = ".$owner_address;
  
  //foreach ($db->query($query) as $row) {
  //  $pin = $row['pin'];
  //}

  $query = "update access set pin = '0000' where pin = '1242' and smartcontract='".$contract_address."'";
  $stmt = $db->prepare($query);
  if($stmt->execute()){
        return true;
  }
    
?>