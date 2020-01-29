<?php include '../view/header.php';


require('../mt_model/mt_database.php');
require('../mt_model/mt_employee_db.php');
require('../mt_model/mt_dept_db.php');



$employees = get_employees();
;?>

<?php $subaction = filter_input(INPUT_POST, 'subaction');

    if ($_SERVER['REQUEST_METHOD']=='POST')   {

    if (isset( $subaction))
    {

        $deptID = filter_input(INPUT_POST, 'deptID', FILTER_VALIDATE_INT);
        $empID = filter_input(INPUT_POST, 'empID', FILTER_VALIDATE_INT);
        $empCode = filter_input(INPUT_POST, 'empCode');
        $empName = filter_input(INPUT_POST, 'empName');
        $empSalary= filter_input(INPUT_POST, 'empSalary', FILTER_VALIDATE_FLOAT);

        add_employees($empID,$deptID,$empCode,$empName,$empSalary);
        echo("<meta http-equiv='refresh' content='0'>");
    }



    } ?>


<?php

$editconf =    filter_input(INPUT_POST, 'editconf');

    if(isset($editconf))
    {
        $edit_empID2= filter_input(INPUT_POST, 'edit_empID2');
        $edit_deptID2= filter_input(INPUT_POST, 'edit_deptID2');
        $edit_empCode2= filter_input(INPUT_POST, 'edit_empCode2');
        $edit_empName2= filter_input(INPUT_POST, 'edit_empName2');
        $edit_empSalary2= filter_input(INPUT_POST, 'edit_empSalary2');
//        //update_employee($edit_deptID2,$edit_empCode2,$edit_empName2, $edit_empSalary2, $edit_empID2);




        update_employee($edit_empID2, $edit_empName2,$edit_empCode2,$edit_empSalary2,$edit_deptID2);

//
//        $edit_empID2= $_POST['edit_empID'];


    } ?>

    <h1>Employees List</h1>

    <form action="index.php" method="post" id="add">
        <input type="hidden" name="action" value="add">
         <input type="submit"  value="ADD EMPLOYEE FORM">
    </form> <br>


<table>
    <tr>
        <th>Employee ID</th>
        <th>Employee Code</th>
        <th>Employee Name</th>
        <th>Employee Salary</th>
        <th>Employee Department</th>
        <th>Press a button below to fire an employee</th>
        <th>Press a button to edit</th>
    </tr>


    <?php foreach ($employees as $emp) : ?>
        <tr>
            <td><?php echo $emp['empID']; ?></td>
            <td><?php echo $emp['empCode']; ?></td>
            <td><?php echo $emp['empName']; ?></td>
            <td><?php echo $emp['empSalary']; ?></td>
            <td><?php echo $emp['deptName']; ?></td>
<!--            <td>--><?php //echo $emp['']; ?><!--</td>-->

            <td><form action="." method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="empID" value="<?php echo $emp['empID']; ?>">
                    <input type="submit" value="Press to fire employee">
                </form></td>
            <td><form action="." method="post">
                    <input type="hidden" name="edit" value="yes">
                    <input type="hidden" name="edit_empID" value="<?php echo $emp['empID']; ?>">
                    <input type="hidden" name="edit_empCode" value="<?php echo $emp['empCode']; ?>">
                    <input type="hidden" name="edit_empName" value="<?php echo $emp['empName']; ?>">
                    <input type="hidden" name="edit_empSalary" value="<?php echo $emp['empSalary'];?>">
                    <input type="hidden" name="edit_deptID" value="<?php echo $emp['deptID']; ?>">
                    <input type="submit" value="Edit">

                </form></td>
        </tr>

    <?php endforeach; ?>
</table>




