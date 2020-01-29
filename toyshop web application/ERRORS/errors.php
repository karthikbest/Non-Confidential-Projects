<?php
function errorMsgForClient()
{ // REMOVES ALL CONFIDENTIAL INFORMATION ( BOTH FOR SAFETY & ABSTRACTION)
    // TELLS THE CUSTOMER ONLY WHAT HE NEEDS TO KNOW. NOT MORE. ( BOTH FOR SAFETY & ABSTRACTION)

    echo ("The website is facing difficulty due to some technical issues. Our Engineers are working on it. Please come back later");

}

function errorMsgForDebugging(Exception $e)
    // THIS FUNCTION IS FOR DEBUGGING ONLY AND MUST NOT BE SHOWN TO CLIENT - TO PREVENT CONFIDENTIAL INFO LEAKING
{
    echo 'Caught exception: ',  $e->getMessage(), "\n";

}