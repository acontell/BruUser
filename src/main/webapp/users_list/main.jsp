<div class="small-padding-left">
    <table class="table">
        <thead>
            <tr>
                <th>User name</th>
                <th>Full name</th>
                <th>LastUpdate</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="mainTable"></tbody>
    </table>
</div>
<script id="userRowTemplate" type="text/template">
    <tr>
        <td>{{ userName }}</td>
        <td>{{ fullName }}</td>
        <td>{{ lastUpdate }}</td>
        <td>
            <a class="edit" data-username="{{ userName }}" href="#"><span class="glyphicon glyphicon-pencil"></span></a>
            &nbsp; 
            <a class="remove" data-username="{{ userName }}" href="#"><span class="glyphicon glyphicon-remove"></span></a>
        </td>
    </tr>
</script>