<?php $action = filter_input(INPUT_POST, 'action');
$empID = filter_input(INPUT_POST, 'empID', FILTER_VALIDATE_INT);
// Following are the variables required for edit
$edit = filter_input(INPUT_POST, 'edit');
$edit_empId = filter_input(INPUT_POST, 'edit_empID');
$edit_deptID= filter_input(INPUT_POST, 'edit_deptID');
$edit_empCode= filter_input(INPUT_POST, 'edit_empCode');
$edit_empName=filter_input(INPUT_POST, 'edit_empName');
$edit_empSalary = filter_input(INPUT_POST, 'edit_empSalary');

   if($action == "delete") {
       if ($empID == NULL || $empID == FALSE) {
           $error = "Missing or incorrect product id or category id.";

       }
       else {

           delete_employee($empID);
           echo("<meta http-equiv='refresh' content='0'>");
           //REFRESH DONE THROUGH HTML META TAGS SAVES LOAD ON OUR SERVER, AS THE REFRESH IS DONE IN CLIENT SERVER




       }



   }
   else if ($action == "add")
   {
       $department1 = get_departments(); ?>
        <h2>ADD EMPLOYEE - FORM</h2>
        <form action="index.php" method="post" id="add_product_form" name="add">


            <label>Dept ID:</label>
            <select name="deptID">
                <?php foreach ( $department1 as $a ) : ?>
                    <option value="<?php echo $a['deptID']; ?>">
                        <?php echo $a['deptID']; ?>
                    </option>
                <?php endforeach; ?>
            </select>
            <span id="span_add_deptID"></span>
            <span id="span2_add_deptID"></span>
            <br><br>


            <label>Employee ID:</label>
            <input type="text" name="empID" />
            <span id="span_add_empID"></span>
            <span id="span2_add_empID"></span>
            <br><br>

            <label>Employee Name:</label>
            <input type="text" name="empName" />
            <span id="span_add_empName"></span>
            <span id="span2_add_empName"></span>
            <br><br>

            <label>Employee Code:</label>
            <input type="text" name="empCode" />
            <span id="span_add_empCode"></span>
            <span id="span2_add_empCode"></span>
            <br><br>

            <label>Employee Salary:</label>
            <input type="text" name="empSalary" />
            <span id="span_add_empSalary"></span>
            <span id="span2_add_empSalary"></span>
            <br><br>



                <input type="hidden" name="subaction" value="add">
                <input type="submit" name="subaction" value="ADD EMPLOYEE" id="btnAddEmployee">


<script>

    //Java script hint For dept ID (drop down box) of Add form
            document.add.deptID.onclick=function () { //Identified the form using names
            document.getElementById('span_add_deptID').innerHTML = "Please select one existing Department ID"; // identified using ids
            }

            document.add.deptID.onblur=function () {
            document.getElementById('span_add_deptID').innerHTML = "";
            }

   //Java script hint and data validation for emp id in add form

            document.add.empID.onclick=function () {
                document.getElementById('span_add_empID').innerHTML = "Please choose a employee id";
            }

            document.add.empID.onblur=function () {
                document.getElementById('span_add_empID').innerHTML = "";
            }


        var ValidationFieldA2 = document.add.empID;
        var ErrorLocA2 = document.getElementById('span2_add_empID');

    ValidationFieldA2.onchange = function() {
        var Pattern = new RegExp("[^0-9]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
        var dataValidity = this.value.search(Pattern) < 0;

        if (dataValidity) {
            ErrorLocA2.innerHTML = "";
            document.getElementById('btnAddEmployee').style.visibility = 'visible';

        } else {
            ErrorLocA2.innerHTML = "[Please check your input. It must be Numbers only]";
            document.getElementById('btnAddEmployee').style.visibility = 'hidden';//Add Button is hidden if input is invalid
        }
    }

    //Java script hint and data validation for empName in add form


    document.add.empName.onclick=function () {
        document.getElementById('btnAddEmployee').innerHTML = "Please choose a employee name";
    }

    document.add.empName.onblur=function () {
        document.getElementById('btnAddEmployee').innerHTML = "";
    }


    var ValidationFieldA3 = document.add.empName;
    var ErrorLocA3 = document.getElementById('span2_add_empName');

    ValidationFieldA3.onchange = function() {
        var Pattern = new RegExp("[^a-zA-Z ]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
        var dataValidity = this.value.search(Pattern) < 0;

        if (dataValidity) {
            ErrorLocA3.innerHTML = "";
            document.getElementById('btnAddEmployee').style.visibility = 'visible';

        } else {
            ErrorLocA3.innerHTML = "[Please check your input. It must be Numbers only]";
            document.getElementById('btnAddEmployee').style.visibility = 'hidden';//Add Button is hidden if input is invalid
        }
    }


    //Java script hint and data validation for empCode in Add form


    document.add.empCode.onclick=function () {
        document.getElementById('span_add_empCode').innerHTML = "Please select a employee code";
    }

    document.add.empCode.onblur=function () {
        document.getElementById('span_add_empCode').innerHTML = "";
    }


    var ValidationFieldA4 = document.add.empCode;
    var ErrorLocA4 = document.getElementById('span2_add_empCode');

    ValidationFieldA4.onchange = function() {
        var Pattern = new RegExp("[^a-zA-Z ]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
        var dataValidity = this.value.search(Pattern) < 0;

        if (dataValidity) {
            ErrorLocA4.innerHTML = "";
            document.getElementById('btnAddEmployee').style.visibility = 'visible';

        } else {
            ErrorLocA4.innerHTML = "[Please check your input. It must be only letters]";
            document.getElementById('btnAddEmployee').style.visibility = 'hidden';//Add Button is hidden if input is invalid
        }
    }



    //Java script hint and data validation for empSalary in Add form


    document.add.empSalary.onclick=function () {
        document.getElementById('span_add_empSalary').innerHTML = "Please input salary";
    }

    document.add.empSalary.onblur=function () {
        document.getElementById('span_add_empSalary').innerHTML = "";
    }


    var ValidationFieldA5 = document.add.empSalary;
    var ErrorLocA5 = document.getElementById('span2_add_empSalary');

    ValidationFieldA5.onchange = function() {
        var Pattern = new RegExp("[^0-9]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
        var dataValidity = this.value.search(Pattern) < 0;

        if (dataValidity) {
            ErrorLocA5.innerHTML = "";
            document.getElementById('btnAddEmployee').style.visibility = 'visible';

        } else {
            ErrorLocA5.innerHTML = "[Please check your input. It must be only number]";
            document.getElementById('btnAddEmployee').style.visibility = 'hidden';//Add Button is hidden if input is invalid
        }
    }



</script>
            </form>
            <br>

       <?php
   }

   else if(isset($edit))

   {
        ?>
       <h2>EDIT EMPLOYEE - FORM </h2>

       <form action="index.php" method="post" id="edit_employee_form" name="edit_employee_form">


           <label>Department ID:</label>
           <input type="text" value="<?php echo $edit_deptID;?>" name="edit_deptID2" />
           <span id="Span_edit_deptID2"></span>
           <span id="Span2_edit_deptID2"></span>
           <br><br>



           <label>Employee ID:</label>
           <input type="text" value="<?php echo $edit_empId;?>" name="edit_empID2" TITLE="Employee ID is Read only. Edit is not possible." readonly/>
           <span id="Span_edit_empID2"></span>
           <span id="Span2_edit_empID2"></span>
           <br><br>

           <label>Employee Name:</label>
           <input type="text" value="<?php echo $edit_empName;?>" name="edit_empName2" id="edit_empName2" />
           <span id="Span_edit_empName2"></span>
           <span id="Span2_edit_empName2"></span>


           <br><br>

           <label>Employee Code:</label>
           <input type="text" value="<?php echo $edit_empCode;?>" name="edit_empCode2"/>
           <span id="Span_edit_empCode2"></span>
           <span id="Span2_edit_empCode2"></span>
           <br><br>

           <label>Employee Salary:</label>
           <input type="text" value="<?php echo $edit_empSalary; ?>"name="edit_empSalary2" />
           <span id="Span_edit_empSalary2"></span>
           <span id="Span2_edit_empSalary2"></span>
           <br><br>

        <input type="hidden" name="editconf" value="yes">
            <input type="submit" value="UPDATE" name="btnEditEmployeeConfirm" id="btnEditEmployeeConfirm"> <br>

           <script>

               //Data validation the input of edit form
               //Data validation -Dept ID text box of edit form
           document.edit_employee_form.edit_deptID2.onclick=function () {
                document.getElementById('Span_edit_deptID2').innerHTML = "Please enter one existing Department ID";
            }

               document.edit_employee_form.edit_deptID2.onblur=function () {
                   document.getElementById('Span_edit_deptID2').innerHTML = "";
               }

               var ValidationField1 = document.edit_employee_form.edit_deptID2;
               var ErrorLoc1 = document.getElementById('Span2_edit_deptID2');

               ValidationField1.onchange = function() {
                   var Pattern = new RegExp("[^0-9]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
                   var dataValidity = this.value.search(Pattern) < 0;

                   if (dataValidity) {
                       ErrorLoc1.innerHTML = "";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'visible';

                   } else {
                       ErrorLoc1.innerHTML = "[Please check your input. It must be Numbers only]";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'hidden';//Add Button is hidden if input is invalid
                   }

               }




               //Data validation -Emp ID text box of edit form
           document.edit_employee_form.edit_empID2.onclick=function () {
               document.getElementById('Span_edit_empID2').innerHTML = "Employee ID is Read only. Edit is not possible.";
           }

           document.edit_employee_form.edit_empID2.onblur=function () {
               document.getElementById('Span_edit_empID2').innerHTML = "";
           }
               //emp id is ready only


               //Data validation -Emp Name text box of edit form

           document.edit_employee_form.edit_empName2.onclick=function () {
               document.getElementById('Span_edit_empName2').innerHTML = "Please enter employee name";
           }

           document.edit_employee_form.edit_empName2.onblur=function () {
               document.getElementById('Span_edit_empName2').innerHTML = "";
           }

               var ValidationField2 = document.edit_employee_form.edit_empName2;
               var ErrorMsgLocation2 = document.getElementById('Span2_edit_empName2');

               ValidationField2.onchange = function()
               {
                   var Pattern = new RegExp("[^a-zA-Z ]", "i");
                   var dataValidity = this.value.search(Pattern) < 0;

                   if (dataValidity) {
                       ErrorMsgLocation2.innerHTML = "";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'visible';

                   }
                   else
                   {
                       ErrorMsgLocation2.innerHTML = "[Please check your input. It must be only letters]";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'hidden'; //Add Button is hidden if input is invalid
                   }
               }


               //Data validation -Emp Code text box of edit form

           document.edit_employee_form.edit_empCode2.onclick=function () {
               document.getElementById('Span_edit_empCode2').innerHTML = "Please enter employee code";
           }

           document.edit_employee_form.edit_empCode2.onblur=function () {
               document.getElementById('Span_edit_empCode2').innerHTML = "";
           }

               var ValidationField3 = document.edit_employee_form.edit_empCode2;
               var ErrorLoc3 = document.getElementById('Span2_edit_empCode2');

               ValidationField3.onchange = function() {
                   var Pattern = new RegExp("[^a-zA-Z ]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
                   var dataValidity = this.value.search(Pattern) < 0;

                   if (dataValidity) {
                       ErrorLoc3.innerHTML = "";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'visible';

                   } else {
                       ErrorLoc3.innerHTML = "[Please check your input. It must be only letters]";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'hidden';//Add Button is hidden if input is invalid
                   }

               }






           document.edit_employee_form.edit_empSalary2.onclick=function () {
               document.getElementById('Span_edit_empSalary2').innerHTML = "Please enter the Salary";
           }

           document.edit_employee_form.edit_empSalary2.onblur=function () {
               document.getElementById('Span_edit_empSalary2').innerHTML = "";
           }

               var ValidationField4 = document.edit_employee_form.edit_empSalary2;
               var ErrorLoc4 = document.getElementById('Span2_edit_empSalary2');

               ValidationField4.onchange = function() {
                   var Pattern = new RegExp("[^0-9]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
                   var dataValidity = this.value.search(Pattern) < 0;

                   if (dataValidity) {
                       ErrorLoc4.innerHTML = "";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'visible';

                   } else {
                       ErrorLoc4.innerHTML = "[Please check your input. It must be Numbers only]";
                       document.getElementById('btnEditEmployeeConfirm').style.visibility = 'hidden';//Add Button is hidden if input is invalid
                   }

               }









           </script>



         <?php  $editconf= filter_input(INPUT_POST, 'editconf');

         ?>

         </form>

       <p> NOTE:'Employee ID' is 'read only'. Editting  is not possible.How ever, You can delete the record </p>



  <?php }?>



            














<?php include '../view/footer.php'; ?>