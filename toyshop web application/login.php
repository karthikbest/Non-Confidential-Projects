
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
	<title>Company</title>
	<link rel="stylesheet" href="normalize.css" />
	<link rel="stylesheet" href="mystyle.css" />

<!--[if lt IE 8]>
	<style>
legend {
    display: block;
    padding: 0;
    padding-top: 30px;
			font-weight: bold;
			font-size: 1.25em;
			color: #FFD98D;
			margin: 0 auto;
		}
	</style>
<![endif]-->

</head>
<body>
<form id="myform" name="theform" class="group" action="index.php" method="POST">
	<fieldset id="login" title="Login Info">
		<legend>Login Information</legend>

		<!-- THERE IS AN ADDITIONAL SPAN HERE:-->

		<span id="mynamehint" class="hint"></span>

		<ol>
			<li>
				<label for="myname">Name *</label>
				<input type="text" name="myname" id="myname" title="Please enter your name" pattern="[A-Za-z ]+, [A-Za-z ]+" autofocus required placeholder="Last, First" />
			</li>
			<li>
				<label for="myemail">Email *</label>
				<input type="email" name="myemail" required id="myemail" autocomplete="off" />
			</li>
			<li>
				<label for="mypassword">Password</label>
				<input type="password" name="mypassword" id="mypassword" />
			</li>
		</ol>
	</fieldset>
	<fieldset id="other" class="hidden" title="Other Info">
		<legend>Other</legend>
		<ol>
			<li>
				<label for="myurl">Website</label>
				<input type="url" name="myurl" id="myurl" />
			</li>
			<li>
				<label for="mytelephone">Telephone</label>
				<input type="tel" name="mytelephone" id="mytelephone" pattern="\d{3}[\-]\d{3}[\-]\d{4}" placeholder="xxx-xxx-xxxx"/>
			</li>
			<li class="singleline">
				<input type="checkbox" id="subscribeitem" name="subscribe" CHECKED value="yes" />
				<label for="subscribeitem">Subscribe to our mailing list?</label>
			</li>
			<li>
				<label for="reference">How did you hear about us?</label>
				<select name="reference" id="reference">
					<option>Choose...</option>
					<option value="friend">A friend</option>
					<option value="facebook">Facebook</option>
					<option value="twitter">Twitter</option>
				</select>
			</li>
		</ol>
	</fieldset>
	<fieldset id="comments"  class="hidden" title="Comments">
	<legend>Comments</legend>
		<ol>
			<li>
				<div class="grouphead">Request Type</div>
				<ol>
					<li>
						<input type="radio" name="requesttype" value="question" id="questionitem" />
						<label for="questionitem">Question</label>
					</li>
					<li>
						<input type="radio" name="requesttype" value="comment" id="commentitem" />
						<label for="commentitem">Comment</label>
					</li>
				</ol>
			</li>
			<li>
				<label for="mycomments">Comment</label>
				<textarea name="mycomments" id="mycomments"></textarea>
			</li>
		</ol>
		<button type="submit">send</button>
	</fieldset>
</form>

<!-- ADDING A SIMPLE JAVASCRIPT:-->
<!--PLEASE PAY A CLOSE ATTENTION TO THIS PART OF THE CODE!-->

<script>
document.theform.myname.onclick=function() {
    document.getElementById('mynamehint').innerHTML = "(Enter last name followed by a comma & space and then first name)";
}

	document.theform.myname.onblur=function() {
        document.getElementById('mynamehint').innerHTML = "";
    }
</script>

</body>
</html>