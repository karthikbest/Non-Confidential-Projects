<?php


class databaseConnection
{

    //dependency injection  through constructor of the class
    public  function __construct($dsn, $username, $password) //dsn, username & password are injected into constructor.
        //We can establish multiple connections with Same database (or) different databases using same constructor.
    {
        global  $db;
        $db = new PDO($dsn, $username, $password);
    }
}

$databaseconnection = new databaseConnection('mysql:host=localhost;dbname=company','mgs_user', 'pwd');



?>