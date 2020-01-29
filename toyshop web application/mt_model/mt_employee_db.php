<?php

function get_employees()
{
    try {
        global $db;
        $query = 'SELECT E.empID, E.empCode, E.empName, E.empSalary, D.deptName, E.deptID FROM emps AS E JOIN depts AS D ON  E.deptID = D.deptID;';
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

function add_employees($empID,$deptID, $empCode, $empName,$empSalary)
{
    try {
        global $db;
        $query = 'INSERT INTO emps VALUES(:a,:b,:c, :d, :e)';
        $statement = $db->prepare($query);
        $statement->bindValue(':a', $empID);
        $statement->bindValue(':b', $deptID);
        $statement->bindValue(':c', $empCode);
        $statement->bindValue(':d', $empName);
        $statement->bindValue(':e', $empSalary);
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

function delete_employee($empID) {
    try
    {
        global $db;
        $query = 'DELETE FROM emps
              WHERE empID = :emp_id limit 1';
        //EXTRA DATA PROTECTION ADDED THROUGH LIMIT IN SQL QUERY. CANNOT DELETE MORE THAN ONE ROW AT ANY COST
        $statement = $db->prepare($query);
        $statement->bindValue(':emp_id', $empID);
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


function update_employee($id,$name,$code,$salary,$department_id)
{
    try {
        global $db;
        $query = 'update emps set empID=:id,deptID=:department_id,empName=:name,empCode=:code,empSalary=:salary where empID=:id';
        $statement = $db->prepare($query);
        $statement->bindValue(':id', $id);
        $statement->bindValue(':department_id', $department_id);
        $statement->bindValue(':name', $name);
        $statement->bindValue(':code', $code);
        $statement->bindValue(':salary', $salary);
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