<div class="quarter-width small-padding-left">
    <form action="#" id="userForm">
        <div class="form-group">
            <label for="userName">User name:</label>
            <input type="text" class="form-control" id="userName">
        </div>
        <div class="form-group">
            <label for="fullName">Full name:</label>
            <input type="text" class="form-control" id="fullName">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="text" class="form-control" id="password">
        </div>
        <div class="form-group">
            <label for="lastUpdate">Last update:</label>
            <input type="text" class="form-control" id="lastUpdate" readonly>
        </div>
        <button type="reset" class="btn btn-default reset" value="Reset">Reset</button>
        <button type="submit" class="btn btn-default">Create/update</button>
    </form>
    <br>
    <div class="alert alert-warning alert-info-medium" id="errors">
        <strong>Warning!</strong> Some of the fields are not compliant with the specifications, check them!
    </div>
    <div class="alert alert-success alert-info-medium" id="success">
        <strong>Success!</strong> User updated on the Data Base.
    </div>
</div>