<?php include '../view/header.php';

require('../mt_model/mt_database.php');
require('../mt_model/mt_dept_db.php');

global $departments;
$departments = get_departments();

 ?>


<?php $add_action2 = filter_input(INPUT_POST, 'add_action2');
echo  $add_action2;

    if ($_SERVER['REQUEST_METHOD']=='POST')
    {

    if (isset( $add_action2))
    {


        $deptID = filter_input(INPUT_POST, 'deptID');
        $deptName= filter_input(INPUT_POST, 'deptName');


        add_departments($deptID,$deptName);
        echo("<meta http-equiv='refresh' content='0'>");
    } }

    $edit_deptID2= filter_input(INPUT_POST, 'edit_deptID2');
    $edit_deptName2= filter_input(INPUT_POST, 'edit_deptName2');
    $editconf= filter_input(INPUT_POST, 'editconf');
    if(isset($editconf))
    {
        update_department($edit_deptID2,$edit_deptName2);
    }
    ?>


<h1>Departments List</h1>
<form action="index.php" method="post" id="add">
    <input type="hidden" name="add_action" value="add">
    <input type="submit" value="ADD DEPARTMENT FORM">
   <?php $add_action = filter_input(INPUT_POST, 'add_action'); ?>
</form>
<br>
<table>
            <tr>
                <th>Department ID</th>
                <th>Department Name</th>
                <th>Delete Department</th>
                <th>Edit Department</th>


            </tr>


    <?php foreach ($departments as $dep) : ?>
            <tr>
                <td><?php echo $dep['deptID']; ?></td>
                <td><?php echo $dep['deptName']; ?></td>
                <td><form action="." method="post">
                        <input type="hidden" name="delete_action" value="delete">
                        <input type="hidden" name="delete_deptID" value="<?php echo $dep['deptID']; ?>">
                        <input type="submit" value="DELETE">
                    </form></td>
                <td><form action="." method="post">
                        <input type="hidden" name="edit" value="yes">
                        <input type="hidden" name="edit_deptID" value="<?php echo $dep['deptID']; ?>">
                        <input type="hidden" name="edit_deptName" value="<?php echo $dep['deptName']; ?>">
                        <input type="submit" value="EDIT">

                    </form></td>
            </tr>


    <?php endforeach; ?>
</table>

<p> NOTE: </p>
<ul>
    <li> It is not possible to directly delete a department which has  employees. </li>
    <li>If you wish to delete such departments, You have to delete those employees (or) transfer them to some other department.
        To do these changes: <a href="../mt_employees/index.php"> Click here to go to Employees page </a></li>

</ul>


