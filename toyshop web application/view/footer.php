<footer>
    <br>
    <button onclick="goBack()">Go Back</button>

    <script>
        function goBack() {
            window.history.back();
        }
    </script>

    <input type="button" onclick="location.href='/5daCompanyDatabaseandDependencyInjection_Final';" value="Go to Home" />

    <input type="button" onclick="location.href='/5daCompanyDatabaseandDependencyInjection_Final/login.php';" value="Logout" />

    <br>


    <p class="copyright">
        &copy; <?php echo date("Y"); ?> Midterm Prog8020 by Karthik Appaswamy
    </p>
</footer>
</body>
</html>