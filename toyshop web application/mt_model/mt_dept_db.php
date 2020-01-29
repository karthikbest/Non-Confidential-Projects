<?php
function get_departments()
{ try {
    global $db;
    $query = 'SELECT deptID, deptName FROM depts';
    $statement = $db->prepare($query);
    $statement->execute();
    return $statement;
}
catch (Exception $e)
{

    errorMsgForClient();
//THE FOLLOWING FUNCTION  IS FOR DEBUGGING ONLY. IT IS NOT TO BE SENT TO CLIENT. HENCE, IT IS COMMENTED
//       errorMsgForDebugging($e);

}
}


function add_departments($deptID, $depName)
{
    try {
        global $db;
        echo "add function active";


        $query = 'INSERT INTO depts VALUES(:a, :b)';

        $statement = $db->prepare($query);
        $statement->bindValue(':a', $deptID);
        $statement->bindValue(':b', $depName);
        $statement->execute();
        $statement->closeCursor();
    }
    catch (Exception $e)
    {

        errorMsgForClient();
//THE FOLLOWING FUNCTION  IS FOR DEBUGGING ONLY. IT IS NOT TO BE SENT TO CLIENT. HENCE, IT IS COMMENTED
//       errorMsgForDebugging($e);

    }

}


function delete_departments($deptID)
{
    try {
        global $db;

        $query = 'DELETE FROM depts
              WHERE deptID = :a';
        //EXTRA DATA PROTECTION ADDED THROUGH LIMIT IN SQL QUERY. CANNOT DELETE MORE THAN ONE ROW AT ANY COST
        $statement = $db->prepare($query);
        $statement->bindValue(':a', $deptID);
        $statement->execute();
        $statement->closeCursor();
        echo("<meta http-equiv='refresh' content='0'>");
    }
    catch (Exception $e)
    {

        errorMsgForClient();
//THE FOLLOWING FUNCTION  IS FOR DEBUGGING ONLY. IT IS NOT TO BE SENT TO CLIENT. HENCE, IT IS COMMENTED
//       errorMsgForDebugging($e);

    }
}

function update_department($id,$name)
{
    try {
        global $db;
        echo $id;
        echo $name;
        $query = 'update depts set deptName= :b where deptID=:a';
        $statement = $db->prepare($query);
        $statement->bindValue(':b', $name);
        $statement->bindValue(':a', $id);

        $statement->execute();
        $statement->closeCursor();
        echo("<meta http-equiv='refresh' content='0'>");
    }
    catch (Exception $e)
    {

        errorMsgForClient();
//THE FOLLOWING FUNCTION  IS FOR DEBUGGING ONLY. IT IS NOT TO BE SENT TO CLIENT. HENCE, IT IS COMMENTED
//       errorMsgForDebugging($e);

    }
}



?>



