<?php
/**
 * Created by PhpStorm.
 * User: bladzher
 * Date: 23.03.19
 * Time: 17:09
 */

// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

// include database and object files
include_once 'db_conn.php';


$database = new Database();
$db = $database->getConnection();

$request_id = isset($_GET['id']) ? $_GET['id'] : die();


$query = "SELECT eth_address, smartcontract FROM ouchsu00_savetickets.access WHERE pin=".$request_id;

$result = array();
$id = 0;

foreach ($db->query($query) as $row) {
    $result[] = array (
        "id" => $id,
        "eth_address" => $row['eth_address'],
        "smartcontract" => $row['smartcontract'],

    );
    $id = $id + 1;

}



echo json_encode($result);


$database->closeConnection();