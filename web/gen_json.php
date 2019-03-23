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

$query = "SELECT * FROM ouchsu00_savetickets.access WHERE pin=".$request_id;

foreach ($db->query($query) as $row) {
    $json_data = array (
        "id" => $row['id'],
        "email" => $row['email'],
        "password" => $row['password'],
        "pin" => $row['pin'],
        "eth_address" => $row['eth_address'],

    );



}



echo json_encode($json_data);


$database->closeConnection();