<?php $action = filter_input(INPUT_POST, 'action');
$deptID = filter_input(INPUT_POST, 'deptID');
// Following are the variables required for edit
$edit = filter_input(INPUT_POST, 'edit');
$edit_deptID = filter_input(INPUT_POST, 'edit_deptID');
$delete_action= filter_input(INPUT_POST, 'delete_action');
$delete_deptID= filter_input(INPUT_POST, 'delete_deptID');
$edit_deptName= filter_input(INPUT_POST, 'edit_deptName');


   if($delete_action == "delete")
   {


       if ($delete_deptID == NULL || $delete_deptID == FALSE)
       {

           $error = "Missing or incorrect product id or category id.";

       }
       else
           {

           delete_departments($delete_deptID);
//           echo("<meta http-equiv='refresh' content='0'>");
           //REFRESH DONE THROUGH HTML META TAGS SAVES LOAD ON OUR SERVER, AS THE REFRESH IS DONE IN CLIENT SERVER


       }
   }
   else  if($add_action=="add")
   {

       ?>
       <h2>ADD DEPARTMENTS FORM: </h2>
       <form action="index.php" method="post" id="add_product_form" name="adddeptform">

           <label>Department ID:</label>
           <input type="text" name="deptID" />
           <span id="deptidhint" class="hint"></span>
           <span id="deptiderror" class="hint"></span>

           <br>

           <label>Department Name:</label>
           <input type="text" name="deptName" />
           <span id="deptnamehint" class="hint"></span>
           <span id="deptnameerror" class="hint"></span>

           <br>

           <input type="hidden" name="add_action2" value="yes">
           <input type="submit" value="ADD DEPARTMENT" id="adddeptconfirm">
           <?php $add_action2 = filter_input(INPUT_POST, 'add_action2');
           echo $add_action2; ?>
       </form> <br>
       <script>
           //JavaScript validations for department name.
           document.adddeptform.deptName.onclick=function() {
               document.getElementById('deptnamehint').innerHTML = "Please enter the department name";
           }

           document.adddeptform.deptName.onblur=function() {
               document.getElementById('deptnamehint').innerHTML = "";
           }


           var F = document.adddeptform.deptName;
           var Error_ID = document.getElementById('deptnameerror');

           F.onchange = function() {
               var Pattern = new RegExp("[^a-zA-Z ]", "i");
               var dataValidity = this.value.search(Pattern) < 0;

               if (dataValidity) {
                   Error_ID.innerHTML = "";
                   document.getElementById('adddeptconfirm').style.visibility = 'visible';

               } else {
                   Error_ID.innerHTML = "[Please check your input. It must be letter only]";
                   document.getElementById('adddeptconfirm').style.visibility = 'hidden'; //Add Button is hidden if input is invalid
               }

           }

           //JavaScript validations for department id.
           document.adddeptform.deptID.onclick=function() {
               document.getElementById('deptidhint').innerHTML = "Please enter the department id";
           }

           document.adddeptform.deptID.onblur=function() {
               document.getElementById('deptidhint').innerHTML = "";
           }

           var F = document.adddeptform.deptID;
           var E = document.getElementById('deptiderror');

          F.onchange = function() {
               var Pattern = new RegExp("[^0-9]", "i"); //PATTERN OF CHARACTERS THAT ARE TO BE AVOIDED
               var dataValidity = this.value.search(Pattern) < 0;

               if (dataValidity) {
                   E.innerHTML = "";
                   document.getElementById('adddeptconfirm').style.visibility = 'visible';

               } else {
                   E.innerHTML = "[Please check your input. It must be Numbers only]";
                   document.getElementById('adddeptconfirm').style.visibility = 'hidden';//Add Button is hidden if input is invalid
               }

           }


       </script>


   <?php }

   else if ($edit=="yes")
   {


        ?>
<h2>EDIT DEPARTMENT FORM</h2>



        <form action="index.php" method="post" name="edit">


        <label>Department ID:</label>
        <input type="text" value="<?php echo $edit_deptID;?>" name="edit_deptID2" TITLE="Department ID is Read only. Edit is not possible." readonly/>
        <span id="span_deptid"></span>
            <span id="span2_deptid"></span>
            <br><br>



        <label>Department Name:</label>
        <input type="text" value="<?php echo $edit_deptName?>" name="edit_deptName2" />
            <span id="span_deptname"></span>
            <span id="span2_deptname"></span>
        <br>


        <input type="hidden" name="editconf" value="yes">
        <input type="submit" value="UPDATE" name="btn_edit" id="btn_edit">

            <?php $edit_deptID2= filter_input(INPUT_POST, 'edit_deptID2'); ?>
            <?php $edit_deptName2= filter_input(INPUT_POST, 'edit_deptName2'); ?>
            <?php $editconf= filter_input(INPUT_POST, 'editconf'); ?>

<script>
//Javascript hint for dept id in edit form
            document.edit.edit_deptID2.onclick=function() {
            document.getElementById('span_deptid').innerHTML = "Department ID is read only. You cannot edit it";
            }

            document.edit.edit_deptID2.onblur=function() {
            document.getElementById('span_deptid').innerHTML = "";
            }


//Javascript hint & datavalidation for dept name in edit form

            document.edit.edit_deptName2.onclick=function() {
                document.getElementById('span_deptname').innerHTML = "Please enter the department name";

            }

            document.edit.edit_deptName2.onblur=function() {
                document.getElementById('span_deptname').innerHTML = "";

            }


            var F2 = document.edit.edit_deptName2;
            var Error_ID2= document.getElementById('span2_deptname');

            F2.onchange = function()
            {
                var Pattern = new RegExp("[^a-zA-Z ]", "i");
                var dataValidity = this.value.search(Pattern) < 0;

                if (dataValidity) {
                    Error_ID2.innerHTML = "";
                    document.getElementById('btn_edit').style.visibility = 'visible';

                } else {
                    Error_ID2.innerHTML = "[Please check your input. It must be letters only]";
                    document.getElementById('btn_edit').style.visibility = 'hidden'; //Add Button is hidden if input is invalid
                }

            }

</script>


        </form> <br>


    <?php
   }







 include '../view/footer.php'; ?>
